let rec fib n =
	match n with
	| n when n<0 -> raise (Failure "Wrong input")
	| 0 -> 0
	| 1 -> 1
	| n -> fib (n-2) + fib (n-1)
;;


fib 42;;
