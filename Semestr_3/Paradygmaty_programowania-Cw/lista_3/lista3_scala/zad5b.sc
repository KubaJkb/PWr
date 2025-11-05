import scala.annotation.tailrec

def split[A] (lst: List[A]): (List[A], List[A]) =
  @tailrec def splitting(left: List[A], right: List[A], rem: List[A]): (List[A], List[A]) =
    rem match
      case Nil => (left, right)
      case x :: xs =>
        if (left.length < rem.length) then splitting(x::left, right, xs)
        else (left.reverse, rem)
  splitting (Nil, Nil, lst)

def mergeSort[A] (cmp: (A, A) => Boolean) (lst: List[A]): List[A] =
  def merge (left: List[A]) (right: List[A]): List[A] =
    (left, right) match
      case (Nil, ys) => ys
      case (xs, Nil) => xs
      case (x::xs, y::ys) =>
        if cmp(x,y) then x::merge(xs)(right)
        else y::merge(left)(ys)

  lst match
    case Nil => Nil
    case x::Nil => List(x)
    case _ =>
      val (left, right) = split(lst)
      merge(mergeSort(cmp)(left))(mergeSort(cmp)(right))


val lst = List(("a", 2), ("b", 1), ("c", 2), ("d", 1), ("e", 1))
val cmp: ((String, Int), (String, Int)) => Boolean =
  (_, _) match
    case ((_, x), (_, y)) => x <= y

mergeSort(cmp)(lst)

