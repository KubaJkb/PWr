import sys


def mex4word_sentences():
    sentence = ""
    inside_word = False
    word_counter = 0

    for line in sys.stdin:
        # PUSTA LINIA - KONIEC ZDANIA
        if line.strip() == "":
            if sentence != "" and word_counter <= 4:
                print(sentence)
            sentence = ""
        else:
            for char in line:
                if sentence == "":
                    # NOWE ZDANIE
                    if char.isalpha():
                        sentence = char
                        inside_word = True
                        word_counter = 1
                else:
                    sentence += char
                    # KONIEC ZDANIA
                    if char == "." or char == "!" or char == "?":
                        if sentence != "" and word_counter <= 4:
                            print(sentence)
                        sentence = ""
                    # NIE SPEŁNIA WARUNKU
                    elif word_counter > 4:
                        continue
                    # ZWYKŁA LITERA
                    elif char.isalpha():
                        # NOWE SŁOWO
                        if not inside_word:
                            inside_word = True
                            word_counter += 1
                    # INNY ZNAK, KONIEC SŁOWA
                    else:
                        inside_word = False

    if sentence != "" and word_counter <= 4:
        print(sentence)


if __name__ == "__main__":
    mex4word_sentences()
