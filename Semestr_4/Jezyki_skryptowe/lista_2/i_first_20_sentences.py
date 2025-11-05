import sys

def first_20_sentences():
    sentence = ""
    sentences_left = 20

    for line in sys.stdin:
        if not sentences_left > 0:
            continue
        # PUSTA LINIA - KONIEC ZDANIA
        if line.strip() == "":
            print(line, end="")
            if sentence != "":
                sentences_left -= 1
                sentence = ""
        else:
            for char in line:
                if sentences_left > 0:
                    sentence += char
                # ZNAK KOŃCA ZDANIA
                if char == "." or char == "!" or char == "?":
                    sentences_left -= 1
            if sentence != "":
                if len(sentence) < len(line):
                    print(sentence, end="")
                else:
                    print(line, end="")
            sentence = ""

    if sentences_left > 0 and sentence != "":
        print(sentence, end="")


if __name__ == "__main__":
    first_20_sentences()
