from pathlib import Path
from datetime import datetime, date
from numpy import mean, std
from typing import List, Sequence, Optional, Tuple, Union
import csv


class TimeSeries:
    def __init__(self, filename: str, station_code: str) -> None:
        self.station_code: str = station_code
        self.filepath: Path = Path("data/measurements") / filename

        with open(self.filepath, encoding="utf-8") as f:
            reader = csv.reader(f)
            rows: List[Sequence[str]] = list(reader)

        header_station_codes: Sequence[str] = rows[1]
        try:
            self.col_index: int = header_station_codes.index(station_code)
        except ValueError:
            raise ValueError(f"Nie znaleziono stacji {station_code} w pliku {filename}")

        self.indicator: str = rows[2][self.col_index]
        self.avg_time: str = rows[3][self.col_index]
        self.unit: str = rows[4][self.col_index]

        self.dates: List[datetime] = []
        self.values: List[Optional[float]] = []

        for row in rows[6:]:
            if not row:
                continue
            try:
                dt: datetime = datetime.strptime(row[0], "%d/%m/%y %H:%M")
                self.dates.append(dt)

                val_str: str = row[self.col_index]
                value: Optional[float] = float(val_str) if val_str else None
                self.values.append(value)
            except (ValueError, IndexError):
                continue

    def __getitem__(self, key: Union[int, slice, datetime, date]) -> Union[Tuple[datetime, Optional[float]],
    List[Tuple[datetime, Optional[float]]],
    Optional[float],
    List[Optional[float]]]:
        if isinstance(key, (int, slice)):
            if isinstance(key, int):
                return self.dates[key], self.values[key]
            else:
                return list(zip(self.dates[key], self.values[key]))

        elif isinstance(key, (datetime, date)):
            results: List[Optional[float]] = []
            for dt, val in zip(self.dates, self.values):
                if (isinstance(key, datetime) and dt == key) or \
                        (isinstance(key, date) and dt.date() == key):
                    results.append(val)

            if not results:
                raise KeyError(f"Brak danych dla daty {key}")
            return results[0] if len(results) == 1 else results

        raise TypeError("Nieobsługiwany typ klucza")

    @property
    def mean(self) -> Optional[float]:
        valid: List[float] = [v for v in self.values if v is not None]
        return float(mean(valid)) if valid else None

    @property
    def stddev(self) -> Optional[float]:
        valid: List[float] = [v for v in self.values if v is not None]
        return float(std(valid)) if valid else None

    def __str__(self) -> str:
        return (f"TimeSeries for station '{self.station_code}'\n"
                f"Indicator: {self.indicator}, Averaging Time: {self.avg_time}, Unit: {self.unit}\n"
                f"Records: {len(self.dates)}, File: {self.filepath.name}")

    def __repr__(self) -> str:
        return (f"TimeSeries(filename='{self.filepath.name}', "
                f"station_code='{self.station_code}', "
                f"records={len(self.dates)})")
