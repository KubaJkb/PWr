#include <string>
#include <vector>
#include <set>
#include <map>
using namespace std;

class Node {
public:
    Node();
    Node(short type, short childLimit, string value);
    ~Node();
    bool addChild(Node* newNode);
    void printNode();
    void printNodeValue();
    vector<string> addVars(vector<string> vars);
    double computeNode(map<string, double> dict);
    Node* cloneNode();
    Node* getLeaf();
    vector<Node*> getChildren();
    void replaceNode(Node* newNode);
    bool hasParent();

private:
    Node* parent;
    vector<Node*> children;
    short type; // 0 - num, 1 - var, 2 - op
    short childLimit;
    string value;
};
