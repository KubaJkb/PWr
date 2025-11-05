def replaceNth[A] (xs: List[A], n: Int, x: A): List[A] =
  (xs, n) match
    case (Nil, _) => Nil
    case (_::t, 0) => x::t
    case (h::t, _) => h::replaceNth(t, n-1, x)


replaceNth(List('o','l','a', 'm', 'a', 'k', 'o', 't', 'a'), 1, 's')
