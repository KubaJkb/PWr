//
// Created by jakub on 22 lis 2024.
//

#ifndef CTREE_H
#define CTREE_H

#include <vector>
#include <cstdlib>
#include <set>
#include <string>

#include "Node.h"

using namespace std;
class Tree
{
public:
    void operator=(const Tree &pcNewVal);
    Tree operator+(Tree &pcNewVal);
    Tree(){cHead = NULL;};
    set<string> vGetVars();
    void vInitializeTree(vector<string> vInputs);
    void vPrintTree();
    void vPrintVars();
    void vCalculateTree(vector<string> vInputs);
    void vJoinTree(vector<string> vInputs);

private:
    Node* cHead;
    bool bPushElement(string element);
    void vFillTree();


};



#endif //CTREE_H
