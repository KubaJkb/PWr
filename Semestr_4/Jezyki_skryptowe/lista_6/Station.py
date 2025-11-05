class Station:
    def __init__(self, data_row: dict):
        self.nr = int(data_row["Nr"])
        self.kod_stacji = data_row["Kod stacji"]
        self.kod_miedzynarodowy = data_row["Kod międzynarodowy"]
        self.nazwa_stacji = data_row["Nazwa stacji"]
        self.stary_kod = data_row["Stary Kod stacji \n(o ile inny od aktualnego)"]
        self.data_uruchomienia = data_row["Data uruchomienia"]
        self.data_zamkniecia = data_row["Data zamknięcia"]
        self.typ_stacji = data_row["Typ stacji"]
        self.typ_obszaru = data_row["Typ obszaru"]
        self.rodzaj_stacji = data_row["Rodzaj stacji"]
        self.wojewodztwo = data_row["Województwo"]
        self.miejscowosc = data_row["Miejscowość"]
        self.adres = data_row["Adres"]
        self.lat = float(data_row["WGS84 φ N"])
        self.lon = float(data_row["WGS84 λ E"])

    def __str__(self):
        return f"{self.nazwa_stacji} ({self.kod_stacji}) w {self.miejscowosc}, woj. {self.wojewodztwo}"

    def __repr__(self):
        return f"Station(kod_stacji='{self.kod_stacji}', nazwa_stacji='{self.nazwa_stacji}')"

    def __eq__(self, other):
        if isinstance(other, Station):
            return self.kod_stacji == other.kod_stacji
        return False
