import csv
from datetime import datetime
from typing import Dict, List, Any


def parse_stations_metadata(filepath: str) -> List[Dict[str, Any]]:
    stations = []
    with open(filepath, newline='', encoding='utf-8') as csvfile:
        reader = csv.DictReader(csvfile)
        for row in reader:
            row['Data uruchomienia'] = datetime.strptime(row['Data uruchomienia'], '%Y-%m-%d') if row[
                'Data uruchomienia'] else None
            row['Data zamknięcia'] = datetime.strptime(row['Data zamknięcia'], '%Y-%m-%d') if row[
                'Data zamknięcia'] else None
            row['WGS84 φ N'] = float(row['WGS84 φ N']) if row['WGS84 φ N'] else None
            row['WGS84 λ E'] = float(row['WGS84 λ E']) if row['WGS84 λ E'] else None
            stations.append(row)
    return stations


def parse_measurement_file(filepath: str) -> Dict[str, Any]:
    with open(filepath, newline='', encoding='utf-8') as csvfile:
        reader = csv.reader(csvfile)
        rows = list(reader)

        metadata = {
            'numbers': rows[0][1:],         # "Nr"
            'station_codes': rows[1][1:],   # "Kod stacji"
            'indicator': rows[2][1],        # "Wskaźnik"
            'averaging_time': rows[3][1],   # "Czas uśredniania"
            'unit': rows[4][1],             # "Jednostka"
            'station_ids': rows[5][1:]      # "Kod stanowiska"
        }

        measurements = []
        for row in rows[6:]:
            timestamp_str = row[0]
            try:
                timestamp = datetime.strptime(timestamp_str, "%m/%d/%y %H:%M")
            except ValueError:
                timestamp = timestamp_str

            values = []
            for val in row[1:]:
                try:
                    values.append(float(val) if val else None)
                except ValueError:
                    values.append(None)

            measurements.append({
                'date': timestamp,
                'values': values
            })

        return {
            'metadata': metadata,
            'measurements': measurements
        }
