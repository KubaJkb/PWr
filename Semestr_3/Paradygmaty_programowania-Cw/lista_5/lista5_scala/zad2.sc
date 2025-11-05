def lfib: LazyList[Int] = 
  def fib(a: Int, b: Int): LazyList[Int] = 
    a #:: fib(b, a + b)
  fib(0, 1)


val fibEx = lfib.take(10).toList