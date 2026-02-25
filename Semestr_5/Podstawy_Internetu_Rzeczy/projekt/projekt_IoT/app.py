#!/usr/bin/env python3
# pylint: disable=no-member

import os
import sys
import time
import threading
import json
from flask import Flask, render_template, jsonify, request, session

sys.path.insert(0, os.path.dirname(__file__))
sys.path.insert(0, os.path.join(os.path.dirname(__file__), '..'))

from config import SERVER_HOST, SERVER_PORT
import database as db

try:
    import hardware as hw
    import animations
    import games
    HARDWARE_AVAILABLE = True

    hw.init_oled()
    hw.init_ws2812()
    print("Hardware zainicjalizowany pomyslnie")
except Exception as e:
    HARDWARE_AVAILABLE = False
    print(f"Hardware niedostepny (tryb symulacji): {e}")

app = Flask(__name__, static_folder='static', template_folder='templates')
app.secret_key = 'casino_secret_key_12345'

current_user = None
current_user_lock = threading.Lock()

game_in_progress = False
game_lock = threading.Lock()


def check_rfid_loop():
    """Petla sprawdzajaca karte RFID w tle"""
    global current_user

    while True:
        if HARDWARE_AVAILABLE:
            rfid_uid = hw.rfid_read()
            if rfid_uid:
                with current_user_lock:
                    user = db.get_or_create_user(rfid_uid)
                    current_user = user

                    hw.oled_show_balance(user['balance'])
                    hw.buzzer_beep(0.1)
                    hw.led_sequence()
                print(f"Zalogowano uzytkownika: {rfid_uid}")
        time.sleep(0.5)


@app.route('/')
def index():
    """Strona glowna"""
    return render_template('index.html')


@app.route('/api/status')
def api_status():
    """Zwraca status zalogowania i balans"""
    global current_user
    with current_user_lock:
        if current_user:

            current_user['balance'] = db.get_balance(current_user['id'])
            return jsonify({
                'logged_in': True,
                'user_id': current_user['id'],
                'balance': current_user['balance']
            })
        return jsonify({'logged_in': False, 'balance': 0})


@app.route('/api/logout', methods=['POST'])
def api_logout():
    """Wylogowanie"""
    global current_user
    with current_user_lock:
        current_user = None
        if HARDWARE_AVAILABLE:
            hw.oled_show_message("Wylogowano", "Przyloz karte", "aby grac")
    return jsonify({'success': True})


@app.route('/api/play/slots', methods=['POST'])
def api_play_slots():
    """Gra w sloty"""
    global current_user, game_in_progress

    with current_user_lock:
        if not current_user:
            return jsonify({'error': 'Nie zalogowano'}), 401

        user_id = current_user['id']
        balance = db.get_balance(user_id)

    bet = games.SlotsGame.COST

    if balance < bet:
        return jsonify({'error': 'Za malo srodkow'}), 400

    with game_lock:
        if game_in_progress:
            return jsonify({'error': 'Gra w toku'}), 400
        game_in_progress = True

    try:

        db.subtract_from_balance(user_id, bet)

        if HARDWARE_AVAILABLE:
            hw.oled_show_balance(balance - bet, bet)
            time.sleep(1)

            win_amount = games.play_slots(hw, animations, bet)
        else:

            symbols = games.SlotsGame.spin()
            win_amount = int(games.SlotsGame.calculate_win(symbols, bet))
            time.sleep(1)

        if win_amount > 0:
            db.add_to_balance(user_id, win_amount)
            result = 'win'
        else:
            result = 'lose'

        db.add_game_history(user_id, 'slots', bet, win_amount, result)

        new_balance = db.get_balance(user_id)
        with current_user_lock:
            if current_user and current_user['id'] == user_id:
                current_user['balance'] = new_balance

        if HARDWARE_AVAILABLE:
            time.sleep(2)
            hw.oled_show_balance(new_balance)

        return jsonify({
            'success': True,
            'win_amount': win_amount,
            'new_balance': new_balance,
            'result': result
        })

    finally:
        with game_lock:
            game_in_progress = False


