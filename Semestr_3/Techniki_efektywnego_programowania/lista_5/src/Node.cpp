#include "Node.h"
#include <iostream>
#include <vector>
#include <cmath>
#include "constants.cpp"
using namespace std;

Node::Node() : parent(nullptr) {
    this->type = DEFAULT_TYPE;
    this->childLimit = DEFAULT_CHILD_LIMIT;
    this->value = DEFAULT_VALUE;
}

Node::Node(short type, short childLimit, string value) : parent(nullptr) {
    this->type = type;
    this->childLimit = childLimit;
    this->value = value;
}

Node::~Node() {
    for (Node* child : children) {
        delete child;
    }
    children.clear();
}

bool Node::addChild(Node* newNode) {
    for (int i = 0; i < children.size(); i++) {
        bool isAdded = children[i]->addChild(newNode);
        if (isAdded) {
            return true;
        }
    }

    if (children.size() < childLimit) {
        children.push_back(newNode);
        newNode->parent = this;
        return true;
    }

    return false;
}

void Node::printNodeIter() {
    cout << value << " ";
    for (int i = 0; i < children.size(); i++) {
        children[i]->printNodeIter();
    }
}

string Node::getValIter() {
    string res = value;
    for (int i = 0; i < children.size(); i++) {
        res += " " + children[i]->getValIter();
    }
    return res;
}

void Node::printNodeValue() const {
    cout << value << " ";
}

vector<Node*> Node::getChildren() {
    return children;
}

vector<string> Node::addVarsIter(vector<string> vars) {
    for (int i = 0; i < children.size(); ++i) {
        vars = children[i]->addVarsIter(vars);
    }

    if (type == 1) {
        if (find(vars.begin(), vars.end(), value) == vars.end()) {
            vars.push_back(value);
        }
    }

    return vars;
}

Result<double, Error> Node::computeNode(map<string, double> dict) {
    if (type == TYPE_NUMBER) {
        return Result<double, Error>::ok(atof(value.c_str()));

    }
    else if (type == TYPE_VARIABLE) {
        return Result<double, Error>::ok(dict[value]);

    }
    else if (type == TYPE_OPERATOR) {
        if (value == OPERATOR_ADD) { // "+"
            Result<double, Error> left = children[0]->computeNode(dict);
            Result<double, Error> right = children[1]->computeNode(dict);
            if (left.isSuccess() && right.isSuccess()) {
                return Result<double, Error>::ok(left.getValue() + right.getValue());
            }
            else {
                vector<Error*> allErrors = Error::combineErrors(left.getErrors(), right.getErrors());
                return Result<double, Error>::fail(allErrors);
            }
        }
        else if (value == OPERATOR_SUB) { // "-"
            Result<double, Error> left = children[0]->computeNode(dict);
            Result<double, Error> right = children[1]->computeNode(dict);
            if (left.isSuccess() && right.isSuccess()) {
                return Result<double, Error>::ok(left.getValue() - right.getValue());
            }
            else {
                vector<Error*> allErrors = Error::combineErrors(left.getErrors(), right.getErrors());
                return Result<double, Error>::fail(allErrors);
            }
        }
        else if (value == OPERATOR_MUL) { // "*"
            Result<double, Error> left = children[0]->computeNode(dict);
            Result<double, Error> right = children[1]->computeNode(dict);
            if (left.isSuccess() && right.isSuccess()) {
                return Result<double, Error>::ok(left.getValue() * right.getValue());
            }
            else {
                vector<Error*> allErrors = Error::combineErrors(left.getErrors(), right.getErrors());
                return Result<double, Error>::fail(allErrors);
            }
        }
        else if (value == OPERATOR_DIV) { // "/"
            Result<double, Error> left = children[0]->computeNode(dict);
            Result<double, Error> right = children[1]->computeNode(dict);
            if (left.isSuccess() && right.isSuccess()) {
                if (right.getValue() == 0) {
                    return Result<double, Error>::fail(new Error(Error::ErrorType::INCORRECT_DIVISION, "W trakcie obliczania wartosci drzewa wystapilo niedozwolone dzielenie przez 0"));
                }
                else if (right.getValue() < 0) {
                    return Result<double, Error>::fail(new Error(Error::ErrorType::INCORRECT_DIVISION, "Nie mozna dzielic przez liczby ujemne"));
                }
                return Result<double, Error>::ok(left.getValue() / right.getValue());
            }
            else {
                vector<Error*> allErrors = Error::combineErrors(left.getErrors(), right.getErrors());
                return Result<double, Error>::fail(allErrors);
            }
        }
        else if (value == OPERATOR_SIN) { // "sin"
            Result<double, Error> res = children[0]->computeNode(dict);
            if (res.isSuccess()) {
                return Result<double, Error>::ok(sin(res.getValue()));
            }
            else {
                return Result<double, Error>::fail(res.getErrors());
            }
        }
        else if (value == OPERATOR_COS) { // "cos"
            Result<double, Error> res = children[0]->computeNode(dict);
            if (res.isSuccess()) {
                return Result<double, Error>::ok(cos(res.getValue()));
            }
            else {
                return Result<double, Error>::fail(res.getErrors());
            }
        }
    }

    // ***************************** ERROR ********************************
    return Result<double, Error>::fail(new Error(Error::ErrorType::UNKNOWN, "Nieznany blad podczas obliczania wartosci drzewa!"));
}

Node* Node::cloneNode() {
    Node* newNode = new Node(type, childLimit, value);

    for (Node* child : children) {
        Node* childClone = child->cloneNode();
        childClone->parent = newNode;

        newNode->children.push_back(childClone);
    }
    return newNode;
}

Node* Node::getLeaf() {
    if (!children.empty()) {
        return children[0]->getLeaf();
    }
    else {
        return this;
    }
}

void Node::replaceNode(Node* newNode) {
    if (hasParent()) {
        for (int i = 0; i < parent->children.size(); i++) {
            if (parent->children[i] == this) {
                parent->children[i] = newNode;
                break;
            }
        }
        newNode->parent = this->parent;
    }
}

bool Node::hasParent() {
    return (parent != nullptr);
}