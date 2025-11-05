import scala.annotation.tailrec

def split3TailWrong(lst: List[Int]): (List[Int], List[Int], List[Int]) = {
  def acc(remLst: List[Int], l1: List[Int], l2: List[Int], l3: List[Int]): (List[Int], List[Int], List[Int]) = remLst match {
    case h1 :: h2 :: h3 :: t => acc(t, h1 :: l1, h2 :: l2, h3 :: l3)
    case _ => (l1, l2, l3)
  }
  acc(lst, Nil, Nil, Nil)
}

def split3Tail(lst: List[Int]): (List[Int], List[Int], List[Int]) = {
  def length(lst: List[Int]): Int = {
    def accLength(lst: List[Int], i: Int): Int =
      lst match {
        case Nil => i
        case _ :: t => accLength(t, i + 1)
      }
    accLength(lst, 0)
  }

  def cutNElements(lst: List[Int], n: Int): (List[Int], List[Int]) = {
    @tailrec def accCutNElements(cutList: List[Int], n: Int, remList: List[Int]): (List[Int], List[Int]) = (n, remList) match {
      case (0, _) => (cutList, remList)
      case (_, h :: t) => accCutNElements(h :: cutList, n - 1, t)
      case (_, Nil) => (cutList, Nil)
    }
    accCutNElements(Nil, n, lst)
  }

  def revList(lst: List[Int]): List[Int] = {
    @tailrec def accRevList(rev: List[Int], rem: List[Int]): List[Int] = rem match {
      case Nil => rev
      case h :: t => accRevList(h :: rev, t)
    }
    accRevList(Nil, lst)
  }

  val len = length(lst) / 3
  val (l1, remList1) = cutNElements(lst, len)
  val (l2, remList2) = cutNElements(remList1, len)
  val (l3, remList3) = cutNElements(remList2, len)
  (revList(l1), revList(l2), revList(l3))
}

// Przykładowe wywołanie funkcji
println(split3TailWrong(List(1, 2, 3, 4, 5, 6, 7, 8)))
println(split3Tail(List(1, 2, 3, 4, 5, 6, 7, 8)))
