#include <string>
#include <map>
using namespace std;

const string RESULT_FILE_NAME = "wynik.txt";

// Interface
const string GET_COMMAND_MESSAGE = "Podaj komende:\n";
const char DEFAULT_SEPARATOR = ' ';
const string ERROR_UNKNOWN_COMMAND = "Niepoprawna komenda!\n";
const string COMMAND_EXIT = "exit";
const string COMMAND_ENTER = "enter";
const string COMMAND_VARS = "vars";
const string COMMAND_PRINT = "print";
const string COMMAND_COMP = "comp";
const string COMMAND_JOIN = "join";
const string COMMAND_LEVELS = "levels";

// Tree
const int OPERATORS_SIZE = 6;
const string OPERATOR_ADD = "+";
const string OPERATOR_SUB = "-";
const string OPERATOR_MUL = "*";
const string OPERATOR_DIV = "/";
const string OPERATOR_SIN = "sin";
const string OPERATOR_COS = "cos";
const map<string, int> OPERATORS_MAP = {
	{OPERATOR_ADD, 2},
	{OPERATOR_SUB, 2},
	{OPERATOR_MUL, 2},
	{OPERATOR_DIV, 2},
	{OPERATOR_SIN, 1},
	{OPERATOR_COS, 1}
};
const short TYPE_NUMBER = 0;
const short TYPE_VARIABLE = 1;
const short TYPE_OPERATOR = 2;
const short TYPE_NUM_VAR_LIMIT = 0;
const char NEGATION = '-';

// Node
const short DEFAULT_TYPE = 0;
const short DEFAULT_CHILD_LIMIT = 0;
const string DEFAULT_VALUE = "1";
