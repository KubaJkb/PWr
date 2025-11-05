class UnderflowException(msg: String) extends Exception(msg)

class MyQueue[+T] private (private val front: List[T], private val back: List[T]):
  def enqueue[B >: T](x: B): MyQueue[B] =
    new MyQueue(front, x :: back)

  def head: T =
    front match
      case x :: _ => x
      case Nil =>
        back.reverse match
          case y :: _ => y
          case Nil => throw new UnderflowException("Empty queue")

  def dequeue: MyQueue[T] =
    front match
      case _ :: xs => new MyQueue(xs, back)
      case Nil =>
        back.reverse match
          case _ :: ys => new MyQueue(ys, Nil)
          case Nil => this

  def isEmpty: Boolean =
    front.isEmpty && back.isEmpty
end MyQueue

object MyQueue:
  def apply[T](xs: T*): MyQueue[T] =
    new MyQueue(xs.toList, Nil)

  def empty[T]: MyQueue[T] =
    new MyQueue[T](Nil, Nil)
end MyQueue


val q1 = MyQueue(1, 2, 3) // Tworzy kolejkę z elementami 1, 2, 3
val q2 = q1.enqueue(4)    // Dodaje element 4 na koniec
println(q2.head)          // Wypisuje 1
val q3 = q2.dequeue       // Usuwa pierwszy element (1)
println(q3.head)          // Wypisuje 2
println(q3.isEmpty)       // Wypisuje false

val emptyQueue = MyQueue.empty[Int] // Tworzy pustą kolejkę
println(emptyQueue.isEmpty)         // Wypisuje true
