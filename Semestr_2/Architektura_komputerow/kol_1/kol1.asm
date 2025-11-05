# Napisać program który poprosi użytkownika o ciąg znaków s, maks długość s=400. Dla wprowadzonego ciągu:
# 1. Wyświetla ciąg znaków składających się z: co drugi znak czytając od tyłu wprowadzony ciąg.
# 2. Sprawdzi czy string utworzony z co drugiego znaku idąc od przodu jest palindromem (napisze "TAK" lub "NIE") [pusty string jest palindromem]


.data
buffer: .space 400      
prompt: .asciiz "Wprowadz ciag: "
newline: .asciiz "\n"
result: .asciiz "Wynik: "

.text

main:

    li $v0, 4              
    la $a0, prompt   
    syscall


    li $v0, 8              
    la $a0, buffer         
    li $a1, 400           
    syscall

    la $t0, buffer          
    li $t1, 0              

find_length:
    lb $t2, 0($t0)         
    beq $t2, $zero, length_found 
    addi $t0, $t0, 1       
    addi $t1, $t1, 1       
    j find_length

length_found:

    li $v0, 4              
    la $a0, result         
    syscall

    subi $t0, $t0, 1       
    addi $t1, $t1, -1 
    
        

print_reverse:
    blt $t1, $zero, end    
    lb $t2, 0($t0)         
    li $v0, 11              
    move $a0, $t2          
    syscall

    subi $t0, $t0, 2       
    subi $t1, $t1, 2       
    j print_reverse

end:

    li $v0, 4              
    la $a0, newline        
    syscall


    li $v0, 10             
    syscall
