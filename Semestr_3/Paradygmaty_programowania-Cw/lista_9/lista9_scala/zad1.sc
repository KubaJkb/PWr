class Time(var _hour: Int):
  _hour = if _hour < 0 then 0 else _hour

  def hour: Int = _hour

  def hour_=(newHour: Int): Unit =
    _hour = if newHour < 0 then 0 else newHour


val time = Time(-5)
println(time.hour)

time.hour = 10
println(time.hour)

time.hour = -3
println(time.hour)


//class Time(var hour: Int):
//  hour = math.max(hour, 0)
//
//  def getHour: Int = hour
//
//  def setHour(newHour: Int): Unit =
//    hour = math.max(newHour, 0)
//
//
//val time = Time(-5)
//println(time.getHour)
//
//time.setHour(10)
//println(time.getHour)
//
//time.setHour(-3)
//println(time.getHour)
