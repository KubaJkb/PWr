#pragma once
#include <vector>
#include <cstdlib>
#include "Node.h"
using namespace std;

class Tree {
public:
    Tree();
    Tree(const Tree& other);
    ~Tree();
    void initTree(vector<string> inputs);
    void printTree();
    void printVars();
    void computeTree(vector<string> inputs);
    void joinTree(vector<string> inputs);
    void printLevels();
    void operator=(const Tree& other);
    Tree operator+(const Tree& other) const;

private:
    Node* root;
    bool pushElement(string element);
    short determineType(string& elem);
    string stripAlphanumeric(const string& input);
    int findPos(const string arr[], int size, const string& value);
    void fillTree();
    vector<string> getVars();
    double strToDouble(const string& str);
};
