//class Time(var _hour: Int, var _minute: Int):
//  if _hour < 0 || _hour > 23 then
//    throw IllegalArgumentException(s"Godzina ($_hour) musi być w przedziale 0-23")
//  if _minute < 0 || _minute > 59 then
//    throw IllegalArgumentException(s"Minuty ($_minute) muszą być w przedziale 0-59")
//
//  def hour: Int = _hour
//  def hour_=(newHour: Int): Unit =
//    if newHour < 0 || newHour > 23 then
//      throw IllegalArgumentException(s"Godzina ($newHour) musi być w przedziale 0-23")
//    _hour = newHour
//
//  def minute: Int = _minute
//  def minute_=(newMinute: Int): Unit =
//    if newMinute < 0 || newMinute > 59 then
//      throw IllegalArgumentException(s"Minuty ($newMinute) muszą być w przedziale 0-59")
//    _minute = newMinute
//
//  def before(other: Time): Boolean =
//    if _hour < other.hour then true
//    else if _hour == other.hour && _minute < other.minute then true
//    else false
//
//
//val time1 = Time(21, 15)
//val time2 = Time(22, 5)
//time1.before(time2)     // true
//
//time1.hour = 23
//time1.minute = 0
//time1.before(time2)     // false
//
//val invalidTime = Time(24, 30)
//
//time1.minute = 60

// ************************************************************************************

class Time(_hour: Int, _minute: Int):
  private var minutesFromMidnight: Int =
    if _hour < 0 || _hour > 23 then
      throw IllegalArgumentException(s"Godzina ($_hour) musi być w przedziale 0-23")
    if _minute < 0 || _minute > 59 then
      throw IllegalArgumentException(s"Minuty ($_minute) muszą być w przedziale 0-59")
    _hour * 60 + _minute

  def hour: Int = minutesFromMidnight / 60
  def hour_=(newHour: Int): Unit =
    if newHour < 0 || newHour > 23 then
      throw IllegalArgumentException(s"Godzina ($newHour) musi być w przedziale 0-23")
    minutesFromMidnight = newHour * 60 + minute

  def minute: Int = minutesFromMidnight % 60
  def minute_=(newMinute: Int): Unit =
    if newMinute < 0 || newMinute > 59 then
      throw IllegalArgumentException(s"Minuty ($newMinute) muszą być w przedziale 0-59")
    minutesFromMidnight = hour * 60 + newMinute

  def before(other: Time): Boolean =
    minutesFromMidnight < other.minutesFromMidnight


val time1 = Time(21, 15)
val time2 = Time(22, 5)
time1.before(time2)     // true

time1.hour = 23
time1.minute = 0
time1.before(time2)     // false

val invalidTime = Time(24, 30)

time1.minute = 60