def sqrList (xs: List[Int]): List[Int] = {
  if xs == Nil then Nil
  else xs.head*xs.head :: sqrList(xs.tail)
}


sqrList(List(1,2,3,-4))
  