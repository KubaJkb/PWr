type 'a ltree3 = 
	| LEmpty
	| LNode of 'a * 'a ltree3 Lazy.t * 'a ltree3 Lazy.t * 'a ltree3 Lazy.t
;;

type 'a llist = LNil | LCons of 'a * (unit -> 'a llist);;

let rec ltake n lxs =
	match n, lxs with
	| 0, _ -> []
	| _, LNil -> []
	| n, LCons (x, xf) -> x :: ltake (n - 1) (xf ())
;;

let rec append_llist lxs1 lxs2 =
	match lxs1 with
	| LNil -> lxs2 ()
	| LCons (x, xf) -> LCons (x, fun () -> append_llist (xf ()) lxs2)
;;


let rec traverse order tree =
	match tree with
	| LEmpty -> LNil
	| LNode (value, left, middle, right) ->
		let children = order (Lazy.force left) (Lazy.force middle) (Lazy.force right) 
		in
		LCons (value, fun () -> traverse_children order children)

and traverse_children order = function
	| [] -> LNil
	| child :: rest ->
		let child_list = traverse order child 
		in
		append_llist child_list (fun () -> traverse_children order rest)
;;


(* ---------------- TESTY --------------- *)

let preorder left middle right = [middle; left; right];;

let my_tree = 
	LNode ( 1,
		lazy ( LNode (2,
			lazy (LNode (11, lazy LEmpty, lazy LEmpty, lazy LEmpty)),
			lazy (LNode (10, lazy LEmpty, lazy LEmpty, lazy LEmpty)),
			lazy LEmpty
		)),
		lazy ( LNode (3, 
			lazy LEmpty, lazy LEmpty, lazy LEmpty)),
		lazy ( LNode (4, 
			lazy LEmpty, lazy LEmpty, lazy LEmpty))
    )
;;
let result = traverse preorder my_tree;;

ltake 6 result;;





(*
type 'a lazy_tree3 =
	| LEmpty
	| LNode of 'a * (unit -> 'a lazy_tree3) * (unit -> 'a lazy_tree3) * (unit -> 'a lazy_tree3)
;;

let rec traverse order tree =
	match tree with
	| LEmpty -> LNil
	| LNode (value, left, middle, right) ->
		let children = order (left ()) (middle ()) (right ()) 
		in
		LCons (value, fun () -> traverse_children order children)

and traverse_children order = function
	| [] -> LNil
	| child :: rest ->
		let child_list = traverse order child 
		in
		append_llist child_list (fun () -> traverse_children order rest)
;;
*)