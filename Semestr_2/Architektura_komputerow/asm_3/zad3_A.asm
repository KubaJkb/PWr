	.data
#array: .word 0:100
array: .space 400   # zarezerwowanie miejsca na 100 s³ów (1 s³owo = 4 bajty), bez inicjowania ich
prompt_n: .asciiz "Podaj N: "
prompt_numbers: .asciiz "Podaj liczbe: "
comma: .asciiz ", "
    
   	.text
# ------- WPROWADZANIE DANYCH -------
get_data:
	# wczytaj N
	li $v0, 4
	la $a0, prompt_n
	syscall
	li $v0, 5
	syscall
	move $s0, $v0  # $s0 przechowuje N

	# wczytaj N liczb
	li $t1, 0  # $t1 to i
read_loop:
	li $v0, 4
	la $a0, prompt_numbers
	syscall
	li $v0, 5
	syscall
    
	sll $t9, $t1, 2    
	sw $v0, array($t9)
    
	addi $t1, $t1, 1
	blt $t1, $s0, read_loop # je¿eli i < N, pobierz kolejn¹ liczbê

	# wywo³anie procedury sortowania	
	jal sort


# ------- WYDRUK WYNIKÓW -------
print_array:
	li $t1, 0  # $t1 to i 
print_comma:
	beqz $t1, print_loop
	li $v0, 4
    	la $a0, comma
    	syscall  
print_loop:    
	sll $t9, $t1, 2  # $t8 to i*4
	    
	li $v0, 1
	lw $a0, array($t9)  # $a0 to array[i]
	syscall    	  
    
	addi $t1, $t1, 1  # zwiêksz indeks
	blt $t1, $s0, print_comma  # jeœli i < N, wypisz kolejn¹ wartoœæ z array

exit:
	li $v0, 10
	syscall
	
# ------- SORTOWANIE -------
sort:
	li $t1, 1        # $t1 to i
	move $t3, $s0
	subi $t3, $t3, 1 # $t3 to N-1-i
i_loop:
	beq $t1, $s0, end_sort   # jeœli i == N, zakoñcz sortowanie
	li $t2, 0        # $t2 to j
j_loop:
	beq $t2, $t3, increment_i   # jeœli j == (N-1-i), inkrementuj i
	sll $t9, $t2, 2        # $t9 to j*4
	
	lw $t4, array($t9)	   # $t4 to array[j]
	lw $t5, array+4($t9)   # $t5 to array[j+1]
	
	ble $t4, $t5, increment_j
	# zamieñ array[j] i array[j+1]
	sw $t5, array($t9)
	sw $t4, array+4($t9)
increment_j:
	addi $t2, $t2, 1
	j j_loop
increment_i:
	addi $t1, $t1, 1
	subi $t3, $t3, 1
	j i_loop
end_sort:
	jr $ra
