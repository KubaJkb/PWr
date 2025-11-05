let rec hits (list1, list2) =
  if list1 = [] || list2 = [] then 0  
  else if List.hd list1 = List.hd list2 then
    1 + hits (List.tl list1, List.tl list2)
  else
    hits (List.tl list1, List.tl list2) 
;;


hits ([1; 2; 3; 4], [1; 3; 3; 5]);;
hits([1; 2; 3; 4], [1; 2; 3; 4]);;
hits([1; 2; 3; 4], [4; 3; 2; 1]);;
hits([1.1; 2.2; 3.3; 4.4], [1.2; 2.1; 3.3; 4.4]);;
