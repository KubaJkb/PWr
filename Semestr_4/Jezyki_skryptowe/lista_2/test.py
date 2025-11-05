import subprocess


process1 = subprocess.Popen(["python", "remove_metadata.py"], stdin=open("test.txt", "r", encoding="utf-8"), stdout=subprocess.PIPE)
process2 = subprocess.Popen(["python", "j_specific_words_sentences.py"], stdin=process1.stdout)

process1.stdout.close()     # Zamykamy strumień wyjściowy pierwszego procesu
process2.communicate()      # Uruchamiamy drugi proces i czekamy na zakończenie


# process1 = subprocess.Popen(["python", "remove_metadata.py"], stdin=open("calineczka.txt", "r", encoding="utf-8"), stdout=subprocess.PIPE)
# process2 = subprocess.Popen(["python", "i_first_20_sentences.py"], stdin=process1.stdout, stdout=subprocess.PIPE)
# process3 = subprocess.Popen(["python", "a_count_paragraphs.py"], stdin=process2.stdout)
#
# process1.stdout.close()     # Zamykamy strumień wyjściowy pierwszego procesu
# process2.stdout.close()     # Zamykamy strumień wyjściowy pierwszego procesu
# process3.communicate()      # Uruchamiamy drugi proces i czekamy na zakończenie
