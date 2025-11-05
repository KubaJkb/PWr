#ifndef INTERFACE_H
#define INTERFACE_H

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
    Result<Tree*, Error> convertToResultTree(Result<void, Error>& result);
};

#endif