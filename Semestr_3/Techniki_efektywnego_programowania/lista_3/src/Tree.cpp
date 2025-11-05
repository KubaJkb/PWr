#include "Tree.h"
#include "constants.cpp"
#include <iostream>
#include <vector>
#include <set>
#include <map>
#include <cstdlib>
#include <queue>
using namespace std;

void Tree::printLevels() {
    if (root == nullptr) {
        cout << EMPTY_TREE << endl;
        return;
    }

    queue<Node*> nodesQueue;
    nodesQueue.push(root);
    
    int levelNr = 0;

    while (!nodesQueue.empty()) {
        
        int levelSize = nodesQueue.size();
        cout << ++levelNr << ": ";
        
        for (int i = 0; i < levelSize; i++) {
            Node* currentNode = nodesQueue.front();
            nodesQueue.pop();

            currentNode->printNodeValue();

            for (Node* child : currentNode->getChildren()) {
                nodesQueue.push(child);
            }
        }

        cout << endl;
    }
}

Tree::Tree() {
    root = nullptr;
}

Tree::Tree(const Tree& other) {
    if (other.root != nullptr) {
        root = other.root->cloneNode();
    }
    else {
        root = nullptr;
    }
}

Tree::~Tree() {
    delete root;
    root = nullptr;
}

void Tree::initTree(vector<string> args) {
    for (int i = 0; i < args.size(); i++) {
        bool isPushed = pushElement(args[i]);
        if (!isPushed) {
            cout << ERROR_SKIP << args[i] << endl;
        }
    }
    fillTree();
}

bool Tree::pushElement(string elem) {
    Node* newNode;
    short type = determineType(elem);

    if (type == TYPE_NUMBER) {
        if (elem[0] == '-') {
            elem.erase(0, 1);
            cout << SWAPPED_NUM[0] << elem << SWAPPED_NUM[1];
        }
        newNode = new Node(TYPE_NUMBER, TYPE_NUM_VAR_LIMIT, elem);

    } else if (type == TYPE_VARIABLE) {
        string strippedVar = stripAlphanumeric(elem);
        if (elem != strippedVar) {
            cout << SWAPPED_VAR[0] << elem << SWAPPED_VAR[1] << strippedVar << endl;
        }
        newNode = new Node(TYPE_VARIABLE, TYPE_NUM_VAR_LIMIT, strippedVar);
    
    } else if (type == TYPE_OPERATOR) {
        newNode = new Node(TYPE_OPERATOR, OPERATORS_CHILDREN_NUMBER[findPos(OPERATORS, OPERATORS_SIZE, elem)], elem);
    
    } else {
        return false;
    }

    if (root == nullptr) {
        root = newNode;
        return true;
    }
    else {
        return root->addChild(newNode);
    }
}

short Tree::determineType(string& elem) {
    for (int i = 0; i < OPERATORS_SIZE; i++) {
        if (elem == OPERATORS[i]) {
            return TYPE_OPERATOR;
        }
    }
    
    bool isNumber = true;
    for (int i = 0; i < elem.size(); i++) {
        char ch = elem[i];
        if (!isdigit(ch)) {
            if (!(i == 0 && ch == '-')) {
                isNumber = false;
            }
            if (isalpha(ch)) {
                return TYPE_VARIABLE;
            }
        }
    }
    if (isNumber) {
        return TYPE_NUMBER;
    }

    return -1;
}

string Tree::stripAlphanumeric(const string& elem) {
    string result;

    for (int i = 0; i < elem.size(); i++) {
        char c = elem[i];
        if (isalpha(c) || isdigit(c)) {
            result += c;
        }
    }

    return result;
}

int Tree::findPos(const string arr[], int size, const string& elem) {
    for (int i = 0; i < size; i++) {
        if (arr[i] == elem) {
            return i;
        }
    }
    return -1;
}

void Tree::fillTree() {
    bool isDefaultAdded = true;
    while (isDefaultAdded) {
        isDefaultAdded = pushElement(DEFAULT_VALUE);
        if (isDefaultAdded) {
            cout << ADDED_ONE;
        }
    }
}

void Tree::printTree() {
    if (root != nullptr) {
        root->printNode();
    }
    cout << "\n";
}

void Tree::printVars() {
    vector<string> vars = getVars();
    for (string var : vars) {
        cout << var << " ";
    }
    cout << endl;
}

vector<string> Tree::getVars() {
    vector<string> vars;

    if (root != nullptr) {
        return root->addVars(vars);
    }

    return vars;
}

void Tree::computeTree(vector<string> args) {
    if (root == nullptr) {
        cout << COMP_ERROR;
        return;
    }

    vector<string> vars = getVars();
    if (vars.size() != args.size()) {
        cout << ERROR_VARS_NUM;
        return;
    }

    map<string, double> dict;
    vector<string>::iterator it;

    for (int i = 0; i < args.size(); i++) {
        if (determineType(args[i]) == TYPE_NUMBER) {
            dict[vars[i]] = strToDouble(args[i]);
        } else {
            cout << ERROR_VARS_NOTNUM;
            return;
        }
    }

    cout << CONST_MESSAGE << root->computeNode(dict) << endl;
}

double Tree::strToDouble(const string& str) {
    return atof(str.c_str());
}

void Tree::joinTree(vector<string> args) {
    Tree secondTree = Tree();
    secondTree.initTree(args);
    *this = *this + secondTree;
}

void Tree::operator=(const Tree& other) {
    if (this != &other) {
        
        delete root;
        root = nullptr;
        
        if (other.root != nullptr) {
            root = other.root->cloneNode();
        }
        
    }
}

Tree Tree::operator+(const Tree& other) const {
    if (root == nullptr) {
        return other;
    }
    if (other.root == nullptr) {
        return *this;
    }

    Tree result;
    result.root = root->cloneNode();

    Node* leaf = result.root->getLeaf();
    Node* clonedOtherRoot = other.root->cloneNode();

    if (leaf->hasParent()) {
        leaf->replaceNode(clonedOtherRoot);
    } else {
        result.root = clonedOtherRoot;
    }

    delete leaf;

    return result;
}
