let rec lrepeat k lxs =
  match lxs with
  | LNil -> LNil
  | LCons (x, lazy xs) ->
    let rec replicate n x =
      if n = 0 then lrepeat k xs
      else LCons (x, lazy (replicate (n - 1) x))
    in
    replicate k x
;;


let ex1 = ltake (12, lrepeat 3 (toLazyList ['a'; 'b'; 'c'; 'd']));;
let ex2 = ltake (15, lrepeat 3 (lfrom 1));;
let ex3 = ltake (15, lrepeat 3 LNil);;
let ex4 = lrepeat 2 (toLazyList [1,2,3]);;
