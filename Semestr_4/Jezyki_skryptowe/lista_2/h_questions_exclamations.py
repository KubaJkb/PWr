import sys

def questions_exclamations():
    sentence = ""

    for line in sys.stdin:
        # PUSTA LINIA - KONIEC ZDANIA
        if line.strip() == "":
            sentence = ""
        else:
            for char in line:
                if sentence == "" and not char.isalpha():
                    continue

                sentence += char
                # ZNAK KOŃCA ZDANIA
                if char == ".":
                    sentence = ""
                elif char == "!" or char == "?":
                    print(sentence)
                    sentence = ""



if __name__ == "__main__":
    questions_exclamations()
