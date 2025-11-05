let rec flatten1 xss =
	if xss = [] then []
	else List.hd xss @ flatten1(List.tl xss)
;;


flatten1([[5;6];[1;2;3]]);;
