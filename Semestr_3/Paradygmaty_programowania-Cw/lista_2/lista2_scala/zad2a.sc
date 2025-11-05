def fib (n: Int): Int =
  n match
    case n if n<0 => throw new IllegalArgumentException("Wrong input")
    case 0 => 0
    case 1 => 1
    case _ => fib (n-2) + fib (n-1)
  

fib (42)
