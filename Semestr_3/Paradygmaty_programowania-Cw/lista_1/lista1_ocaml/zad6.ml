let rec listLength xs =
	if xs = [] then 0
	else 1 + listLength(List.tl xs)
;;


listLength([1;2;3;4]);;
