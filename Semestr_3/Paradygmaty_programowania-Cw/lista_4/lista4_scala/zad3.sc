import scala.annotation.tailrec

sealed trait BT[+A]
case object Empty extends BT[Nothing]
case class Node[+A](elem: A, left: BT[A], right: BT[A]) extends BT[A]

val tt = Node(1,Node(2,Node(4,Empty,Empty),Empty),Node(3,Node(5,Empty,Node(6,Empty,Empty)),Empty))



def breadthBT[A] (tree: BT[A]): List[A] =
  @tailrec
  def bfs(queue: List[BT[A]])(acc: List[A]): List[A] =
    queue match
      case Nil => acc.reverse
      case Empty::t => bfs (t) (acc)
      case Node(el, l, r)::t => bfs (t ::: List(l, r)) (el::acc)
  bfs (List(tree)) (Nil)

breadthBT (tt)
