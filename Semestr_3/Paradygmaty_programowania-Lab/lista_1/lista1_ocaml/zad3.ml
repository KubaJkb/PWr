let rec insert (list, element, position) =
	if list = [] then [element]
	else if position <= 0 then element :: list
	else List.hd list :: insert (List.tl list, element, (position-1))
;;


let list1 = [1; 2; 3; 4];;
insert (list1, 5, 2);;
insert (list1, 0, 0);;
insert (list1, 6, 10);;
insert (list1, (-1), (-2));;
