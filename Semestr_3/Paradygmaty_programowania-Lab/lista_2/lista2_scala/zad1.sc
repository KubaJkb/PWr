def indexSwap(a: Int, b: Int, lst: List[Int]): List[Int] = {
  def get(lstG: List[Int], idx: Int): Option[Int] = lstG match {
    case Nil => None
    case h :: _ if idx == 0 => Some(h)
    case _ :: t => get(t, idx - 1)
  }
  def set(lstS: List[Int], idx: Int, newVal: Int): List[Int] = lstS match {
    case Nil => Nil
    case _ :: t if idx == 0 => newVal :: t
    case h :: t => h :: set(t, idx - 1, newVal)
  }

  (get(lst, a), get(lst, b)) match {
    case (Some(x), Some(y)) =>
      val lstWithY = set(lst, a, y)
      set(lstWithY, b, x)
    case _ => lst
  }
}

val indexSwap15 = indexSwap(1, 5, _: List[Int])
println(indexSwap15(List(0, 1, 2, 3, 4, 5, 6)))
