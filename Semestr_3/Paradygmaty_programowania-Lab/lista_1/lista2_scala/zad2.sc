def hits[T] (list1: List[T], list2: List[T]): Int = {
  if list1.isEmpty || list2.isEmpty then 0
  else if list1.head == list2.head then 1 + hits(list1.tail, list2.tail)
  else hits(list1.tail, list2.tail)
}


hits(List(1, 2, 3, 4), List(1, 3, 3, 5))
hits(List(1, 2, 3, 4), List(1, 2, 3, 4))
hits(List(1, 2, 3, 4), List(4, 3, 2, 1))
hits(List(1.1, 2.2, 3.3, 4.4), List(1.2, 2.1, 3.3, 4.4))
