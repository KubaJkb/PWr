import sys
import json
from collections import Counter
import io

sys.stdin = io.TextIOWrapper(sys.stdin.buffer, encoding="utf-8")

def analyze_file(file_path):
    with open(file_path, 'r', encoding='utf-8') as file:
        content = file.read()
        lines = content.splitlines()
        words = content.split()

        # Zlicz wszystkie znaki (bez białych znaków)
        chars = [c for c in content if c not in (' ', '\n', '\t', '\r')]
        char_freq = Counter(chars)

        # Zlicz wszystkie słowa
        word_freq = Counter(words)

        return {
            "path": file_path,
            "char_count": len(content),
            "word_count": len(words),
            "line_count": len(lines),
            "char_frequency": char_freq,
            "word_frequency": word_freq,
            "most_common_char": char_freq.most_common(1)[0][0] if chars else None,
            "most_common_word": word_freq.most_common(1)[0][0] if words else None
        }


if __name__ == "__main__":
    file_path = sys.stdin.readline().strip()
    stats = analyze_file(file_path)

    # Wyjście w formacie JSON
    print(json.dumps(stats, ensure_ascii=False))
