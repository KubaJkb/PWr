//class GenericCellImm[T] (val x: T)

class GenericCellImm[+T] (val x: T)

//class GenericCellMut[T] (var x: T)

class GenericCellMut[+T] (var x: T)

//Kowariancja +T nie jest kompatybilna z mutowalnym polem var.
//Kontrawariancja -T również nie działa z mutowalnym polem var.
//Aby zachować bezpieczeństwo typów, klasy z modyfikowalnymi polami są zawsze inwariantne.