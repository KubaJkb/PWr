#include <LiquidCrystal_I2C.h>

#define LED_RED 6
#define LED_GREEN 5
#define LED_BLUE 3
#define RED_BUTTON 2
#define GREEN_BUTTON 4

LiquidCrystal_I2C lcd(0x27, 16, 2);

unsigned long redInterval = 900;
unsigned long greenInterval = 1000;
unsigned long blueInterval = 1100;

int redState = LOW;
int greenState = LOW;
int blueState = LOW;

unsigned long previousRedTime = 0;
unsigned long previousGreenTime = 0;
unsigned long previousBlueTime = 0;

#define DEBOUNCE_PERIOD 10UL

// aktualnie wybrana dioda (0-Red, 1-Green, 2-Blue)
int selectedLED = 0;

// dostępne czasy migania (od 500ms do 2000ms z krokiem 100ms)
const int availableTimes[] = {500, 600, 700, 800, 900, 1000, 1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800, 1900, 2000};
const int numTimes = 16;

bool isGreenButtonPressed() {
  static int debounced_button_state = HIGH;
  static int previous_reading = HIGH;
  static unsigned long last_change_time = 0UL;
  bool isPressed = false;

  int current_reading = digitalRead(GREEN_BUTTON);

  if (previous_reading != current_reading) {
    last_change_time = millis();
  }

  if (millis() - last_change_time > DEBOUNCE_PERIOD) {
    if (current_reading != debounced_button_state) {
      if (debounced_button_state == HIGH && current_reading == LOW) {
        isPressed = true;
      }
      debounced_button_state = current_reading;
    }
  }

  previous_reading = current_reading;
  return isPressed;
}

bool isRedButtonPressed() {
  static int debounced_button_state = HIGH;
  static int previous_reading = HIGH;
  static unsigned long last_change_time = 0UL;
  bool isPressed = false;

  int current_reading = digitalRead(RED_BUTTON);

  if (previous_reading != current_reading) {
    last_change_time = millis();
  }

  if (millis() - last_change_time > DEBOUNCE_PERIOD) {
    if (current_reading != debounced_button_state) {
      if (debounced_button_state == HIGH && current_reading == LOW) {
        isPressed = true;
      }
      debounced_button_state = current_reading;
    }
  }

  previous_reading = current_reading;
  return isPressed;
}

int findTimeIndex(unsigned long timeValue) {
  for (int i = 0; i < numTimes; i++) {
    if (availableTimes[i] == timeValue) {
      return i;
    }
  }
}

void changeSelectedLED() {
  selectedLED = ++selectedLED % 3;
  updateDisplay();
}

void changeLEDInterval() {
  unsigned long* currentInterval;
  
  switch (selectedLED) {
    case 0: currentInterval = &redInterval; break;
    case 1: currentInterval = &greenInterval; break;
    case 2: currentInterval = &blueInterval; break;
  }
  
  int currentIndex = findTimeIndex(*currentInterval);
  int newIndex = (currentIndex + 1) % numTimes;
  *currentInterval = availableTimes[newIndex];
  
  updateDisplay();
}

void updateDisplay() {
  lcd.clear();
  lcd.setCursor(0, 0);
  
  switch (selectedLED) {
    case 0:
      lcd.print("RED: ");
      lcd.print(redInterval);
      lcd.print("ms");
      break;
    case 1:
      lcd.print("GREEN: ");
      lcd.print(greenInterval);
      lcd.print("ms");
      break;
    case 2:
      lcd.print("BLUE: ");
      lcd.print(blueInterval);
      lcd.print("ms");
      break;
  }
  
  lcd.setCursor(0, 1);
  lcd.print("R");
  lcd.print(redInterval/1000);
  lcd.print(",");
  lcd.print((redInterval-redInterval/1000*1000)/100);
  lcd.print(" G");
  lcd.print(greenInterval/1000);
  lcd.print(",");
  lcd.print((greenInterval-greenInterval/1000*1000)/100);
  lcd.print(" B");
  lcd.print(blueInterval/1000);
  lcd.print(",");
  lcd.print((blueInterval-blueInterval/1000*1000)/100);
  lcd.print(" s");
}

void blinkRedLED() {
  unsigned long currentTime = millis();
  
  if (currentTime - previousRedTime >= redInterval) {
    previousRedTime = currentTime;
    
    if (redState == LOW) {
      redState = HIGH;
    } else {
      redState = LOW;
    }
    
    digitalWrite(LED_RED, redState);
  }
}

void blinkGreenLED() {
  unsigned long currentTime = millis();
  
  if (currentTime - previousGreenTime >= greenInterval) {
    previousGreenTime = currentTime;
    
    if (greenState == LOW) {
      greenState = HIGH;
    } else {
      greenState = LOW;
    }
    
    digitalWrite(LED_GREEN, greenState);
  }
}

void blinkBlueLED() {
  unsigned long currentTime = millis();
  
  if (currentTime - previousBlueTime >= blueInterval) {
    previousBlueTime = currentTime;
    
    if (blueState == LOW) {
      blueState = HIGH;
    } else {
      blueState = LOW;
    }
    
    digitalWrite(LED_BLUE, blueState);
  }
}

void initLCD() {
  lcd.init();
  lcd.clear();
  lcd.backlight();
}

void setup() {
  pinMode(LED_RED, OUTPUT);
  pinMode(LED_GREEN, OUTPUT);
  pinMode(LED_BLUE, OUTPUT);
  pinMode(RED_BUTTON, INPUT_PULLUP);
  pinMode(GREEN_BUTTON, INPUT_PULLUP);
  
  digitalWrite(LED_RED, LOW);
  digitalWrite(LED_GREEN, LOW);
  digitalWrite(LED_BLUE, LOW);
  
  initLCD();
  updateDisplay();
}

void loop() {
  blinkRedLED();
  blinkGreenLED();
  blinkBlueLED();
  
  if (isRedButtonPressed()) {
    changeSelectedLED();
  }
  
  if (isGreenButtonPressed()) {
    changeLEDInterval();
  }
}
