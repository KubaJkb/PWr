#include "DateTime.h"

#include <iostream>

using namespace std;

int main() {

    TimeSpan ts1(0, 5, 30);
    TimeSpan ts2 = 1_d + 20_h + 40_m;

    TimeSpan sum = ts1 + ts2;
    cout << "Suma: " << sum.getDays() << " dni, " << sum.getHours() << " godzin, " << sum.getMinutes() << " minut.\n";
    TimeSpan roz = ts1 - ts2;
    cout << "Roznica: " << roz.getDays() << " dni, " << roz.getHours() << " godzin, " << roz.getMinutes() << " minut.\n";


    DateTime dt1(2025, 1, 31, 12, 0);    
    DateTime dt2 = dt1 + ts2;
    DateTime dt3 = dt1 - ts1;
    cout << dt1.printDate() << endl;
    cout << dt2.printDate() << endl;
    cout << dt3.printDate() << endl;

    TimeSpan diff1 = dt2 - dt1;
    cout << "Roznica: " << diff1.getDays() << " dni, " << diff1.getHours() << " godzin, " << diff1.getMinutes() << " minut.\n";

    cout << endl;

    DateTime dt4(2025, 1, 22, 10, 30);
    DateTime dt5 = dt4 + 2_d + 3_h + 15_m;
    cout << dt4.printDate() << endl;
    cout << dt5.printDate() << endl;

    TimeSpan diff2 = dt5 - dt4;
    cout << "Roznica: " << diff2.getDays() << " dni, " << diff2.getHours() << " godzin, " << diff2.getMinutes() << " minut.\n";


    return 0;
}
