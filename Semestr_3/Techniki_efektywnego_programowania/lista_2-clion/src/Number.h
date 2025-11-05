//
// Created by jakub on 3 lis 2024.
//

#ifndef NUMBER_H
#define NUMBER_H
#include <string>

using namespace std;

class Number {
private:
    int* table;
    int length;
    bool isNegative;
    int base;

    void fromDecInt(int value);

public:
    Number();
    Number(int base);
    Number(int base, int decVal);
    Number(const Number& other);
    ~Number();

    Number& operator= (int value);
    Number& operator= (const Number& other);

    Number operator+ (const Number& other) const;
    Number operator- (const Number& other) const;
    Number operator* (const Number& other) const;
    Number operator/ (const Number& other) const;
    Number operator% (int iSize) const;

    string toStr() const;
    Number operator- () const;
    bool operator< (const Number& other) const;
};

#endif //NUMBER_H
