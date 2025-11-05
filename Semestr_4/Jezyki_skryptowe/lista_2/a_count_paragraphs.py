import sys


def count_paragraphs():
    paragraph_count = 0
    previous_line_empty = False

    for line in sys.stdin:
        line = line.strip()

        if line != "":
            previous_line_empty = False
        else:
            if not previous_line_empty:
                paragraph_count += 1
            previous_line_empty = True

    if not previous_line_empty:
        paragraph_count += 1

    return paragraph_count


if __name__ == "__main__":
    print(count_paragraphs())
