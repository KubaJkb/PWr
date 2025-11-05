let rec sqrList xs =
	if xs = [] then []
	else List.hd xs * List.hd xs :: sqrList(List.tl xs)
;;


sqrList([1;2;3;-4]);;
