//
// Created by jakub on 22 lis 2024.
//

#include "Tree.h"
#include <iostream>
#include <vector>
#include <set>
#include <map>
#include <cstdlib>
#include "Node.cpp"
using namespace std;


void Tree::operator=(const Tree &pcOther)
{
    if (this == &pcOther) return;

    delete cHead;
    if (pcOther.cHead != NULL){
        cHead = pcOther.cHead->vCloneNode();
    }
}

Tree Tree::operator+(Tree &pcNewVal){
    Tree cNewTree = Tree();
    Node* cPlus = new Node(TYPE_FUNCTION, TYPE_FUNCTION_PLUS_LIMIT, TYPE_FUNCTION_PLUS_VALUE);
    cPlus->bAddChild(cHead->vCloneNode());
    cPlus->bAddChild(pcNewVal.cHead->vCloneNode());
    cNewTree.cHead = cPlus;
    return cNewTree;
}

void Tree::vInitializeTree(vector<string> vInputs){
    cHead = NULL;
    for (int i = 0; i < vInputs.size(); ++i){
        bool tmpRes = bPushElement(vInputs[i]);
        if (!tmpRes){
            cout << "Nie dodano: " << vInputs[i] << endl;
        }
    }
    vFillTree();
}


bool Tree::bPushElement(string sElement){
    Node* newNode;
    if (helper::isNumber(sElement)){
        newNode = new Node(TYPE_NUMBER, TYPE_NUMBER_LIMIT, sElement);
    } else if (helper::contains(DEFAULT_OPERATIONS, OPERATIONS_SIZE, sElement) != -1){
        newNode = new Node(TYPE_FUNCTION, DEFAULT_CHILD_LIMITS[helper::contains(DEFAULT_OPERATIONS, OPERATIONS_SIZE, sElement)], sElement);
    } else if (helper::filterAlphaNumeric(sElement) != "") {
        string sTmpElement = helper::filterAlphaNumeric(sElement);
        if (sElement != sTmpElement){
            cout << "zmieniono " << sElement << " na " << sTmpElement << "\n";
        }
        newNode = new Node(TYPE_VARIABLE, TYPE_VARIABLE_LIMIT, sTmpElement);
    } else {
        cout << "wykryto zły element - zmieniono na abc\n";
        newNode = new Node(TYPE_VARIABLE, TYPE_VARIABLE_LIMIT, TYPE_VARIABLE_VALUE);
    }
    if (cHead == NULL){
        cHead = newNode;
        return true;
    } else {
        return cHead->bAddChild(newNode);
    }
}

void Tree::vFillTree(){
    bool bTmpRes = true;
    while (bTmpRes){
        bTmpRes = bPushElement(DEFAULT_ELEMENT);
        if (bTmpRes){
            cout << "dodano jedynkę bo napsułeś\n";
        }
    }
}

void Tree::vPrintTree(){
    if (cHead != NULL) cHead->vPrintNode();
    cout << "\n";
}

set<string> Tree::vGetVars(){
    set<string> sResSet;

    if (cHead != NULL){
        return cHead->vAddVars(sResSet);
    }

    return sResSet;
}

void Tree::vCalculateTree(vector<string> vInputs){
    if (cHead == NULL){
        cout << "glowa jest nullem - nie podales formuly\n";
        return;
    }
    set<string> vars = vGetVars();
    if (vars.size() != vInputs.size()){
        cout << "zla ilosc podanych danych\n";
        return;
    }

    map<string, double> mDict;
    set<string>::iterator it;

    for (int i = 0; i < vInputs.size(); ++i) {
        if (helper::isNaturalNumber(vInputs[i])) {
            double iCurrNum = helper::strToDouble(vInputs[i]);
            it = vars.begin();
            advance(it, i);
            mDict[*it] = iCurrNum;
        } else {
            cout << "liczby musza byc naturalne\n";
            return;
        }
    }

    double iResult = cHead->iCalculateNode(mDict);
    cout << "wynik: " << iResult << endl;
}

void Tree::vJoinTree(vector<string> vInputs){
    Tree cSecondTree = Tree();
    cSecondTree.vInitializeTree(vInputs);
    *this = *this + cSecondTree;
}

void Tree::vPrintVars(){
    set<string> vars = vGetVars();
    for (set<string>::iterator it = vars.begin(); it != vars.end(); ++it) {
        cout << *it << " ";
    }
    cout << "\n";
}