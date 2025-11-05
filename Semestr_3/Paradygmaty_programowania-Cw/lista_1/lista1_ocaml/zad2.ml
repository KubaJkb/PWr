let rec count (x, xs) =
	if xs = [] then 0
	else if x = List.hd xs then 1 + count(x, List.tl xs)
	else count(x, List.tl xs)
;;


count("a", ["a";"l";"a"]);;
