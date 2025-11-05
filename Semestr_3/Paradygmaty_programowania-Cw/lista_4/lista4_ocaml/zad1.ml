let f1 x y z = x y z;; 
 
 (* val f1 : ('a -> 'b -> 'c) -> 'a -> 'b -> 'c = <fun> *)
 
let f2 x y = function z -> x::y;;

(* val f2 : 'a -> 'a list -> 'b -> 'a list = <fun> *)
