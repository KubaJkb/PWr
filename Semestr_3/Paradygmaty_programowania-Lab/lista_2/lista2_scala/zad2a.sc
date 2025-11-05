def split3RecWrong(lst: List[Int]): (List[Int], List[Int], List[Int]) = lst match {
  case h1 :: h2 :: h3 :: t =>
    val (l1, l2, l3) = split3RecWrong(t)
    (h1 :: l1, h2 :: l2, h3 :: l3)
  case _ => (Nil, Nil, Nil)
}

def split3Rec(lst: List[Int]): (List[Int], List[Int], List[Int]) = {
  def length(lst: List[Int]): Int = lst match {
    case Nil => 0
    case _ :: t => 1 + length(t)
  }

  def cutNElements(lst: List[Int], n: Int): (List[Int], List[Int]) = (n, lst) match {
    case (0, _) => (Nil, lst)
    case (_, h :: t) =>
      val (remEl, remTl) = cutNElements(t, n - 1)
      (h :: remEl, remTl)
    case (_, Nil) => (Nil, Nil)
  }

  val len = length(lst) / 3
  val (l1, remList1) = cutNElements(lst, len)
  val (l2, remList2) = cutNElements(remList1, len)
  val (l3, remList3) = cutNElements(remList2, len)
  (l1, l2, l3)
}

println(split3RecWrong(List(1, 2, 3, 4, 5, 6, 7, 8)))
println(split3Rec(List(1, 2, 3, 4, 5, 6, 7, 8)))
