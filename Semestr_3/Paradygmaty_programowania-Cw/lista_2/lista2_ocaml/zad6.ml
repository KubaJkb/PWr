let rec replaceNth (xs, n, x) =
	match (xs, n) with
	| ([], _) -> []
	| (_::tail, 0) -> x::tail
	| (head::tail, _) -> head::replaceNth(tail, n-1, x)
;;

replaceNth(['o';'l';'a'; 'm'; 'a'; 'k'; 'o'; 't'; 'a'], 1, 's');;
