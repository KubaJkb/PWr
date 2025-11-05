#include <LiquidCrystal_I2C.h>
#include <util/atomic.h>
#include <OneWire.h>
#include <DallasTemperature.h>

LiquidCrystal_I2C lcd(0x27, 16, 2);

#define LED_RED 6
#define LED_GREEN 5
#define LED_BLUE 3

#define RED_BUTTON 2    
#define GREEN_BUTTON 4 

#define ENCODER1 A2
#define ENCODER2 A3

#define POTENTIOMETER A0

#define DEBOUNCE_PERIOD 80UL

// --- OneWire / DallasTemperature ---
OneWire oneWire(A1);
DallasTemperature sensors(&oneWire);

void initSensors()
{
  sensors.begin();
}

// --- Zmienne przerwań enkodera ---
volatile int encoder1 = HIGH;
volatile int encoder2 = HIGH;
volatile unsigned long encoderTimestamp = 0UL;

// --- Stany menu ---
enum MenuLevel { TOP = 0, SUB = 1, ADJUST = 2 };
volatile MenuLevel menuLevel = TOP;

// --- Pozycje menu głównego ---
const char* topMenus[] = {
  "1. LED options",
  "2. Display",
  "3. Temperature",
  "4. About"
};
const uint8_t TOP_COUNT = sizeof(topMenus) / sizeof(topMenus[0]);

// --- Podmenu ---
const char* sub_LED[] = { "a.Power", "b.Red", "c.Green", "d.Blue" };
const char* sub_Display[] = { "a.Backlight", "b.Selector" };
const char* sub_Temp[] = { "a.Sensor IN", "b.Sensor OUT", "c.Units" };
const char* sub_About[] = { "a.Info" };

uint8_t currentTop = 0;
uint8_t currentSub = 0;

// --- Stany urządzeń ---
bool ledPower = false;
uint8_t ledR = 0;
uint8_t ledG = 0;
uint8_t ledB = 0;

bool lcdBacklightState = true;
uint8_t selectorOption = 0;
uint8_t tempUnits = 0;

// --- Zmienne odbicia przycisków ---
unsigned long lastEncoderChange = 0UL;
unsigned long lastGreenChange = 0UL;
int debouncedGreenState = HIGH;
int previousGreenReading = HIGH;
unsigned long lastRedChange = 0UL;
int debouncedRedState = HIGH;
int previousRedReading = HIGH;

// --- Znak strzałki ---
byte arrowChar[8] = {
  0b00000,
  0b00100,
  0b00110,
  0b11111,
  0b11111,
  0b00110,
  0b00100,
  0b00000
};

// --- Deklaracje funkcji ---
void renderMenu();
void applyLedOutputs();
void enterSubmenu();
void execActionTopNoSub();
void handleGreenPressEvent();
bool isGreenButtonPressed();
bool isRedButtonPressed();
void exitLevelOrBack();
void drawSelectorAt(uint8_t col, uint8_t row);
void showAboutScreen();
void showTemperature(uint8_t which);

 // --- Przerwanie enkodera ---
ISR(PCINT1_vect) {
  encoder1 = digitalRead(ENCODER1);
  encoder2 = digitalRead(ENCODER2);
  encoderTimestamp = millis();
}

void setup() {
  // --- Konfiguracja pinów ---
  pinMode(LED_RED, OUTPUT);
  pinMode(LED_GREEN, OUTPUT);
  pinMode(LED_BLUE, OUTPUT);
  pinMode(RED_BUTTON, INPUT_PULLUP);
  pinMode(GREEN_BUTTON, INPUT_PULLUP);
  pinMode(ENCODER1, INPUT_PULLUP);
  pinMode(ENCODER2, INPUT_PULLUP);

  // --- Inicjalizacja LCD ---
  lcd.init();
  lcd.backlight();
  lcd.createChar(0, arrowChar);
  lcd.clear();

  applyLedOutputs();

  // --- Inicjalizacja sensorów temperatury ---
  initSensors();

  // --- Konfiguracja przerwań enkodera ---
  PCICR |= (1 << PCIE1);
  PCMSK1 |= (1 << PCINT10);

  renderMenu();
}

