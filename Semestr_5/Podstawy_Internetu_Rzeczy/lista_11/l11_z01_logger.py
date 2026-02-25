#!/usr/bin/env python3

import json
import sqlite3
from datetime import datetime
import paho.mqtt.client as mqtt

BROKER = "localhost"
TOPIC = "rfid/access"
DB_FILE = "rfid_log.db"

def ensure_db():
    conn = sqlite3.connect(DB_FILE)
    cur = conn.cursor()
    cur.execute("""
        CREATE TABLE IF NOT EXISTS rfid_log (
            log_time TEXT,
            uid TEXT,
            terminal TEXT
        )
    """)
    conn.commit()
    conn.close()

def save_entry(log_time, uid, terminal):
    conn = sqlite3.connect(DB_FILE)
    cur = conn.cursor()
    cur.execute("INSERT INTO rfid_log VALUES (?,?,?)", (log_time, uid, terminal))
    conn.commit()
    conn.close()

def on_connect(client, userdata, flags, rc):
    if rc == 0:
        print("Połączono z brokerem MQTT.")
        client.subscribe(TOPIC)
        print("Subskrybowano temat:", TOPIC)
    else:
        print("Błąd połączenia z brokerem. Kod:", rc)

def on_message(client, userdata, msg):
    payload = msg.payload.decode('utf-8', errors='replace')
    try:
        data = json.loads(payload)
        uid = data.get("uid", "<brak>")
        timestamp = data.get("timestamp", datetime.now().isoformat(sep=' ', timespec='seconds'))
        terminal = data.get("terminal", "<unknown>")
    except Exception:
        uid = payload
        timestamp = datetime.now().isoformat(sep=' ', timespec='seconds')
        terminal = "<unknown>"

    print(f"[{timestamp}] Otrzymano UID={uid} z terminala {terminal}")
    save_entry(timestamp, uid, terminal)

def main():
    ensure_db()
    client = mqtt.Client()
    client.on_connect = on_connect
    client.on_message = on_message
    print("Łączenie z brokerem:", BROKER)
    client.connect(BROKER)
    client.loop_forever()

if __name__ == "__main__":
    main()