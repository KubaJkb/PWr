import re
from pathlib import Path
from typing import Dict, Tuple

def group_measurement_files_by_key(path: Path) -> Dict[Tuple[str, str, str], Path]:
    pattern = re.compile(r'^(\d{4})_(.+)_([0-9a-z]+)\.csv$')

    result = {}

    for file in path.iterdir():
        if file.is_file():
            match = pattern.match(file.name)
            if match:
                year, variable, frequency = match.groups()
                result[(year, variable, frequency)] = file

    return result
