type 'a graph = Graph of ('a -> 'a list);;

let g = Graph 
(function
  | 0 -> [3]
  | 1 -> [0; 2; 4]
  | 2 -> [1]
  | 3 -> []
  | 4 -> [0; 2]
  | n -> failwith ("Graph g: node " ^ string_of_int n ^ " doesn't exist")
);;



let depthSearch (Graph succ) startNode =
	let rec dfs visited stack =
		match stack with
		| [] -> []
		| h::t -> if List.mem h visited then dfs visited t 
			else h :: dfs (h::visited) (succ h @ t)
	in
	dfs [] [startNode]
;;

let depthSearch (Graph succ) startNode =
	let rec dfs visited stack =
		match stack with
		| [] -> List.rev visited
		| h::t -> if List.mem h visited then dfs visited t 
			else dfs (h::visited) (succ h @ t)
	in
	dfs [] [startNode]
;;

depthSearch g 4;
