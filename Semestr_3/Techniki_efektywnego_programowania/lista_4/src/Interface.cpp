#include <iostream>
#include "Interface.h"
#include "constants.cpp"
#include "ResultSaver.h"


using namespace std;

void Interface::inputCommand() {
    cout << GET_COMMAND_MESSAGE;
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

    if (instruction != COMMAND_EXIT) {
        Result<void, Error> result;
        
        if (instruction == COMMAND_ENTER) {
            result = currentTree.initTree(args);
        }
        else if (instruction == COMMAND_VARS) {
            result = currentTree.printVars();
        }
        else if (instruction == COMMAND_PRINT) {
            result = currentTree.printTree();
        }
        else if (instruction == COMMAND_COMP) {
            result = currentTree.computeTree(args);
        }
        else if (instruction == COMMAND_JOIN) {
            result = currentTree.joinTree(args);
        }
        else if (instruction == COMMAND_LEVELS) {
            result = currentTree.printLevels();
        }
        else {
            cout << ERROR_UNKNOWN_COMMAND;
            inputCommand();
            return;
        }
        
        vector<Error*>& errors = result.getErrors();
        for (Error* error : errors) {
            cout << error->getMessage();
        }

        //ResultSaver<void> resSave = ResultSaver<void>(result);
        //resSave.save(RESULT_FILE_NAME);


        Result<Tree*, Error> resultTree = convertToResultTree(result);
        ResultSaver<Tree*> resSave = ResultSaver<Tree*>(resultTree);
        resSave.save(RESULT_FILE_NAME);
        
        inputCommand();
    }
}

Result<Tree*, Error> Interface::convertToResultTree(Result<void, Error>& result) {
    if (result.isSuccess()) {
        return Result<Tree*, Error>::ok(&currentTree);
    }
    else {
        return Result<Tree*, Error>::fail(result.getErrors());
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
        }
        else {
            temp += ch;
        }
    }

    if (!temp.empty()) {
        result.push_back(temp);
    }

    return result;
}
