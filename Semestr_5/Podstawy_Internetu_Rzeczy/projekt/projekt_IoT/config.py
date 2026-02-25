#!/usr/bin/env python3
# pylint: disable=no-member

import RPi.GPIO as GPIO

# pin numbers in BCM
GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)

# LEDs - 4 jednolite diody
led1 = 13
led2 = 12
led3 = 19
led4 = 26
GPIO.setup(led1, GPIO.OUT)
GPIO.setup(led2, GPIO.OUT)
GPIO.setup(led3, GPIO.OUT)
GPIO.setup(led4, GPIO.OUT)

# Przyciski i enkoder
buttonRed = 5
buttonGreen = 6
encoderLeft = 17
encoderRight = 27
GPIO.setup(buttonRed, GPIO.IN, pull_up_down=GPIO.PUD_UP)
GPIO.setup(buttonGreen, GPIO.IN, pull_up_down=GPIO.PUD_UP)
GPIO.setup(encoderLeft, GPIO.IN, pull_up_down=GPIO.PUD_UP)
GPIO.setup(encoderRight, GPIO.IN, pull_up_down=GPIO.PUD_UP)

# Buzzer
buzzerPin = 23
GPIO.setup(buzzerPin, GPIO.OUT)
GPIO.output(buzzerPin, 1)

# WS2812 pin
ws2812pin = 18

# Liczba LED WS2812
WS2812_COUNT = 8

# Ustawienia serwera
SERVER_HOST = '0.0.0.0'
SERVER_PORT = 5000

# Plik bazy danych
DATABASE_FILE = 'casino.db'

# Startowy balans dla nowych uzytkownikow
STARTING_BALANCE = 1000
