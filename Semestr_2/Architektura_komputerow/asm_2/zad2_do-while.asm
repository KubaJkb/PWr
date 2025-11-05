    .data
prompt: .asciiz "Podaj wartosc N: "
result: .asciiz "Wynik: "
newline: .asciiz "\n"
overflow: .asciiz "Przepenienie - obliczana wartosc silni przekroczyla zakres 32-bitowy.\n"

    .text  
get_input:
    # Wyswietlenie komunikatu proszacego użytkownika o wartosc N
    li $v0, 4           # Kod syscall dla wyswietlenia lancucha
    la $a0, prompt      # Adres lancucha prompt
    syscall
    
    # Wczytanie wartosci N
    li $v0, 5           # Kod syscall dla wczytania liczby calkowitej
    syscall
    move $t0, $v0       # Zapisanie wartosci N w rejestrze $t0

    # Sprawdzenie czy N > 0
    bltz $t0, get_input # Jesli N < 0, wczytaj wartosc ponownie

    # Inicjalizacja wartosci silni
    li $s0, 1           # $s0 przechowuje wynik silni, poczatkowo ustawiony na 1
    li $t1, 1           # $t2 przechowuje wartosc N podczas obliczania silni

calculate_factorial:
    # Sprawdzenie czy wynik mnożenia $s0 przez $t1 miesci sie w zakresie 32-bitowym
    mult $s0, $t1
    mfhi $t8
    mflo $t9
    sra $t9, $t9, 31
    bne $t8, $t9, overflow_occurred

    # Aktualizacja wartosci silni
    mflo $s0       	# Aktualizacja wartosci silni na wynik mnożenia
    addi $t1, $t1, 1	# Zwiekszenie $t1 o 1

    # Sprawdzenie warunku petli
    ble $t1, $t0, calculate_factorial

print_result:
    # Wyswietlenie wyniku
    li $v0, 4           # Kod syscall dla wyswietlenia lancucha
    la $a0, result      # Adres lancucha result
    syscall
    li $v0, 1           # Kod syscall dla wyswietlenia liczby calkowitej
    move $a0, $s0       # Przekazanie wyniku do wyswietlenia
    syscall

exit:
    # Koniec programu
    li $v0, 10          # Kod syscall dla zakonczenia programu
    syscall

overflow_occurred:
    # Obsluga przepelnienia - wypisanie komunikatu
    li $v0, 4           # Kod syscall dla wyswietlenia lancucha
    la $a0, overflow
    syscall
    
    b exit
    