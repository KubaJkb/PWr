import csv
import re
from pathlib import Path
from typing import List, Tuple, Optional


def get_addresses(path: Path, city: str) -> List[Tuple[str, str, str, Optional[str]]]:
    addresses = []

    with path.open(newline='', encoding='utf-8') as csvfile:
        reader = csv.DictReader(csvfile)
        for row in reader:
            if row['Miejscowość'].strip().lower() == city.strip().lower():
                voivodeship = row['Województwo']
                city = row['Miejscowość']
                address = row['Adres']

                address_pattern = re.compile(
                    r'^(?P<street>.+)'
                    r'\s'
                    r'(?P<number>[0-9/-]*)$'
                )
                match = re.match(address_pattern, address)

                if match:
                    street = match.group('street').strip()
                    number = match.group('number').strip() if match.group('number') else None
                    addresses.append((voivodeship, city, street, number))
                else:
                    addresses.append((voivodeship, city, address, None))

    return addresses