@app.route('/api/play/roulette', methods=['POST'])
def api_play_roulette():
    """Gra w ruletke"""
    global current_user, game_in_progress

    with current_user_lock:
        if not current_user:
            return jsonify({'error': 'Nie zalogowano'}), 401

        user_id = current_user['id']
        balance = db.get_balance(user_id)

    data = request.get_json() or {}
    bet_type = data.get('bet_type', 'color')
    bet_value = data.get('bet_value', 'czerwony')
    bet_amount = games.RouletteGame.COST

    if balance < bet_amount:
        return jsonify({'error': 'Za malo srodkow'}), 400

    with game_lock:
        if game_in_progress:
            return jsonify({'error': 'Gra w toku'}), 400
        game_in_progress = True

    try:
        db.subtract_from_balance(user_id, bet_amount)

        if HARDWARE_AVAILABLE:
            hw.oled_show_balance(balance - bet_amount, bet_amount)
            time.sleep(1)

            win_amount = games.play_roulette(hw, animations, bet_type, bet_value, bet_amount)
        else:
            number = games.RouletteGame.spin()
            won = games.RouletteGame.check_bet(number, bet_type, bet_value)
            if won:
                win_amount = int(games.RouletteGame.calculate_win(bet_type, bet_amount))
            else:
                win_amount = 0
            time.sleep(1)

        if win_amount > 0:
            db.add_to_balance(user_id, win_amount)
            result = 'win'
        else:
            result = 'lose'

        db.add_game_history(user_id, 'roulette', bet_amount, win_amount, result)

        new_balance = db.get_balance(user_id)
        with current_user_lock:
            if current_user and current_user['id'] == user_id:
                current_user['balance'] = new_balance

        if HARDWARE_AVAILABLE:
            time.sleep(2)
            hw.oled_show_balance(new_balance)

        return jsonify({
            'success': True,
            'win_amount': win_amount,
            'new_balance': new_balance,
            'result': result
        })

    finally:
        with game_lock:
            game_in_progress = False


@app.route('/api/play/multiplier', methods=['POST'])
def api_play_multiplier():
    """Gra w mnoznik"""
    global current_user, game_in_progress

    with current_user_lock:
        if not current_user:
            return jsonify({'error': 'Nie zalogowano'}), 401

        user_id = current_user['id']
        balance = db.get_balance(user_id)

    bet = games.MultiplierGame.COST

    if balance < bet:
        return jsonify({'error': 'Za malo srodkow'}), 400

    with game_lock:
        if game_in_progress:
            return jsonify({'error': 'Gra w toku'}), 400
        game_in_progress = True

    try:
        db.subtract_from_balance(user_id, bet)

        if HARDWARE_AVAILABLE:
            hw.oled_show_balance(balance - bet, bet)
            time.sleep(1)

            win_amount = games.play_multiplier(hw, animations, bet)
        else:
            multiplier = games.MultiplierGame.spin()
            win_amount = games.MultiplierGame.calculate_win(multiplier, bet)
            time.sleep(1)

        if win_amount > 0:
            db.add_to_balance(user_id, win_amount)
            result = 'win'
        else:
            result = 'lose'

        db.add_game_history(user_id, 'multiplier', bet, win_amount, result)

        new_balance = db.get_balance(user_id)
        with current_user_lock:
            if current_user and current_user['id'] == user_id:
                current_user['balance'] = new_balance

        if HARDWARE_AVAILABLE:
            time.sleep(2)
            hw.oled_show_balance(new_balance)

        return jsonify({
            'success': True,
            'win_amount': win_amount,
            'new_balance': new_balance,
            'result': result
        })

    finally:
        with game_lock:
            game_in_progress = False


@app.route('/api/history')
def api_history():
    """Historia gier"""
    global current_user

    with current_user_lock:
        if not current_user:
            return jsonify({'error': 'Nie zalogowano'}), 401

        user_id = current_user['id']

    history = db.get_user_history(user_id, 20)
    return jsonify({'history': history})


@app.route('/api/game_status')
def api_game_status():
    """Status gry - czy jest w toku"""
    global game_in_progress
    with game_lock:
        return jsonify({'in_progress': game_in_progress})


def main():
    """Glowna funkcja uruchamiajaca serwer"""
    if HARDWARE_AVAILABLE:
        rfid_thread = threading.Thread(target=check_rfid_loop, daemon=True)
        rfid_thread.start()
        hw.oled_show_message("KASYNO", "Przyloz karte", "aby grac")

    print(f"Serwer uruchomiony na http://{SERVER_HOST}:{SERVER_PORT}")
    app.run(host=SERVER_HOST, port=SERVER_PORT, debug=False, threaded=True)


if __name__ == '__main__':
    try:
        main()
    except KeyboardInterrupt:
        print("\nZamykanie...")
        if HARDWARE_AVAILABLE:
            hw.cleanup()
