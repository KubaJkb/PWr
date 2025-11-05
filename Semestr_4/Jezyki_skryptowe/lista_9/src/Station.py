from typing import Dict


class Station:
    def __init__(self, data_row: Dict[str, str]) -> None:
        self.nr: int = int(data_row["Nr"])
        self.kod_stacji: str = data_row["Kod stacji"]
        self.kod_miedzynarodowy: str = data_row["Kod międzynarodowy"]
        self.nazwa_stacji: str = data_row["Nazwa stacji"]
        self.stary_kod: str = data_row["Stary Kod stacji \n(o ile inny od aktualnego)"]
        self.data_uruchomienia: str = data_row["Data uruchomienia"]
        self.data_zamkniecia: str = data_row["Data zamknięcia"]
        self.typ_stacji: str = data_row["Typ stacji"]
        self.typ_obszaru: str = data_row["Typ obszaru"]
        self.rodzaj_stacji: str = data_row["Rodzaj stacji"]
        self.wojewodztwo: str = data_row["Województwo"]
        self.miejscowosc: str = data_row["Miejscowość"]
        self.adres: str = data_row["Adres"]
        self.lat: float = float(data_row["WGS84 φ N"])
        self.lon: float = float(data_row["WGS84 λ E"])

    def __str__(self) -> str:
        return f"{self.nazwa_stacji} ({self.kod_stacji}) w {self.miejscowosc}, woj. {self.wojewodztwo}"

    def __repr__(self) -> str:
        return f"Station(kod_stacji='{self.kod_stacji}', nazwa_stacji='{self.nazwa_stacji}')"

    def __eq__(self, other: object) -> bool:
        if isinstance(other, Station):
            return self.kod_stacji == other.kod_stacji
        return False
