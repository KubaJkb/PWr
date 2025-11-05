

type 'a binary_tree =
  | Empty
  | Node of 'a * 'a binary_tree * 'a binary_tree;;

type 'a lazy_list =
  | LNil
  | LCons of 'a * (unit -> 'a lazy_list);;

type 'a lazy_tree =
  | LEmpty
  | LNode of 'a * (unit -> 'a lazy_tree) * (unit -> 'a lazy_tree);;

let empty = fun()->LEmpty



let rec count_leaves tree =
  match tree with
  | Empty -> 0
  | Node (_, Empty, Empty) -> 1
  | Node (_, left, right) -> count_leaves left + count_leaves right;;
  
let rec repeat_head n lst =
  match lst with
  | LNil -> LNil
  | LCons (h, _) when n > 0 -> LCons (h, fun () -> repeat_head (n - 1) lst)
  | _ -> LNil;;
  
let uncurry f (x, y) -> f x y;;
let curry f x y -> f (x, y);;

