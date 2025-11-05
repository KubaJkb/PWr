#include <iostream>
#include <fstream>
#include <string>
#include "Interface.h"
#include "constants.cpp"
#include "Result.h"
#include "SmartPointer.h"
using namespace std;

//int main() {
//    ofstream file(RESULT_FILE_NAME, ios::trunc);
//    file.close();
//
//    Interface interface;
//    interface.inputCommand();
//}

//int main() {
//    Tree tree1;
//    Tree tree2;
//    
//    vector<string> args = { "1", "+", "2" };
//    tree1.initTree(args);
//    
//    args = { "3", "*", "4" };
//    tree2.initTree(args);
//    
//    tree1 = std::move(tree2);
//
//    if (tree1.isEmpty()) {
//        std::cout << "tree1 is empty" << std::endl;
//    } 
//    
//    if (tree2.isEmpty()) {
//        std::cout << "tree2 is empty after move" << std::endl;
//    }
//
//
//    return 0;
//}


int main() {
    SmartPointer<int> sp1(new int(47));
    SmartPointer<int> sp2(sp1); 
    SmartPointer<int> sp3 = sp2; 

    sp1.printPointerList();
    sp2.printPointerList();
    sp3.printPointerList();

    {
        SmartPointer<int> sp4 = sp1;
        sp1.printPointerList();
        sp2.printPointerList();
    }

    sp1.printPointerList();


    return 0;
}
