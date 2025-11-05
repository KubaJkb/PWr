def fiddle4[T] (tuple: (T, Double, Double, T)): (T, T, Double) = {
  val (a, b, c, d) = tuple
  (d, a, c - b)
}


fiddle4((1.3, 2.0, 3.1, 4.2))
fiddle4((1, 2, 3, 4))
fiddle4((0,0,0,0))
fiddle4((4,3,-2.1,6.7))
fiddle4((4.5,3.2,-2,6))
