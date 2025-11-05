#include "Node.h"
#include "constants.cpp"
#include <iostream>
#include <vector>
#include <cmath>
using namespace std;

Node::Node() {
    this->type = DEFAULT_TYPE;
    this->childLimit = DEFAULT_CHILD_LIMIT;
    this->value = DEFAULT_VALUE;
}

Node::Node(short type, short childLimit, string value) {
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

void Node::printNode() {
    cout << value << " ";
    for (int i = 0; i < children.size(); i++) {
        children[i]->printNode();
    }
}

void Node::printNodeValue() {
    cout << value << " ";
}

vector<Node*> Node::getChildren() {
    return children;
}

vector<string> Node::addVars(vector<string> vars) {
    for (int i = 0; i < children.size(); ++i) {
        vars = children[i]->addVars(vars);
    }
    
    if (type == 1) {
        if (find(vars.begin(), vars.end(), value) == vars.end()) {
            vars.push_back(value);
        }
    }

    return vars;
}

double Node::computeNode(map<string, double> dict) {
    if (type == TYPE_NUMBER) {
        return atof(value.c_str());
    
    } else if (type == TYPE_VARIABLE) {
        return dict[value];
    
    } else if (type == TYPE_OPERATOR) {
        if (value == OPERATORS[0]) { // "+"
            double result = 0;
            for (int i = 0; i < childLimit; i++) {
                result += children[i]->computeNode(dict);
            }
            return result;
        
        } else if (value == OPERATORS[1]) { // "-"
            return children[0]->computeNode(dict) - children[1]->computeNode(dict);
        
        } else if (value == OPERATORS[2]) { // "*"
            double result = 1;
            for (int i = 0; i < childLimit; i++) {
                result *= children[i]->computeNode(dict);
            }
            return result;
        
        } else if (value == OPERATORS[3]) { // "/"
            return children[0]->computeNode(dict) / children[1]->computeNode(dict);
        
        } else if (value == OPERATORS[4]) { // "sin"
            return sin(children[0]->computeNode(dict));
        
        } else if (value == OPERATORS[5]) { // "cos"
            return cos(children[0]->computeNode(dict));
        }
    }
    
    cout << "Blad!\n";
    return 0;
}

Node* Node::cloneNode() {
    Node* newNode = new Node(type, childLimit, value);
    
    /*vector<Node*> newChildren;
    for (int i = 0; i < children.size(); i++) {
        Node* child = children[i]->cloneNode();
        child->parent = newNode;
        
        newChildren.push_back(child);
    }
    newNode->children = newChildren;*/

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
    } else {
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