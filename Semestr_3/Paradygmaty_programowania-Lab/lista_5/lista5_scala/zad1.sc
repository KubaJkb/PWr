sealed trait LazyTree3[+A]
case object LEmpty extends LazyTree3[Nothing]
case class LNode[A](value: A, l: LazyTree3[A], m: LazyTree3[A], r: LazyTree3[A]) extends LazyTree3[A] {
  lazy val left: LazyTree3[A] = l
  lazy val middle: LazyTree3[A] = m
  lazy val right: LazyTree3[A] = r
}

def traverse[A](order: (LazyTree3[A], LazyTree3[A], LazyTree3[A]) => LazyList[LazyTree3[A]])(tree: LazyTree3[A]): LazyList[A] =
  tree match
    case LEmpty => LazyList()
    case node: LNode[A] =>
      val children = order(node.left, node.middle, node.right)
      node.value #:: children.flatMap(traverse(order))


// ---------------------- TESTY ----------------------

def order[A](left: LazyTree3[A], middle: LazyTree3[A], right: LazyTree3[A]): LazyList[LazyTree3[A]] =
  middle #:: left #:: right #:: LazyList()

val tree = LNode(1,
  LNode (2, LNode(11, LEmpty, LEmpty, LEmpty), LNode(10, LEmpty, LEmpty, LEmpty), LEmpty),
  LNode (3, LEmpty, LEmpty, LEmpty),
  LNode (4, LEmpty, LEmpty, LEmpty)
)

val result = traverse(order)(tree)
result.take(6).toList
