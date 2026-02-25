#!/usr/bin/env python3
# pylint: disable=no-member

import RPi.GPIO as GPIO
import time
import board
import neopixel
from PIL import Image, ImageDraw, ImageFont
import sys
import os

sys.path.insert(0, os.path.join(os.path.dirname(__file__), '..'))
import lib.oled.SSD1331 as SSD1331
from mfrc522 import MFRC522

from config import *

pixels = None

def init_ws2812():
    """Inicjalizuje paski LED WS2812"""
    global pixels
    pixels = neopixel.NeoPixel(board.D18, WS2812_COUNT, brightness=1.0/32, auto_write=False)
    pixels.fill((0, 0, 0))
    pixels.show()

def ws2812_fill(color):
    """Wypelnia wszystkie LED WS2812 jednym kolorem"""
    global pixels
    if pixels:
        pixels.fill(color)
        pixels.show()

def ws2812_set(index, color):
    """Ustawia kolor pojedynczego LED"""
    global pixels
    if pixels and 0 <= index < WS2812_COUNT:
        pixels[index] = color
        pixels.show()

def ws2812_set_all(colors):
    """Ustawia kolory wszystkich LED bez show"""
    global pixels
    if pixels:
        for i, color in enumerate(colors):
            if i < WS2812_COUNT:
                pixels[i] = color
        pixels.show()

def ws2812_off():
    """Wylacza wszystkie WS2812"""
    ws2812_fill((0, 0, 0))

def buzzer(state):
    """Wlacza/wylacza buzzer"""
    GPIO.output(buzzerPin, not state)

def buzzer_beep(duration=0.1):
    """Krotki dzwiek buzzera"""
    buzzer(True)
    time.sleep(duration)
    buzzer(False)

def buzzer_win():
    """Dzwiek wygranej"""
    for _ in range(3):
        buzzer(True)
        time.sleep(0.1)
        buzzer(False)
        time.sleep(0.05)

def buzzer_lose():
    """Dzwiek przegranej"""
    buzzer(True)
    time.sleep(0.5)
    buzzer(False)

def leds_off():
    """Wylacza wszystkie 4 LED"""
    GPIO.output(led1, False)
    GPIO.output(led2, False)
    GPIO.output(led3, False)
    GPIO.output(led4, False)

def leds_on():
    """Wlacza wszystkie 4 LED"""
    GPIO.output(led1, True)
    GPIO.output(led2, True)
    GPIO.output(led3, True)
    GPIO.output(led4, True)

def leds_blink(times=3, delay=0.2):
    """Miga wszystkimi 4 LED"""
    for _ in range(times):
        leds_on()
        time.sleep(delay)
        leds_off()
        time.sleep(delay)

def led_sequence(delay=0.15):
    """Sekwencja zapalania LED po kolei"""
    leds_off()
    for led in [led1, led2, led3, led4]:
        GPIO.output(led, True)
        time.sleep(delay)
    time.sleep(0.2)
    leds_off()

disp = None
font_large = None
font_small = None
font_medium = None

def init_oled():
    """Inicjalizuje wyswietlacz OLED - wersja bezpieczna"""
    global disp, font_large, font_small, font_medium

    try:
        os.system('sudo systemctl stop ip-oled.service 2>/dev/null')
        time.sleep(0.5)
    except:
        pass

    try:
        disp = SSD1331.SSD1331()
        disp.Init()
        disp.clear()
    except Exception as e:
        print(f"Błąd inicjalizacji ekranu: {e}")
        return

    current_dir = os.path.dirname(os.path.abspath(__file__))
    font_path = os.path.join(current_dir, 'lib', 'oled', 'Font.ttf')
    
    try:
        font_large = ImageFont.truetype(font_path, 20)
        font_medium = ImageFont.truetype(font_path, 14)
        font_small = ImageFont.truetype(font_path, 12)
        print(f"Zaladowano czcionke z: {font_path}")
    except OSError:
        print("UWAGA: Nie znaleziono pliku Font.ttf! Uzywam czcionki domyslnej.")
        font_large = ImageFont.load_default()
        font_medium = ImageFont.load_default()
        font_small = ImageFont.load_default()

def oled_clear():
    global disp
    if disp:
        disp.clear()

def oled_show_balance(balance, cost=None):
    global disp, font_large, font_small, font_medium
    if not disp:
        return

    image = Image.new("RGB", (disp.width, disp.height), "BLACK")
    draw = ImageDraw.Draw(image)

    draw.text((5, 2), "KASYNO", font=font_small, fill="YELLOW")

    draw.line([(0, 16), (95, 16)], fill="YELLOW", width=1)

    draw.text((5, 20), "Saldo:", font=font_small, fill="WHITE")
    draw.text((5, 34), f"${balance}", font=font_medium, fill="GREEN")

    if cost is not None:
        draw.text((5, 50), f"Koszt: ${cost}", font=font_small, fill="CYAN")

    disp.ShowImage(image, 0, 0)

