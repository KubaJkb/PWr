type 'a tree3 = 
	| Empty 
	| Node of { value : 'a; left : 'a tree3; middle : 'a tree3; right : 'a tree3 }
;;

let rec combineTree3 f tree1 tree2 =
	match (tree1, tree2) with
	| (Node { value = v1; left = l1; middle = m1; right = r1 }, Node { value = v2; left = l2; middle = m2; right = r2 }) ->
		Node { value = f (Some v1) (Some v2); left = combineTree3 f l1 l2; middle = combineTree3 f m1 m2; right = combineTree3 f r1 r2 }
	| _ -> Empty
;;

type 'a tree4 = 
  | Empty
  | Leaf of { value : 'a }
  | Single of { value : 'a; child : 'a tree4 }
  | Double of { value : 'a; left : 'a tree4; right : 'a tree4 }
  | Triple of { value : 'a; left : 'a tree4; middle : 'a tree4; right : 'a tree4 }
;;

let rec combineTree4 f tree1 tree2 =
  match (tree1, tree2) with
  | (Leaf { value = v1 }, Leaf { value = v2 }) ->
      Leaf { value = f (Some v1) (Some v2) }
  | (Single { value = v1; child = c1 }, Single { value = v2; child = c2 }) ->
      Single { value = f (Some v1) (Some v2); child = combineTree4 f c1 c2 }
  | (Double { value = v1; left = l1; right = r1 }, Double { value = v2; left = l2; right = r2 }) ->
      Double { value = f (Some v1) (Some v2); left = combineTree4 f l1 l2; right = combineTree4 f r1 r2 }
  | (Triple { value = v1; left = l1; middle = m1; right = r1 }, Triple { value = v2; left = l2; middle = m2; right = r2 }) ->
      Triple { value = f (Some v1) (Some v2); left = combineTree4 f l1 l2; middle = combineTree4 f m1 m2; right = combineTree4 f r1 r2 }
  | _ -> Empty

  
(* --------------- Testy ---------------- *)

let addFun x y = 
	match (x, y) with
	| (Some a, Some b) -> a + b
	| _ -> 0
;;

let t1 = Node { value = 1; left = Empty; middle = Empty; right = Empty };;
let t2 = Node { value = 2; left = Empty; middle = Empty; right = Empty };;
let t3 =
  Node {
    value = 3;
    left = Node { value = 4; left = Empty; middle = Empty; right = Empty };
    middle = Node { value = 5; left = Empty; middle = Empty; right = Empty };
    right = Empty;
  }
;;
let t4 =
  Node {
    value = 6;
    left = Empty;
    middle = Node { value = 7; left = Empty; middle = Empty; right = Empty };
    right = Node { value = 8; left = Empty; middle = Empty; right = Empty };
  }
;;

combineTree3 addFun Empty Empty;;
combineTree3 addFun Empty t3;;
combineTree3 addFun t1 t2;;
combineTree3 addFun t3 t4;;

let tree1 =
  Triple {
    value = 1;
    left = Leaf { value = 2 };
    middle = Single { value = 3; child = Leaf { value = 4 } };
    right = Empty
  }

let tree2 =
  Triple {
    value = 10;
    left = Empty;
    middle = Single { value = 30; child = Leaf { value = 40 } };
    right = Leaf { value = 20 }
  }

let combined_tree = combineTree4 addFun tree1 tree2;;