void loop() {
  // --- Odczyt enkodera ---
  int en1, en2;
  unsigned long ts;
  ATOMIC_BLOCK(ATOMIC_RESTORESTATE) {
    en1 = encoder1;
    en2 = encoder2;
    ts = encoderTimestamp;
  }

  // --- Obsługa ruchu enkodera ---
  if (en1 == LOW && ts > lastEncoderChange + DEBOUNCE_PERIOD) {
    bool cw = (en2 == HIGH);
    lastEncoderChange = ts;

    if (menuLevel == TOP) {
      // --- Nawigacja menu głównego ---
      if (cw) {
        currentTop = (currentTop + 1) % TOP_COUNT;
      } else {
        currentTop = (currentTop + TOP_COUNT - 1) % TOP_COUNT;
      }
      renderMenu();
    } else if (menuLevel == SUB) {
      // --- Nawigacja podmenu ---
      uint8_t subcount = 0;
      if (currentTop == 0) subcount = sizeof(sub_LED)/sizeof(sub_LED[0]);
      else if (currentTop == 1) subcount = sizeof(sub_Display)/sizeof(sub_Display[0]);
      else if (currentTop == 2) subcount = sizeof(sub_Temp)/sizeof(sub_Temp[0]);
      else if (currentTop == 3) subcount = 1;

      if (subcount > 0) {
        if (cw) currentSub = (currentSub + 1) % subcount;
        else currentSub = (currentSub + subcount - 1) % subcount;
      }
      renderMenu();
    } else if (menuLevel == ADJUST) {
      // --- Regulacja wartości ---
      if (currentTop == 0) {
        // --- Regulacja kolorów LED ---
        if (currentSub == 1) {
          if (cw) {
            if (ledR <= 240) ledR += 15; else ledR = 255;
          } else {
            if (ledR >= 15) ledR -= 15; else ledR = 0;
          }
        } else if (currentSub == 2) {
          if (cw) {
            if (ledG <= 240) ledG += 15; else ledG = 255;
          } else {
            if (ledG >= 15) ledG -= 15; else ledG = 0;
          }
        } else if (currentSub == 3) {
          if (cw) {
            if (ledB <= 240) ledB += 15; else ledB = 255;
          } else {
            if (ledB >= 15) ledB -= 15; else ledB = 0;
          }
        }
        applyLedOutputs();
        renderMenu();
      } else if (currentTop == 1) {
        // --- Zmiana symbolu selektora ---
        if (cw) selectorOption = (selectorOption + 1) % 3;
        else selectorOption = (selectorOption + 3 - 1) % 3;
        renderMenu();
      } else if (currentTop == 2) {
        // --- Przełączanie jednostek temperatury ---
        if (cw) tempUnits = (tempUnits + 1) % 2;
        else tempUnits = (tempUnits + 1) % 2;
        renderMenu();
      }
    }
  }

  // --- Obsługa przycisków ---
  if (isGreenButtonPressed()) {
    handleGreenPressEvent();
  }

  if (isRedButtonPressed()) {
    exitLevelOrBack();
  }
}

