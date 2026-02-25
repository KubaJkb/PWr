#include <Wire.h>
#include <LiquidCrystal_I2C.h>
#include <OneWire.h>
#include <DallasTemperature.h>
#include <math.h>

LiquidCrystal_I2C lcd(0x27,16,2);
OneWire oneWire(A1);
DallasTemperature sensors(&oneWire);

#define LED_RED 6
#define LED_GREEN 5
#define LED_BLUE 3
#define RED_BUTTON 2
#define GREEN_BUTTON 4
#define ENCODER1 A2
#define ENCODER2 A3
#define POTENTIOMETER A0


const unsigned long updateInterval = 1000;
const unsigned long DEBOUNCE_PERIOD = 50;

float tempOut = 0.0;
float tempIn = 0.0;
float minTemp = 999.0;
float maxTemp = -999.0;

float lastTempOut = -999.0;
float lastTempIn  = -999.0;
float lastMinDisplay = -999.0;
float lastMaxDisplay = -999.0;

unsigned long lastUpdate = 0;

void setup() {
  pinMode(RED_BUTTON, INPUT_PULLUP);
  
  lcd.init();
  lcd.backlight();
  
  sensors.begin();

  lcd.clear();
  lcd.setCursor(0,0);
  lcd.print("Z:      W:     C");
  lcd.setCursor(0,1);
  lcd.print("L:      H:     C");
}

void loop() {
  unsigned long currentMillis = millis();

  if (currentMillis - lastUpdate >= updateInterval) {
    lastUpdate = currentMillis;

    sensors.requestTemperatures();
    tempOut = sensors.getTempCByIndex(0); // zew
    tempIn  = sensors.getTempCByIndex(1); // wew 

    
    if (tempOut < minTemp) minTemp = tempOut;
    if (tempOut > maxTemp) maxTemp = tempOut;
    
    if (tempIn < minTemp) minTemp = tempIn;
    if (tempIn > maxTemp) maxTemp = tempIn;
    

    if (fabs(tempIn - lastTempIn) >= 0.1 || lastTempIn < -900.0) {
      updateTemp();
      lastTempIn = tempIn;
    }

    if (fabs(tempOut - lastTempOut) >= 0.1 || lastTempOut < -900.0) {
      updateTemp();
      lastTempOut = tempOut;
    }

    if (fabs(minTemp - lastMinDisplay) >= 0.1 || lastMinDisplay < -900.0) {
      updateMinMax(); 
      lastMinDisplay = minTemp;
    }
    if (fabs(maxTemp - lastMaxDisplay) >= 0.1 || lastMaxDisplay < -900.0) {
      updateMinMax();
      lastMaxDisplay = maxTemp;
    }
  }

  if (isRedButtonPressed()) {
    minTemp = min(tempOut, tempIn);
    maxTemp = max(tempOut, tempIn);

    lastTempIn = -999.0;
    lastTempOut = -999.0;
    lastMinDisplay = -999.0;
    lastMaxDisplay = -999.0;

    lcd.clear();
    lcd.setCursor(0,0);
    lcd.print("Reset MIN i MAX");
    delay(800);
    lcd.clear();
    lcd.setCursor(0,0);
    lcd.print("Z:      W:     C");
    lcd.setCursor(0,1);
    lcd.print("L:      H:     C");
  }
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

String formatTemp5(float t) {
  String s = String(t, 2);

  if (t < -100) s = "-----";

  if (s.length() > 5) {
    s = s.substring(0, 5);
  }
  while (s.length() < 5) {
    s += ' ';
  }
  return s;
}

void updateTemp() {
  lcd.setCursor(2, 0);
  lcd.print(formatTemp5(tempOut));

  lcd.setCursor(10, 0);
  lcd.print(formatTemp5(tempIn));
}

void updateMinMax() {
  lcd.setCursor(2, 1);
  lcd.print(formatTemp5(minTemp));

  lcd.setCursor(10, 1);
  lcd.print(formatTemp5(maxTemp));
}
