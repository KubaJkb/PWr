import scala.collection.mutable.*

def pascalRow(n: Int): Array[Int] =
  val row = Array.fill(n + 1)(1)
  for (i <- 2 to n)
    for (j <- i - 1 to 1 by -1)
      row(j) = row(j) + row(j - 1)
  row

def pascalGiftI(n: Int, kInput: Int): Array[Int] =
  var k = kInput
  if k >= n then k = n * 2 - 2 - k

  val baseRow = pascalRow(n - 1 - k)
  val res = Array.ofDim[Int](n - (k % 2))
  Array.copy(baseRow, 0, res, k/2, baseRow.length)
  
  var rem = k / 2

  while rem > 0 do
    val rowIdx = n - 1 - (k - 2 - ((k / 2 - rem) * 2))
    val colIdx = (k / 2) - rem + 1
    val value = pascalRow(rowIdx)(colIdx)
    
    res(rem - 1) = value
    res(res.length - rem) = value
    rem -= 1

  res


pascalGiftI(5, 0)
pascalGiftI(5, 1)
pascalGiftI(5, 2)
pascalGiftI(5, 3)
pascalGiftI(5, 4)
pascalGiftI(5, 5)
pascalGiftI(5, 6)
pascalGiftI(5, 7)
pascalGiftI(5, 8)

pascalGiftI(6, 0)
pascalGiftI(6, 1)
pascalGiftI(6, 2)
pascalGiftI(6, 3)
pascalGiftI(6, 4)
pascalGiftI(6, 5)
pascalGiftI(6, 6)
pascalGiftI(6, 7)
pascalGiftI(6, 8)
pascalGiftI(6, 9)
pascalGiftI(6, 10)
