import scala.annotation.tailrec

def fibTail (n: Int): Int = 
  @tailrec def acc (n:Int, prev: Int, curr: Int): Int = 
    n match 
      case n if n<0 => throw new IllegalArgumentException("Wrong input")
      case 0 => prev
      case _ => acc (n-1, curr, prev+curr)
  acc (n, 0, 1)

fibTail (42)