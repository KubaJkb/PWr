#ifndef TREE_H
#define TREE_H

#include <vector>
#include <cstdlib>
#include "Node.h"
#include "Error.h"
#include "Result.h"
using namespace std;

class Tree {
public:
    Tree();
    Tree(const Tree& other);        // Konstruktor kopiujacy
    Tree(Tree&& other) noexcept;    // Konstruktor przenoszacy
    ~Tree();
    Result<void, Error> initTree(vector<string> inputs);
    Result<void, Error> printTree();
    Result<void, Error> printVars();
    Result<void, Error> computeTree(vector<string> inputs);
    Result<void, Error> joinTree(vector<string> inputs);
    Result<void, Error> printLevels();
    Tree operator=(const Tree& other);      // Operator kopiujacy
    Tree operator=(Tree&& other) noexcept;  // Operator przenoszacy
    Tree operator+(const Tree& other) const;
    bool isEmpty();
    string prefixTree();

private:
    Node* root;
    Result<void, Error> pushElement(string element);
    short determineType(string& elem);
    string stripAlphanumeric(const string& input);
    vector<string> getVars();
    double strToDouble(const string& str);
};

#endif