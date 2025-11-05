def swap[T](tab: Array[T], i: Int, j: Int): Unit =
  val aux = tab(i)
  tab(i) = tab(j)
  tab(j) = aux

def choosePivot[T](tab: Array[T], m: Int, n: Int): T =
  tab((m + n) / 2)

def partition[T](tab: Array[T], l: Int, r: Int)(implicit ord: Ordering[T]): (Int, Int) =
  import ord._
  var i = l
  var j = r
  val pivot = choosePivot(tab, l, r)

  while (i <= j)
    while (tab(i) < pivot) i += 1
    while (tab(j) > pivot) j -= 1
    if i <= j then
      swap(tab, i, j)
      i += 1
      j -= 1

  (i, j)

def quick[T](tab: Array[T], l: Int, r: Int)(implicit ord: Ordering[T]): Unit =
  if l < r then
    val (i, j) = partition(tab, l, r)
    if j - l < r - i then
      quick(tab, l, j)
      quick(tab, i, r)
    else
      quick(tab, i, r)
      quick(tab, l, j)

def quicksort[T](tab: Array[T])(implicit ord: Ordering[T]): Unit =
  quick(tab, 0, tab.length - 1)


val t1 = Array(4, 8, 1, 12, 7, 3, 1, 9)
quicksort(t1)
t1

val t2 = Array("kobyla", "ma", "maly", "bok")
quicksort(t2)
t2
