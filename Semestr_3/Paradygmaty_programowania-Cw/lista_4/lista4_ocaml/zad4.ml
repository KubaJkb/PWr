type 'a bt = Empty | Node of 'a * 'a bt * 'a bt;;

let tt = 
Node(1, Node(2, Node(4, Empty, Empty), Empty), Node(3, Node(5, Empty, Node(6, Empty, Empty)), Empty));;



let internal_path_length tree =
    let rec ipl tr depth =
		match tr with
		| Empty -> 0
		| Node(_, l, r) -> depth + ipl l (depth+1) + ipl r (depth+1)
	in
	ipl tree 0
;;

let external_path_length tree =
	let rec epl tr depth =
		match tr with
		| Empty -> depth
		| Node(_, l, r) -> epl l (depth+1) + epl r (depth+1)
	in epl tree 0
;;

internal_path_length tt;;
external_path_length tt;;
