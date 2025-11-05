import sys


def first_subclause():
    sentence = ""
    subclauses_counter = 0
    printed = ""

    for line in sys.stdin:
        if printed != "":
            continue
        # PUSTA LINIA - KONIEC ZDANIA
        if line.strip() == "":
            if subclauses_counter > 1 and sentence != "":
                printed = sentence
            sentence = ""
        else:
            for char in line:
                if sentence == "":
                    # NOWE ZDANIE
                    if char.isalpha():
                        sentence = char
                        subclauses_counter = 1
                else:
                    sentence += char
                    # KONIEC ZDANIA
                    if char == "." or char == "!" or char == "?":
                        if subclauses_counter > 1:
                            printed = sentence
                        sentence = ""
                    # NOWE ZDANIE PODRZĘDNE
                    elif char == ",":
                        subclauses_counter += 1

    if printed == "" and subclauses_counter > 1:
        return sentence
    else:
        return printed


if __name__ == "__main__":
    print(first_subclause())
