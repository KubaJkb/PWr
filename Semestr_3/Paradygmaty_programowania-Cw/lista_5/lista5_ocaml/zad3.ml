type 'a lBT = 
	| LEmpty 
	| LNode of 'a * (unit -> 'a lBT) * (unit -> 'a lBT)
;;

let rec lBreadth tree =
  let rec loop queue =
    match queue with
    | [] -> LNil
    | LEmpty :: rest -> loop rest
    | LNode (v, l, r) :: rest -> LCons (v, lazy (loop (rest @ [l (); r ()])))
  in loop [tree]

let rec lTree n = LNode (n, (fun () -> lTree (2 * n)), (fun () -> lTree (2 * n + 1)))
;;


let treeExample1 = ltake (20, lBreadth (lTree 1));;
let treeExample2 = ltake (20, lBreadth LEmpty);;
