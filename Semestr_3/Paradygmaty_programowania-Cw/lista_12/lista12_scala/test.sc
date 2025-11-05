def f (n: Int): Int =
  if n < 0 then -1
  else if n > 0 then
    if n % 2 == 0 then 2
    else 1
  else 0
  
f (1)
f (3)
f (2)
f (0)
f (6)
f (-6)




/*
sealed trait BinaryTree[+A]
case object Empty extends BinaryTree[Nothing]
case class Node[A](value: A, left: BinaryTree[A], right: BinaryTree[A]) extends BinaryTree[A]

sealed trait LazyList[+A]
case object LNil extends LazyList[Nothing]
case class LCons[A](head: A, tail: () => LazyList[A]) extends LazyList[A]

sealed trait LazyTree[+A]
case object LEmpty extends LazyTree[Nothing]
case class LNode[A](value: A, left: () => LazyTree[A], right: () => LazyTree[A]) extends LazyTree[A]

val empty = ()=>LEmpty



def countLeaves[A](tree: BinaryTree[A]): Int =
  tree match
    case Empty => 0
    case Node(_, Empty, Empty) => 1
    case Node(_, left, right) => countLeaves(left) + countLeaves(right)

def repeatHead[A](n: Int, lst: LazyList[A]): LazyList[A] =
  lst match
    case LNil => LNil
    case LCons (h, _) if n > 0 => LCons(h, ()=>repeatHead(n - 1, lst))
    case _ => LNil

def uncurry[A, B, C](f: A => B => C): (A, B) => C =
  (a, b) => f(a)(b)

def curry[A, B, C](f: (A, B) => C): A => B => C =
  a => b => f(a, b)

*/