from datetime import datetime
from statistics import mean, median, stdev, quantiles
from collections import defaultdict


def detect_anomalies(
    data: list[tuple[datetime, float, str, str]],
    variable: str,
    std_multiplier: float = 2.5,
    extreme_multiplier: float = 5.0,
):
    anomalies = {
        "nagłe skoki": [],
        "podejrzane wartości": [],
        "przekroczenia alarmowe": [],
    }

    by_station = defaultdict(list)

    for timestamp, value, station, var in data:
        if var == variable:
            by_station[station].append((timestamp, value))

    for station, measurements in by_station.items():
        if len(measurements) < 3:
            continue

        measurements.sort()
        values = [v for _, v in measurements]
        avg = mean(values)
        med = median(values)
        std = stdev(values) if len(values) > 1 else 0
        q1, q3 = quantiles(values, n=4)[0], quantiles(values, n=4)[2]
        iqr = q3 - q1

        # Progi
        delta_threshold = std * std_multiplier if std > 0 else avg * 0.3
        suspicious_lower = q1 - 1.1 * iqr
        extreme_threshold = med * extreme_multiplier


        prev_value = None

        for i, (timestamp, value) in enumerate(measurements):
            if value < suspicious_lower:
                anomalies["podejrzane wartości"].append((station, timestamp, value))

            if value > extreme_threshold:
                anomalies["przekroczenia alarmowe"].append((station, timestamp, value))

            if prev_value is not None and abs(value - prev_value) > delta_threshold:
                anomalies["nagłe skoki"].append((station, timestamp, prev_value, value))

            prev_value = value

    return anomalies
