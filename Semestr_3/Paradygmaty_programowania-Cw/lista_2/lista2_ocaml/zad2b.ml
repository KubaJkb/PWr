let fibTail n =
	let rec acc n prev curr =
		match n with 
		| n when n<0 -> raise (Failure "Wrong input")
		| 0 -> prev
		| n -> acc (n-1) curr (curr + prev)
	in
	acc n 0 1
;;


fibTail 42;;
