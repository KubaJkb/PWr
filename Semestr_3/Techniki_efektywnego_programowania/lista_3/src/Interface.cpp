#include <iostream>
#include "Interface.h"
#include "Tree.h"
#include "constants.cpp"
using namespace std;

void Interface::inputCommand() {
    cout << "Podaj komende:\n";
    string line = "";
    while (line == "") {
        getline(cin, line);
    }
    processCommand(line);
}

void Interface::processCommand(string command) {
    vector<string> args = splitStringtoVector(command, DEFAULT_SEPARATOR);
    string instruction = args.front();
    args.erase(args.begin());

    if (instruction != COMMANDS[0]) {
        if (instruction == COMMANDS[1]) {
            currentTree.initTree(args);
        } else if (instruction == COMMANDS[2]) {
            currentTree.printVars();
        } else if (instruction == COMMANDS[3]) {
            currentTree.printTree();
        } else if (instruction == COMMANDS[4]) {
            currentTree.computeTree(args);
        } else if (instruction == COMMANDS[5]) {
            currentTree.joinTree(args);
        } else if (instruction == COMMANDS[6]) {
            currentTree.printLevels();
        } else {
            cout << "Niepoprawna komenda!\n";
        }
        inputCommand();
    }
}

vector<string> Interface::splitStringtoVector(const string& str, const char separator) {
    vector<string> result;
    string temp;

    for (char ch : str) {
        if (ch == separator) {
            if (!temp.empty()) {
                result.push_back(temp);
                temp.clear();
            }
        } else {
            temp += ch;
        }
    }

    if (!temp.empty()) {
        result.push_back(temp);
    }

    return result;
}
