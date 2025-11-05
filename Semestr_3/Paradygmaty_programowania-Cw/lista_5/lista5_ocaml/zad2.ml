let rec lfib =
  let rec fib a b = LCons (a, lazy (fib b (a + b)))
  in 
  fib 0 1
;;


let fibEx = ltake (10, lfib);;
