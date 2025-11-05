abstract class Sequence[+A]:
  def append(x: Sequence[A]): Sequence[A]

//Błąd wynika z naruszenia zasad kowariancji – typ kowariantny A nie może być używany w pozycji kontrawariantnej (czyli jako typ argumentu).

//abstract class Sequence[+A]:
//  def append[B >: A](x: Sequence[B]): Sequence[B]