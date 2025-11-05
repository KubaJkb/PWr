sealed trait BT[+A]
case object Empty extends BT[Nothing]
case class Node[+A](elem: A, left: BT[A], right: BT[A]) extends BT[A]

val tt = Node(1,Node(2,Node(4,Empty,Empty),Empty),Node(3,Node(5,Empty,Node(6,Empty,Empty)),Empty))



def inner_path_length[A] (tree: BT[A]): Int =
  def ipl (tr: BT[A]) (depth: Int): Int =
    tr match
      case Empty => 0
      case Node(_, l, r) => depth + ipl (l) (depth+1) + ipl (r) (depth+1)
  ipl (tree) (0)

def external_path_length[A] (tree: BT[A]): Int =
  def epl (tr: BT[A]) (depth: Int): Int =
    tr match
      case Empty => depth
      case Node(_, l, r) => epl (l) (depth+1) + epl (r) (depth+1)
  epl (tree) (0)

inner_path_length(tt)
external_path_length(tt)
