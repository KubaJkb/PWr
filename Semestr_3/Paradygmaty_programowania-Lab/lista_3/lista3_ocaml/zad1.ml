let (!?) a =
	let rec poly x coefs exp =
		match coefs with
		| [] -> 0.0
		| c::cs -> c *. (x ** float_of_int exp) +. (poly x cs (exp+1))
	in
	fun x -> poly x a 0
;;



let a = [1.0; 2.0; 3.0];;
let f = (!?) a;;
f 2.0;;


(!?) [] 5.0;;
(!?) [3.0] (-2.0);;
(!?) [1.0; 2.0] 2.0;;
(!?) [1.0; -2.0; 3.0] (-1.0);;
(!?) [2.0; -3.0; 4.0; -5.0] 2.0;;
(!?) [1.0; 10.0; 100.0; 1000.0] 1000.0;;







(*
let (!?) a =
  let poly x =
    if a = [] then 0.0
    else List.fold_left (fun acc (coef, idx) -> acc +. coef *. (x ** float_of_int idx)) 
	0.0 (List.mapi (fun idx coef -> (coef, idx)) a)
  in
  poly
;;
*)
