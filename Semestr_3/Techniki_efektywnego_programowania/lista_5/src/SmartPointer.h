#ifndef SMARTPOINTER_H
#define SMARTPOINTER_H

#include "RefCounter.h"
using namespace std;

template <typename T>
class SmartPointer
{
public:
    SmartPointer(T* thePointer) {
        pointer = thePointer;
        counter = new RefCounter();
        counter->add();

        pointerList = new vector<SmartPointer<T>*>();
        pointerList->push_back(this);
    }

    SmartPointer(const SmartPointer &other) {
        pointer = other.pointer;
        counter = other.counter;
        counter->add();

        pointerList = other.pointerList;
        pointerList->push_back(this);
    }

    SmartPointer& operator=(const SmartPointer& other) {
        if (this != &other) {
            if (counter->dec() == 0) {
                delete pointer;
                delete counter;
                delete pointerList;
            }
            else {
                removeFromPointerList();
            }

            pointer = other.pointer;
            counter = other.counter;
            counter->add();

            pointerList = other.pointerList;
            pointerList->push_back(this);
        }

        return *this;
    }

    ~SmartPointer() {
        if (counter->dec() == 0) {
            delete pointer;
            delete counter;
            delete pointerList;
        }
        else {
            removeFromPointerList();
        }
    }

    T& operator*() { return (*pointer); }
    T* operator->() { return (pointer); }

    void printPointerList() const {
        cout << "SmartPointers pointing to " << pointer << ": ";
        for (SmartPointer* sp : *pointerList) {
            cout << sp << " ";
        }
        cout << endl;
    }

private:
    RefCounter* counter;
    T* pointer;      
    vector<SmartPointer<T>*> *pointerList;

    void removeFromPointerList() {
        typename vector<SmartPointer*>::iterator it = find(pointerList->begin(), pointerList->end(), this);
        if (it != pointerList->end()) {
            pointerList->erase(it);
        }

        /*for (int i = 0; i < pointerList->size(); ++i) {
            if ((*pointerList)[i] == this) {
                pointerList->erase(pointerList->begin() + i);
                break;
            }
        }*/
    }

};

#endif
