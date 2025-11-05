import scala.annotation.tailrec

def skipTakeL[A](lazyList: LazyList[A]): LazyList[A] =
  def helper(currentList: LazyList[A], step: Int): LazyList[A] =
    currentList match
      case LazyList() => LazyList()
      case head #:: tail => head #:: helper(skipNextElements(tail, step), step + 1)
  
  @tailrec
  def skipNextElements(currentList: LazyList[A], remaining: Int): LazyList[A] = 
    currentList match 
      case LazyList() => LazyList()
      case head #:: tail =>
        if remaining > 0 then skipNextElements(tail, remaining - 1)
        else LazyList.cons(head, tail)
  
  helper(lazyList, 1)


val lst = LazyList.from(1)
val result = skipTakeL(lst)
result.take(10).toList
result



//def skipTakeL[A](lazyList: LazyList[A]): LazyList[A] = 
//  def helper(currentList: LazyList[A], step: Int): LazyList[A] = 
//    currentList match 
//      case LazyList() => LazyList.empty
//      case _ => LazyList.cons(currentList.head, helper(currentList.drop(step), step + 1))
//  helper(lazyList, 2)