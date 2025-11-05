import csv
from datetime import datetime, date
from operator import contains
from pathlib import Path

from Station import Station
from TimeSeries import TimeSeries
from SeriesValidator import *
from Measurements import Measurements
from SimpleReporter import SimpleReporter


# ------------------------ Station ------------------------

with open("data/stacje.csv", encoding="utf-8") as f:
    reader = csv.DictReader(f)
    stations = [Station(row) for row in reader]

for station in stations:
    print(station)
    print(repr(station))

print(stations[1] == stations[2])
print(stations[1] == stations[1])


# ------------------------ TimeSeries ------------------------

ts = TimeSeries("2023_CO_1g.csv", "SlWodzGalczy")
print(ts.mean, ts.stddev)
print(ts[0])
print(ts[0:3])
print(ts[datetime(2023, 1, 1, 2, 0)])
print(ts[date(2023, 1, 1)])


# ------------------------ Series Validator ------------------------

series = TimeSeries("2023_CO_1g.csv", "SlWodzGalczy")
validators = CompositeValidator([
    OutlierDetector(k=2.5),
    ZeroSpikeDetector(),
    ThresholdDetector(threshold=100)
], mode="OR")

anomalies = validators.analyze(series)
for msg in anomalies:
    print(msg)


# ------------------------ Measurements ------------------------

m = Measurements(Path("data/measurements"))

print(m.__len__())
print(m.__contains__("CO"))
for ts in m.get_by_parameter("CO"):
    print(ts)
print("-------------------------------------")
for ts in m.get_by_station("DsWrocAlWisn"):
    print(ts)


validators = [
    OutlierDetector(k=2.5),
    ZeroSpikeDetector(),
    ThresholdDetector(threshold=100.0)
]

anomalies = m.detect_all_anomalies(validators, preload=True)

for series_id, msgs in anomalies.items():
    print(f"\n{series_id}:")
    for msg in msgs:
        print("  -", msg)


# ------------------------ SimpleReporter ------------------------

series = TimeSeries("2023_CO_1g.csv", "DsWrocAlWisn")

analyzers = [
    OutlierDetector(k=6.0),
    ThresholdDetector(threshold=2.0),
    SimpleReporter()  # nie dziedziczy po SeriesValidator!
]

# Iterujemy bez sprawdzania typu (kacze typowanie)
for analyzer in analyzers:
    results = analyzer.analyze(series)
    print(f"\n{analyzer.__class__.__name__}:")
    for msg in results:
        print("  -", msg)
