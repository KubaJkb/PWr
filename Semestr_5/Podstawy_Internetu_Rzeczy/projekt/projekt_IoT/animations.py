#!/usr/bin/env python3
# pylint: disable=no-member

import time
import threading
from config import WS2812_COUNT

COLORS = [
    (255, 0, 0),
    (255, 127, 0),
    (255, 255, 0),
    (0, 255, 0),
    (0, 255, 255),
    (0, 0, 255),
    (127, 0, 255),
    (255, 0, 127),
]

_animation_running = False

def stop_animation():
    """Zatrzymuje biezaca animacje"""
    global _animation_running
    _animation_running = False

def wave_animation(pixels, duration=3.0, speed=0.05):
    """Animacja fali kolorowych swiatel dla slotow"""
    global _animation_running
    _animation_running = True
    start_time = time.time()
    offset = 0

    while _animation_running and (time.time() - start_time) < duration:
        for i in range(WS2812_COUNT):
            color_idx = (i + offset) % len(COLORS)
            pixels[i] = COLORS[color_idx]
        pixels.show()
        offset = (offset + 1) % len(COLORS)
        time.sleep(speed)

    pixels.fill((0, 0, 0))
    pixels.show()
    _animation_running = False

def rainbow_cycle(pixels, duration=2.0, speed=0.02):
    """Animacja teczy dla wygranej"""
    global _animation_running
    _animation_running = True
    start_time = time.time()

    while _animation_running and (time.time() - start_time) < duration:
        for j in range(256):
            if not _animation_running:
                break
            for i in range(WS2812_COUNT):
                pixel_index = (i * 256 // WS2812_COUNT) + j
                pixels[i] = wheel(pixel_index & 255)
            pixels.show()
            time.sleep(speed)

    pixels.fill((0, 0, 0))
    pixels.show()
    _animation_running = False

def wheel(pos):
    """Generuje kolor teczy na podstawie pozycji 0-255"""
    if pos < 85:
        return (pos * 3, 255 - pos * 3, 0)
    elif pos < 170:
        pos -= 85
        return (255 - pos * 3, 0, pos * 3)
    else:
        pos -= 170
        return (0, pos * 3, 255 - pos * 3)

def flash_red(pixels, times=3, delay=0.2):
    """Miga czerwonym - dla przegranej"""
    for _ in range(times):
        pixels.fill((255, 0, 0))
        pixels.show()
        time.sleep(delay)
        pixels.fill((0, 0, 0))
        pixels.show()
        time.sleep(delay)

def flash_green(pixels, times=3, delay=0.2):
    """Miga zielonym - dla wygranej"""
    for _ in range(times):
        pixels.fill((0, 255, 0))
        pixels.show()
        time.sleep(delay)
        pixels.fill((0, 0, 0))
        pixels.show()
        time.sleep(delay)

def roulette_spin(pixels, duration=3.0, speed_start=0.03, speed_end=0.3):
    """Animacja kreczacej sie ruletki - jeden swiecacy LED krazy"""
    global _animation_running
    _animation_running = True
    start_time = time.time()
    position = 0

    while _animation_running and (time.time() - start_time) < duration:
        elapsed = time.time() - start_time
        progress = elapsed / duration

        current_speed = speed_start + (speed_end - speed_start) * progress

        pixels.fill((0, 0, 0))

        pixels[position] = (255, 255, 0)

        prev_pos = (position - 1) % WS2812_COUNT
        pixels[prev_pos] = (100, 100, 0)
        pixels.show()

        position = (position + 1) % WS2812_COUNT
        time.sleep(current_speed)

    pixels.fill((0, 0, 0))
    pixels.show()
    _animation_running = False

def multiplier_pulse(pixels, duration=2.0):
    """Animacja pulsowania dla mnoznika"""
    global _animation_running
    _animation_running = True
    start_time = time.time()

    while _animation_running and (time.time() - start_time) < duration:

        for brightness in range(0, 256, 10):
            if not _animation_running:
                break
            pixels.fill((brightness, brightness // 2, 0))
            pixels.show()
            time.sleep(0.02)

        for brightness in range(255, -1, -10):
            if not _animation_running:
                break
            pixels.fill((brightness, brightness // 2, 0))
            pixels.show()
            time.sleep(0.02)

    pixels.fill((0, 0, 0))
    pixels.show()
    _animation_running = False

def loading_animation(pixels, duration=1.0):
    """Animacja ladowania"""
    global _animation_running
    _animation_running = True
    start_time = time.time()
    position = 0

    while _animation_running and (time.time() - start_time) < duration:
        pixels.fill((0, 0, 0))
        pixels[position] = (0, 0, 255)
        pixels[(position + 1) % WS2812_COUNT] = (0, 0, 128)
        pixels.show()
        position = (position + 1) % WS2812_COUNT
        time.sleep(0.1)

    pixels.fill((0, 0, 0))
    pixels.show()
    _animation_running = False

def win_celebration(pixels, leds_blink_func, buzzer_win_func):
    """Pelna animacja wygranej"""
    t1 = threading.Thread(target=rainbow_cycle, args=(pixels, 2.0))
    t2 = threading.Thread(target=leds_blink_func, args=(5, 0.15))

    t1.start()
    t2.start()
    buzzer_win_func()

    t1.join()
    t2.join()

    flash_green(pixels, 3, 0.15)

def lose_animation(pixels, leds_blink_func, buzzer_lose_func):
    """Pelna animacja przegranej"""
    t1 = threading.Thread(target=flash_red, args=(pixels, 3, 0.2))
    t2 = threading.Thread(target=leds_blink_func, args=(3, 0.2))

    t1.start()
    t2.start()
    buzzer_lose_func()

    t1.join()
    t2.join()
