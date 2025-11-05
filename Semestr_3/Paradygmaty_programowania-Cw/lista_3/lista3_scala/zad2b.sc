def uncurry3[A, B, C, D] (f: A => B => C => D): (A, B, C) => D =
  (a, b, c) => f(a)(b)(c)

//def uncurry3[A, B, C, D](f: A => B => C => D): ((A, B, C)) => D = {
//  tuple => {
//    val (a, b, c) = tuple
//    f(a)(b)(c)
//  }
//}


val add = (x: Int) => (y: Int) => (z: Int) => x + y + z

uncurry3 (add) (1, 2, 3)
