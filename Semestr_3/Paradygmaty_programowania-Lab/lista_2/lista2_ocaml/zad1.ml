let indexSwap a b lst =
	let rec get lstG idx =
		match lstG, idx with
		| [], _ -> None
		| h :: _, 0 -> Some h
		| _ :: t, _ -> get t (idx - 1)
	in
	let rec set lstS idx newVal =
		match lstS, idx with
		| [], _ -> []
		| _ :: t, 0 -> newVal :: t
		| h :: t, _ -> h :: set t (idx - 1) newVal
	in
	match get lst a, get lst b with
	| Some x, Some y -> 
		let lst = set lst a y in
		set lst b x
	| _ -> lst
;;

let indexSwap15 = indexSwap 1 5;;
indexSwap15 [0; 1; 2; 3; 4; 5; 6];;
