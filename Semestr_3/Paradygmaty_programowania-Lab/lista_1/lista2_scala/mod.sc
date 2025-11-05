def split2 (list: List[Double], a: Double, b: Double): (List[Double], List[Double]) = {
  if list == Nil then (Nil, Nil)
  else {
    val hd = list.head
    val tl = list.tail

    val (firstList, secondList) = split2(tl, a, b)

    if hd <= a && hd >= b then (hd :: firstList, hd :: secondList)
    else if hd <= a then (hd :: firstList, secondList)
    else if hd >= b then (firstList, hd :: secondList)
    else (firstList, secondList)
  }
}


split2(List(1.0, 2.5, 3.5, 4.0, 5.5), 3.0, 4.0)
split2(List(1.0, 2.5, 3.5), 3.0, 4.0)
split2(List(4.0, 5.5), 3.0, 4.0)
split2(Nil, -1.0, 2.0)
split2(List(), 1.0, -2.0)



















//def split2(lst: List[Double], a: Double, b: Double)): (List[Double], List[Double]) = {
//  def pom(tuple:(list: List[Double], left: List[Double], right: List[Double])) : (List[Double], List[Double]) = {
//    if list == Nil then (left, right)
//    else {
//      val hd = list.head
//      val tl = list.tail
//      
//      val newLeft = 
//        if hd <= a then hd :: left 
//        else left
//      val newRight = 
//        if hd >= b then hd :: right 
//        else right
//      
//      pom(tl, newLeft, newRight)
//    }
//  }
//  pom(lst, Nil, Nil)
//}