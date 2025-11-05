import scala.annotation.tailrec

@tailrec
def whileLoop(condition: => Boolean)(body: => Unit): Unit =
  if condition then
    body
    whileLoop(condition)(body)


var count = 0
whileLoop(count < 3) {
  println(count)
  count += 1
}
