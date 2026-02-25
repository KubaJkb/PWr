#!/usr/bin/env python3

import time
import json
from datetime import datetime
import paho.mqtt.client as mqtt
from mfrc522 import MFRC522
import RPi.GPIO as GPIO
import config

BROKER = "localhost"
TOPIC = "rfid/access"
TERMINAL_ID = "T1"

def buzzer(state: bool):
    GPIO.output(config.buzzerPin, not state)

def beep(duration=0.08):
    buzzer(True)
    time.sleep(duration)
    buzzer(False)

def led_flash(duration=0.15):
    try:
        GPIO.output(config.led1, True)
        GPIO.output(config.led2, True)
        GPIO.output(config.led3, True)
        GPIO.output(config.led4, True)
        time.sleep(duration)
    finally:
        GPIO.output(config.led1, False)
        GPIO.output(config.led2, False)
        GPIO.output(config.led3, False)
        GPIO.output(config.led4, False)

client = mqtt.Client()

def connect_mqtt():
    client.connect(BROKER)
    client.loop_start()

def disconnect_mqtt():
    client.loop_stop()
    client.disconnect()

def wait_removal(reader, stable_time=0.1):
    start = None
    while True:
        status, _ = reader.MFRC522_Request(reader.PICC_REQIDL)
        if status == reader.MI_OK:
            start = None
        else:
            if start is None:
                start = time.time()
            elif time.time() - start >= stable_time:
                break
        time.sleep(0.01)

def main():
    reader = MFRC522()
    registered = False
    uid_saved = None

    connect_mqtt()
    print("RFID publisher started.")

    try:
        while True:
            status, _ = reader.MFRC522_Request(reader.PICC_REQIDL)
            if status == reader.MI_OK:
                status, uid = reader.MFRC522_Anticoll()
                if status == reader.MI_OK:
                    num = 0
                    for i in range(len(uid)):
                        num += uid[i] << (i * 8)

                    if (not registered) or (num != uid_saved):
                        uid_saved = num
                        timestamp = datetime.now().isoformat(sep=' ', timespec='seconds')
                        payload = {
                            "uid": str(num),
                            "timestamp": timestamp,
                            "terminal": TERMINAL_ID
                        }
                        client.publish(TOPIC, json.dumps(payload), qos=1)
                        print(f"[{timestamp}] UID={num} published")

                        beep()
                        led_flash()

                        registered = True
                        wait_removal(reader)
                        registered = False
                        uid_saved = None
            time.sleep(0.05)
    except KeyboardInterrupt:
        print("Interrupted.")
    finally:
        disconnect_mqtt()
        GPIO.cleanup()
        print("Program terminated.")

if __name__ == "__main__":
    main()
