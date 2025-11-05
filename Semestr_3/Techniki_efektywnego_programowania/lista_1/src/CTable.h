#pragma once
#include <string>
using namespace std;

class CTable {
private:
	string s_name;
	int* pi_table;
	int i_table_len;

public:
	CTable();
	CTable(string sName, int iTableLen);
	CTable(CTable& pcOther);

	~CTable();

	void vSetName(string sName);
	bool bSetNewSize(int iTableLen);
	CTable* pcClone();
	void vAddElement(int iNewElem);
	CTable* pcMul2();
};