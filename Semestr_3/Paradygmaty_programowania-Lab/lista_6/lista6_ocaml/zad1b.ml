let generate_pascal_triangle n =
  let triangle = Array.make_matrix (n + 1) (n + 1) 0 in
  for i = 0 to n do
    triangle.(i).(0) <- 1;
    for j = 1 to i do
      triangle.(i).(j) <- triangle.(i - 1).(j - 1) + triangle.(i - 1).(j);
    done
  done;
  triangle
;;

let pascalGiftI n k =
  let triangle = generate_pascal_triangle (n - 1) in
  let k = ref k in
  if !k >= n then k := (n * 2 - 2 - !k);

  let base_row = triangle.(n - 1 - !k) in
  let res = Array.make (n - (!k mod 2)) 0 in
  Array.blit base_row 0 res (!k / 2) (n - 1 - !k + 1);

  let rem = ref (!k / 2) in

  while !rem > 0 do
    let value =
      let row_idx = n - 1 - (!k - 2 - ((!k / 2 - !rem) * 2)) in
      let col_idx = (!k / 2) - !rem + 1 in
      triangle.(row_idx).(col_idx)
    in
    res.(!rem - 1) <- value;
    res.(Array.length res - !rem) <- value;

    rem := !rem - 1
  done;

  res
;;

generate_pascal_triangle 5;;

pascalGiftI 5 0;;
pascalGiftI 5 1;;
pascalGiftI 5 2;;
pascalGiftI 5 3;;
pascalGiftI 5 4;;
pascalGiftI 5 5;;
pascalGiftI 5 6;;
pascalGiftI 5 7;;
pascalGiftI 5 8;;

pascalGiftI 6 0;;
pascalGiftI 6 1;;
pascalGiftI 6 2;;
pascalGiftI 6 3;;
pascalGiftI 6 4;;
pascalGiftI 6 5;;
pascalGiftI 6 6;;
pascalGiftI 6 7;;
pascalGiftI 6 8;;
pascalGiftI 6 9;;
pascalGiftI 6 10;;


(*
let pascal_row n =
    let row = Array.make (n + 1) 1 in
    for i = 2 to n do
		  for j = i - 1 downto 1 do
				row.(j) <- row.(j) + row.(j - 1)
		  done
    done;
    row
;;

let rec pascalGiftI n k =
	let k = ref k in
	if !k >= n then k := (n * 2 - 2 - !k);
	
	let base_row = pascal_row (n - 1 - !k) in
	let res = Array.make (n - (!k mod 2)) 0 in 
	Array.blit base_row 0 res (!k / 2) (Array.length base_row);
	
	let rem = ref (!k / 2) in
	
	while !rem > 0 do
		let value = 
			let row_idx = n - 1 - (!k - 2 - ((!k / 2 - !rem) * 2)) in
			let col_idx = (!k / 2) - !rem + 1 in
			(pascal_row row_idx).(col_idx)
		in
		res.(!rem - 1) <- value;
		res.(Array.length res - !rem) <- value;
		
		rem := !rem - 1
	done;
	
	res
;;
*)
