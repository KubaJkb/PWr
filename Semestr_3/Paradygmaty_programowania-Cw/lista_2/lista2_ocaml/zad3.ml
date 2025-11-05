let root3 a =
	let rec root3Iter curr e =
		let next = curr +. (a/.(curr*.curr)-.curr)/.3.
		in
		if abs_float(next*.next*.next-.a) <= e*.abs_float(a) then next
		else root3Iter next e
	in
	root3Iter (if a>1. then a/.3. else a) 1e-15
;;


root3 10.3;;