def oled_show_result(result_text, win=True, amount=0):
    """Wyswietla wynik gry"""
    global disp, font_large, font_small, font_medium
    if not disp:
        return

    image = Image.new("RGB", (disp.width, disp.height), "BLACK")
    draw = ImageDraw.Draw(image)

    if win:
        color = "GREEN"
        title = "WYGRANA!"
    else:
        color = "RED"
        title = "PRZEGRANA"

    draw.text((10, 5), title, font=font_medium, fill=color)
    draw.line([(0, 22), (95, 22)], fill=color, width=1)

    draw.text((5, 28), result_text, font=font_small, fill="WHITE")

    if amount > 0:
        sign = "+" if win else "-"
        draw.text((5, 48), f"{sign}${amount}", font=font_medium, fill=color)

    disp.ShowImage(image, 0, 0)

def oled_show_spinning(game_type, symbols=None):
    """Wyswietla animacje losowania"""
    global disp, font_large, font_small, font_medium
    if not disp:
        return

    image = Image.new("RGB", (disp.width, disp.height), "BLACK")
    draw = ImageDraw.Draw(image)

    draw.text((15, 5), game_type, font=font_small, fill="CYAN")
    draw.line([(0, 18), (95, 18)], fill="CYAN", width=1)

    if symbols:
        draw.text((20, 30), symbols, font=font_large, fill="YELLOW")
    else:
        draw.text((15, 30), "...", font=font_large, fill="YELLOW")

    disp.ShowImage(image, 0, 0)

def oled_show_slots(symbols):
    """Wyswietla symbole slotow"""
    global disp, font_large, font_small
    if not disp:
        return

    image = Image.new("RGB", (disp.width, disp.height), "BLACK")
    draw = ImageDraw.Draw(image)

    draw.text((20, 2), "SLOTY", font=font_small, fill="MAGENTA")
    draw.line([(0, 16), (95, 16)], fill="MAGENTA", width=1)

    draw.rectangle([(5, 22), (90, 55)], outline="WHITE", width=1)

    draw.text((15, 28), symbols, font=font_large, fill="YELLOW")

    disp.ShowImage(image, 0, 0)

def oled_show_roulette(number, color_name):
    """Wyswietla wynik ruletki"""
    global disp, font_large, font_small, font_medium
    if not disp:
        return

    image = Image.new("RGB", (disp.width, disp.height), "BLACK")
    draw = ImageDraw.Draw(image)

    draw.text((15, 2), "RULETKA", font=font_small, fill="CYAN")
    draw.line([(0, 16), (95, 16)], fill="CYAN", width=1)

    if color_name == "czerwony":
        bg_color = "RED"
    elif color_name == "czarny":
        bg_color = "BLACK"
    else:
        bg_color = "GREEN"

    draw.ellipse([(25, 22), (70, 58)], fill=bg_color, outline="WHITE")

    num_str = str(number)
    draw.text((38, 30), num_str, font=font_medium, fill="WHITE")

    disp.ShowImage(image, 0, 0)

def oled_show_multiplier(multiplier, result):
    """Wyswietla mnoznik"""
    global disp, font_large, font_small, font_medium
    if not disp:
        return

    image = Image.new("RGB", (disp.width, disp.height), "BLACK")
    draw = ImageDraw.Draw(image)

    draw.text((10, 2), "MNOZNIK", font=font_small, fill="ORANGE")
    draw.line([(0, 16), (95, 16)], fill="ORANGE", width=1)

    draw.text((25, 25), f"x{multiplier}", font=font_large, fill="YELLOW")

    if result == "win":
        draw.text((20, 50), "WYGRANA!", font=font_small, fill="GREEN")
    else:
        draw.text((15, 50), "PRZEGRANA", font=font_small, fill="RED")

    disp.ShowImage(image, 0, 0)

def oled_show_message(line1, line2="", line3=""):
    """Wyswietla dowolny tekst"""
    global disp, font_small, font_medium
    if not disp:
        return

    image = Image.new("RGB", (disp.width, disp.height), "BLACK")
    draw = ImageDraw.Draw(image)

    draw.text((5, 5), line1, font=font_medium, fill="WHITE")
    if line2:
        draw.text((5, 25), line2, font=font_small, fill="CYAN")
    if line3:
        draw.text((5, 45), line3, font=font_small, fill="YELLOW")

    disp.ShowImage(image, 0, 0)

def rfid_read():
    """Odczytuje karte RFID, zwraca UID lub None"""
    reader = MFRC522()
    (status, TagType) = reader.MFRC522_Request(reader.PICC_REQIDL)
    if status == reader.MI_OK:
        (status, uid) = reader.MFRC522_Anticoll()
        if status == reader.MI_OK:
            print("przylozono")
            num = 0
            for i in range(len(uid)):
                num += uid[i] << (i * 8)
            return str(num)
    return None

def rfid_wait_for_card(timeout=30):
    """Czeka na karte RFID przez okreslony czas"""
    reader = MFRC522()
    start_time = time.time()

    while time.time() - start_time < timeout:
        (status, TagType) = reader.MFRC522_Request(reader.PICC_REQIDL)
        if status == reader.MI_OK:
            (status, uid) = reader.MFRC522_Anticoll()
            if status == reader.MI_OK:
                num = 0
                for i in range(len(uid)):
                    num += uid[i] << (i * 8)
                return str(num)
        time.sleep(0.1)
    return None

def cleanup():
    """Czysci GPIO"""
    leds_off()
    ws2812_off()
    buzzer(False)
    if disp:
        disp.clear()
    GPIO.cleanup()
