import scala.annotation.tailrec

@tailrec
def initSegment[A](xs: List[A], xy: List[A]): Boolean =
  (xs, xy) match
    case (hx::tx, hy::ty) => if hx == hy then initSegment(tx, ty) else false
    case (Nil, _) => true
    case _ => false


initSegment(List(1,2,3), List(1,2,3,4,5))
initSegment(List(1,2,3), List(1,2,3))
initSegment(List(1,2,1), List(1,2,3,4,5))
initSegment(List(1,2,3,4), List(1,2))