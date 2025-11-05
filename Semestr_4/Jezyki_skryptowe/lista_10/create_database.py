import sqlite3
import sys
import os


def create_database(db_name: str):
    ddl_statements = [
        """
        CREATE TABLE IF NOT EXISTS Stations (
            station_id INTEGER PRIMARY KEY AUTOINCREMENT,
            station_name TEXT NOT NULL UNIQUE
        );
        """,
        """
        CREATE TABLE IF NOT EXISTS Rentals (
            rental_id INTEGER PRIMARY KEY,
        bike_number TEXT NOT NULL,
        start_time INTEGER NOT NULL,
        end_time INTEGER NOT NULL,
        rental_station_id INTEGER NOT NULL,
        return_station_id INTEGER NOT NULL,
        duration INTEGER NOT NULL,
        FOREIGN KEY (rental_station_id) REFERENCES Stations(station_id),
        FOREIGN KEY (return_station_id) REFERENCES Stations(station_id)
        );
        """,
        """
        PRAGMA foreign_keys = ON;
        """
    ]

    conn = sqlite3.connect(db_name)
    cursor = conn.cursor()
    for ddl in ddl_statements:
        cursor.execute(ddl)
    conn.commit()
    conn.close()

def main():
    if len(sys.argv) != 2:
        print("Użycie: python create_database.py <nazwa_bazy>")
        sys.exit(1)

    db_name = sys.argv[1]
    if not db_name.endswith(".sqlite3"):
        db_name = f"{db_name}.sqlite3"

    if os.path.exists(db_name):
        print(f"Plik '{db_name}' już istnieje. Zostanie on nadpisany przez nowy plik.")
        os.remove(db_name)

    create_database(db_name)
    print(f"Baza danych '{db_name}' została utworzona.")

if __name__ == '__main__':
    main()