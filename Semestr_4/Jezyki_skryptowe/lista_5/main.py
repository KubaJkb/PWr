from pathlib import Path
from pprint import pprint

from z1 import parse_stations_metadata, parse_measurement_file
from z2 import group_measurement_files_by_key
from z3 import get_addresses
from z4 import analyze_stations_csv


def main():
    stacje_path = Path("stacje.csv")
    measurements_dir = Path("measurements")


    print("=== [Z1] Parsowanie metadanych stacji ===")
    stacje = parse_stations_metadata(str(stacje_path))
    print(f"Liczba stacji: {len(stacje)}")
    print("Przykład:")
    pprint(stacje[0], width=120)

    print("\n=== [Z1] Parsowanie jednego pliku pomiarowego ===")
    sample_measurement_file = measurements_dir / "2023_CO_1g.csv"
    parsed = parse_measurement_file(str(sample_measurement_file))

    metadata = parsed['metadata']
    measurements = parsed['measurements']

    print("Metadane:")
    pprint(metadata, width=120)

    print(f"\nLiczba pomiarów: {len(measurements)}")
    print("Pierwszy pomiar:")
    pprint(measurements[0], width=120)


    print("\n=== [Z2] Grupowanie plików pomiarowych po (rok, wielkość, częstotliwość) ===")
    grouped_files = group_measurement_files_by_key(measurements_dir)
    print(f"Znaleziono {len(grouped_files)} plików:")
    for key, path in grouped_files.items():
        print(f"{key}: {path.name}")


    print("\n=== [Z3] Adresy stacji w miejscowości 'Wrocław' ===")
    addresses = get_addresses(stacje_path, "Wrocław")
    for addr in addresses:
        print(addr)


    print("\n=== [Z4] Analiza pliku stacje.csv ===")
    analysis = analyze_stations_csv(stacje_path)

    print("\n-- (a) Daty uruchomienia/zamknięcia --")
    print(analysis["a"])

    print("\n-- (b) Współrzędne geograficzne z 6 miejscami po przecinku --")
    print(analysis["b"])

    print("\n-- (c) Nazwy stacji zawierające myślnik --")
    print(analysis["c"])

    print("\n-- (d) Nazwy stacji po transformacji --")
    print(analysis["d"])

    print("\n-- (e) Stacje z kodem MOB i nie-mobilne --")
    print(analysis["e"])

    print("\n-- (f) Nazwy stacji z trzema członami rozdzielonymi myślnikiem --")
    print(analysis["f"])

    print("\n-- (g) Adresy zawierające 'ul.' lub 'al.' po przecinku --")
    print(analysis["g"])


if __name__ == "__main__":
    main()
