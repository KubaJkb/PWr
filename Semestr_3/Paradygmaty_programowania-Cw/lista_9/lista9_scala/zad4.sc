class DivisionByZero extends Exception

def div(x: Int, y: Int) =
  if y == 0 then
    throw new DivisionByZero()
  else
    x/y

try
  println(div(5,0))
catch
  case e : DivisionByZero => println("Division by zero!")
  case _ : Throwable => println("Something strange has happened!")