// --- Renderowanie menu na LCD ---
void renderMenu() {
  lcd.clear();
  lcd.setCursor(1, 0);
  lcd.print(topMenus[currentTop]);

  lcd.setCursor(0, 1);

  if (menuLevel == TOP) {
    // --- Wyświetlanie menu głównego ---
    drawSelectorAt(0, 0);
    lcd.setCursor(1, 1);
    lcd.print("-Press Green-");
  } else if (menuLevel == SUB) {
    // --- Wyświetlanie podmenu ---
    if (currentTop == 0) {
      const char* name = sub_LED[currentSub];
      drawSelectorAt(0,1);
      lcd.setCursor(1,1);
      lcd.print(name);
      if (currentSub == 0) {
        lcd.setCursor(10,1);
        lcd.print(ledPower ? "ON " : "OFF");
      } else if (currentSub == 1) {
        lcd.setCursor(9,1);
        char buf[6]; sprintf(buf, "%3d", ledR);
        lcd.print(buf);
      } else if (currentSub == 2) {
        lcd.setCursor(9,1);
        char buf[6]; sprintf(buf, "%3d", ledG);
        lcd.print(buf);
      } else if (currentSub == 3) {
        lcd.setCursor(9,1);
        char buf[6]; sprintf(buf, "%3d", ledB);
        lcd.print(buf);
      }
    } else if (currentTop == 1) {
      const char* name = sub_Display[currentSub];
      drawSelectorAt(0,1);
      lcd.setCursor(1,1);
      lcd.print(name);
      if (currentSub == 0) {
        lcd.setCursor(13,1);
        lcd.print(lcdBacklightState ? "ON " : "OFF");
      } else if (currentSub == 1) {
        lcd.setCursor(13,1);
        if (selectorOption == 0) lcd.print(">");
        else if (selectorOption == 1) lcd.print("-");
        else {
          lcd.write((uint8_t)0);
        }
      }
    } else if (currentTop == 2) {
      const char* name = sub_Temp[currentSub];
      drawSelectorAt(0,1);
      lcd.setCursor(2,1);
      lcd.print(name);
      if (currentSub == 2) {
        lcd.setCursor(12,1);
        lcd.print(tempUnits == 0 ? "C" : "F");
      }
    } else if (currentTop == 3) {
      drawSelectorAt(0,1);
      lcd.setCursor(2,1);
      lcd.print("About");
    }
  } else if (menuLevel == ADJUST) {
    // --- Tryb regulacji ---
    lcd.print("Adj: ");
    if (currentTop == 0) {
      if (currentSub == 1) {
        lcd.print("Red ");
        lcd.setCursor(11,1); char buf[5]; sprintf(buf, "%3d", ledR); lcd.print(buf);
      } else if (currentSub == 2) {
        lcd.print("Green ");
        lcd.setCursor(11,1); char buf[5]; sprintf(buf, "%3d", ledG); lcd.print(buf);
      } else if (currentSub == 3) {
        lcd.print("Blue ");
        lcd.setCursor(11,1); char buf[5]; sprintf(buf, "%3d", ledB); lcd.print(buf);
      } else {
        lcd.print("Power (toggle)");
      }
    } else if (currentTop == 1) {
      lcd.print("Selector: ");
      lcd.setCursor(15,1);
      if (selectorOption == 0) lcd.print(">");
      else if (selectorOption == 1) lcd.print("-");
      else lcd.write((uint8_t)0);
    } else if (currentTop == 2) {
      lcd.print("Unit ");
      lcd.setCursor(11,1);
      lcd.print(tempUnits == 0 ? "C" : "F");
    }
  }
}

// --- Aktualizacja wyjść LED ---
void applyLedOutputs() {
  if (!ledPower) {
    analogWrite(LED_RED, 0);
    analogWrite(LED_GREEN, 0);
    analogWrite(LED_BLUE, 0);
  } else {
    analogWrite(LED_RED, ledR);
    analogWrite(LED_GREEN, ledG);
    analogWrite(LED_BLUE, ledB);
  }
}

// --- Logika przycisku ENTER ---
void handleGreenPressEvent() {
  if (menuLevel == TOP) {
    if (currentTop == 3) {
      showAboutScreen();
      renderMenu();
    } else {
      currentSub = 0;
      menuLevel = SUB;
      renderMenu();
    }
    return;
  }

  if (menuLevel == SUB) {
    if (currentTop == 0) {
      if (currentSub == 0) {
        // --- Przełączanie zasilania LED ---
        ledPower = !ledPower;
        applyLedOutputs();
        renderMenu();
      } else {
        // --- Wejście w tryb regulacji kolorów ---
        menuLevel = ADJUST;
        renderMenu();
      }
    } else if (currentTop == 1) {
      if (currentSub == 0) {
        // --- Przełączanie podświetlenia LCD ---
        lcdBacklightState = !lcdBacklightState;
        if (lcdBacklightState) lcd.backlight(); else lcd.noBacklight();
        renderMenu();
      } else if (currentSub == 1) {
        menuLevel = ADJUST;
        renderMenu();
      }
    } else if (currentTop == 2) {
      if (currentSub == 0) {
        showTemperature(1); // Sensor IN
        renderMenu();
      } else if (currentSub == 1) {
        showTemperature(0); // Sensor OUT
        renderMenu();
      } else if (currentSub == 2) {
        menuLevel = ADJUST;
        renderMenu();
      }
    } else if (currentTop == 3) {
      showAboutScreen();
      renderMenu();
    }
    return;
  }

  if (menuLevel == ADJUST) {
    // --- Wyjście z trybu regulacji ---
    menuLevel = SUB;
    renderMenu();
    return;
  }
}

