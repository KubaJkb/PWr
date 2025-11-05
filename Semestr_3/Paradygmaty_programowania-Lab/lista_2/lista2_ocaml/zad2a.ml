let rec split3Rec lst =
	match lst with
	| h1::h2::h3::t -> 
		let (l1, l2, l3) = split3Rec(t) in
		(h1::l1, h2::l2, h3::l3)
	| _ -> ([], [], [])
;;


let split3Rec lst =
	let rec length lst =
		match lst with
		| [] -> 0
		| h::t -> 1 + length t
	in 
	let rec cutNElements lst n =
		match n, lst with
		| 0, _ -> ([], lst)
		| _, h::t -> let (remEl, remTl) = cutNElements t (n-1) in (h::remEl, remTl)
	in
	let len = (length lst) / 3 in
	let remList = lst in
	let (l1, remList) = cutNElements remList len in
	let (l2, remList) = cutNElements remList len in
	let (l3, remList) = cutNElements remList len in
	(l1, l2, l3)
;;



split3Rec [1; 2; 3; 4; 5; 6; 7; 8];;
