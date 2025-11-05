//sealed trait Tree3[+A]
//case object Empty extends Tree3[Nothing]
//case class Node[A](value: A, left: Tree3[A], middle: Tree3[A], right: Tree3[A]) extends Tree3[A]
//
//val tree: Tree3[Int] = Node(
//  value = 1,
//  left = Node(3, Empty, Empty, Empty),
//  middle = Empty,
//  right = Node(6, Empty, Empty, Empty)
//)


sealed trait Tree4[+A]
case object Empty extends Tree4[Nothing]
case class Leaf[A](value: A) extends Tree4[A]
case class Single[A](value: A, child: Tree4[A]) extends Tree4[A]
case class Double[A](value: A, left : Tree4[A], right : Tree4[A]) extends Tree4[A]
case class Triple[A](value: A, left : Tree4[A], middle : Tree4[A], right : Tree4[A]) extends Tree4[A]
