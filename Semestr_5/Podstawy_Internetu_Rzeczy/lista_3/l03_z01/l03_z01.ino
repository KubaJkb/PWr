#include <LiquidCrystal_I2C.h>
LiquidCrystal_I2C lcd(0x27, 16, 2);

#define LED_RED 6
#define LED_GREEN 5
#define LED_BLUE 3

#define RED_BUTTON 2
#define GREEN_BUTTON 4

#define DEBOUNCE_PERIOD 10UL

int counter = 0;
bool pressedGreen = false;
bool pressedRed = false;

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

int waitForButtonReleaseOrSecondPress() {
  unsigned long startTime = millis();
  
  while (true) {
    bool greenButtonState = digitalRead(GREEN_BUTTON);
    bool redButtonState = digitalRead(RED_BUTTON);
    
    if (greenButtonState == LOW && redButtonState == LOW) {
      return 2; // Oba przyciski wciśnięte
    }
    if (pressedGreen && (greenButtonState == HIGH || millis() - startTime > 1000)) {
      return 0; // Zielony zwolniony
    }
    if (pressedRed && (redButtonState == HIGH || millis() - startTime > 1000)) {
      return 1; // Czerwony zwolniony
    }
        
    //delay(10);
  }
}

void blinkLED(int times) {
  for (int i = 0; i < times; i++) {
    digitalWrite(LED_RED, HIGH);
    digitalWrite(LED_GREEN, HIGH);
    digitalWrite(LED_BLUE, HIGH);
    delay(300);
    digitalWrite(LED_RED, LOW);
    digitalWrite(LED_GREEN, LOW);
    digitalWrite(LED_BLUE, LOW);
    if (i < times - 1) {
      delay(300);
    }
  }
}

void displayMessage(const char* message) {
  lcd.setCursor(0, 1);
  lcd.print("                ");
  lcd.setCursor(0, 1);
  lcd.print(message);
}

void updateCounter(int n) {
  counter += n;
  if (counter < 0) {
    counter = 0;
  }
  lcd.setCursor(0, 0);
  lcd.print("Counter: ");
  lcd.print(counter);
  lcd.print("   ");
}

void displayInitialState() {
  lcd.clear();
  updateCounter(0);
  lcd.setCursor(0, 1);
  lcd.print("Green+  Red-");
}

void initLCD() {
  lcd.init();
  lcd.backlight();
  lcd.clear();
}

void setup() {
  pinMode(GREEN_BUTTON, INPUT_PULLUP);
  pinMode(RED_BUTTON, INPUT_PULLUP);
  pinMode(LED_RED, OUTPUT);
  pinMode(LED_GREEN, OUTPUT);
  pinMode(LED_BLUE, OUTPUT);
  
  Serial.begin(115200);
  Serial.print("Program start — licznik = ");
  Serial.println(counter);

  initLCD();
  displayInitialState();
}

void loop() {
  pressedGreen = isGreenButtonPressed();
  pressedRed = isRedButtonPressed();

  if (pressedGreen || pressedRed) {
    if (pressedGreen) {
      displayMessage("Green...");

    }
    else {
      displayMessage("Red...");
    }

    int result = waitForButtonReleaseOrSecondPress();

    if (result == 0) { // tylko zielony
      updateCounter(+1);
      Serial.print("Licznik zwiekszony: ");
      Serial.println(counter);
      displayMessage("+1");
    }
    else if (result == 1) { // tylko czerwony
      updateCounter(-1);
      Serial.print("Licznik zmniejszony: ");
      Serial.println(counter);
      displayMessage("-1");
    }
    else if (result == 2) { // oba przyciski
      Serial.print("Migniecie LED ");
      Serial.print(counter);
      Serial.println(" razy.");
      displayMessage("Blinking...");
      blinkLED(counter);
    }

    if (digitalRead(GREEN_BUTTON) == LOW || digitalRead(RED_BUTTON) == LOW) {
      displayMessage("Release buttons!");
    }
    while (digitalRead(GREEN_BUTTON) == LOW || digitalRead(RED_BUTTON) == LOW) {
      delay(10);
    }
    
    displayMessage("Green+  Red-");
  }
  //delay(10);  
}
