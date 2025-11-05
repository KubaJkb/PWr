#include <Wire.h>
#include <LiquidCrystal_I2C.h>

LiquidCrystal_I2C lcd(0x27, 16, 2); 

const int POT_PIN = A0;
const float VREF = 5.0;    
const unsigned long UPDATE_MS = 150; 

char lastLine[17] = {0};

void trimLeadingSpaces(char *s) {
  int i = 0;
  int len = strlen(s);
  while (i < len && s[i] == ' ') i++;
  if (i > 0) {
    memmove(s, s + i, len - i + 1); 
  }
}

void setup() {
  lcd.init();
  lcd.backlight();
  lcd.clear();

  lcd.setCursor(0, 0);
  lcd.print("Miernik A0"); 

  Serial.begin(9600);
}

void loop() {
  static unsigned long lastMillis = 0;
  unsigned long now = millis();
  if (now - lastMillis < UPDATE_MS) return;
  lastMillis = now;

  int adc = analogRead(POT_PIN); 
  float voltage = adc * (VREF / 1023.0);

  char vnum[8]; 
  dtostrf(voltage, 0, 2, vnum); 
  trimLeadingSpaces(vnum);

  char leftBuf[9];
  snprintf(leftBuf, sizeof(leftBuf), "V=%s", vnum); 

  char rightBuf[9];
  snprintf(rightBuf, sizeof(rightBuf), "ADC=%4d", adc); 

  char line[17];
  snprintf(line, sizeof(line), "%-8s%8s", leftBuf, rightBuf); 
  line[16] = '\0';

  if (strcmp(line, lastLine) != 0) {
    lcd.setCursor(0, 1);
    lcd.print(line);
    strcpy(lastLine, line);
  }

  char vstrPlot[12];
  dtostrf(voltage, 0, 3, vstrPlot); 
  trimLeadingSpaces(vstrPlot);

  char plotLine[32];
  sprintf(plotLine, "Voltage:%s,ADC:%d", vstrPlot, adc);
  Serial.println(plotLine);
}
