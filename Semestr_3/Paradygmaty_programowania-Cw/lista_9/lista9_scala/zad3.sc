class Pojazd(val producent: String, val model: String, val rokProdukcji: Int = -1, var numerRejestracyjny: String = ""):

  override def toString: String =
    s"Pojazd(producent='$producent', model='$model', rokProdukcji=$rokProdukcji, numerRejestracyjny='$numerRejestracyjny')"

object Pojazd:
  def apply(producent: String, model: String): Pojazd =
    new Pojazd(producent, model)

  def apply(producent: String, model: String, rokProdukcji: Int): Pojazd =
    new Pojazd(producent, model, rokProdukcji)

  def apply(producent: String, model: String, numerRejestracyjny: String): Pojazd =
    new Pojazd(producent, model, numerRejestracyjny = numerRejestracyjny)

  def apply(producent: String, model: String, rokProdukcji: Int, numerRejestracyjny: String): Pojazd =
    new Pojazd(producent, model, rokProdukcji, numerRejestracyjny)


val pojazd1 = Pojazd("Toyota", "Corolla")
val pojazd2 = Pojazd("Ford", "Focus", 2010)
val pojazd3 = Pojazd("BMW", "X5", "KR12345")
val pojazd4 = Pojazd("Audi", "A4", 2020, "DW45678")
