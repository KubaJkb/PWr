import scala.annotation.tailrec
import scala.math.abs

def root3 (a: Double): Double =
  @tailrec def root3Iter (curr: Double, e: Double): Double =
    val next = curr + (a / (curr*curr) - curr) / 3
    if abs(next*next*next - a) <= e * abs(a) then next
    else root3Iter (next, e)
  root3Iter (if a > 1 then a/3 else a, 1e-15)

root3 (10.3)