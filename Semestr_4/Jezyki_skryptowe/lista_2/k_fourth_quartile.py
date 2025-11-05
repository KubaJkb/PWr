import sys

def fourth_quartile():
    sentence = ""
    longest = ""
    all_sentences = []

    for line in sys.stdin:
        # PUSTA LINIA - KONIEC ZDANIA
        if line.strip() == "":
            if sentence != "":
                if len(sentence) > len(longest):
                    longest = sentence
                all_sentences.append(sentence)
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
                    all_sentences.append(sentence)
                    sentence = ""

    if sentence != "":
        if len(sentence) > len(longest):
            longest = sentence
        all_sentences.append(sentence)

    fourth_quartile_length = len(longest)*3/4
    for s in all_sentences:
        if len(s) > fourth_quartile_length:
            print(s)


if __name__ == "__main__":
    fourth_quartile()
