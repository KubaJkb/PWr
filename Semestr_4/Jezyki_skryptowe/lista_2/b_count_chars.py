import sys
import re

def count_chars():
    char_count = 0

    for line in sys.stdin:
        line = line.strip()

        line = "".join(line.split())
        # line = line.replace(" ", "").replace("\t", "").replace("\n", "")
        # line = re.sub(r"\s+", "", line)

        char_count += len(line)

    return char_count


if __name__ == "__main__":
    print(count_chars())
