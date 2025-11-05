import os
import logging
import json
from datetime import datetime
from typing import List, Dict
import subprocess


def setup_logging(log_file: str = 'mediaconvert.log') -> None:
    """Konfiguruje logowanie do pliku i konsoli"""

    logging.basicConfig(
        level=logging.INFO,
        format='%(asctime)s - %(levelname)s - %(message)s',
        handlers=[
            logging.FileHandler(log_file),
            logging.StreamHandler()
        ]
    )


def check_tool_available(tool_name: str) -> bool:
    """Weryfikuje czy narzędzie (FFmpeg/ImageMagick) jest dostępne"""

    try:
        subprocess.run([tool_name, '-version'], stdout=subprocess.PIPE, stderr=subprocess.PIPE, check=True)
        return True
    except (subprocess.CalledProcessError, FileNotFoundError):
        return False


def get_output_dir() -> str:
    """Zwraca katalog wynikowy (ze zmiennej środowiskowej CONVERTED_DIR lub domyślny "converted")"""

    return os.environ.get('CONVERTED_DIR', os.path.join(os.getcwd(), 'converted'))


def find_media_files(directory: str) -> List[str]:
    """Wyszukuje pliki multimedialne w podanym katalogu (obsługuje wideo, audio i obrazy)"""

    supported_extensions = [
        # wideo/audio
        '.mp4', '.mov', '.avi', '.mkv', '.webm',
        '.mp3', '.wav', '.flac', '.ogg', '.aac',
        # obrazy
        '.jpg', '.jpeg', '.png', '.gif', '.bmp', '.tiff', '.webp'
    ]
    media_files = []
    for root, _, files in os.walk(directory):
        for file in files:
            if os.path.splitext(file)[1].lower() in supported_extensions:
                media_files.append(os.path.join(root, file))
    return media_files


def create_timestamp_filename(original_path: str, output_format: str) -> str:
    """Generuje unikalną nazwę pliku z timestampem"""

    timestamp = datetime.now().strftime("%Y%m%d-%H%M%S")
    original_name = os.path.splitext(os.path.basename(original_path))[0]
    return f"{timestamp}-{original_name}.{output_format}"


def is_image_file(file_path: str) -> bool:
    """Sprawdza czy plik jest obrazem (dla ImageMagick)"""

    image_extensions = ['.jpg', '.jpeg', '.png', '.gif', '.bmp', '.tiff', '.webp']
    return os.path.splitext(file_path)[1].lower() in image_extensions


def log_conversion(history_path: str, entry: Dict[str, str]) -> None:
    os.makedirs(os.path.dirname(history_path), exist_ok=True)

    history_data = []
    if os.path.exists(history_path):
        with open(history_path, 'r') as f:
            history_data = json.load(f)

    history_data.append(entry)

    with open(history_path, 'w') as f:
        json.dump(history_data, f, indent=2)
