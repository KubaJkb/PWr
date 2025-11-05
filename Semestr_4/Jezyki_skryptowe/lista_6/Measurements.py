from pathlib import Path
from typing import *
import re

from SeriesValidator import SeriesValidator
from TimeSeries import TimeSeries


class Measurements:
    def __init__(self, directory: Path):
        self.directory = Path(directory)
        self._files = list(self.directory.glob("*.csv"))
        self._index = self._build_index()           # List(Dict{str, str})
        self._cache: Dict[tuple, TimeSeries] = {}   # Dict {Tuple(str, str), TimeSeries}

    def _parse_filename(self, filename: str) -> tuple:
        match = re.match(r"(\d{4})_(.+)_(.+)\.csv", filename)
        if match:
            year, param, freq = match.groups()
            return int(year), param.lower(), freq.lower()
        else:
            raise ValueError(f"Nieprawidłowy format nazwy pliku: {filename}")

    def _build_index(self) -> List[dict]:
        index = []
        for file in self._files:
            year, param, freq = self._parse_filename(file.name)
            with open(file, encoding="utf-8") as f:
                header = f.readlines()[1].strip().split(",")
                station_codes = header[1:]
                for station_code in station_codes:
                    index.append({
                        "file": file,
                        "year": year,
                        "param": param,
                        "freq": freq,
                        "station": station_code
                    })

        return index

    def __len__(self) -> int:
        return len(self._index)

    def __contains__(self, parameter_name: str) -> bool:
        return any(entry["param"] == parameter_name.lower() for entry in self._index)

    def get_by_parameter(self, param_name: str) -> List[TimeSeries]:
        param_name = param_name.lower()
        result = []
        for entry in self._index:
            if entry["param"] == param_name:
                key = (entry["file"], entry["station"])
                if key not in self._cache:
                    self._cache[key] = TimeSeries(entry["file"].name, entry["station"])
                result.append(self._cache[key])
        return result

    def get_by_station(self, station_code: str) -> List[TimeSeries]:
        result = []
        for entry in self._index:
            if entry["station"] == station_code:
                key = (entry["file"], entry["station"])
                if key not in self._cache:
                    self._cache[key] = TimeSeries(entry["file"].name, entry["station"])
                result.append(self._cache[key])
        return result

    def detect_all_anomalies(self, validators: List[SeriesValidator], preload: bool = False) -> Dict[str, List[str]]:
        results = {}

        if preload:
            for entry in self._index:
                key = (entry["file"], entry["station"])
                if key not in self._cache:
                    print(key)      #todo DELETE
                    self._cache[key] = TimeSeries(entry["file"].name, entry["station"])

        for (file_path, station_code), series in self._cache.items():
            ts_key = f"{file_path.name}:{station_code}"
            all_messages = []

            for validator in validators:
                messages = validator.analyze(series)
                if messages:
                    all_messages.extend(messages)

            if all_messages:
                results[ts_key] = all_messages

        return results

