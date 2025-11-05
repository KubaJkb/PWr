def militaryMinutes (hour: Int, minutes: Int, period: String): String = {
  
  if hour < 1 || hour > 12 then
    throw new IllegalArgumentException("Godzina musi być w zakresie od 1 do 12.")

  if minutes < 0 || minutes > 59 then
    throw new IllegalArgumentException("Minuty muszą być w zakresie od 0 do 59.")

  if period != "AM" && period != "PM" then
    throw new IllegalArgumentException("Okres musi być 'AM' lub 'PM'.")

  var militaryHour = hour
  
  if period == "AM" && hour == 12 then militaryHour = 0
  else if period == "PM" && hour != 12 then militaryHour = hour + 12

  f"$militaryHour%02d:$minutes%02d"
}


militaryMinutes(12, 30, "AM")
militaryMinutes(1, 15, "PM")
militaryMinutes(12, 45, "PM")
militaryMinutes(6, 0, "AM")
militaryMinutes(11, 59, "PM")
militaryMinutes(13, 30, "AM")
militaryMinutes(12, 70, "PM")
militaryMinutes(10, 30, "XY")
