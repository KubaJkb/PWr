let rec pascal_row n =
	match n with
	| 0 -> [1]
	| 1 -> [1; 1]
	| _ -> 
		let prev = pascal_row (n - 1) 
		in
		let rec compute_line acc = function
			| [] | [_] -> List.rev acc
			| x :: y :: rest -> compute_line ((x + y) :: acc) (y :: rest)
		in
		1 :: (compute_line [] prev) @ [1]
;;


let rec pascalGiftF n k = 
	let rec add_side n k res rem =
		if rem > 0 then
			let value = List.nth (pascal_row (n - 1 - (k-2 - (k/2-rem)*2))) (k/2-rem+1)
			in
			add_side n k (value::res@[value]) (rem-1)
		else
			res
	in
	
	if k < n then add_side n k (pascal_row (n-1-k)) (k/2)
	else pascalGiftF n (n*2-2-k)
;;

pascalGiftF 5 0;;
pascalGiftF 5 1;;
pascalGiftF 5 2;;
pascalGiftF 5 3;;
pascalGiftF 5 4;;
pascalGiftF 5 5;;
pascalGiftF 5 6;;
pascalGiftF 5 7;;
pascalGiftF 5 8;;

pascalGiftF 6 0;;
pascalGiftF 6 1;;
pascalGiftF 6 2;;
pascalGiftF 6 3;;
pascalGiftF 6 4;;
pascalGiftF 6 5;;
pascalGiftF 6 6;;
pascalGiftF 6 7;;
pascalGiftF 6 8;;
pascalGiftF 6 9;;
pascalGiftF 6 10;;
