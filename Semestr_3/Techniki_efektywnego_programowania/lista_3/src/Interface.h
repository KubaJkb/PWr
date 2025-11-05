#pragma once
#include "Tree.h"
#include <string> 
#include <vector> 
using namespace std;

class Interface {
public:
    void inputCommand();
    void processCommand(string command);

private:
    vector<string> splitStringtoVector(const string& str, const char separator);
    Tree currentTree;
};
