import sys


def percent_sentences():
    total_sentences = 0
    proper_name_sentences = 0
    sentence_ended = True
    inside_word = False

    for line in sys.stdin:
        line = line.strip()

        # PUSTA LINIA - KONIEC ZDANIA
        if line == "":
            sentence_ended = True
        else:
            for char in line:
                # ZWYKŁA LITERA
                if char.isalpha():
                    if sentence_ended:      # NOWE ZDANIE
                        sentence_ended = False
                        inside_word = True
                        total_sentences += 1
                    elif not inside_word:   # NOWY WYRAZ
                        inside_word = True
                        if char.isupper():      # NAZWA WŁASNA
                            proper_name_sentences += 1
                # ZNAK KOŃCA ZDANIA
                elif char == "." or char == "!" or char == "?":
                    sentence_ended = True
                # POZOSTAŁE ZNAKI
                else:
                    inside_word = False

    if total_sentences == 0:
        return 0.0
    return proper_name_sentences / total_sentences * 100


if __name__ == "__main__":
    print(percent_sentences())
