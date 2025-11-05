# Napisać program który poprosi użytkownika o wprowadzenie liczby rzeczywistej N. 
# Program ma wyliczyć i wydrukować dwie wartości:
# W1 = pierwiastek kwadratowy z N
# W2 = pierwiastek trzeciego stopnia z N
# dokładność = 10^-6 (0,000001)

.data
    prompt: .asciiz "Podaj liczbe: "
    newline: .asciiz "\n"
    #: .space 0
    f_zero: .float 0.0 	
    f_two: .float 2.0
    precision: .float 0.000001
    

.text
.globl main
main:
	l.s $f1, precision
	l.s $f2, f_two
	
	li $v0, 4
	la $a0, prompt
	syscall
	
	li $v0, 6
	syscall
	
	mov.s $f3, $f0
	l.s $f4, f_zero 		# $f4 - lewa
	mov.s $f5, $f3	  	# $f5 - prawa
	
	mov.s $f7, $f0
	mov.s $f8, $f0

square_root:
	add.s $f6, $f4, $f5
	div.s $f6, $f6, $f2
	
	mul.s $f10, $f6, $f6 	# $f6 = wartość kwadratu
	
	c.le.s $f3, $f10     	# if $f6 > wartość N  to  $f4 = $f4 /2 
	bc1t square_bigger
	
	c.lt.s $f10, $f3		# if #f6 < wartość N  to  $f4 = ($f4 + $f3) / 2
	bc1t square_smaller
	
	j root_three
	
	square_bigger:		# $f4 = $f4 / 2
	sub.s $f10, $f10, $f1	# jeżeli po odjęciu precyzji $f6 <= wartość N to koniec liczenia
	c.le.s $f10, $f3
	bc1t root_three_s
	
	mov.s $f5, $f6
	j square_root
	
	square_smaller:		# $f4 = ($f4 + $f3) / 2
	add.s $f10, $f10, $f1	# jeżeli po dodaniu precyzji $f6 >= wartość N to koniec liczenia
	c.le.s $f3, $f10
	bc1t root_three_s
	
	mov.s $f4, $f6
	j square_root

root_three_s:
	l.s $f4, f_zero 	# $f4 - lewa
	mov.s $f5, $f3	  	# $f5 - prawa
	mov.s $f11, $f6

root_three:
	add.s $f6, $f4, $f5
	div.s $f6, $f6, $f2
	
	mul.s $f10, $f6, $f6 	# $f6 = wartość kwadratu
	mul.s $f10, $f10, $f6
	
	c.le.s $f3, $f10     	# if $f6 > wartość N  to  $f4 = $f4 /2 
	bc1t three_bigger
	
	c.lt.s $f10, $f3		# if #f6 < wartość N  to  $f4 = ($f4 + $f3) / 2
	bc1t three_smaller
	
	j exit
	
	three_bigger:		# $f4 = $f4 / 2
	sub.s $f10, $f10, $f1	# jeżeli po odjęciu precyzji $f6 <= wartość N to koniec liczenia
	c.le.s $f10, $f3
	bc1t exit
	
	mov.s $f5, $f6
	j root_three
	
	three_smaller:		# $f4 = ($f4 + $f3) / 2
	add.s $f10, $f10, $f1	# jeżeli po dodaniu precyzji $f6 >= wartość N to koniec liczenia
	c.le.s $f3, $f10
	bc1t exit
	
	mov.s $f4, $f6
	j root_three
	
exit:
	li $v0, 2
	mov.s $f12, $f11
	syscall
	li $v0, 4
	la $a0, newline
	syscall
	li $v0, 2
	mul.s $f12, $f11, $f11
	syscall
	li $v0, 4
	la $a0, newline
	syscall
	
	li $v0, 2
	mov.s $f12, $f6
	syscall
	li $v0, 4
	la $a0, newline
	syscall
	li $v0, 2
	mul.s $f12, $f6, $f6
	mul.s $f12, $f12, $f6
	syscall
	
	li $v0, 10
	syscall
	
