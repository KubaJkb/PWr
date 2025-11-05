def lrepeat[A](k: Int)(lxs: LazyList[A]): LazyList[A] =
  lxs match
    case LazyList() => LazyList()
    case head #:: tail => LazyList.fill(k)(head) #::: lrepeat(k)(tail)


val ex1 = lrepeat(3)(LazyList('a', 'b', 'c', 'd'))
val ex2 = lrepeat(3)(LazyList.from(1))
val ex3 = lrepeat(3)(LazyList()).take(15)