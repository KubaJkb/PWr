#include "Error.h"

Error::Error(const string& errMsg) 
	: errorMessage(errMsg) {}

string Error::getMessage() const { 
	return errorMessage + "\n";
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
