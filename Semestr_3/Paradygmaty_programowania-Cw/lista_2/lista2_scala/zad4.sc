val x = List(-2, -1, 0, 1, 2) match 
 case List(_, _, res, _*) => res


val y = List((1,2), (0,1)) match 
 case List(_, (res, _), _*) => res

