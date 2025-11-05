#include "Tree.h"
#include "constants.cpp"
#include <iostream>
#include <vector>
#include <set>
#include <map>
#include <cstdlib>
#include <queue>
using namespace std;

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

Result<void, Error> Tree::initTree(vector<string> args) {
    delete root;
    root = nullptr;
    
    for (int i = 0; i < args.size(); i++) {
        Result<void, Error> pushRes = pushElement(args[i]);
        if (!pushRes.isSuccess()) {
            // ***************************** ERROR ********************************
            delete root;
            root = nullptr;
            return Result<void, Error>::fail(pushRes.getErrors());
        }
    }
    if (pushElement(DEFAULT_VALUE).isSuccess()) {
        // ***************************** ERROR ********************************
        delete root;
        root = nullptr;
        return Result<void, Error>::fail(new Error("Za malo argumentow aby stworzyc drzewo"));
    }
    return Result<void, Error>::ok();
}

Result<void, Error> Tree::pushElement(string elem) {
    Node* newNode;
    short type = determineType(elem);

    if (type == TYPE_NUMBER) {
        if (elem[0] == NEGATION) {
            elem.erase(0, 1);
            // ***************************** ERROR ********************************
            return Result<void, Error>::fail(new Error("Liczby ujemne " + elem + " nie sa dozwolone"));
        }
        newNode = new Node(TYPE_NUMBER, TYPE_NUM_VAR_LIMIT, elem);

    }
    else if (type == TYPE_VARIABLE) {
        if (elem != stripAlphanumeric(elem)) {
            // ***************************** ERROR ********************************
            return Result<void, Error>::fail(new Error("W wyrazeniu " + elem + " znajduje sie niedozwolona wartosc"));
        }
        newNode = new Node(TYPE_VARIABLE, TYPE_NUM_VAR_LIMIT, stripAlphanumeric(elem));

    }
    else if (type == TYPE_OPERATOR) {
        newNode = new Node(TYPE_OPERATOR, OPERATORS_MAP.at(elem), elem);

    }
    else {
        // ***************************** ERROR ********************************
        return Result<void, Error>::fail(new Error("Nierozpoznany typ elementu: " + elem));
    }

    if (root == nullptr) {
        root = newNode;
        return Result<void, Error>::ok();
    }
    else {
        if (!root->addChild(newNode)) {
            // ***************************** ERROR ********************************
            return Result<void, Error>::fail(new Error("Nie bylo miejsca w drzewie na dodanie wezla " + elem));
        }

        return Result<void, Error>::ok();
    }
}

short Tree::determineType(string& elem) {
    if (OPERATORS_MAP.count(elem) > 0) {
        return TYPE_OPERATOR;
    }

    bool isNumber = true;
    for (int i = 0; i < elem.size(); i++) {
        char ch = elem[i];
        if (!isdigit(ch)) {
            if (!(i == 0 && ch == NEGATION)) {
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

Result<void, Error> Tree::printTree() {
    if (root == nullptr) {
        return Result<void, Error>::fail(new Error("Nie mozna wydrukowac pustego drzewa"));
    }
    root->printNodeIter();
    cout << "\n";
    return Result<void, Error>::ok();
}

Result<void, Error> Tree::printVars() {
    vector<string> vars = getVars();
    if (vars.empty()) {
        return Result<void, Error>::fail(new Error("W drzewie nie ma zadnych zmiennych"));
    }
    
    for (string var : vars) {
        cout << var << " ";
    }
    cout << endl;
    return Result<void, Error>::ok();
}

vector<string> Tree::getVars() {
    vector<string> vars;

    if (root != nullptr) {
        return root->addVarsIter(vars);
    }

    return vars;
}

Result<void, Error> Tree::computeTree(vector<string> args) {
    if (root == nullptr) {
        // ***************************** ERROR ********************************
        return Result<void, Error>::fail(new Error("Nie mozna obliczyc wartosci pustego drzewa"));
    }

    vector<string> vars = getVars();
    if (vars.size() != args.size()) {
        // ***************************** ERROR ********************************
        return Result<void, Error>::fail(new Error("Liczba podanych argumentow jest nieprawidlowa"));
    }

    map<string, double> dict;
    vector<string>::iterator it;

    for (int i = 0; i < args.size(); i++) {
        if (determineType(args[i]) == TYPE_NUMBER) {
            dict[vars[i]] = strToDouble(args[i]);
        }
        else {
            // ***************************** ERROR ********************************
            return Result<void, Error>::fail(new Error("Podany argument " + args[i] + " nie jest liczba"));
        }
    }
    
    Result<double, Error> res = root->computeNode(dict);
    if (!res.isSuccess()) {
        return Result<void, Error>::fail(res.getErrors());
    }

    cout << res.getValue() << endl;
    return Result<void, Error>::ok();
}

double Tree::strToDouble(const string& str) {
    return atof(str.c_str());
}

Result<void, Error> Tree::joinTree(vector<string> args) {
    Tree secondTree = Tree();
    
    Result<void, Error> secTreeInit = secondTree.initTree(args);
    
    if (!secTreeInit.isSuccess()) {
        return Result<void, Error>::fail(secTreeInit.getErrors());
    }
    
    *this = *this + secondTree;
    return Result<void, Error>::ok();
}

Result<void, Error> Tree::printLevels() {
    if (root == nullptr) {
        // ***************************** ERROR ********************************
        return Result<void, Error>::fail(new Error("Drzewo jest puste."));
    }

    queue<Node*> nodesQueue;
    nodesQueue.push(root);

    int levelNr = 0;

    while (!nodesQueue.empty()) {

        size_t levelSize = nodesQueue.size();
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

    return Result<void, Error>::ok();
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
    }
    else {
        result.root = clonedOtherRoot;
    }

    delete leaf;

    return result;
}

bool Tree::isEmpty() {
    return root == nullptr;
}

string Tree::prefixTree() {
    return root->getValIter() + "\n";
}