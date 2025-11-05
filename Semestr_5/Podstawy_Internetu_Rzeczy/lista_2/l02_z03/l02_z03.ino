#define LED_RED   6
#define LED_GREEN 5
#define LED_BLUE  3
#define POTENTIOMETER A0

const int fadeDelay = 10;

void setup() {
  pinMode(LED_RED, OUTPUT);
  pinMode(LED_GREEN, OUTPUT);
  pinMode(LED_BLUE, OUTPUT);
  pinMode(POTENTIOMETER, INPUT);
}

void setColor(int r, int g, int b, float brightness) {
  analogWrite(LED_RED, r * brightness);
  analogWrite(LED_GREEN, g * brightness);
  analogWrite(LED_BLUE, b * brightness);
}

void loop() {
  int potValue = analogRead(POTENTIOMETER);
  float brightness = potValue / 1023.0;
  
  // od czerwonego do zielonego
  for (int i = 0; i <= 255; i++) {
    potValue = analogRead(POTENTIOMETER);
    brightness = potValue / 1023.0; 
    setColor(255 - i, i, 0, brightness);
    delay(fadeDelay);
  }

  // od zielonego do niebieskiego
  for (int i = 0; i <= 255; i++) {
    potValue = analogRead(POTENTIOMETER);
    brightness = potValue / 1023.0; 
    setColor(0, 255 - i, i, brightness);
    delay(fadeDelay);
  }

  // od niebieskiego do czerwonego
  for (int i = 0; i <= 255; i++) {
    potValue = analogRead(POTENTIOMETER);
    brightness = potValue / 1023.0; 
    setColor(i, 0, 255 - i, brightness);
    delay(fadeDelay);
  }
}
