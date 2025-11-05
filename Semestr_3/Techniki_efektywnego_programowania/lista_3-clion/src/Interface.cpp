//
// Created by jakub on 22 lis 2024.
//

#include "Interface.h"
#include <iostream>
#include "Tree.cpp"
using namespace std;


void Interface::vExecuteCommand(string sCommand){
    vector<string> vStrings = splitStringByString(sCommand, DEFAULT_SPLITTER);
    string sFirstWord = vStrings[0];
    vStrings.erase(vStrings.begin());

    if (sFirstWord == "enter"){
        cCurrentTree.vInitializeTree(vStrings);
    } else if (sFirstWord == "vars"){
        cCurrentTree.vPrintVars();
    } else if (sFirstWord == "print"){
        cCurrentTree.vPrintTree();
    } else if (sFirstWord == "comp"){
        cCurrentTree.vCalculateTree(vStrings);
    } else if (sFirstWord == "join"){
        cCurrentTree.vJoinTree(vStrings);
    } else{
        cout << "Wrong command.\n";
    }
}

void Interface::vStartListening(){
    cout << "You can start typing now [type exit to leave]: \n";
    string sLine;
    while (true) {
        getline(cin, sLine);
        if (sLine == "exit") {
            break;
        }
        vExecuteCommand(sLine);
    }
}

vector<string> Interface::splitStringByString(const string& str, const string splitter) {
    vector<string> result;
    size_t pos = 0;
    size_t spacePos = str.find(splitter);

    while (spacePos != string::npos) {
        result.push_back(str.substr(pos, spacePos - pos));
        pos = spacePos + 1;
        spacePos = str.find(splitter, pos);
    }

    result.push_back(str.substr(pos));

    return result;
}
