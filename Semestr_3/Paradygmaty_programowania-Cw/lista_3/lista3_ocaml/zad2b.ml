let uncurry3 f (x, y, z) = f x y z;;

let uncurry3 = function f -> function (x, y, z) -> f x y z;;

(* val uncurry3 : ('a -> 'b -> 'c -> 'd) -> 'a * 'b * 'c -> 'd = <fun> *)

let add x y z = x + y + z;;

uncurry3 add (1, 2, 3);;
