import argparse
import csv
import logging
import random
import sys
from datetime import datetime
from pathlib import Path
from statistics import mean, stdev

import zr2

# ------------- Konfiguracja loggera -------------
logger = logging.getLogger("air_quality_cli")
logger.setLevel(logging.DEBUG)

stdout_handler = logging.StreamHandler(sys.stdout)
stderr_handler = logging.StreamHandler(sys.stderr)

stdout_handler.setLevel(logging.DEBUG)
stderr_handler.setLevel(logging.ERROR)

stdout_formatter = logging.Formatter("[%(levelname)s] %(message)s")
stderr_formatter = logging.Formatter("[%(levelname)s] %(message)s")
stdout_handler.setFormatter(stdout_formatter)
stderr_handler.setFormatter(stderr_formatter)

logger.addHandler(stdout_handler)
logger.addHandler(stderr_handler)


# ------------- Pomocnicze funkcje -------------
def parse_date(date_str):
    return datetime.strptime(date_str, "%Y-%m-%d")


def read_measurement_file(file_path: Path, start_date, end_date):
    data = []
    try:
        logger.info(f"Otwieranie pliku: {file_path}")
        with file_path.open("r", encoding="utf-8") as f:
            reader = csv.reader(f)
            headers = [next(reader) for _ in range(6)]

            station_codes = headers[1][1:]
            time_format = "%m/%d/%y %H:%M"

            for row in reader:
                byte_count = len(','.join(row).encode("utf-8"))
                logger.debug(f"Przeczytano {byte_count} bajtów")

                try:
                    timestamp = datetime.strptime(row[0], time_format)
                except ValueError:
                    continue

                if not (start_date <= timestamp <= end_date):
                    continue

                for i, value in enumerate(row[1:], start=0):
                    try:
                        data.append((station_codes[i], float(value)))
                    except ValueError:
                        continue
    except FileNotFoundError:
        logger.error(f"Plik {file_path} nie istnieje.")
        sys.exit(1)
    finally:
        logger.info(f"Zamknięto plik: {file_path}")
    return data


def get_matching_file(data_dir: Path, variable: str, freq: str) -> Path | None:
    for file in data_dir.iterdir():
        if file.is_file():
            if f"_{variable}_{freq}.csv" in file.name:
                return file
    logger.warning("Nie znaleziono pliku z podaną wielkością i częstotliwością.")
    return None


def load_stations(stations_path: Path) -> list[dict] | None:
    stations = []
    try:
        logger.info(f"Otwieranie pliku: {stations_path}")
        with stations_path.open("r", encoding="utf-8") as f:
            reader = csv.DictReader(f)
            for row in reader:
                stations.append(row)
                byte_count = len(str(row).encode("utf-8"))
                logger.debug(f"Przeczytano {byte_count} bajtów")
    except FileNotFoundError:
        logger.error("Plik ze stacjami nie istnieje.")
        sys.exit(1)
    finally:
        logger.info("Zamknięto plik stacji.")
    return stations


# ------------- Podkomendy -------------
def cmd_random_station(args):
    file = get_matching_file(args.data_dir, args.variable, args.freq)
    if not file:
        return

    data = read_measurement_file(file, args.start_date, args.end_date)
    if not data:
        logger.warning("Brak pomiarów dla podanych parametrów.")
        return

    stations = load_stations(args.stations)
    available_codes = {station_code for station_code, _ in data}

    matching = [s for s in stations if s['Kod stacji'] in available_codes]
    if not matching:
        logger.warning("Nie znaleziono żadnej pasującej stacji.")
        return

    station = random.choice(matching)
    print(f"Nazwa stacji: {station['Nazwa stacji']}\nAdres: {station['Adres']}")


def cmd_stats(args):
    file = get_matching_file(args.data_dir, args.variable, args.freq)
    if not file:
        return

    data = read_measurement_file(file, args.start_date, args.end_date)
    values = [value for code, value in data if code == args.station]

    if not values:
        logger.warning("Brak danych dla wybranej stacji i przedziału czasu.")
        return

    print(f"Średnia: {mean(values):.3f}")
    if len(values) > 1:
        print(f"Odchylenie standardowe: {stdev(values):.3f}")
    else:
        print("Za mało danych do obliczenia odchylenia standardowego.")

# ************************************* ZR2 ************************************
def cmd_anomalies(args):
    file = get_matching_file(args.data_dir, args.variable, args.freq)
    if not file:
        return

    raw_data = read_measurement_file(file, args.start_date, args.end_date)

    structured = [(args.start_date, v, s, args.variable) for s, v in raw_data]

    anomalies = zr2.detect_anomalies(structured, args.variable)
    for category, entries in anomalies.items():
        print(f"\n{category.upper()}:")
        for e in entries:
            print("  ", e)
# ************************************* ZR2 ************************************


def main():
    parser = argparse.ArgumentParser(description="CLI do analizy pomiarów jakości powietrza")
    parser.add_argument("--data-dir", type=Path, required=True, help="Katalog z plikami pomiarowymi (*.csv)")
    parser.add_argument("--stations", type=Path, required=True, help="Plik CSV z metadanymi stacji")
    parser.add_argument("--variable", required=True, help="Mierzona wielkość (np. PM10, CO)")
    parser.add_argument("--freq", required=True, help="Częstotliwość pomiaru (np. 1g, 24g)")
    parser.add_argument("--start-date", type=parse_date, required=True, help="Początek przedziału (rrrr-mm-dd)")
    parser.add_argument("--end-date", type=parse_date, required=True, help="Koniec przedziału (rrrr-mm-dd)")

    subparsers = parser.add_subparsers(dest="command", required=True)

    random_parser = subparsers.add_parser("random", help="Wypisz losową stację mierzącą daną wielkość")
    random_parser.set_defaults(func=cmd_random_station)

    stats_parser = subparsers.add_parser("stats", help="Statystyki dla danej stacji")
    stats_parser.add_argument("--station", required=True, help="Kod stacji do analizy")
    stats_parser.set_defaults(func=cmd_stats)

    # ************************************* ZR2 ************************************
    anomaly_parser = subparsers.add_parser("anomalies", help="Wykryj anomalie w pomiarach")
    anomaly_parser.set_defaults(func=cmd_anomalies)
    # ************************************* ZR2 ************************************

    args = parser.parse_args()
    args.func(args)


if __name__ == "__main__":
    main()
