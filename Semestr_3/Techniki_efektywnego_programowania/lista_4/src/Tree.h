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
    Tree(const Tree& other);
    ~Tree();
    Result<void, Error> initTree(vector<string> inputs);
    Result<void, Error> printTree();
    Result<void, Error> printVars();
    Result<void, Error> computeTree(vector<string> inputs);
    Result<void, Error> joinTree(vector<string> inputs);
    Result<void, Error> printLevels();
    void operator=(const Tree& other);
    Tree operator+(const Tree& other) const;
    bool isEmpty();
    string prefixTree();

private:
    Node* root;
    Result<void, Error> pushElement(string element);
    short determineType(string& elem);
    string stripAlphanumeric(const string& input);
    //int findPos(const string arr[], int size, const string& value);
    //void fillTree();
    vector<string> getVars();
    double strToDouble(const string& str);
};

#endif