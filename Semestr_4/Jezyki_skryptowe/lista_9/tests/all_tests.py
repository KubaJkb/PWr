import pytest
from datetime import datetime, date
from pathlib import Path

from src.Station import Station
from src.TimeSeries import TimeSeries
from src.SeriesValidator import (OutlierDetector, ZeroSpikeDetector, ThresholdDetector, SimpleReporter)
from src.Measurements import Measurements


# ——— a. Testy __eq__ z klasy Station ———
class TestA:
    @pytest.fixture(scope="class")
    def station_data(self):
        base = {
            "Nr": "1", "Kod stacji": "", "Kod międzynarodowy": "",
            "Nazwa stacji": "", "Stary Kod stacji \n(o ile inny od aktualnego)": "",
            "Data uruchomienia": "", "Data zamknięcia": "",
            "Typ stacji": "", "Typ obszaru": "", "Rodzaj stacji": "",
            "Województwo": "", "Miejscowość": "", "Adres": "",
            "WGS84 φ N": "0", "WGS84 λ E": "0"
        }
        return base

    def test_eq_same_code(self, station_data):
        d1 = station_data.copy(); d1["Kod stacji"] = "ABC"; d1["Nr"] = "1"
        d2 = station_data.copy(); d2["Kod stacji"] = "ABC"; d2["Nr"] = "2"
        s1 = Station(d1)
        s2 = Station(d2)
        assert s1 == s2

    def test_eq_different_code(self, station_data):
        d1 = station_data.copy(); d1["Kod stacji"] = "ABC"
        d2 = station_data.copy(); d2["Kod stacji"] = "XYZ"
        s1 = Station(d1)
        s2 = Station(d2)
        assert not (s1 == s2)


# ——— b. Testy __getitem__ z klasy TimeSeries ———
class TestB:
    class DummyTS(TimeSeries):
        def __init__(self):
            # nie wywołujemy bazowego __init__
            self.station_code = "XYZ"
            self.dates = [
                datetime(2023, 1, 1, 12),
                datetime(2023, 1, 2, 12),
                datetime(2023, 1, 3, 12),
            ]
            self.values = [10.0, None, 30.0]

    @pytest.fixture(scope="class")
    def ts(self):
        return TestB.DummyTS()

    def test_getitem_int(self, ts):
        dt, val = ts[0]
        assert dt == datetime(2023, 1, 1, 12)
        assert val == 10.0

    def test_getitem_slice(self, ts):
        result = ts[0:2]
        assert isinstance(result, list)
        assert len(result) == 2
        assert result[0] == (ts.dates[0], ts.values[0])
        assert result[1] == (ts.dates[1], ts.values[1])

    def test_getitem_date_existing(self, ts):
        val = ts[date(2023, 1, 3)]
        assert val == 30.0

    def test_getitem_date_missing(self, ts):
        with pytest.raises(KeyError):
            _ = ts[date(2025, 1, 1)]


# ——— c. Testy mean i stddev w TimeSeries ———
class TestC:
    class DummyTS(TimeSeries):
        def __init__(self, vals):
            self.values = vals

    def test_mean_complete(self):
        ts = TestC.DummyTS([10.0, 20.0, 30.0])
        assert ts.mean == pytest.approx(20.0)

    def test_mean_with_none(self):
        ts = TestC.DummyTS([10.0, None, 30.0])
        assert ts.mean == pytest.approx(20.0)

    def test_stddev_complete(self):
        ts = TestC.DummyTS([10.0, 20.0, 30.0])
        # sqrt(((−10)²+0²+10²)/3) ≈ 8.16497
        assert ts.stddev == pytest.approx(8.16497, rel=1e-3)

    def test_stddev_with_none(self):
        ts = TestC.DummyTS([10.0, None, 30.0])
        # sqrt(((−10)²+(10)²)/2) = 10
        assert ts.stddev == pytest.approx(10.0)


# ——— d. Test walidatora OutlierDetector ———
class TestD:
    class DummyTS(TimeSeries):
        def __init__(self):
            self.values = [10, 10, 10, 100, 10]
            self.dates = [datetime(2023,1,i+1) for i in range(5)]

    def test_detects_outlier(self):
        ts = TestD.DummyTS()
        det = OutlierDetector(k=1.0)
        msgs = det.analyze(ts)
        assert msgs == ['Wartość odstająca: 100 w dniu 2023-01-04 00:00']


# ——— e. Test walidatora ZeroSpikeDetector ———
class TestE:
    class DummyTS:
        def __init__(self):
            self.values = [5, None, 0, 0, 5]
            self.dates = [datetime(2023,1,i+1) for i in range(5)]

    def test_three_zeros_in_row(self):
        ts = TestE.DummyTS()
        det = ZeroSpikeDetector()
        msgs = det.analyze(ts)
        assert msgs == ['Co najmniej 3 zera/braki danych z rzędu od 2023-01-02 00:00']


# ——— f. Test walidatora ThresholdDetector ———
class TestF:
    class DummyTS:
        def __init__(self):
            self.values = [5, 15, 25, 15, 5]
            self.dates = [datetime(2023,1,i+1) for i in range(5)]

    def test_threshold_exceeded(self):
        ts = TestF.DummyTS()
        det = ThresholdDetector(threshold=20)
        msgs = det.analyze(ts)
        assert msgs == ['Wartość 25 przekracza próg 20 w dniu 2023-01-03 00:00']


# ——— g. Test detect_all_anomalies() z parametrize i polimorfizmem ———
class TestG:
    class MockTS(TimeSeries):
        def __init__(self):
            self.dates = [datetime(2023, 1, i + 1, 0, 0) for i in range(4)]
            self.values = [0.0, 0.0, 0.0, 100.0]
            self.station_code = "STA"
            self.indicator = "PM10"
            self.avg_time = "1h"
            self.unit = "ug/m3"

    @pytest.fixture
    def setup_measurements(self, monkeypatch, tmp_path):
        m = Measurements(tmp_path)

        idx = [{"file": Path("x.csv"), "station": "STA"}]
        cache = {(Path("x.csv"), "STA"): TestG.MockTS()}

        monkeypatch.setattr(m, "_index", idx)
        monkeypatch.setattr(m, "_cache", cache)

        return m

    @pytest.mark.parametrize("validator", [
        OutlierDetector(k=1.0),
        ZeroSpikeDetector(),
        ThresholdDetector(threshold=50),
        SimpleReporter()
    ])
    def test_polymorphic_detect(self, setup_measurements, validator):
        m = setup_measurements
        result = m.detect_all_anomalies(validators=[validator])

        ts_key = "x.csv:STA"
        # sprawdzamy, że metoda detect_all_anomalies pozwala na dostęp po kluczu
        assert ts_key in result

        msgs = result[ts_key]
        # sprawdzamy, że zwrócono co najmniej jedną wiadomość
        assert msgs, "Oczekiwano co najmniej jednej wiadomości od validatora"

        # sprawdzamy 'duck typing': każdą wiadomość można zamienić na str
        for msg in msgs:
            _ = f"{msg}"
