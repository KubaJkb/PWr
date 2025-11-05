#include <iostream>
#include "Number.h"

using namespace std;

int main() {
    // System dziesiêtny
    Number num1(10);
    Number num2(10);
    Number num3(10);
    num1 = 1234;
    num2 = -56;
    cout << "num1 = " << num1.toStr() << endl;
    cout << "num2 = " << num2.toStr() << endl;

    num3 = num1;
    cout << "Po przypisaniu num3 = num1: num3 = " << num3.toStr() << endl;
    Number sum = num1 + num2;
    cout << "num1 + num2 = " << sum.toStr() << endl;
    Number diff = num1 - num2;
    cout << "num1 - num2 = " << diff.toStr() << endl;
    Number product = num1 * num2;
    cout << "num1 * num2 = " << product.toStr() << endl;
    Number quotient = num1 / num2;
    cout << "num1 / num2 = " << quotient.toStr() << endl;

    // System szesnastkowy
    Number hexNum1(16);
    Number hexNum2(16);
    hexNum1 = 255;
    hexNum2 = 4095;
    cout << "hexNum1 (w systemie szesnastkowym) = " << hexNum1.toStr() << endl;
    cout << "hexNum2 (w systemie szesnastkowym) = " << hexNum2.toStr() << endl;

    Number hexSum = hexNum1 + hexNum2;
    cout << "hexNum1 + hexNum2 = " << hexSum.toStr() << endl;
    
    // System binarny
    Number binNum1(2);
    Number binNum2(2);
    binNum1 = 0b101010;
    binNum2 = 0b110011;
    cout << "binNum1 (w systemie binarnym) = " << binNum1.toStr() << endl;
    cout << "binNum2 (w systemie binarnym) = " << binNum2.toStr() << endl;
    
    Number binSum = binNum1 + binNum2;
    cout << "binNum1 + binNum2 = " << binSum.toStr() << endl;

    Number num;
    num = 123456;

    Number zeroFill1 = num % 3;
    cout << "num % 3 = " << zeroFill1.toStr() << endl;
    Number zeroFill2 = num % 0;
    cout << "num % 0 = " << zeroFill2.toStr() << endl;
    Number zeroFill3 = num % 50;
    cout << "num % 50 = " << zeroFill3.toStr() << endl;

    return 0;
}
