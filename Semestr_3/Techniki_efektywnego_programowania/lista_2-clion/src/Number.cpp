#include "Number.h"
#include <iostream>
#include <algorithm>
#include <cmath>
#include <sstream>

#define DEFAULT_TABLE_CONTENT new int[1]{0}
#define DEFAULT_TABLE_LENGTH 1
#define DEFAULT_TABLE_ISNEGATIVE false
#define DEFAULT_TABLE_BASE 10
#define DIFFERENT_BASE_ERROR_MESSAGE "Different bases"


void Number::fromDecInt(int value) {
	delete[] table;

	int temp = abs(value);
	isNegative = (value < 0);
	length = (temp == 0) ? 1 : static_cast<int>(log(temp) / log(base)) + 1;

	table = new int[length];
	for (int i = 0; i < length; i++) {
		table[i] = temp % base;
		temp /= base;
	}
}

Number::Number() : table(DEFAULT_TABLE_CONTENT), length(DEFAULT_TABLE_LENGTH), isNegative(DEFAULT_TABLE_ISNEGATIVE), base(DEFAULT_TABLE_BASE) {

}

Number::Number(int base) : table(DEFAULT_TABLE_CONTENT), length(DEFAULT_TABLE_LENGTH), isNegative(DEFAULT_TABLE_ISNEGATIVE), base(base) {}

Number::Number(int base, int decVal) {
	if (base < 2) throw invalid_argument("Invalid base");
	fromDecInt(decVal);
}

Number::Number(const Number& other) {
	base = other.base;
	length = other.length;
	isNegative = other.isNegative;
	table = new int[length];
	copy(other.table, other.table + length, table);
}

Number::~Number() {
	delete[] table;
}

Number& Number::operator= (int value) {
	fromDecInt(value);
	return *this;
}

Number& Number::operator= (const Number& other) {
	if (this == &other) return *this;

	base = other.base;
	length = other.length;
	isNegative = other.isNegative;
	copy(other.table, other.table + length, table);

	return *this;
}

string intToString(int value) {
	stringstream ss;
	ss << value;
	return ss.str();
}

string Number::toStr() const {
	string result = isNegative ? "-" : "";

	for (int i = length - 1; i >= 0; i--) {
		if (table[i] < 10) {
			result += intToString(table[i]);
		}
		else {
			result += "(" + intToString(table[i]) + ")";
		}
	}

	return result;
}

Number Number::operator- () const {
	Number result(*this);
	result.isNegative = !isNegative;
	return result;
}

bool Number::operator< (const Number& other) const {
	if (isNegative != other.isNegative) {
		return isNegative;
	}

	if (length != other.length) {
		return (length < other.length) != isNegative;
	}

	for (int i = length - 1; i >= 0; i--) {
		if (table[i] != other.table[i]) {
			return (table[i] < other.table[i]) != isNegative;
		}
	}

	return false;
}

Number Number::operator+ (const Number& other) const {
	if (base != other.base) throw invalid_argument(DIFFERENT_BASE_ERROR_MESSAGE);;

	if (isNegative != other.isNegative) {
		if (isNegative) {
			return other - (-(*this));
		}
		else {
			return *this - (-other);
		}
	}

	int maxLength = max(length, other.length);
	Number result(base);
	result.isNegative = isNegative;
	result.length = maxLength;
	result.table = new int[maxLength + 1];

	int carry = 0;
	for (int i = 0; i < maxLength; i++) {
		int sum = carry;
		if (i < length) sum += table[i];
		if (i < other.length) sum += other.table[i];

		result.table[i] = sum % base;
		carry = sum / base;
	}

	if (carry) {
		result.table[result.length++] = carry;
	}

	return result;
}

Number Number::operator- (const Number& other) const {
	if (base != other.base) throw invalid_argument(DIFFERENT_BASE_ERROR_MESSAGE);;

	if (isNegative != other.isNegative) return *this + (-other);

	if (*this < other) return - (other - *this);

	Number result(base);
	result.isNegative = isNegative;
	result.length = length;
	result.table = new int[result.length];

	int borrow = 0;
	for (int i = 0; i < length; ++i) {
		int sub = table[i] - borrow;
		if (i < other.length) sub -= other.table[i];

		if (sub < 0) {
			sub += base;
			borrow = 1;
		}
		else {
			borrow = 0;
		}
		result.table[i] = sub;
	}

	while (result.length > 1 && result.table[result.length - 1] == 0) {
		result.length--;
	}

	return result;
}

Number Number::operator* (const Number& other) const {
	if (base != other.base) throw invalid_argument(DIFFERENT_BASE_ERROR_MESSAGE);

	Number result(base);
	result.isNegative = (isNegative != other.isNegative);
	result.length = length + other.length;
	result.table = new int[result.length]();

	for (int i = 0; i < length; i++) {
		int carry = 0;
		for (int j = 0; j < other.length || carry; j++) {
			int current = result.table[i + j] + carry;
			if (j < other.length) current += table[i] * other.table[j];
			result.table[i + j] = current % base;
			carry = current / base;
		}
	}

	while (result.length > 1 && result.table[result.length - 1] == 0) {
		result.length--;
	}

	return result;
}

Number Number::operator/ (const Number& other) const {
	if (base != other.base) throw invalid_argument(DIFFERENT_BASE_ERROR_MESSAGE);
	if (other.length == 1 && other.table[0] == 0) throw invalid_argument("Divisor is equal to 0");;

	Number result(base);
	result.isNegative = (isNegative != other.isNegative);

	Number dividend = *this;
	dividend.isNegative = false;
	Number divisor = other;
	divisor.isNegative = false;

	if (dividend < divisor) {
		result.isNegative = false;
		result.length = 1;
		result.table = new int[1] {0};
		return result;
	}

	// Wynik
	result.table = new int[dividend.length];
	for (int i = 0; i < dividend.length; ++i) {
		result.table[i] = 0;
	}
	result.length = dividend.length;

	// Bieżąca wartość dzielonego elementu
	Number current(base);
	current.table = new int[dividend.length];
	for (int i = 0; i < dividend.length; ++i) {
		result.table[i] = 0;
	}
	current.length = 0;

	for (int i = dividend.length - 1; i >= 0; i--) {
		// Dodawanie bieżącej cyfry dzielnej do bieżącej wartości
		if (current.length < dividend.length) {
			for (int j = current.length; j > 0; j--) {
				current.table[j] = current.table[j - 1];
			}
			current.table[0] = dividend.table[i];
			current.length++;
			while (current.length > 1 && current.table[current.length - 1] == 0) {
				current.length--;
			}
		}

		// Znajdywanie największej cyfry x  (x * divisor <= current)
		int x = 0;
		while (!(current < divisor)) {
			current = current - divisor;
			x++;
		}

		result.table[i] = x;
	}

	while (result.length > 1 && result.table[result.length - 1] == 0) {
		result.length--;
	}

	return result;
}

Number Number::operator% (int size) const {
	if (size < 0) throw invalid_argument("Invalid size");

	Number result(base);
	result.isNegative = isNegative;
	result.length = length + size;
	result.table = new int[result.length];

	for (int i = 0; i < length; i++) {
		result.table[i+size] = table[i];
	}

	int fillVal = 0;
	for (int i = 0; i < size; ++i) {
		result.table[i] = fillVal;
	}

	return result;
}
