.data
    prompt: .asciiz "Podaj ciag liczb oddzielonych spacja reprezentujacych wektor: "
    error: .asciiz "Nie mozna wykonac iloczynu wektorow o roznych wymiarach!"

    input1_buffer: .space 100 	# Bufory na ciagi wejsciowe
    input2_buffer: .space 100
    
    vector1_values: .space 200 	# Pamiec na wektory rzadkie
    vector1_indexes: .space 200 	# Zakladajac maksymalnie 50 par 
    vector2_values: .space 200 	# (wartosc, indeks)
    vector2_indexes: .space 200 	# przy maksymalnej dlugosci wejsciowej 100 znakow
    
.text
.globl main
main:
    	#la $t7, vector1_values    # Zaladuj adres vector1_values do $t7
	#li $v0, 1                 # Kod syscall dla print_int
	#move $a0, $t7             # Przenies wartosc adresu do $a0
	#syscall                   # Wypisz wartosc adresu
    
    jal get_vector1			# WCZYTYWANIE 1. WEKTORA
    jal get_vector2			# WCZYTYWANIE 2. WEKTORA
    
    bne $s1, $s2, cant_multiply
    
    jal dot_product_start		# OBLICZANIE ILOCZYNU
    j exit			# ZAKONCZENIE PROGRAMU

get_vector1:
    li $v0, 4
    la $a0, prompt			# WYSWIETL KOMUNIKAT
    syscall

    li $v0, 8			# ODCZYTAJ CIAG LICZB
    la $a0, input1_buffer
    li $a1, 100 			# Maksymalna liczba znakow do odczytu
    syscall
    
    li $t0, 0  			# $t0 = Wskaznik do input1_buffer
    li $t1, 0 			# $t1 = Wskaznik do vector1_values
    li $t2, 0			# $t2 = Wskaznik do vector1_indexes 
    li $s1, 1			# Zmienna do przechowywania indeksu liczby
parse1_loop:
    li   $t5, 0			# Zmienna do przechowywania wartosci
    li   $t7, 0			# Zmienna przechowujaca znak liczby (0+ ; 1-)
parse1_value:
    lb   $t6, input1_buffer($t0)         
    addi $t0, $t0, 1        		# Przesun wskaznik bufora
    ble  $t6, 32, end1_value  	# Jesli znak jest spacja/nullem/enterem, zakoncz parsowanie wartosci
    
    bne  $t6, 45, positive1		# SPRAWDZANIE CZY LICZBA JEST UJEMNA
    li   $t7, 1
    j    parse1_value
    positive1:
    
    subi $t6, $t6, 48       		# Konwertuj znak na cyfre (ASCII '0' -> 0)
    mul  $t5, $t5, 10      		# Przesun poprzednie cyfry w lewo
    add  $t5, $t5, $t6      		# Dodaj nowa cyfre
    
    
    j    parse1_value         	# Powtorz dla nastepnego znaku
end1_value:
    beqz $t5, skip_saving		# Jezeli wartosc wynosi 0, nie zapisujemy tej liczby
    
    beq  $t7, 0, num_positive
    sub $t5, $zero, $t5
    num_positive:
    sw   $t5, vector1_values($t1)	# Zapisz wartosc do vector1_values
    sw   $s1, vector1_indexes($t2)	# Zapisz indeks do vector1_indexes
    addi $t1, $t1, 4		# Przesun wskaznik vector1_values
    addi $t2, $t2, 4		# Przesun wskaznik vector1_indexes
    skip_saving:
    
    blt  $t6, 32, end_parse		# Jesli znak jest null, zakoncz parsowanie
    addi $s1, $s1, 1		# Zwieksz indeks analizowanej liczby o 1
    j parse1_loop			# Przejdz do zapisywania kolejnej liczby
    
get_vector2:
    la $a0, prompt			# WYSWIETL KOMUNIKAT
    li $v0, 4
    syscall

    li $v0, 8			# ODCZYTAJ CIAG LICZB
    la $a0, input2_buffer
    li $a1, 100 			# Maksymalna liczba znakow do odczytu
    syscall
    
    li $t0, 0  			# $t0 = Wskaznik do input1_buffer
    li $t1, 0 			# $t1 = Wskaznik do vector1_values
    li $t2, 0			# $t2 = Wskaznik do vector1_indexes 
    li $s2, 1			# Zmienna do przechowywania indeksu liczby
parse2_loop:
    li   $t5, 0			# Zmienna do przechowywania wartosci
    li   $t7, 0			# Zmienna przechowujaca znak liczby (0+ ; 1-)
parse2_value:
    lb   $t6, input2_buffer($t0)         
    addi $t0, $t0, 1        		# Przesun wskaznik bufora
    ble  $t6, 32, end2_value  	# Jesli znak jest spacja/nullem/enterem, zakoncz parsowanie wartoïsci
    
    bne  $t6, 45, positive2		# SPRAWDZANIE CZY LICZBA JEST UJEMNA
    li   $t7, 1
    j    parse2_value
    positive2:
    
    subi $t6, $t6, 48       		# Konwertuj znak na cyfre (ASCII '0' -> 0)
    mul  $t5, $t5, 10      		# Przesun poprzednie cyfry w lewo
    add  $t5, $t5, $t6      		# Dodaj nowa cyfre
    
    j    parse2_value         	# Powtorz dla nastepnego znaku
end2_value:
    beqz $t5, skip_saving2		# Jezeli wartosc wynosi 0, nie zapisujemy tej liczby
    
    beq  $t7, 0, num_positive2
    sub $t5, $zero, $t5
    num_positive2:
    sw   $t5, vector2_values($t1)      	# Zapisz wartosc do vector1_values
    sw   $s2, vector2_indexes($t2)	# Zapisz indeks do vector1_indexes
    addi $t1, $t1, 4         		# Przesun wskaznik vector1_values
    addi $t2, $t2, 4         		# Przesun wskaznik vector1_indexes
    skip_saving2:
    
    blt  $t6, 32, end_parse		# Jesli znak jest null, zakoncz parsowanie
    addi $s2, $s2, 1		# Zwieksz indeks analizowanej liczby o 1
    j parse2_loop			# Przejdz do zapisywania kolejnej liczby
    
end_parse:
    jr   $ra
    
dot_product_start:
    la $t1, 0			# Wskaznik do vector1
    la $t2, 0 			# Wskaznik do vector2
    li $s0, 0			# Wartosc ilocznu skalarnego
    
dot_product:
    lw $t3, vector1_indexes($t1)
    lw $t4, vector2_indexes($t2)
    
    beqz $t3, end_dot_product
    beqz $t4, end_dot_product
    
    beq  $t3, $t4, multiply_and_add 	# Jezeli $t4 == $t5
    
    bgt  $t3, $t4, not_less
    addi $t1, $t1, 4		# Jezeli $t4 < $t5
    not_less:
    blt  $t3, $t4, not_bigger
    addi $t2, $t2, 4		# Jezeli $t4 > $t5
    not_bigger:
    
    j dot_product			# Jezeli $t4 != $t5
    
multiply_and_add:
    lw   $t3, vector1_values($t1)
    lw   $t4, vector2_values($t2)
    
    mul  $t5, $t3, $t4
    add  $s0, $s0, $t5
    
    addi $t1, $t1, 4
    addi $t2, $t2, 4
    
    j dot_product
    
end_dot_product:
    li   $v0, 1
    move $a0, $s0
    syscall
    
    jr $ra

cant_multiply:
    li $v0, 4
    la $a0, error			# WYSWIETL KOMUNIKAT
    syscall


exit:
    li $v0, 10
    syscall
    
