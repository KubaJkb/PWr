//
// Created by jakub on 22 lis 2024.
//

#include <string>
using namespace std;

const short TYPE_FUNCTION = 2;
const short TYPE_FUNCTION_PLUS_LIMIT = 2;
const short TYPE_NUMBER = 0;
const short TYPE_NUMBER_LIMIT = 0;
const short TYPE_VARIABLE = 1;
const short TYPE_VARIABLE_LIMIT = 0;
const string TYPE_FUNCTION_PLUS_VALUE = "+";
const string TYPE_VARIABLE_VALUE = "abc";
const short DEFAULT_S_TYPE = 0;
const short DEFAULT_CHILD_LIMIT = 0;
const string DEFAULT_S_VALUE = "1";
const short OPERATIONS_SIZE = 6;
const string DEFAULT_OPERATIONS[OPERATIONS_SIZE] = {"sin", "cos", "+", "-", "/", "*"};
const int DEFAULT_CHILD_LIMITS[OPERATIONS_SIZE] = {1,1,2,2,2,2};
const string DEFAULT_ELEMENT = "1";
const string DEFAULT_SPLITTER = " ";
