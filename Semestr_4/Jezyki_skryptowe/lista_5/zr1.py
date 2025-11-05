import typer
import random
from pathlib import Path
from statistics import mean, stdev

from z5 import (
    read_measurement_file,
    get_matching_file,
    load_stations,
    parse_date
)
import zr2


app = typer.Typer(help="CLI do analizy pomiarów jakości powietrza")


@app.command()
def random_station(
    data_dir: Path,
    stations: Path,
    variable: str,
    freq: str,
    start_date: str,
    end_date: str
):
    file = get_matching_file(data_dir, variable, freq)
    if not file:
        return

    data = read_measurement_file(file, parse_date(start_date), parse_date(end_date))
    if not data:
        typer.echo("Brak pomiarów.")
        return

    stations_list = load_stations(stations)
    available = {s for s, _ in data}
    matching = [s for s in stations_list if s['Kod stacji'] in available]
    if not matching:
        typer.echo("Brak stacji.")
        return

    station = random.choice(matching)
    typer.echo(f"{station['Nazwa stacji']} – {station['Adres']}")


@app.command()
def stats(
    data_dir: Path,
    stations: Path,
    variable: str,
    freq: str,
    station: str,
    start_date: str,
    end_date: str
):
    file = get_matching_file(data_dir, variable, freq)
    if not file:
        return

    data = read_measurement_file(file, parse_date(start_date), parse_date(end_date))
    values = [v for s, v in data if s == station]
    if not values:
        typer.echo("Brak danych.")
        return

    typer.echo(f"Średnia: {mean(values):.3f}")
    if len(values) > 1:
        typer.echo(f"Odchylenie standardowe: {stdev(values):.3f}")
    else:
        typer.echo("Za mało danych do odchylenia standardowego.")


@app.command()
def anomalies(
    data_dir: Path,
    variable: str,
    freq: str,
    start_date: str,
    end_date: str,
):
    file = get_matching_file(data_dir, variable, freq)
    if not file:
        return

    raw_data = read_measurement_file(file, parse_date(start_date), parse_date(end_date))

    structured = [
        (parse_date(start_date), value, station_code, variable)
        for station_code, value in raw_data
    ]

    result = zr2.detect_anomalies(structured, variable)
    for category, entries in result.items():
        typer.echo(f"\n{category.upper()}:")
        for e in entries:
            typer.echo(f"  {e}")


if __name__ == "__main__":
    app()
