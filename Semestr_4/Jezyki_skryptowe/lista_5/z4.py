import csv
import re
from pathlib import Path
from typing import List, Dict


def analyze_stations_csv(path: Path) -> Dict[str, List]:
    results = {
        'a': set(),  # Unikalne daty RRRR-MM-DD
        'b': [],  # Współrzędne geograficzne
        'c': [],  # Nazwy stacji z myślnikiem
        'd': [],  # Nazwy stacji po transformacji
        'e': [],  # Stacje z kodem MOB i nie-mobilne
        'f': [],  # Lokalizacje 3-członowe
        'g': []  # Lokalizacje z ul./al. i przecinkiem
    }

    POLISH_CHARS = {
        'ą': 'a', 'ć': 'c', 'ę': 'e', 'ł': 'l', 'ń': 'n', 'ó': 'o', 'ś': 's', 'ź': 'z', 'ż': 'z',
        'Ą': 'A', 'Ć': 'C', 'Ę': 'E', 'Ł': 'L', 'Ń': 'N', 'Ó': 'O', 'Ś': 'S', 'Ź': 'Z', 'Ż': 'Z'
    }

    with path.open(newline='', encoding='utf-8') as csvfile:
        reader = csv.DictReader(csvfile)
        for row in reader:
            # a. Daty w formacie RRRR-MM-DD
            for date in [row['Data uruchomienia'], row['Data zamknięcia']]:
                if re.fullmatch(r'\d{4}-\d{2}-\d{2}', date):
                    results['a'].add(date)

            # b. Współrzędne geograficzne z 6 cyframi po kropce
            lat = row['WGS84 φ N']
            lon = row['WGS84 λ E']
            if lat and lon:
                if re.fullmatch(r'\d+\.\d{6}', lat) and re.fullmatch(r'\d+\.\d{6}', lon):
                    results['b'].append((float(lat), float(lon)))

            # c. Nazwy stacji zawierające myślnik
            if re.search(r'\s+-\s+', row['Nazwa stacji']):
                results['c'].append(row['Nazwa stacji'])

            # d. Modyfikacja nazw stacji: spacje na _, bez polskich znaków
            name = row['Nazwa stacji']
            name = re.sub(r'\s+', '_', name)
            name = ''.join(POLISH_CHARS.get(c, c) for c in name)
            results['d'].append(name)

            # e. Czy kod kończy się na MOB i rodzaj stacji =/= 'mobilna'
            if row['Kod stacji'].endswith('MOB') and row['Rodzaj stacji'] != 'mobilna':
                results['e'].append(row['Kod stacji'])

            # f. Lokalizacje z trzema członami oddzielonymi myślnikami
            if re.search(r'\s+-\s+[^-]+\s+-\s+', row['Nazwa stacji']):
                results['f'].append(row['Nazwa stacji'])

            # g. Lokalizacje zawierające przecinek i słowo "ul." lub "al."
            if re.search(r',.*\b(ul|al)\.', row['Nazwa stacji']):
                results['g'].append(row['Nazwa stacji'])

    results['a'] = list(results['a'])

    return results
