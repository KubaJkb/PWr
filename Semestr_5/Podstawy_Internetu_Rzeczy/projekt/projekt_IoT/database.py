#!/usr/bin/env python3

import sqlite3
import os
from config import DATABASE_FILE, STARTING_BALANCE

DB_PATH = os.path.join(os.path.dirname(__file__), DATABASE_FILE)


def init_db():
    """Inicjalizuje baze danych"""
    conn = sqlite3.connect(DB_PATH)
    cursor = conn.cursor()

    cursor.execute('''
        CREATE TABLE IF NOT EXISTS users (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            rfid_uid TEXT UNIQUE NOT NULL,
            balance INTEGER DEFAULT 1000,
            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        )
    ''')

    cursor.execute('''
        CREATE TABLE IF NOT EXISTS game_history (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            user_id INTEGER,
            game_type TEXT,
            bet_amount INTEGER,
            win_amount INTEGER,
            result TEXT,
            played_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            FOREIGN KEY (user_id) REFERENCES users(id)
        )
    ''')

    conn.commit()
    conn.close()


def get_user_by_rfid(rfid_uid):
    """Pobiera uzytkownika po UID karty RFID"""
    conn = sqlite3.connect(DB_PATH)
    cursor = conn.cursor()
    cursor.execute('SELECT id, rfid_uid, balance FROM users WHERE rfid_uid = ?', (rfid_uid,))
    user = cursor.fetchone()
    conn.close()

    if user:
        return {'id': user[0], 'rfid_uid': user[1], 'balance': user[2]}
    return None


def create_user(rfid_uid):
    """Tworzy nowego uzytkownika"""
    conn = sqlite3.connect(DB_PATH)
    cursor = conn.cursor()

    try:
        cursor.execute(
            'INSERT INTO users (rfid_uid, balance) VALUES (?, ?)',
            (rfid_uid, STARTING_BALANCE)
        )
        conn.commit()
        user_id = cursor.lastrowid
        conn.close()
        return {'id': user_id, 'rfid_uid': rfid_uid, 'balance': STARTING_BALANCE}
    except sqlite3.IntegrityError:
        conn.close()
        return get_user_by_rfid(rfid_uid)


def get_or_create_user(rfid_uid):
    """Pobiera uzytkownika lub tworzy nowego"""
    user = get_user_by_rfid(rfid_uid)
    if user:
        return user
    return create_user(rfid_uid)


def update_balance(user_id, new_balance):
    """Aktualizuje balans uzytkownika"""
    conn = sqlite3.connect(DB_PATH)
    cursor = conn.cursor()
    cursor.execute('UPDATE users SET balance = ? WHERE id = ?', (new_balance, user_id))
    conn.commit()
    conn.close()


def add_to_balance(user_id, amount):
    """Dodaje do balansu uzytkownika"""
    conn = sqlite3.connect(DB_PATH)
    cursor = conn.cursor()
    cursor.execute('UPDATE users SET balance = balance + ? WHERE id = ?', (amount, user_id))
    conn.commit()
    conn.close()


def subtract_from_balance(user_id, amount):
    """Odejmuje od balansu uzytkownika"""
    conn = sqlite3.connect(DB_PATH)
    cursor = conn.cursor()
    cursor.execute('UPDATE users SET balance = balance - ? WHERE id = ?', (amount, user_id))
    conn.commit()
    conn.close()


def get_balance(user_id):
    """Pobiera aktualny balans uzytkownika"""
    conn = sqlite3.connect(DB_PATH)
    cursor = conn.cursor()
    cursor.execute('SELECT balance FROM users WHERE id = ?', (user_id,))
    result = cursor.fetchone()
    conn.close()
    return result[0] if result else 0


def add_game_history(user_id, game_type, bet_amount, win_amount, result):
    """Dodaje wpis do historii gier"""
    conn = sqlite3.connect(DB_PATH)
    cursor = conn.cursor()
    cursor.execute('''
        INSERT INTO game_history (user_id, game_type, bet_amount, win_amount, result)
        VALUES (?, ?, ?, ?, ?)
    ''', (user_id, game_type, bet_amount, win_amount, result))
    conn.commit()
    conn.close()


def get_user_history(user_id, limit=10):
    """Pobiera historie gier uzytkownika"""
    conn = sqlite3.connect(DB_PATH)
    cursor = conn.cursor()
    cursor.execute('''
        SELECT game_type, bet_amount, win_amount, result, played_at
        FROM game_history
        WHERE user_id = ?
        ORDER BY played_at DESC
        LIMIT ?
    ''', (user_id, limit))
    history = cursor.fetchall()
    conn.close()

    return [
        {
            'game_type': row[0],
            'bet_amount': row[1],
            'win_amount': row[2],
            'result': row[3],
            'played_at': row[4]
        }
        for row in history
    ]

init_db()
