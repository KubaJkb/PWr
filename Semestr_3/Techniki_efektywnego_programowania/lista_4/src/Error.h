#ifndef ERROR_H
#define ERROR_H
#include <string>
#include <vector>

using namespace std;

class Error {
public:
	Error(const string& errMsg);
	string getMessage() const;
	static vector<Error*> combineErrors(vector<Error*>& first, vector<Error*>& second);

private:
	string errorMessage;
};

#endif
