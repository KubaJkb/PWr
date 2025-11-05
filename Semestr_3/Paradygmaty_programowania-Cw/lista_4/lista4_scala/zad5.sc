import scala.annotation.tailrec

sealed trait Graphs[A]
case class Graph[A](succ: A=>List[A]) extends Graphs[A]

val g = Graph((i: Int) =>
  i match
    case 0 => List(3)
    case 1 => List(0, 2, 4)
    case 2 => List(1)
    case 3 => Nil
    case 4 => List(0, 2)
    case n => throw new Exception(s"Graph g: node $n doesn't exist")
)



def depthSearch[A] (g: Graph[A]) (startNode: A): List[A] =
  def dfs (visited: List[A]) (stack: List[A]): List[A] =
    stack match
      case Nil => Nil
      case h::t => if visited contains h then dfs (visited) (t)
        else h :: dfs (h::visited) ((g succ h) ::: t)
  dfs (Nil) (List(startNode))

//def depthSearch[A] (g: Graph[A]) (startNode: A): List[A] =
//  @tailrec
//  def dfs(visited: List[A])(stack: List[A]): List[A] =
//    stack match
//      case Nil => visited.reverse
//      case h::t => if visited contains h then dfs (visited) (t)
//        else dfs (h::visited) ((g succ h) ::: t)
//  dfs (Nil) (List(startNode))

depthSearch (g) (4)
