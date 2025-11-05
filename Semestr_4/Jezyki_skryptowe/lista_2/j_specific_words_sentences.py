import sys

# ********************* DWA TE SAME SŁOWA *********************
# def specific_words_sentences():
#     sentence = ""
#     word = ""
#     specific_words_count = 0
#     specific_words = {"i", "oraz", "ale", "że", "lub"}
#
#     for line in sys.stdin:
#         # PUSTA LINIA - KONIEC ZDANIA
#         if line.strip() == "":
#             if word in specific_words:
#                 specific_words_count += 1
#             if specific_words_count >= 2:
#                 print(sentence)
#             sentence = ""
#         else:
#             for char in line:
#                 if sentence == "":
#                     # NOWE ZDANIE
#                     if char.isalpha():
#                         sentence = char
#                         word = char
#                         specific_words_count = 0
#                 else:
#                     sentence += char
#                     # KONIEC ZDANIA
#                     if char == "." or char == "!" or char == "?":
#                         if word in specific_words:
#                             specific_words_count += 1
#                         if specific_words_count >= 2:
#                             print(sentence)
#                         sentence = ""
#                     # JUŻ SPEŁNIA WARUNEK
#                     elif specific_words_count >= 2:
#                         continue
#                     # ZWYKŁA LITERA
#                     elif char.isalpha():
#                         word += char
#                     # INNY ZNAK, KONIEC SŁOWA
#                     else:
#                         if word in specific_words:
#                             specific_words_count += 1
#                         word = ""
#
#     if word in specific_words:
#         specific_words_count += 1
#     if specific_words_count >= 2:
#         print(sentence)

# ********************* DWA RÓŻNE SŁOWA *********************
def specific_words_sentences():
    sentence = ""
    word = ""
    specific_words_count = 0
    specific_words = {"i", "oraz", "ale", "że", "lub"}
    words_left = {}

    for line in sys.stdin:
        # PUSTA LINIA - KONIEC ZDANIA
        if line.strip() == "":
            if word in words_left:  # ------- KONIEC SŁOWA
                specific_words_count += 1
            if specific_words_count >= 2:
                print(sentence)
            sentence = ""
        else:
            for char in line:
                if sentence == "":
                    # NOWE ZDANIE
                    if char.isalpha():
                        sentence = char
                        word = char
                        specific_words_count = 0
                        words_left = specific_words.copy()
                else:
                    sentence += char
                    # KONIEC ZDANIA
                    if char == "." or char == "!" or char == "?":
                        if word in words_left:  # ------- KONIEC SŁOWA
                            specific_words_count += 1
                        if specific_words_count >= 2:
                            print(sentence)
                        sentence = ""
                    # JUŻ SPEŁNIA WARUNEK
                    elif specific_words_count >= 2:
                        continue
                    # ZWYKŁA LITERA
                    elif char.isalpha():
                        word += char
                    # INNY ZNAK, KONIEC SŁOWA
                    else:
                        if word in words_left:  # ------- KONIEC SŁOWA
                            words_left.remove(word)
                            specific_words_count += 1
                        word = ""

    if sentence != "":
        if word in words_left:  # ------- KONIEC SŁOWA
            specific_words_count += 1
    if specific_words_count >= 2:
        print(sentence)

if __name__ == "__main__":
    specific_words_sentences()
