#define LED_RED 6
#define LED_GREEN 5
#define LED_BLUE 3

int redVal = 0;
int greenVal = 0;
int blueVal = 0;

void setup() {
  pinMode(LED_RED, OUTPUT);
  pinMode(LED_GREEN, OUTPUT);
  pinMode(LED_BLUE, OUTPUT);

  analogWrite(LED_RED, 0);
  analogWrite(LED_GREEN, 0);
  analogWrite(LED_BLUE, 0);

  Serial.begin(9600);
  while (!Serial) { }

  Serial.println(F("RGB Serial Controller ready. Type HELP for usage."));
}

bool isNumeric(const String &s) {
  if (s.length() == 0) return false;
  for (unsigned int i = 0; i < s.length(); ++i) {
    char c = s.charAt(i);
    if (c < '0' || c > '9') return false;
  }
  return true;
}

int parseValue(const String &token, bool &ok) {
  ok = false;
  String t = token;
  t.trim();
  if (t.length() == 0) return 0;
  String digits = "";
  for (unsigned int i = 0; i < t.length(); ++i) {
    char c = t.charAt(i);
    if (c >= '0' && c <= '9') digits += c;
  }
  if (!isNumeric(digits)) return 0;
  long v = digits.toInt();
  if (v < 0) v = 0;
  if (v > 255) v = 255;
  ok = true;
  return (int)v;
}

void setChannel(char chan, int value) {
  value = constrain(value, 0, 255);
  if (chan == 'R') {
    redVal = value;
    analogWrite(LED_RED, redVal);
  } else if (chan == 'G') {
    greenVal = value;
    analogWrite(LED_GREEN, greenVal);
  } else if (chan == 'B') {
    blueVal = value;
    analogWrite(LED_BLUE, blueVal);
  }
}

void printStatus() {
  Serial.print("R=");
  Serial.print(redVal);
  Serial.print(" G=");
  Serial.print(greenVal);
  Serial.print(" B=");
  Serial.println(blueVal);
}

void loop() {
  if (Serial.available() > 0) {
    String raw = Serial.readStringUntil('\n');
    raw.trim();
    if (raw.length() == 0) return;

    String u = raw;
    u.toUpperCase();
    u.replace(',', ' ');
    u.trim();

    int sp = u.indexOf(' ');
    String head = (sp == -1) ? u : u.substring(0, sp);
    String rest = (sp == -1) ? "" : u.substring(sp + 1);
    rest.trim();

    if (head == "HELP") {
      Serial.println(F("Commands:"));
      Serial.println(F(" R <0-255>     - set red brightness"));
      Serial.println(F(" G <0-255>     - set green brightness"));
      Serial.println(F(" B <0-255>     - set blue brightness"));
      Serial.println(F(" RGB r g b     - set all three (space or comma separated)"));
      Serial.println(F(" GET           - print current values"));
      Serial.println(F(" OFF           - turn all channels to 0"));
      Serial.println(F(" ON            - set all channels to 255"));
      Serial.println(F(" Examples: R 128 | RGB 255,128,64"));
      return;
    }

    if (head == "OFF") {
      setChannel('R', 0);
      setChannel('G', 0);
      setChannel('B', 0);
      Serial.println(F("All channels OFF."));
      printStatus();
      return;
    }

    if (head == "GET") {
      printStatus();
      return;
    }

    if (head == "ON" || head == "FULL") {
      setChannel('R', 255);
      setChannel('G', 255);
      setChannel('B', 255);
      Serial.println(F("All channels set to FULL (255)."));
      printStatus();
      return;
    }

    if (head == "RGB") {
      int a1 = rest.indexOf(' ');
      if (a1 == -1) { Serial.println(F("ERROR: Expected 3 values: RGB r g b")); return; }
      String s1 = rest.substring(0, a1);
      String tail = rest.substring(a1 + 1);
      tail.trim();
      int a2 = tail.indexOf(' ');
      if (a2 == -1) { Serial.println(F("ERROR: Expected 3 values: RGB r g b")); return; }
      String s2 = tail.substring(0, a2);
      String s3 = tail.substring(a2 + 1);

      bool ok1, ok2, ok3;
      int v1 = parseValue(s1, ok1);
      int v2 = parseValue(s2, ok2);
      int v3 = parseValue(s3, ok3);
      if (!ok1 || !ok2 || !ok3) {
        Serial.println(F("ERROR: RGB needs three numeric values 0-255. Example: RGB 255,128,64"));
        return;
      }
      setChannel('R', v1);
      setChannel('G', v2);
      setChannel('B', v3);
      Serial.println(F("RGB updated."));
      printStatus();
      return;
    }

    if (head == "R" || head == "G" || head == "B") {
      if (rest.length() == 0) {
        Serial.println(F("ERROR: brak wartości. Użyj np. 'R 128'"));
        return;
      }
      bool ok;
      int v = parseValue(rest, ok);
      if (!ok) {
        Serial.println(F("ERROR: wartość niepoprawna. Podaj liczbę 0-255."));
        return;
      }
      setChannel(head.charAt(0), v);
      Serial.print(head);
      Serial.print(" set to ");
      Serial.println(v);
      printStatus();
      return;
    }

    char first = head.charAt(0);
    if (first == 'R' || first == 'G' || first == 'B') {
      String possible = head.substring(1);
      possible.trim();
      if (possible.length() > 0 && (possible.charAt(0) == ':' || possible.charAt(0) == '=')) possible = possible.substring(1);
      bool ok;
      int v = parseValue(possible, ok);
      if (ok) {
        setChannel(first, v);
        Serial.print(first);
        Serial.print(" set to ");
        Serial.println(v);
        printStatus();
        return;
      }
    }

    Serial.print(F("Unknown command: '"));
    Serial.print(raw);
    Serial.println(F("'. Type HELP for list of commands."));
  } 
}
