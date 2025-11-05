//
// Created by jakub on 22 lis 2024.
//

#include "Node.h"
#include <iostream>
#include <vector>
#include <cmath>
#include "helper.cpp"
using namespace std;

Node::Node(){
    this->sType = DEFAULT_S_TYPE;
    this->sChildLimit = DEFAULT_CHILD_LIMIT;
    this->sValue = DEFAULT_S_VALUE;
}

Node::Node(short sType, short sChildLimit, string sValue){
    this->sType = sType;
    this->sChildLimit = sChildLimit;
    this->sValue = sValue;
}

bool Node::bAddChild(Node* newNode){
    for (int i = 0; i < vChildren.size(); ++i){
        bool result = vChildren[i]->bAddChild(newNode);
        if (result) return true;
    }
    if (vChildren.size() < sChildLimit){
        vChildren.push_back(newNode);
        newNode->cParent = this;
        return true;
    }
    return false;
}

void Node::vPrintNode(){
    cout << sValue << " ";
    for (int i = 0; i < vChildren.size(); ++i){
        vChildren[i]->vPrintNode();
    }
}

double Node::iCalculateNode(map<string, double>mDict){
    if (sType == TYPE_NUMBER) return helper::strToDouble(sValue);
    if (sType == TYPE_VARIABLE) return mDict[sValue];
    if (sValue == "sin"){
        return sin(vChildren[0]->iCalculateNode(mDict));
    } else if (sValue == "cos"){
        return cos(vChildren[0]->iCalculateNode(mDict));
    } else if (sValue == "+"){
        double result = 0;
        for (int i = 0; i<sChildLimit; ++i){
            result += vChildren[i]->iCalculateNode(mDict);
        }
        return result;
    } else if (sValue == "*"){
        double result = 1;
        for (int i = 0; i<sChildLimit; ++i){
            result *= vChildren[i]->iCalculateNode(mDict);
        }
        return result;
    } else if (sValue == "-"){
        return vChildren[0]->iCalculateNode(mDict) - vChildren[1]->iCalculateNode(mDict);
    } else if (sValue == "/"){
        return vChildren[0]->iCalculateNode(mDict) / vChildren[1]->iCalculateNode(mDict);
    } else {
        cout << "to sie nie powinno pojawic, nie znaleziono operacji\n";
        return 0;
    }
}

Node* Node::vCloneNode(){
    Node* cNewNode = new Node(this->sType, this->sChildLimit, this->sValue);
    vector<Node*> newVec;
    for (int i = 0; i < vChildren.size(); ++i){
        Node* tmpNode = vChildren[i]->vCloneNode();
        tmpNode->cParent = cNewNode;
        newVec.push_back(tmpNode);
    }
    cNewNode->vChildren = newVec;
    return cNewNode;
}

short Node::getType(){
    return sType;
}

string Node::getValue(){
    return sValue;
}

set<string> Node::vAddVars(set<string> sResSet){
    if (sType == 1){
        sResSet.insert(sValue);
    }
    for (int i = 0; i < vChildren.size(); ++i){
        sResSet = vChildren[i]->vAddVars(sResSet);
    }
    return sResSet;
}