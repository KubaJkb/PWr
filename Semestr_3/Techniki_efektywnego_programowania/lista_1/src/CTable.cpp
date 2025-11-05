#include "CTable.h"
#include <iostream>
#include <string>
#include <algorithm> 

using namespace std;


//void CTable::vSetName(string sName)

const string s_default_name = "default_name";
const int i_default_len = 10;

CTable::CTable() : s_name(s_default_name), i_table_len(i_default_len) {
	pi_table = new int[i_table_len];
	cout << "bezp: '" << s_name << "'" << endl;
}

CTable::CTable(string sName, int iTableLen) : s_name(sName), i_table_len(iTableLen) {
	pi_table = new int[i_table_len];
	cout << "parametr: '" << s_name << "'" << endl;
}

CTable::CTable(CTable& pcOther) : s_name(pcOther.s_name + "_copy"), i_table_len(pcOther.i_table_len) {
	pi_table = new int[i_table_len];
	for (int i = 0; i < i_table_len; i++) {
		pi_table[i] = pcOther.pi_table[i];
	}
	cout << "kopiuj: '" << s_name << "'" << endl;
}

CTable::~CTable() {
	cout << "usuwam: '" << s_name << "'" << endl;
	delete[] pi_table;
}

void CTable::vSetName(string sName) {
	s_name = sName;
}

bool CTable::bSetNewSize(int iTableLen) {
	if (iTableLen <= 0) {
		return false;
	}

	int* piNewTable = new int[iTableLen];
	for (int i = 0; i < min(i_table_len, iTableLen); i++) {
		piNewTable[i] = pi_table[i];
	}

	delete[] pi_table;
	pi_table = piNewTable;
	i_table_len = iTableLen;

	return true;
}

CTable* CTable::pcClone() {
	return new CTable(*this);
}

void CTable::vAddElement(int iNewElem) {
	int* piNewTable = new int[i_table_len + 1];

	for (int i = 0; i < i_table_len; i++) {
		piNewTable[i] = pi_table[i];
	}

	piNewTable[i_table_len] = iNewElem;

	delete[] pi_table;
	pi_table = piNewTable;
	i_table_len++;
}

CTable* CTable::pcMul2() {
	CTable* pcMul2 = pcClone();

	pcMul2->bSetNewSize(2 * i_table_len);

	for (int i = 0; i < i_table_len; i++) {
		pcMul2->pi_table[i + i_table_len] = pi_table[i];
	}

	return pcMul2;
}


//class CTable {
//private:
//	string s_name;
//	int* pi_table;
//	int i_table_len;
//
//	string const s_default_name = "default_name";
//	int const i_default_len = 10;
//
//public:
//	CTable() : s_name(s_default_name), i_table_len(i_default_len) {
//		pi_table = new int[i_table_len];
//		cout << "bezp: '" << s_name << "'" << endl;
//	}
//
//	CTable(string sName, int iTableLen) : s_name(sName), i_table_len(iTableLen) {
//		pi_table = new int[i_table_len];
//		cout << "parametr: '" << s_name << "'" << endl;
//	}
//
//	CTable(CTable& pcOther) : s_name(pcOther.s_name + "_copy"), i_table_len(pcOther.i_table_len) {
//		pi_table = new int[i_table_len];
//		for (int i = 0; i < i_table_len; i++) {
//			pi_table[i] = pcOther.pi_table[i];
//		}
//
//		cout << "kopiuj: '" << s_name << "'" << endl;
//	}
//
//	~CTable() {
//		cout << "usuwam: '" << s_name << "'" << endl;
//		delete[] pi_table;
//	}
//
//	void vAddElement(int iNewElem) {
//		int* piNewTable = new int[i_table_len + 1];
//
//		for (int i = 0; i < i_table_len; i++) {
//			piNewTable[i] = pi_table[i];
//		}
//
//		piNewTable[i_table_len] = iNewElem;
//
//		delete[] pi_table;
//
//		pi_table = piNewTable;
//
//		i_table_len++;
//	}
//
//	void vSetName(string sName) {
//		s_name = sName;
//	}
//
//	bool bSetNewSize(int iTableLen) {
//		if (iTableLen <= 0) {
//			return false;
//		}
//
//		int* piNewTable = new int[iTableLen];
//		for (int i = 0; i < min(i_table_len, iTableLen); i++) {
//			piNewTable[i] = pi_table[i];
//		}
//		delete[] pi_table;
//		pi_table = piNewTable;
//		i_table_len = iTableLen;
//		return true;
//	}
//
//	CTable* pcClone() {
//		return new CTable(*this);
//	}
//
//	CTable* pcMul2() {
//		CTable* pcMul2 = pcClone();
//
//		pcMul2->bSetNewSize(2 * i_table_len);
//
//		for (int i = 0; i < i_table_len; i++) {
//			pcMul2->pi_table[i + i_table_len] = pi_table[i];
//		}
//
//		return pcMul2;
//	}
//
//};