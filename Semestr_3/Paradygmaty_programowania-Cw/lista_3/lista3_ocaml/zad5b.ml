

let split lst =
  let rec splitting left right lst =
    match lst with
    | [] -> (List.rev left, List.rev right)
    | x :: xs ->
        if List.length left <= List.length lst / 2 then
          splitting (x :: left) right xs
        else
          splitting left (x :: right) xs
  in
  splitting [] [] lst

let rec mergeSort cmp lst =
  let rec merge left right =
  match (left, right) with
  | ([], ys) -> ys
  | (xs, []) -> xs
  | (x :: xs, y :: ys) ->
      if cmp x y then x :: merge xs right
      else y :: merge left ys
  in
  match lst with
  | [] -> []
  | [x] -> [x]
  | _ ->
      let (left, right) = split lst in
      merge (mergeSort cmp left) (mergeSort cmp right)
;;


let lst = [("a", 2); ("b", 1); ("c", 2); ("d", 1); ("e", 1)];;
let cmp (_, x) (_, y) = x <= y;;
mergeSort cmp lst;;
