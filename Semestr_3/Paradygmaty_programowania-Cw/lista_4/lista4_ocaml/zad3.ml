type 'a bt = Empty | Node of 'a * 'a bt * 'a bt;;

let tt = 
Node(1, 
	Node(2, 
		Node(4, 
			Empty, 
			Empty
		),
		Empty
	),
	Node(3,
		Node(5,
			Empty,
			Node(6,
				Empty,
				Empty
			)
		),
		Empty
	)
);;



let breadthBT tree =
	let rec bfs queue acc =
		match queue with
		| [] -> List.rev acc
		| Empty::t -> bfs t acc
		| Node(v,l,r)::t -> bfs (t @ [l; r]) (v::acc)
	in
	bfs [tree] []
;;

breadthBT tt;;
