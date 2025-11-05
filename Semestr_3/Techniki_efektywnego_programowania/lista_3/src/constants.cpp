#include <string>
using namespace std;

// Interface
const char DEFAULT_SEPARATOR = ' ';
const string COMMANDS[7] = {"exit", "enter", "vars", "print", "comp", "join", "levels"};

// Tree
const int OPERATORS_SIZE = 6;
const string OPERATORS[OPERATORS_SIZE] = {"+", "-", "*", "/", "sin", "cos"};
const int OPERATORS_CHILDREN_NUMBER[OPERATORS_SIZE] = {2, 2, 2, 2, 1, 1};
const short TYPE_NUMBER = 0;
const short TYPE_VARIABLE = 1;
const short TYPE_OPERATOR = 2;
const short TYPE_NUM_VAR_LIMIT = 0;

const string ERROR_SKIP = "Pominieto element ";
const string SWAPPED_NUM[2] = { "Zamieniono liczbê ", " z ujemnej na dodatni¹\n" };
const string SWAPPED_VAR[2] = { "Zamieniono zmienn¹ ", " na " };
const string ADDED_ONE = "Dodano element o wartosci 1 na koniec formuly\n";
const string COMP_ERROR = "Wprowadz formule przed proba obliczenia jej wartosci\n";
const string ERROR_VARS_NUM = "Liczba podanych argumentow jest nieprawidlowa\n";
const string ERROR_VARS_NOTNUM = "Podane argumenty musza byc liczbami\n";
const string CONST_MESSAGE = "Wynik: ";
const string EMPTY_TREE = "Tree is empty.";


// Node
const short DEFAULT_TYPE = 0;
const short DEFAULT_CHILD_LIMIT = 0;
const string DEFAULT_VALUE = "1";
