#!/usr/bin/env python3
from config import *
import time
import math
import w1thermsensor
import board
import busio
import adafruit_bme280.advanced as adafruit_bme280

P0 = 1013

ds = w1thermsensor.W1ThermSensor()
i2c = busio.I2C(board.SCL, board.SDA)
bme = adafruit_bme280.Adafruit_BME280_I2C(i2c, 0x76)
bme.sea_level_pressure = P0

def altitude(ph):
    return 44330.0 * (1.0 - (ph / P0) ** (1.0 / 5.255))

while True:
    t_ds = ds.get_temperature()
    t_bme = bme.temperature
    h = bme.humidity
    p = bme.pressure
    alt = altitude(p)

    print("------------------------------------------------")
    print(f"DS18B20 Temperature: {t_ds:.2f} °C")
    print(f"BME280 Temperature: {t_bme:.2f} °C")
    print(f"Humidity: {h:.1f} %")
    print(f"Pressure: {p:.2f} hPa")
    print(f"Altitude: {alt:.2f} m")
    print("------------------------------------------------\n")

    time.sleep(5)