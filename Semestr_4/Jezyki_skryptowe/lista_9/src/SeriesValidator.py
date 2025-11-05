from abc import ABC, abstractmethod
from datetime import datetime
from typing import List, Optional, Set

from src.TimeSeries import TimeSeries


class SeriesValidator(ABC):
    @abstractmethod
    def analyze(self, series: TimeSeries) -> List[str]:
        pass


class OutlierDetector(SeriesValidator):
    def __init__(self, k: float = 3.0) -> None:
        self.k: float = k

    def analyze(self, series: TimeSeries) -> List[str]:
        mean: Optional[float] = series.mean
        stddev: Optional[float] = series.stddev
        if mean is None or stddev is None or stddev == 0:
            return []

        messages: List[str] = []
        for i, value in enumerate(series.values):
            if value is None:
                continue
            if abs(value - mean) > self.k * stddev:
                dt: datetime = series.dates[i]
                messages.append(
                    f"Wartość odstająca: {value} w dniu {dt.strftime('%Y-%m-%d %H:%M')}"
                )
        return messages


class ZeroSpikeDetector(SeriesValidator):
    def analyze(self, series: TimeSeries) -> List[str]:
        messages: List[str] = []
        zero_streak: int = 0
        for i, val in enumerate(series.values):
            if val in (0, None):
                zero_streak += 1
                if zero_streak == 3:
                    dt: datetime = series.dates[i - 2]
                    messages.append(
                        f"Co najmniej 3 zera/braki danych z rzędu od {dt.strftime('%Y-%m-%d %H:%M')}"
                    )
            else:
                zero_streak = 0
        return messages


class ThresholdDetector(SeriesValidator):
    def __init__(self, threshold: float) -> None:
        self.threshold: float = threshold

    def analyze(self, series: TimeSeries) -> List[str]:
        messages: List[str] = []
        for i, val in enumerate(series.values):
            if val is not None and val > self.threshold:
                dt: datetime = series.dates[i]
                messages.append(
                    f"Wartość {val} przekracza próg {self.threshold} w dniu {dt.strftime('%Y-%m-%d %H:%M')}"
                )
        return messages


class CompositeValidator(SeriesValidator):
    def __init__(self, validators: List[SeriesValidator], mode: str = "OR") -> None:
        assert mode in ("OR", "AND"), "Tryb musi być 'OR' lub 'AND'"
        self.validators: List[SeriesValidator] = validators
        self.mode: str = mode

    def analyze(self, series: TimeSeries) -> List[str]:
        if self.mode == "OR":
            messages: List[str] = []
            for validator in self.validators:
                messages.extend(validator.analyze(series))
            return messages
        else:   # mode == "AND"
            results: List[Set[str]] = [set(v.analyze(series)) for v in self.validators]
            if not results:
                return []
            common: Set[str] = set.intersection(*results)
            return list(common)


class SimpleReporter:
    def analyze(self, series: TimeSeries) -> List[str]:
        return [f"Info: {series.indicator} at {series.station_code} has mean = {series.mean}"]
