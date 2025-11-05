import subprocess
import json
import sys
import os
from collections import Counter
from typing import List, Dict


def process_directory(directory: str) -> List[Dict]:
    file_results = []

    for root, _, files in os.walk(directory):
        for file in files:
            file_path = os.path.join(root, file)

            result = subprocess.run(
                ['python', 'IV_analyze.py'],
                input=file_path,
                text=True,
                capture_output=True,
                check=True
            )

            # Konwersja wyniku JSON do słownika
            file_data = json.loads(result.stdout)
            file_results.append(file_data)

    return file_results

def generate_summary(results: List[Dict]) -> Dict:
    char_counter = Counter()
    word_counter = Counter()
    total_chars = 0
    total_words = 0
    total_lines = 0

    for result in results:
        if "error" not in result:
            total_chars += result.get("char_count", 0)
            total_words += result.get("word_count", 0)
            total_lines += result.get("line_count", 0)

            if "char_frequency" in result:
                char_counter.update(result["char_frequency"])
            if "word_frequency" in result:
                word_counter.update(result["word_frequency"])

    summary = {
        "files_processed": len(results),
        "total_chars": total_chars,
        "total_words": total_words,
        "total_lines": total_lines,
        "global_most_common_char": char_counter.most_common(1)[0][0] if char_counter else None,
        "global_most_common_word": word_counter.most_common(1)[0][0] if word_counter else None,
    }

    return summary


if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Użyj python dir_processor.py <katalog>", file=sys.stderr)
        sys.exit(1)

    directory = sys.argv[1]
    if not os.path.isdir(directory):
        print(f"Błąd: {directory} nie jest poprawnym katalogiem.", file=sys.stderr)
        sys.exit(1)

    results = process_directory(directory)
    summary = generate_summary(results)

    print(json.dumps(summary, indent=2, ensure_ascii=False))
