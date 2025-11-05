def insert[T] (list: List[T], element: T, position: Int): List[T] = {  
  if list == Nil then List(element)
  else if position <= 0 then element :: list
  else {
    val head = list.head
    val tail = list.tail
    head :: insert(tail, element, position-1)
  }
}


val list1 = List(1, 2, 3, 4)
insert(list1, 5, 2)
insert(list1, 0, 0)
insert(list1, 6, 10)
insert(list1, -1, -2)
