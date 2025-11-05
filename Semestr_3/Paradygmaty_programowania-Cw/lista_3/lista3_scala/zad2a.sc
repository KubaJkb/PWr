def curry3[A,B,C,D] (f: (A, B, C) => D): A => B => C => D =
  a => b => c => f(a, b, c)

//def curry3[A, B, C, D](f: ((A, B, C)) => D): A => B => C => D = {
//  (a: A) => {
//    (b: B) => {
//      (c: C) => {
//        f((a, b, c))
//      }
//    }
//  }
//}


val plus = (x: Int, y: Int, z:Int) => x+y+z

curry3 (plus) (1) (2) (3)
