from pathlib import Path
from typing import List, Dict, Tuple, Union, Any
import re

from src.SeriesValidator import SeriesValidator
from src.TimeSeries import TimeSeries


class Measurements:
    def __init__(self, directory: Union[Path, str]) -> None:
        self.directory: Path = Path(directory)
        self._files: List[Path] = list(self.directory.glob("*.csv"))
        self._index: List[Dict[str, Any]] = self._build_index()
        self._cache: Dict[Tuple[Path, str], TimeSeries] = {}

    def _parse_filename(self, filename: str) -> Tuple[int, str, str]:
        match = re.match(r"(\d{4})_(.+)_(.+)\.csv", filename)
        if not match:
            raise ValueError(f"Nieprawidłowy format nazwy pliku: {filename}")
        year, param, freq = match.groups()
        return int(year), param.lower(), freq.lower()

    def _build_index(self) -> List[Dict[str, Any]]:
        index: List[Dict[str, Any]] = []
        for file in self._files:
            year, param, freq = self._parse_filename(file.name)
            with open(file, encoding="utf-8") as f:
                header: List[str] = f.readlines()[1].strip().split(",")
                station_codes: List[str] = header[1:]
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
        param_lower: str = param_name.lower()
        result: List[TimeSeries] = []
        for entry in self._index:
            if entry["param"] == param_lower:
                key: Tuple[Path, str] = (entry["file"], entry["station"])
                if key not in self._cache:
                    self._cache[key] = TimeSeries(entry["file"].name, entry["station"])
                result.append(self._cache[key])
        return result

    def get_by_station(self, station_code: str) -> List[TimeSeries]:
        result: List[TimeSeries] = []
        for entry in self._index:
            if entry["station"] == station_code:
                key: Tuple[Path, str] = (entry["file"], entry["station"])
                if key not in self._cache:
                    self._cache[key] = TimeSeries(entry["file"].name, entry["station"])
                result.append(self._cache[key])
        return result

    def detect_all_anomalies(self, validators: List[SeriesValidator], preload: bool = False) -> Dict[str, List[str]]:
        results: Dict[str, List[str]] = {}

        if preload:
            for entry in self._index:
                key: Tuple[Path, str] = (entry["file"], entry["station"])
                if key not in self._cache:
                    self._cache[key] = TimeSeries(entry["file"].name, entry["station"])

        for (file_path, station_code), series in self._cache.items():
            ts_key: str = f"{file_path.name}:{station_code}"
            all_messages: List[str] = []

            for validator in validators:
                messages: List[str] = validator.analyze(series)
                if messages:
                    all_messages.extend(messages)

            if all_messages:
                results[ts_key] = all_messages

        return results
