let militaryMinutes (hour, minutes, period) =
  if hour < 1 || hour > 12 then
    failwith "Godzina musi być w zakresie od 1 do 12."
  else if minutes < 0 || minutes > 59 then
    failwith "Minuty muszą być w zakresie od 0 do 59."
  else if period <> "AM" && period <> "PM" then
    failwith "Okres musi być 'AM' lub 'PM'."
  else
    let military_hour = 
		if period = "AM" && hour = 12 then 0
		else if period = "PM" && hour <> 12 then hour + 12 
		else hour
    in
    Printf.sprintf "%02d:%02d" military_hour minutes
;;


militaryMinutes (12, 30, "AM");;
militaryMinutes (1, 15, "PM");;
militaryMinutes (12, 45, "PM");;
militaryMinutes (6, 0, "AM");;
militaryMinutes (11, 59, "PM");;
militaryMinutes (13, 30, "AM");;
militaryMinutes (12, 70, "PM");;
militaryMinutes (10, 30, "XY");;
