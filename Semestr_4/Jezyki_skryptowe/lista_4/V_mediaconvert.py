import argparse
import os
from datetime import datetime
from typing import Optional, Dict
from V_utils import (
    setup_logging,
    find_media_files,
    get_output_dir,
    create_timestamp_filename,
    log_conversion,
    is_image_file,
    check_tool_available
)
import subprocess
import logging

# Konfiguracja dla FFmpeg
FFMPEG_CODECS = {
    'mp4': ['-c:v', 'libx264', '-preset', 'medium', '-crf', '23'],
    'webm': ['-c:v', 'libvpx-vp9', '-b:v', '1M'],
    'mp3': ['-c:a', 'libmp3lame', '-q:a', '2'],
    'wav': ['-c:a', 'pcm_s16le'],
    'ogg': ['-c:a', 'libvorbis'],
    'png': ['-c:v', 'png'],
    'jpg': ['-c:v', 'mjpeg', '-qscale:v', '2'],
    'webp': ['-c:v', 'libwebp', '-qscale:v', '80']
}

# Konfiguracja dla ImageMagick
IMAGEMAGICK_FORMATS = {
    'png': 'PNG',
    'jpg': 'JPEG',
    'webp': 'WEBP',
    'bmp': 'BMP',
    'tiff': 'TIFF'
}


def convert_media(input_path: str, output_format: str, output_dir: str) -> Optional[Dict[str, str]]:
    output_filename = create_timestamp_filename(input_path, output_format)
    output_path = os.path.join(output_dir, output_filename)
    os.makedirs(output_dir, exist_ok=True)

    tool_used = None
    success = False

    if is_image_file(input_path):
        if check_tool_available('magick'):
            success = convert_with_imagemagick(input_path, output_format, output_path)
            tool_used = 'ImageMagick'
        else:
            logging.warning("ImageMagick nie znaleziony, próba użycia ffmpeg")
            success = convert_with_ffmpeg(input_path, output_format, output_path)
            tool_used = 'FFmpeg (fallback)'
    else:
        success = convert_with_ffmpeg(input_path, output_format, output_path)
        tool_used = 'FFmpeg'

    if success:
        logging.info(f"Konwersja udana: {input_path} -> {output_path}")
        return {
            'timestamp': datetime.now().isoformat(),
            'original_path': input_path,
            'output_format': output_format,
            'output_path': output_path,
            'tool_used': tool_used
        }
    else:
        logging.error(f"Błąd konwersji: {input_path}")
        return None


def convert_with_imagemagick(input_path: str, output_format: str, output_path: str) -> bool:
    if output_format not in IMAGEMAGICK_FORMATS:
        logging.error(f"Nieobsługiwany format wyjściowy dla ImageMagick: {output_format}")
        return False

    cmd = ['magick', input_path, '-auto-orient', f'{IMAGEMAGICK_FORMATS[output_format]}:{output_path}']

    try:
        subprocess.run(cmd, check=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        return True
    except subprocess.CalledProcessError as e:
        logging.error(f"Błąd ImageMagick: {e.stderr.decode()}")
        return False


def convert_with_ffmpeg(input_path: str, output_format: str, output_path: str) -> bool:
    if output_format not in FFMPEG_CODECS:
        logging.error(f"Nieobsługiwany format wyjściowy dla FFmpeg: {output_format}")
        return False

    cmd = ['ffmpeg', '-i', input_path, '-y'] + FFMPEG_CODECS[output_format] + [output_path]

    try:
        subprocess.run(cmd, check=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        return True
    except subprocess.CalledProcessError as e:
        logging.error(f"Błąd FFmpeg: {e.stderr.decode()}")
        return False


def main():
    setup_logging()

    parser = argparse.ArgumentParser(description='Konwerter plików multimedialnych i graficznych')
    parser.add_argument('input_dir', help='Katalog z plikami do konwersji')
    parser.add_argument('format', help='Format docelowy (mp4, webm, mp3, png, jpg, itp.)')
    args = parser.parse_args()

    if not check_tool_available('ffmpeg'):
        logging.error("Nie znaleziono ffmpeg w systemie!")
        return

    output_dir = get_output_dir()
    media_files = find_media_files(args.input_dir)

    if not media_files:
        logging.warning("Nie znaleziono plików do konwersji")
        return

    logging.info(f"Rozpoczęcie konwersji {len(media_files)} plików do formatu {args.format}")

    history_path = os.path.join(output_dir, 'history.json')
    success_count = 0

    for file_path in media_files:
        result = convert_media(file_path, args.format, output_dir)
        if result:
            log_conversion(history_path, result)
            success_count += 1

    logging.info(f"Zakończono: {success_count}/{len(media_files)} plików przekonwertowanych pomyślnie")

if __name__ == "__main__":
    main()
