let rec sumProd xs =
	match xs with
	| h::t -> let (s, p) = sumProd t in (h+s, h*p)
	| [] -> (0, 1)
;;

let sumProd xs =
	List.fold_left (fun (s, p) x -> (x+s, x*p)) (0, 1) xs 
;;

sumProd [1;2;3;4;5];;
