let rec initSegment (xs, ys) =
	match (xs, ys) with
	| (hx::tx, hy::ty) -> if hx = hy then initSegment (tx, ty) else false
	| ([], _) -> true
	| _ -> false
;;

initSegment([1;2;3], [1;2;3;4;5]);;
initSegment([1;2;3], [1;2;3]);;
initSegment([1;2;1], [1;2;3]);;
initSegment([1;2;3;4], [1;2]);;
