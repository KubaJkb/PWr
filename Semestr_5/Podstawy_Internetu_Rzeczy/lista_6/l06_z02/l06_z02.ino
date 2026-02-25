#include <Arduino.h>
#include <ButtonHandler.h>
#include <LiquidCrystal_I2C.h>

#define RED_BUTTON   2
#define GREEN_BUTTON 4

constexpr unsigned long DEBOUNCE_MS  = 50UL;
constexpr unsigned long LONGPRESS_MS = 800UL;
constexpr unsigned long DISPLAY_MS   = 1200UL;

LiquidCrystal_I2C lcd(0x27, 16, 2);

ButtonHandler redButton(RED_BUTTON);
ButtonHandler greenButton(GREEN_BUTTON);

unsigned long returnTime = 0;
bool showingMessage = false;

void showMainScreen() {
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("Press a button");
  lcd.setCursor(0,1);
  lcd.print("GREEN or RED");
  showingMessage = false;
}

void setup() {
  lcd.init();
  lcd.backlight();

  showMainScreen();

  redButton.begin(DEBOUNCE_MS, LONGPRESS_MS);
  greenButton.begin(DEBOUNCE_MS, LONGPRESS_MS);

  redButton.onShortPress([]() {
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("RED - short");
    lcd.setCursor(0, 1);
    lcd.print("Short RED press");
    returnTime = millis() + DISPLAY_MS;
    showingMessage = true;
  });

  redButton.onLongPress([]() {
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("RED - long");
    lcd.setCursor(0, 1);
    lcd.print("Long RED press");
    returnTime = millis() + DISPLAY_MS;
    showingMessage = true;
  });

  greenButton.onShortPress([]() {
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("GREEN - short");
    lcd.setCursor(0, 1);
    lcd.print("Short GREEN press");
    returnTime = millis() + DISPLAY_MS;
    showingMessage = true;
  });

  greenButton.onLongPress([]() {
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("GREEN - long");
    lcd.setCursor(0, 1);
    lcd.print("Long GREEN press");
    returnTime = millis() + DISPLAY_MS;
    showingMessage = true;
  });
}

void loop() {
  ButtonHandler::process();

  if (showingMessage && millis() >= returnTime) {
    showMainScreen();
  }
}
