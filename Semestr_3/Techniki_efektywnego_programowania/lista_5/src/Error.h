#ifndef ERROR_H
#define ERROR_H
#include <string>
#include <vector>

using namespace std;

class Error {
public:
    enum class ErrorType {
        UNKNOWN,
        NEGATIVE_NUMBER,
        INVALID_EXPRESSION,
        UNRECOGNIZED_ELEMENT,
        TREE_FULL,
        TREE_EMPTY,
        VARS_EMPTY,
        INVALID_ARGUMENT_COUNT,
        ARGUMENT_NOT_NUMBER,
        INCORRECT_DIVISION,
        OTHER
    };

	Error(ErrorType type, const string& errMsg);
	string getMessage() const;
    ErrorType getType() const;
	static vector<Error*> combineErrors(vector<Error*>& first, vector<Error*>& second);

private:
	string errorMessage;
    ErrorType errorType;

};

#endif
