#!/usr/bin/env python3
# pylint: disable=no-member

import random
import time
import threading

SLOT_SYMBOLS = ['7', 'A', 'K', 'Q', 'J', '*', '$', '#']

ROULETTE_NUMBERS = {
    0: 'zielony',
    1: 'czerwony', 2: 'czarny', 3: 'czerwony', 4: 'czarny',
    5: 'czerwony', 6: 'czarny', 7: 'czerwony', 8: 'czarny',
    9: 'czerwony', 10: 'czarny', 11: 'czarny', 12: 'czerwony',
    13: 'czarny', 14: 'czerwony', 15: 'czarny', 16: 'czerwony',
    17: 'czarny', 18: 'czerwony', 19: 'czerwony', 20: 'czarny',
    21: 'czerwony', 22: 'czarny', 23: 'czerwony', 24: 'czarny',
    25: 'czerwony', 26: 'czarny', 27: 'czerwony', 28: 'czarny',
    29: 'czarny', 30: 'czerwony', 31: 'czarny', 32: 'czerwony',
    33: 'czarny', 34: 'czerwony', 35: 'czarny', 36: 'czerwony'
}


MULTIPLIERS = [0, 0, 0, 0, 0.5, 0.5, 1, 1, 1.5, 2, 3, 5, 10]


class SlotsGame:
    """Gra w automaty (sloty)"""

    COST = 10

    @staticmethod
    def spin():
        """Losuje 3 symbole"""
        return [random.choice(SLOT_SYMBOLS) for _ in range(3)]

    @staticmethod
    def calculate_win(symbols, bet):
        """Oblicza wygrana na podstawie symboli"""
        if symbols[0] == symbols[1] == symbols[2]:
            if symbols[0] == '7':
                return bet * 10
            elif symbols[0] == '$':
                return bet * 7
            elif symbols[0] == '*':
                return bet * 5
            else:
                return bet * 3
        elif symbols[0] == symbols[1] or symbols[1] == symbols[2] or symbols[0] == symbols[2]:
            return bet * 1.5
        return 0

    @staticmethod
    def animate_spin(hw, animations, duration=3.0):
        """Animacja losowania slotow"""
        if hw.pixels:
            anim_thread = threading.Thread(
                target=animations.wave_animation,
                args=(hw.pixels, duration)
            )
            anim_thread.start()

        steps = int(duration / 0.15)
        for i in range(steps):
            symbols = SlotsGame.spin()
            symbol_str = ' '.join(symbols)
            hw.oled_show_slots(symbol_str)
            time.sleep(0.15)

        animations.stop_animation()
        if hw.pixels:
            anim_thread.join()

        final_symbols = SlotsGame.spin()
        return final_symbols


class RouletteGame:
    """Gra w ruletke"""

    COST = 20

    @staticmethod
    def spin():
        """Losuje numer 0-36"""
        return random.randint(0, 36)

    @staticmethod
    def get_color(number):
        """Zwraca kolor numeru"""
        return ROULETTE_NUMBERS.get(number, 'zielony')

    @staticmethod
    def check_bet(number, bet_type, bet_value):
        """
        Sprawdza czy zaklad wygral
        bet_type: 'number', 'color', 'even_odd', 'half'
        """
        color = RouletteGame.get_color(number)

        if bet_type == 'number':
            return number == bet_value
        elif bet_type == 'color':
            return color == bet_value
        elif bet_type == 'even_odd':
            if number == 0:
                return False
            is_even = number % 2 == 0
            return (bet_value == 'parzyste' and is_even) or (bet_value == 'nieparzyste' and not is_even)
        elif bet_type == 'half':
            if number == 0:
                return False
            return (bet_value == '1-18' and 1 <= number <= 18) or (bet_value == '19-36' and 19 <= number <= 36)

        return False

    @staticmethod
    def calculate_win(bet_type, bet_amount):
        """Oblicza wyplate w zaleznosci od typu zakladu"""
        payouts = {
            'number': 35,
            'color': 1,
            'even_odd': 1,
            'half': 1
        }
        return bet_amount * (payouts.get(bet_type, 1) + 1)

    @staticmethod
    def animate_spin(hw, animations, duration=4.0):
        """Animacja kreczacej sie ruletki"""
        if hw.pixels:
            anim_thread = threading.Thread(
                target=animations.roulette_spin,
                args=(hw.pixels, duration)
            )
            anim_thread.start()

        steps = int(duration / 0.1)
        speed_increase = duration / steps

        for i in range(steps):
            num = random.randint(0, 36)
            color = RouletteGame.get_color(num)
            hw.oled_show_roulette(num, color)
            delay = 0.05 + (i * speed_increase / steps) * 0.15
            time.sleep(delay)

        animations.stop_animation()
        if hw.pixels:
            anim_thread.join()

        final_number = RouletteGame.spin()
        return final_number


