#define LED_RED   6
#define LED_GREEN 5
#define LED_BLUE  3
#define POTENTIOMETER A0

const int fadeDelay = 5;

int redValue = 255;
int greenValue = 0;
int blueValue = 0;

void setup() {
  pinMode(LED_RED, OUTPUT);
  pinMode(LED_GREEN, OUTPUT);
  pinMode(LED_BLUE, OUTPUT);
  pinMode(POTENTIOMETER, INPUT);
}

void setColor(float brightness) {
  analogWrite(LED_RED,   redValue   * brightness);
  analogWrite(LED_GREEN, greenValue * brightness);
  analogWrite(LED_BLUE,  blueValue  * brightness);
}

// direction = +1 rozjaśnianie, -1 ściemnianie
void fadeChannel(int &channelValue, int direction, float brightness) {
  for (int i = channelValue; i <= 255 && i >= 0; i += direction) {
    int potValue = analogRead(POTENTIOMETER);
    brightness = potValue / 1023.0;
    channelValue = i;
    setColor(brightness);
    delay(fadeDelay);
  }
}

void loop() {
  int potValue = analogRead(POTENTIOMETER);
  float brightness = potValue / 1023.0;

  fadeChannel(greenValue, +1, brightness);

  fadeChannel(redValue, -1, brightness);

  fadeChannel(blueValue, +1, brightness);

  fadeChannel(greenValue, -1, brightness);

  fadeChannel(redValue, +1, brightness);

  fadeChannel(blueValue, -1, brightness);
}
