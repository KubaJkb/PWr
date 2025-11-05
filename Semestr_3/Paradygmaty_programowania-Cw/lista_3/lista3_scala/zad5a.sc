def insertionSort[A] (cmp: (A, A) => Boolean)(lst: List[A]): List[A] =
  def insert (x: A, sorted: List[A]): List[A] =
    sorted match
      case Nil => List(x)
      case h::t if cmp(x, h) => x :: sorted
      case h::t => h :: insert(x, t)
  lst match
    case Nil => Nil
    case h::t => insert(h, insertionSort(cmp)(t))


insertionSort((x: Int, y: Int) => x<=y)(List(4,2,3,1,5,1))