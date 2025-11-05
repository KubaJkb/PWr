import scala.collection.mutable

def copy[T](dest: mutable.Seq[? >: T], src: mutable.Seq[? <: T]): Unit =
  var index = 0
  src.foreach { elem =>
    dest.update(index, elem)
    index += 1
  }

//  src.zipWithIndex.foreach { case (elem, index) =>
//    dest.update(index, elem)
//  }


val src = mutable.Seq(1, 2, 3)              // Kolekcja źródłowa
val dest = mutable.Seq(0, 0, 0)            // Kolekcja docelowa

copy(dest, src)                            // Kopiowanie elementów z src do dest
println(dest)                              // Wypisuje: ArrayBuffer(1, 2, 3)
