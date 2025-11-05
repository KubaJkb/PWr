def composed(n: Int): List[Int] = {
  for {
    i <- List.range(2, n + 1)
    if (for (j <- List.range(2, i) if i % j == 0) yield j) != Nil
  } yield i
}


composed(-5)
composed(0)
composed(1)
composed(2)
composed(3)
composed(4)
composed(20)
composed(123)
composed(1234)
