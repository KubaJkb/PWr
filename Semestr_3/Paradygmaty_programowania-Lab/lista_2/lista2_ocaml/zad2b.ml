let split3Tail lst =
	let rec acc remLst l1 l2 l3 =
	match remLst with
	| h1::h2::h3::t -> acc t (h1::l1) (h2::l2) (h3::l3)
	| _ -> (l1, l2, l3)
	in acc lst [] [] []
;;


let split3Tail lst =
	let length lst =
		let rec accLength lst i =
			match lst with
			| [] -> i
			| _::t -> accLength t (i+1)
		in
		accLength lst 0
	in 
	let cutNElements lst n =
		let rec accCutNElements cutList n remList =
			match n, remList with
			| 0, _ -> (cutList, remList)
			|_, h::t -> accCutNElements (h::cutList) (n-1) t
		in
		accCutNElements [] n lst
	in
	let revList lst = 
		let rec accRevList rev rem = 
			match rem with
			| [] -> rev
			| h::t -> accRevList (h::rev) t
		in
		accRevList[] lst
	in
	let len = (length lst) / 3 in
	let remList = lst in
	let (l1, remList) = cutNElements remList len in
	let (l2, remList) = cutNElements remList len in
	let (l3, remList) = cutNElements remList len in
	(revList l1, revList l2, revList l3)
;;



split3Tail [1; 2; 3; 4; 5; 6; 7; 8];;
