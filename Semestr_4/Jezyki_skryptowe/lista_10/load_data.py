import sqlite3
import sys
import os
import csv
from datetime import datetime


def ensure_station(cursor, station_name: str) -> int:
    cursor.execute("INSERT OR IGNORE INTO Stations (station_name) VALUES (?);", (station_name,))
    cursor.execute("SELECT station_id FROM Stations WHERE station_name = ?;", (station_name,))
    row = cursor.fetchone()
    return row[0]

def parse_datetime_to_unix(date_str: str) -> int:
    dt = datetime.strptime(date_str, '%Y-%m-%d %H:%M:%S')
    return int(dt.timestamp())

def load_csv_into_db(csv_path: str, db_name: str):
    if not os.path.exists(csv_path):
        print(f"Plik CSV nie istnieje: {csv_path}")
        sys.exit(1)
    if not os.path.exists(db_name):
        print(f"Baza danych nie istnieje: {db_name}")
        sys.exit(1)

    conn = sqlite3.connect(db_name)
    cursor = conn.cursor()

    with open(csv_path, 'r', encoding='utf-8') as f:
        reader = csv.DictReader(f)
        for row in reader:
            rental_id           = int(row['UID wynajmu'])
            bike_number         = row['Numer roweru']
            start_ts            = parse_datetime_to_unix(row['Data wynajmu'])
            end_ts              = parse_datetime_to_unix(row['Data zwrotu'])
            duration            = int(row['Czas trwania'])
            rental_station_name = row['Stacja wynajmu']
            return_station_name = row['Stacja zwrotu']

            rental_station_id = ensure_station(cursor, rental_station_name)
            return_station_id = ensure_station(cursor, return_station_name)

            cursor.execute(
            """
                INSERT OR IGNORE INTO Rentals 
                (rental_id, bike_number, start_time, end_time, rental_station_id, return_station_id, duration)
                VALUES (?, ?, ?, ?, ?, ?, ?);
                """,
            (rental_id, bike_number, start_ts, end_ts, rental_station_id, return_station_id, duration)
            )

    conn.commit()
    conn.close()
    print(f"Wczytano rekordy z pliku '{csv_path}' do bazy danych '{db_name}'.")

def main():
    if len(sys.argv) != 3:
        print("Użycie: python load_data.py <plik_csv> <nazwa_bazy>")
        sys.exit(1)

    csv_arg  = sys.argv[1]
    db_name = sys.argv[2]
    if not db_name.endswith(".sqlite3"):
        db_name = f"{db_name}.sqlite3"

    if csv_arg.lower() == "all":
        for month in range(1, 13):
            mm = f"{month:02d}"
            csv_arg = f"historia_przejazdow_2021-{mm}.csv"
            load_csv_into_db(csv_arg, db_name)
        print("Załadowano wszystkie pliki historii przejazdów z 2021 roku!")
    else:
        load_csv_into_db(csv_arg , db_name)

if __name__ == '__main__':
    main()