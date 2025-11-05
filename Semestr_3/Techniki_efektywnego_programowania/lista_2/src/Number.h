#pragma once

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
	Number operator% (int size) const;

	string toStr() const;
	Number operator- () const;
	bool operator< (const Number& other) const;
};
