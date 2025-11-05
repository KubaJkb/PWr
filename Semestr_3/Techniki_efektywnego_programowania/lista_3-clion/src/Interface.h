//
// Created by jakub on 22 lis 2024.
//

#ifndef CREADER_H
#define CREADER_H

#include "Tree.h"
#include <string>
#include <vector>
using namespace std;

class Interface{
public:
    Interface(){};
    void vStartListening();
    void vExecuteCommand(string sCommand);
private:
    vector<string> splitStringByString(const string& str, const string splitter);
    Tree cCurrentTree;
};



#endif //CREADER_H
