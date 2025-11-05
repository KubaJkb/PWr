import sys

def longest_sentence():
    sentence = ""
    longest = ""

    for line in sys.stdin:
        # PUSTA LINIA - KONIEC ZDANIA
        if line.strip() == "":
            if len(sentence) > len(longest):
                longest = sentence
            sentence = ""
        else:
            for char in line:
                if sentence == "" and not char.isalpha():
                    continue

                sentence += char
                # ZNAK KOŃCA ZDANIA
                if char == "." or char == "!" or char == "?":
                    if len(sentence) > len(longest):
                        longest = sentence
                    sentence = ""

    if len(sentence) > len(longest):
        longest = sentence

    print(longest)


if __name__ == "__main__":
    longest_sentence()