class MultiplierGame:
    """Gra w mnoznik"""

    COST = 15

    @staticmethod
    def spin():
        """Losuje mnoznik"""
        return random.choice(MULTIPLIERS)

    @staticmethod
    def calculate_win(multiplier, bet):
        """Oblicza wygrana"""
        return int(bet * multiplier)

    @staticmethod
    def animate_spin(hw, animations, duration=2.5):
        """Animacja losowania mnoznika"""
        if hw.pixels:
            anim_thread = threading.Thread(
                target=animations.multiplier_pulse,
                args=(hw.pixels, duration)
            )
            anim_thread.start()

        steps = int(duration / 0.12)

        for i in range(steps):
            mult = random.choice(MULTIPLIERS)
            if mult == 0:
                result = "lose"
            else:
                result = "win"
            hw.oled_show_multiplier(mult, result)
            time.sleep(0.12)

        animations.stop_animation()
        if hw.pixels:
            anim_thread.join()

        final_multiplier = MultiplierGame.spin()
        return final_multiplier


def play_slots(hw, animations, bet=10):
    """Pelna rozgrywka slotow"""
    hw.oled_show_spinning("SLOTY", "...")

    symbols = SlotsGame.animate_spin(hw, animations)
    symbol_str = ' '.join(symbols)

    hw.oled_show_slots(symbol_str)
    time.sleep(0.5)

    win_amount = SlotsGame.calculate_win(symbols, bet)

    if win_amount > 0:
        hw.oled_show_result(symbol_str, win=True, amount=int(win_amount))
        animations.win_celebration(hw.pixels, hw.leds_blink, hw.buzzer_win)
        return int(win_amount)
    else:
        hw.oled_show_result(symbol_str, win=False, amount=bet)
        animations.lose_animation(hw.pixels, hw.leds_blink, hw.buzzer_lose)
        return 0


def play_roulette(hw, animations, bet_type, bet_value, bet_amount=20):
    """Pelna rozgrywka ruletki"""
    number = RouletteGame.animate_spin(hw, animations)
    color = RouletteGame.get_color(number)

    hw.oled_show_roulette(number, color)
    time.sleep(0.5)

    won = RouletteGame.check_bet(number, bet_type, bet_value)

    if won:
        win_amount = RouletteGame.calculate_win(bet_type, bet_amount)
        hw.oled_show_result(f"{number} {color}", win=True, amount=int(win_amount))
        animations.win_celebration(hw.pixels, hw.leds_blink, hw.buzzer_win)
        return int(win_amount)
    else:
        hw.oled_show_result(f"{number} {color}", win=False, amount=bet_amount)
        animations.lose_animation(hw.pixels, hw.leds_blink, hw.buzzer_lose)
        return 0


def play_multiplier(hw, animations, bet=15):
    """Pelna rozgrywka mnoznika"""
    multiplier = MultiplierGame.animate_spin(hw, animations)

    win_amount = MultiplierGame.calculate_win(multiplier, bet)

    if multiplier == 0:
        result = "lose"
    else:
        result = "win"

    hw.oled_show_multiplier(multiplier, result)
    time.sleep(0.5)

    if win_amount > 0:
        hw.oled_show_result(f"x{multiplier}", win=True, amount=win_amount)
        animations.win_celebration(hw.pixels, hw.leds_blink, hw.buzzer_win)
        return win_amount
    else:
        hw.oled_show_result(f"x{multiplier}", win=False, amount=bet)
        animations.lose_animation(hw.pixels, hw.leds_blink, hw.buzzer_lose)
        return 0
