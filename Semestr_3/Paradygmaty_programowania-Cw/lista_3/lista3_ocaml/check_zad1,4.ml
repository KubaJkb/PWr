(* Zad 1 *)

let f1 x = x 2 2;;
(* val f1 : (int -> int -> 'a) -> 'a = <fun> *)

let f2 x y z = x ( y ^ z );;
(* val f2 : (string -> 'a) -> string -> string -> 'a = <fun> *)

(* Zad 4 *)

let rec quicksort = function
	[] -> []
	| [x] -> [x]
	| xs -> let small = List.filter (fun y -> y < List.hd xs ) xs
		and large = List.filter (fun y -> y >= List.hd xs ) xs
		in quicksort small @ quicksort large
;;

let rec quicksort' = function
	[] -> []
	| x::xs -> let small = List.filter (fun y -> y < x ) xs
		and large = List.filter (fun y -> y >= x ) xs
		in quicksort' small @ (x :: quicksort' large)
;;
