#define LED_GREEN 5

#define RED_BUTTON 2
#define GREEN_BUTTON 4

int brightness = 0;
const int step = 5;
const int delayTime = 50;

void setup() {
  pinMode(LED_GREEN, OUTPUT);
  pinMode(RED_BUTTON, INPUT_PULLUP);
  pinMode(GREEN_BUTTON, INPUT_PULLUP);

  analogWrite(LED_GREEN, brightness);
}

void loop() {
  bool redPressed = digitalRead(RED_BUTTON) == LOW;
  bool greenPressed = digitalRead(GREEN_BUTTON) == LOW;

  if (greenPressed && brightness < 255) {
    brightness += step;
    if (brightness > 255) brightness = 255;
    analogWrite(LED_GREEN, brightness);
    delay(delayTime);
  }

  if (redPressed && brightness > 0) {
    brightness -= step;
    if (brightness < 0) brightness = 0;
    analogWrite(LED_GREEN, brightness);
    delay(delayTime);
  }
}
