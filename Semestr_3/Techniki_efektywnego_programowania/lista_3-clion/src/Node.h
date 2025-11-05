//
// Created by jakub on 22 lis 2024.
//

#ifndef CNODE_H
#define CNODE_H

#include "constants.cpp"
#include <vector>
#include <set>
#include <map>
#include <string>
using namespace std;

class Node {
public:
    void operator=(const Node &pcNewNode);
    Node(short sType, short sChildLimit, string sValue);
    Node();
    short getType();
    string getValue();
    bool bAddChild(Node* newNode);
    void vPrintNode();
    Node* vCloneNode();
    set<string> vAddVars(set<string> sResSet);
    double iCalculateNode(map<string, double>mDict);

private:

    Node* cParent;
    vector<Node*>vChildren;
    short sType; // 0 - liczba, 1 - zmienna, 2 - operacja
    short sChildLimit;
    string sValue; //sin,cos,+,-,/,*,1,2,3,4,5,6,7,8,9,a,b,c,...

};



#endif //CNODE_H
