#!/usr/bin/env python3

import time
import RPi.GPIO as GPIO
from mfrc522 import MFRC522
from datetime import datetime
import board
import neopixel
from config import *

GPIO.setmode(GPIO.BCM)
GPIO.setup(buzzerPin, GPIO.OUT)

pixels = neopixel.NeoPixel(board.D18, 8, brightness=1.0/32, auto_write=False)

def buzzer(state):
    GPIO.output(buzzerPin, not state)

def beep():
    buzzer(True)
    time.sleep(0.1)
    buzzer(False)

def led_flash():
    pixels.fill((0,255,0))
    pixels.show()
    time.sleep(0.5)
    pixels.fill((0,0,0))
    pixels.show()

def wait_removal(reader):
    start = None
    while True:
        status, _ = reader.MFRC522_Request(reader.PICC_REQIDL)
        if status == reader.MI_OK:
            start = None
        else:
            if start is None:
                start = time.time()
            elif time.time() - start >= .1:
                break
        time.sleep(0.01)

def rfid_registration():
    reader = MFRC522()
    registered = False
    uid_saved = None
    try:
        while True:
            status, _ = reader.MFRC522_Request(reader.PICC_REQIDL)
            if status == reader.MI_OK:
                status, uid = reader.MFRC522_Anticoll()
                if status == reader.MI_OK:
                    num = 0
                    for i in range(len(uid)):
                        num += uid[i] << (i*8)

                    if not registered or num != uid_saved:
                        uid_saved = num
                        print(f"[{datetime.now().strftime('%Y-%m-%d %H:%M:%S')}] Karta UID: {num}")
                        beep()
                        led_flash()
                        registered = True
                        wait_removal(reader)
                        registered = False
                        uid_saved = None

            time.sleep(0.05)

    except KeyboardInterrupt:
        pixels.fill((0,0,0))
        pixels.show()
        GPIO.cleanup()
        print("Zakończono działanie programu")

if __name__ == "__main__":
    rfid_registration()