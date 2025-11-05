
.data
prompt_a: .asciiz "Podaj wartosc wspolczynnika 'a': "
prompt_b: .asciiz "Podaj wartosc wspolczynnika 'b': "
prompt_c: .asciiz "Podaj wartosc wspolczynnika 'c': "
prompt_d: .asciiz "Podaj wartosc wspolczynnika 'd': "
prompt_x: .asciiz "Podaj wartosc 'x': "
result_y: .asciiz "Wartosc wielomianu trzeciego stopnia y = "
new_line: .asciiz "\n"

.text
main:
    # Wyswietlanie wiadomosci i pobieranie danych od uzytkownika
    li $v0, 4                  # Laduje "System call code" dla 4 - "print_string"
    la $a0, prompt_a           # Laduje adres wiadomosci dla 'a'
    syscall                     # Wykonuje "print_string" z argumentem $a0 = prompt_a
    li $v0, 5                  # Laduje "System call code" dla 5 - "read_int"
    syscall                     # Wykonuje "read_int" i zapisuje wartosc 'a' do $v0
    move $s0, $v0              # Kopiuje wartosc integer 'a' z rejestru $v0 do $s0
    
    li $v0, 4                  # Laduje "System call code" dla 4 - "print_string"
    la $a0, prompt_b           # Laduje adres wiadomosci dla 'b'
    syscall                     # Wykonuje "print_string" z argumentem $a0 = prompt_b
    li $v0, 5                  # Laduje "System call code" dla 5 - "read_int"
    syscall                     # Wykonuje "read_int" i zapisuje wartosc 'b' do $v0
    move $s1, $v0              # Kopiuje wartosc integer 'b' z rejestru $v0 do $s1
    
    li $v0, 4                  # Laduje "System call code" dla 4 - "print_string"
    la $a0, prompt_c           # Laduje adres wiadomosci dla 'c'
    syscall                     # Wykonuje "print_string" z argumentem $a0 = prompt_c
    li $v0, 5                  # Laduje "System call code" dla 5 - "read_int"
    syscall                     # Wykonuje "read_int" i zapisuje wartosc 'c' do $v0
    move $s2, $v0              # Kopiuje wartosc integer 'c' z rejestru $v0 do $s2
    
    li $v0, 4                  # Laduje "System call code" dla 4 - "print_string"
    la $a0, prompt_d           # Laduje adres wiadomosci dla 'd'
    syscall                     # Wykonuje "print_string" z argumentem $a0 = prompt_d
    li $v0, 5                  # Laduje "System call code" dla 5 - "read_int"
    syscall                     # Wykonuje "read_int" i zapisuje wartosc 'd' do $v0
    move $s3, $v0              # Kopiuje wartosc integer 'd' z rejestru $v0 do $s3
    
    li $v0, 4                  # Laduje "System call code" dla 4 - "print_string"
    la $a0, prompt_x           # Laduje adres wiadomosci dla 'x'
    syscall                     # Wykonuje "print_string" z argumentem $a0 = prompt_x
    li $v0, 5                  # Laduje "System call code" dla 5 - "read_int"
    syscall                     # Wykonuje "read_int" i zapisuje wartosc 'x' do $v0
    move $s4, $v0              # Kopiuje wartosc integer 'x' z rejestru $v0 do $s4
    
    # Obliczanie warto�ci wielomianu
    mul $t1, $s4, $s4          # t1 = x * x
    mul $t1, $t1, $s4          # t1 = t1 * x (t1 = x^3)
    mul $t1, $t1, $s0          # t1 = t1 * a (t1 = ax^3)
    
    mul $t2, $s4, $s4          # t2 = x * x
    mul $t2, $t2, $s1          # t2 = t2 * b (t2 = bx^2)
    
    mul $t3, $s4, $s2          # t3 = x * c (t3 = cx)
    
    add $t4, $t1, $t2          # t4 = ax^3 + bx^2
    add $t4, $t4, $t3          # t4 = ax^3 + bx^2 + cx
    add $t4, $t4, $s3          # t4 = ax^3 + bx^2 + cx + d
    
    # Wy�wietlanie wyniku
    li $v0, 4                  # Laduje "System call code" dla 4 - "print_string"
    la $a0, result_y           # Laduje adres wiadomosci dla 'y'
    syscall                     # Wykonuje "print_string" z argumentem $a0 = prompt_y
    li $v0, 1                  # Laduje "System call code" dla 1 - "print_int"
    move $a0, $t4              # Laduje wartosc wyniku do $a0
    syscall                     # Wykonuje "print_int" z argumentem $a0 = [wartosc wielomianu]
    li $v0, 4                  # Laduje "System call code" dla 4 - "print_string"
    la $a0, new_line           # Laduje adres wiadomosci z nowa pusta linia
    syscall                     # Wykonuje "print_string" z argumentem $a0 = new_line
    
    # Exit program
    li $v0, 10                 # Laduje "System call code" dla 10 - "exit"
    syscall                     # Wykonuje "exit"
