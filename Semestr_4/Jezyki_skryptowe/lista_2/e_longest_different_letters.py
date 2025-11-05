import sys


def longest_different_letters():
    sentence = ""
    longest = ""
    prev_word_letter = ""
    different_letters = True
    inside_word = False

    for line in sys.stdin:
        # PUSTA LINIA - KONIEC ZDANIA
        if line.strip() == "":
            if len(sentence) > len(longest) and different_letters:
                longest = sentence
            sentence = ""
        else:
            for char in line:
                if sentence == "":
                    # NOWE ZDANIE
                    if char.isalpha():
                        sentence = char
                        prev_word_letter = char
                        different_letters = True
                        inside_word = True
                else:
                    sentence += char
                    # KONIEC ZDANIA
                    if char == "." or char == "!" or char == "?":
                        if len(sentence) > len(longest) and different_letters:
                            longest = sentence
                        sentence = ""
                    # NIE SPEŁNIA WARUNKU
                    elif not different_letters:
                        continue
                    # ZWYKŁA LITERA
                    elif char.isalpha():
                        # NOWE SŁOWO
                        if not inside_word:
                            inside_word = True
                            # TE SAME LITERY W SĄSIEDNICH WYRAZACH
                            if prev_word_letter.lower() == char.lower():
                                different_letters = False
                            else:
                                prev_word_letter = char
                    # INNY ZNAK, KONIEC SŁOWA
                    else:
                        inside_word = False


    if len(sentence) > len(longest) and different_letters:
        longest = sentence

    print(longest)


if __name__ == "__main__":
    longest_different_letters()
