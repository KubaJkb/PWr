#include <iostream>
#include <fstream>
#include <string>
#include "Interface.h"
#include "constants.cpp"
#include "Result.h"
using namespace std;

int main() {
    ofstream file(RESULT_FILE_NAME, ios::trunc);
    file.close();

    Interface interface;
    interface.inputCommand();
}
