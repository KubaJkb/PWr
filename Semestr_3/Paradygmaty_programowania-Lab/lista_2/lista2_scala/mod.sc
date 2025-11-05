import scala.annotation.tailrec

def inversionAB(lst: List[Int], a: Int, b: Int): List[Int] = {
  val (first, last) = if (a <= b) then (a, b) else (b, a)

  def cutElements(lst: List[Int], idx: Int, n: Int): List[Int] = (lst, idx, n) match {
    case (Nil, _, _) => Nil
    case (_, _, 0) => Nil
    case (h::t, 0, _) =>
      val cutLst = cutElements(t, 0, n-1)
      h::cutLst
    case (_::t, i, n) => cutElements(t, i-1, n)
  }

  def revList(lst: List[Int]): List[Int] = {
    @tailrec def accRevList(rev: List[Int], rem: List[Int]): List[Int] = rem match {
      case Nil => rev
      case h :: t => accRevList(h :: rev, t)
    }
    accRevList(Nil, lst)
  }

  val part1 = cutElements(lst, 0, first)
  val part2 = revList(cutElements(lst, first, last-first+1))
  val part3 = cutElements(lst, last+1, -1)

  part1:::part2:::part3

}

inversionAB (List(1,2,3,4,5,6,7,8,9), 2, 4)
inversionAB (List(1,2,3,4), 2, 4)
inversionAB (List(1,2,3,4), 7, 2)
inversionAB (List(1,2,3,4), 4, -2)















