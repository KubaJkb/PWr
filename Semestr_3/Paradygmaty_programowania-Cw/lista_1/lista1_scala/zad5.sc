def palindrome[A] (xs: List[A]): Boolean = {
  xs == xs.reverse
}


palindrome(List("a", "l", "a"))
