let rec split2 (lst, a, b) =
	if lst = [] then ([], [])
	else
		let hd = List.hd lst in
		let tl = List.tl lst in
		let (firstList, secondList) = split2(tl, a, b) in
		
		if hd <= a && hd >= b then (hd::firstList, hd::secondList)
		else if hd <= a then (hd::firstList, secondList)
		else if hd >= b then (firstList, hd::secondList)
		else (firstList, secondList)
;;


split2([1.0; 2.5; 3.5; 4.0; 5.5], 3.0, 4.0);;
split2([1.0; 2.5; 3.5], 3.0, 4.0);;
split2([4.0; 5.5], 3.0, 4.0);;

































let split2 (lst, a, b) =
  let rec pom (lst, left, right) =
    if lst = [] then
      (left, right)
    else
      let hd = List.hd lst in
      let tl = List.tl lst in
      let newLeft = if hd <= a then hd :: left else left in
      let newRight = if hd >= b then hd :: right else right in
      pom (tl, newLeft, newRight)
  in
  pom (lst, [], [])
;;