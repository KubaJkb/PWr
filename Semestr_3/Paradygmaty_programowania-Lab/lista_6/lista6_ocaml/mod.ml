
let sndMax arr =
	let n = Array.length arr in
	if n < 2 then
		failwith "Tablica musi posiadac co najmniej 2 elementy"
	else
		let max1 = ref min_int in
		let max2 = ref min_int in
		for i = 0 to n - 1 do
			if arr.(i) > !max1 then (
				max2 := !max1;
				max1 := arr.(i)
			) else if arr.(i) >= !max2 then (
				max2 := arr.(i)
			)
		done;
		!max2
;;


sndMax [|1; 2; 3; 4; 5|];;
sndMax [|-10; -20; -30; -5; -15|];;
sndMax [|5; 1; 5; 2; 3|];;
sndMax [|10; 20|];;
sndMax [|7; 7; 7|];;
sndMax [|42|];;
