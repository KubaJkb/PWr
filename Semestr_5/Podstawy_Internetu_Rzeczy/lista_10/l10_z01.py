#!/usr/bin/env python3

import time
from PIL import Image, ImageDraw, ImageFont
from config import *
import lib.oled.SSD1331 as SSD1331
import w1thermsensor
import board
import busio
import adafruit_bme280.advanced as adafruit_bme280

disp = SSD1331.SSD1331()
disp.Init()
disp.clear()

P0 = 1013
ds = w1thermsensor.W1ThermSensor()
i2c = busio.I2C(board.SCL, board.SDA)
bme = adafruit_bme280.Adafruit_BME280_I2C(i2c, 0x76)
bme.sea_level_pressure = P0

fontSmall = ImageFont.load_default()

icon_size = (8, 8)
icon_temp = Image.open("icons/temp.png").convert("RGB").resize(icon_size)
icon_pressure = Image.open("icons/cisnienie.png").convert("RGB").resize(icon_size)
icon_humidity = Image.open("icons/mokrosc.png").convert("RGB").resize(icon_size)

def altitude(ph):
    return 44330.0 * (1.0 - (ph / P0) ** (1.0 / 5.255))

while True:
    t_ds = ds.get_temperature()
    t_bme = bme.temperature
    pressure = bme.pressure
    humidity = bme.humidity
    h = altitude(pressure)

    image = Image.new("RGB", (disp.width, disp.height), "BLACK")
    draw = ImageDraw.Draw(image)

    image.paste(icon_temp, (2, 2))
    draw.text((15, 0), f"{t_ds:.1f}C", font=fontSmall, fill="RED")

    image.paste(icon_pressure, (2, 17))
    draw.text((15, 15), f"{pressure:.0f}hPa", font=fontSmall, fill="BLUE")

    image.paste(icon_humidity, (2, 32))
    draw.text((15, 30), f"{humidity:.1f}%", font=fontSmall, fill="CYAN")

    draw.text((0, 45), f"h={h:.1f}m", font=fontSmall, fill="WHITE")

    disp.ShowImage(image, 0, 0)

    time.sleep(0.5)