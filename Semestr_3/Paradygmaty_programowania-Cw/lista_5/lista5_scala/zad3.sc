sealed trait lBT[+A]
case object LEmpty extends lBT[Nothing]
case class LNode[+A](elem: A, left: () => lBT[A], right: () => lBT[A]) extends lBT[A]


def lBreadth[A](ltree: lBT[A]): LazyList[A] =
  def loop(queue: List[lBT[A]]): LazyList[A] =
    queue match
      case Nil => LazyList.empty
      case LEmpty :: rest => loop(rest)
      case LNode(v, l, r) :: rest => v #:: loop(rest ++ List(l(), r()))

  loop(List(ltree))


def lTree(n: Int): lBT[Int] =
  LNode(n, () => lTree(2 * n), () => lTree(2 * n + 1))


val treeExample1 = lBreadth(lTree(1)).take(20).toList
val treeExample2 = lBreadth(LEmpty).take(20).toList