#define LED_RED     6
#define LED_GREEN   5
#define LED_BLUE    3

#define RED_BUTTON   2   // W(y)łącza diodę
#define GREEN_BUTTON 4   // Zmienia kolor

bool ledOn = false;
int colorIndex = 0;

void showColor(int index) {
  digitalWrite(LED_RED, LOW);
  digitalWrite(LED_GREEN, LOW);
  digitalWrite(LED_BLUE, LOW);

  if (!ledOn) return;

  switch (index) {
    case 0: digitalWrite(LED_RED, HIGH); break;
    case 1: digitalWrite(LED_GREEN, HIGH); break;
    case 2: digitalWrite(LED_BLUE, HIGH); break;
  }
}

void setup() {
  pinMode(LED_RED, OUTPUT);
  pinMode(LED_GREEN, OUTPUT);
  pinMode(LED_BLUE, OUTPUT);

  pinMode(RED_BUTTON, INPUT_PULLUP);
  pinMode(GREEN_BUTTON, INPUT_PULLUP);

  showColor(colorIndex);
}

void loop() {
  static bool lastRedState = HIGH;
  static bool lastGreenState = HIGH;

  bool redState = digitalRead(RED_BUTTON);
  bool greenState = digitalRead(GREEN_BUTTON);

  if (redState == LOW && lastRedState == HIGH) {
    ledOn = !ledOn;
    showColor(colorIndex);
  }
  lastRedState = redState;

  if (greenState == LOW && lastGreenState == HIGH) {  
    colorIndex = ++colorIndex % 3;
    showColor(colorIndex);
  }
  lastGreenState = greenState;

  delay(20);
}
