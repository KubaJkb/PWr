def flatten1[A] (xss: List[List[A]]): List[A] = {
  if xss == Nil then Nil
  else xss.head ::: flatten1(xss.tail)
}


flatten1(List(List(5,6), List(1,2,3)))
