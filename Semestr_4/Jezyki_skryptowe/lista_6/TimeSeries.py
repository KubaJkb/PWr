from pathlib import Path
from datetime import datetime, date
from typing import *
import numpy as np
import csv


class TimeSeries:
    def __init__(self, filename: str, station_code: str):
        self.station_code = station_code            # b.
        self.filepath = Path("data/measurements") / filename

        with open(self.filepath, encoding="utf-8") as f:
            reader = csv.reader(f)
            rows = list(reader)

        header_station_codes = rows[1]
        try:
            self.col_index = header_station_codes.index(station_code)
        except ValueError:
            raise ValueError(f"Nie znaleziono stacji {station_code} w pliku {filename}")

        self.indicator = rows[2][self.col_index]    # a.
        self.avg_time = rows[3][self.col_index]     # c.
        self.unit = rows[4][self.col_index]         # f.

        self.dates = []                             # d.
        self.values = []                            # e.

        for row in rows[6:]:
            if not row:
                continue
            try:
                dt = datetime.strptime(row[0], "%d/%m/%y %H:%M")
                self.dates.append(dt)

                val_str = row[self.col_index]
                value = float(val_str) if val_str else None
                self.values.append(value)
            except (ValueError, IndexError):
                continue

        self.values = np.array(self.values, dtype=object)

    def __getitem__(self, key: Union[int, slice, datetime, date]) -> Union[Tuple[datetime, float], List[Tuple[datetime, float]], float, List[float]]:
        if isinstance(key, (int, slice)):
            if isinstance(key, int):
                return self.dates[key], self.values[key]
            else:
                return list(zip(self.dates[key], self.values[key]))

        elif isinstance(key, (datetime, date)):
            results = []
            for dt, val in zip(self.dates, self.values):
                if (isinstance(key, datetime) and dt == key) or \
                        (isinstance(key, date) and dt.date() == key):
                    results.append(val)

            if not results:
                raise KeyError(f"Brak danych dla daty {key}")
            if len(results) == 1:
                return results[0]
            else:
                return results

        else:
            raise TypeError("Nieobsługiwany typ klucza")

    @property
    def mean(self) -> Union[float, None]:
        valid = [v for v in self.values if v is not None]
        return float(np.mean(valid)) if valid else None

    @property
    def stddev(self) -> Union[float, None]:
        valid = [v for v in self.values if v is not None]
        return float(np.std(valid)) if valid else None

    def __str__(self):
        return (f"TimeSeries for station '{self.station_code}'\n"
                f"Indicator: {self.indicator}, Averaging Time: {self.avg_time}, Unit: {self.unit}\n"
                f"Records: {len(self.dates)}, File: {self.filepath.name}")

    def __repr__(self):
        return (f"TimeSeries(filename='{self.filepath.name}', "
                f"station_code='{self.station_code}', "
                f"records={len(self.dates)})")
