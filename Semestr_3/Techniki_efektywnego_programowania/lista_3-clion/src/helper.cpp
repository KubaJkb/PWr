//
// Created by jakub on 22 lis 2024.
//

#include <iostream>
#include <stdlib.h>
#include <vector>
#include <string>

using namespace std;

namespace helper {
    bool isNumber(string& str) {
        if (str.empty()) return false;

        if (str[0] == '-'){
            string str2 = str;
            str2.erase(0,1);
            if (isNumber(str2)){
                cout << "nie moze byc ujemnych, zmieniono na dodatnia\n";
                str.erase(0,1);
            }
        }
        for (size_t i = 0; i < str.size(); ++i) {
            if (!isdigit(str[i])) {
                return false;
            }
        }
        return true;
    }

    int contains(const string arr[], int size, const string& value) {
        for (int i = 0; i < size; ++i) {
            if (arr[i] == value) {
                return i;
            }
        }
        return -1;
    }

    string filterAlphaNumeric(const string& input) {
        string result;

        for (size_t i = 0; i < input.size(); ++i) {
            char c = input[i];
            if (isalpha(c) || isdigit(c)) {
                result += c;
            }
        }

        return result;
    }

    bool isNaturalNumber(const string& str) {
        if (str.empty()) return false;
        for (size_t i = 0; i < str.size(); ++i) {
            if (!isdigit(str[i])) return false;
        }
        return true;
    }

    double strToDouble(const string& str) {
        return atof(str.c_str());
    }

}