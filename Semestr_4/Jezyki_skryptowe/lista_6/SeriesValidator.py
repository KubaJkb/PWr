from abc import ABC, abstractmethod
from typing import *

from TimeSeries import TimeSeries

class SeriesValidator(ABC):
    @abstractmethod
    def analyze(self, series: TimeSeries) -> List[str]:
        pass

class OutlierDetector(SeriesValidator):
    def __init__(self, k: float = 3.0):
        self.k = k

    def analyze(self, series: TimeSeries) -> List[str]:
        mean = series.mean
        stddev = series.stddev
        if mean is None or stddev is None or stddev == 0:
            return []

        messages = []
        for i, value in enumerate(series.values):
            if value is None:
                continue
            if abs(value - mean) > self.k * stddev:
                dt = series.dates[i]
                messages.append(f"Wartość odstająca: {value} w dniu {dt.strftime('%Y-%m-%d %H:%M')}")
        return messages

class ZeroSpikeDetector(SeriesValidator):
    def analyze(self, series: TimeSeries) -> List[str]:
        messages = []
        zero_streak = 0
        for i, val in enumerate(series.values):
            if val in (0, None):
                zero_streak += 1
                if zero_streak == 3:
                    dt = series.dates[i - 2]
                    messages.append(f"Co najmniej 3 zera/braki danych z rzędu od {dt.strftime('%Y-%m-%d %H:%M')}")
            else:
                zero_streak = 0
        return messages

class ThresholdDetector(SeriesValidator):
    def __init__(self, threshold: float):
        self.threshold = threshold

    def analyze(self, series: TimeSeries) -> List[str]:
        messages = []
        for i, val in enumerate(series.values):
            if val is not None and val > self.threshold:
                dt = series.dates[i]
                messages.append(f"Wartość {val} przekracza próg {self.threshold} w dniu {dt.strftime('%Y-%m-%d %H:%M')}")
        return messages

class CompositeValidator(SeriesValidator):
    def __init__(self, validators: List[SeriesValidator], mode: str = "OR"):
        assert mode in ("OR", "AND"), "Tryb musi być 'OR' lub 'AND'"
        self.validators = validators
        self.mode = mode

    def analyze(self, series: TimeSeries) -> list[str] | None:
        if self.mode == "OR":
            messages = []
            for validator in self.validators:
                messages.extend(validator.analyze(series))
            return messages

        elif self.mode == "AND":
            results = [set(v.analyze(series)) for v in self.validators]
            if not results:
                return []
            common = set.intersection(*results)
            return list(common)
