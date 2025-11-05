#include "Error.h"

Error::Error(ErrorType type, const string& errMsg)
    : errorMessage(errMsg), errorType(type) {}

string Error::getMessage() const {
    return errorMessage + "\n";
}

Error::ErrorType Error::getType() const {
    return errorType;
}

vector<Error*> Error::combineErrors(vector<Error*>& first, vector<Error*>& second) {
    vector<Error*> combined;

    for (Error* error : first) {
        combined.push_back(error);
    }
    for (Error* error : second) {
        combined.push_back(error);
    }
    return combined;
}
