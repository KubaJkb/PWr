import sys
import argparse
import time
import os
import io

sys.stdin = io.TextIOWrapper(sys.stdin.buffer, encoding="utf-8")

def tail(file, num_lines=10):
    try:
        with open(file, 'r', encoding='utf-8') as f:
            lines = f.readlines()
    except FileNotFoundError:
        print(f"Błąd: Plik '{file}' nie istnieje.", file=sys.stderr)
        sys.exit(1)

    for line in lines[-num_lines:]:
        print(line, end="")

def tail_stdin(num_lines=10):
    lines = sys.stdin.readlines()
    for line in lines[-num_lines:]:
        print(line, end="")


def follow(file, num_lines=10):
    tail(file, num_lines)
    try:
        print(f"\n - Śledzenie pliku {file} (Ctrl+C aby zakończyć) - ")

        with open(file, 'r') as f:
            f.seek(0, os.SEEK_END)

            while True:
                line = f.readline()
                if not line:
                    time.sleep(0.1)
                    continue
                print(line, end="")
    except FileNotFoundError:
        print(f"Błąd: Plik '{file}' nie istnieje.", file=sys.stderr)
        sys.exit(1)
    except KeyboardInterrupt:
        print("\nZakończono śledzenie pliku.", file=sys.stderr)
        sys.exit(0)


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Uproszczona wersja tail.")
    parser.add_argument(
        "file",
        nargs="?",
        help="Ścieżka do pliku do odczytania."
    )
    parser.add_argument(
        "--lines",
        "-n",
        type=int,
        default=10,
        help="Liczba ostatnich linii do wyświetlenia (domyślnie 10)."
    )
    parser.add_argument(
        "--follow",
        "-f",
        action="store_true",
        help="Śledź plik na bieżąco."
    )

    args = parser.parse_args()

    if args.file:
        if args.follow:
            follow(args.file, args.lines)
        else:
            tail(args.file, args.lines)
    elif not sys.stdin.isatty():
        tail_stdin(args.lines)
    else:
        print("Błąd: Brak pliku i danych na wejściu standardowym.", file=sys.stderr)