// --- Logika przycisku BACK ---
void exitLevelOrBack() {
  if (menuLevel == ADJUST) {
    menuLevel = SUB;
    renderMenu();
  } else if (menuLevel == SUB) {
    menuLevel = TOP;
    renderMenu();
  } else {
    lcd.clear();
    lcd.print("At top menu");
    delay(400);
    renderMenu();
  }
}

// --- Detekcja naciśnięcia przycisku z odbiciem ---
bool isGreenButtonPressed() {
  int current = digitalRead(GREEN_BUTTON);
  if (current != previousGreenReading) {
    lastGreenChange = millis();
  }
  bool isPressed = false;
  if (millis() - lastGreenChange > DEBOUNCE_PERIOD) {
    if (current != debouncedGreenState) {
      if (debouncedGreenState == HIGH && current == LOW) {
        isPressed = true;
      }
      debouncedGreenState = current;
    }
  }
  previousGreenReading = current;
  return isPressed;
}

bool isRedButtonPressed() {
  int current = digitalRead(RED_BUTTON);
  if (current != previousRedReading) {
    lastRedChange = millis();
  }
  bool isPressed = false;
  if (millis() - lastRedChange > DEBOUNCE_PERIOD) {
    if (current != debouncedRedState) {
      if (debouncedRedState == HIGH && current == LOW) {
        isPressed = true;
      }
      debouncedRedState = current;
    }
  }
  previousRedReading = current;
  return isPressed;
}

// --- Rysowanie symbolu selektora ---
void drawSelectorAt(uint8_t col, uint8_t row) {
  lcd.setCursor(col, row);
  if (selectorOption == 0) {
    lcd.print(">");
  } else if (selectorOption == 1) {
    lcd.print("-");
  } else {
    lcd.write((uint8_t)0);
  }
}

// --- Ekran informacyjny ---
void showAboutScreen() {
  lcd.clear();
  lcd.setCursor(0,0);
  lcd.print("About:");
  lcd.setCursor(0,1);
  lcd.print("Jakub Kacprzyk");
  unsigned long start = millis();
  while (millis() - start < 3000) {
    if (isGreenButtonPressed() || isRedButtonPressed()) break;
    delay(10);
  }
}

// --- Wyświetlanie rzeczywistej temperatury (z Dallas/OneWire) ---
void showTemperature(uint8_t which) {
  uint8_t index = which;

  lcd.clear();
  if (which == 1) lcd.print("Sensor IN:");
  else lcd.print("Sensor OUT:");

  while (true) {
    sensors.requestTemperatures();

    float tempC = sensors.getTempCByIndex(index); 
    bool disconnected = (tempC == DEVICE_DISCONNECTED_C);

    float displayTemp = 0.0;
    char unitChar = 'C';
    if (!disconnected) {
      displayTemp = tempC;
      if (tempUnits == 1) {
        displayTemp = tempC * 9.0 / 5.0 + 32.0;
        unitChar = 'F';
      }
    }

    lcd.setCursor(0,1);
    char buf[16];
    if (disconnected) {
      sprintf(buf, "   --.- %c", (tempUnits==0 ? 'C' : 'F'));
      lcd.print(buf);
    } else {
      int val10 = (int)(displayTemp * 10.0 + (displayTemp >= 0.0 ? 0.5 : -0.5));
      int whole = val10 / 10;
      int dec = abs(val10 % 10);
      sprintf(buf, "%4d.%1d %c", whole, dec, unitChar);
      lcd.print(buf);
    }

    unsigned long waitStart = millis();
    while (millis() - waitStart < 800) {
      if (isGreenButtonPressed() || isRedButtonPressed()) {
        return; 
      }
      delay(10);
    }
  }
}
