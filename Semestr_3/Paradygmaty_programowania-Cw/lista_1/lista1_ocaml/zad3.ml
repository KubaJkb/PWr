let rec replicate (x, n) =
	if n <= 0 then []
	else x :: replicate(x, n-1)
;;


replicate("la",3);;