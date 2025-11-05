//def sumProd(xs: List[Int]): (Int, Int) =
//  xs match
//    case h::t =>
//      val (s, p) = sumProd(t)
//      (h + s, h * p)
//    case Nil => (0, 1)

def sumProd(xs: List[Int]): (Int, Int) =
  xs.foldLeft ((0,1)) ((acc, x) => (x+acc._1, x*acc._2))


sumProd (List(1,2,3,4,5))
