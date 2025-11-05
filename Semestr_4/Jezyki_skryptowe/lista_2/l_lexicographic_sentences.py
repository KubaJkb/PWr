import sys


def lexicographic_sentences():
    sentence = ""
    word = ""
    prev_word = ""
    lexicographic = True

    for line in sys.stdin:
        # PUSTA LINIA - KONIEC ZDANIA
        if line.strip() == "":
            if sentence != "":
                if not prev_word <= word:
                    lexicographic = False
                if lexicographic:
                    print(sentence)
                sentence = ""
        else:
            for char in line:
                if sentence == "":
                    # NOWE ZDANIE
                    if char.isalpha():
                        sentence = char
                        word = char
                        prev_word = ""
                        lexicographic = True
                else:
                    sentence += char
                    # KONIEC ZDANIA
                    if char == "." or char == "!" or char == "?":
                        if not prev_word <= word:
                            lexicographic = False
                        if sentence != "" and lexicographic:
                            print(sentence)
                        sentence = ""
                    # NIE SPEŁNIA WARUNKU
                    elif not lexicographic:
                        continue
                    # ZWYKŁA LITERA
                    elif char.isalpha():
                        word += char
                    # INNY ZNAK, KONIEC SŁOWA
                    else:
                        if word != "":
                            if not prev_word <= word:
                                lexicographic = False
                            prev_word = word
                            word = ""

    if sentence != "":
        if not prev_word <= word:
            lexicographic = False
        if lexicographic:
            print(sentence)


if __name__ == "__main__":
    lexicographic_sentences()
