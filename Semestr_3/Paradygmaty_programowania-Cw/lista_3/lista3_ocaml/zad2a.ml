let curry3 f x y z = f (x, y, z);;

let curry3 = function f -> function x -> function y -> function z -> f (x, y, z);;

(* val curry3 : ('a * 'b * 'c -> 'd) -> 'a -> 'b -> 'c -> 'd = <fun> *)

let plus (x, y, z) = x + y + z;;

curry3 plus 1 2 3;;
