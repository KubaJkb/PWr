let rec insertionSort compare lst =
	let rec insert x sorted =
		match sorted with
		| [] -> [x]
		| h::t when compare x h -> x::sorted
		| h::t -> h::(insert x t)
	in
	match lst with
	| [] -> []
	| h::t -> insert h (insertionSort compare t)
;;

let lst = [("a", 2); ("b", 1); ("c", 2); ("d", 1); ("e", 1)];;
let cmp (_, x) (_, y) = x <= y;;
insertionSort cmp lst;;
