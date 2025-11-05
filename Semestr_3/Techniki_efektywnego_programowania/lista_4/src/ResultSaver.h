#ifndef RESULT_SAVER_H
#define RESULT_SAVER_H
#pragma once


#include "Tree.h"
#include "Error.h"
#include "Result.h"
#include <iostream>
#include <fstream>
#include <string>
using namespace std;

template <typename T>
class ResultSaver {
public:
	ResultSaver(Result<T, Error>& result);
	void save(const string& fileName);

private:
	Result<T, Error>& result;

};


template <typename T>
ResultSaver<T>::ResultSaver(Result<T, Error>& result) 
	: result(result) {}

template <typename T> 
void ResultSaver<T>::save(const string& fileName) {
    ofstream file(fileName, ios::app);

	if (!result.isSuccess()) {
		file << "Errors:\n";
		for (Error* err : result.getErrors()) {
			file << " - " << err->getMessage();
		}
	}

    file.close();

}

template <> 
void ResultSaver<Tree*>::save(const string& fileName) {
    ofstream file(fileName, ios::app);

	if (!result.isSuccess()) {
		file << "Errors:\n";
		for (Error* err : result.getErrors()) {
			file << " - " << err->getMessage();
		}
	}
	else {
		file <<"Tree in prefix form:\n" << result.getValue()->prefixTree();
	}

    file.close();

}

#endif
