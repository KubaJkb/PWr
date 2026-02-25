#!/usr/bin/env python3

def main():
    print("Witaj! To jest prosty skrypt interaktywny.")
    name = input("Jak masz na imię? ")
    age_str = input("Ile masz lat? ")
    try:
        age = int(age_str)
        if age < 0:
            print("Wiek nie może być ujemny — chyba żartujesz 😉")
        else:
            print(f"Cześć, {name}! Za 10 lat będziesz mieć {age + 10} lat.")
    except ValueError:
        print("Nie podałeś prawidłowej liczby dla wieku.")

if __name__ == "__main__":
    main()
