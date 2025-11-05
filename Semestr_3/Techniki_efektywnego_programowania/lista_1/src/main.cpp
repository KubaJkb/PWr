#include <iostream>
#include "CTable.h"
using namespace std;

void v_alloc_table_fill_34(int iSize) {
	const int iFillValue = 34;

	if (iSize <= 0) {
		return;
	}

	int* piTable;
	piTable = new int[iSize];

	for (int i = 0; i < iSize; i++) {
		piTable[i] = iFillValue;
	}

	cout << "Zawartoœæ tablicy: ";
	for (int i = 0; i < iSize; i++) {
		cout << piTable[i] << " ";
	}

	delete[] piTable;
}

bool b_alloc_table_2_dim(int*** piTable, int iSizeX, int iSizeY) {
	if (iSizeX <= 0 || iSizeY <= 0) {
		return false;
	}

	*piTable = new int* [iSizeX];

	for (int i = 0; i < iSizeX; i++) {
		(*piTable)[i] = new int[iSizeY];
	}

	return true;
}

bool b_dealloc_table_2_dim(int** piTable, int iSizeX) {
	if (piTable == nullptr) {
		return false;
	}

	for (int i = 0; i < iSizeX; i++) {
		delete[] piTable[i];
	}

	delete[] piTable;

	return true;
}

void v_mod_tab(CTable* pcTab, int iNewSize) {
	pcTab->bSetNewSize(iNewSize);
}

void v_mod_tab(CTable cTab, int iNewSize) {
	cTab.bSetNewSize(iNewSize);
}

void v_zad_1() {
	int size;

	cout << "Podaj rozmiar tablicy: ";
	cin >> size;

	v_alloc_table_fill_34(size);
}

void v_zad_2_and_3() {
	int** pi2DimTable = nullptr;
	int iSizeX = 5;
	int iSizeY = 3;

	if (b_alloc_table_2_dim(&pi2DimTable, iSizeX, iSizeY)) {
		/*for (int i = 0; i < iSizeX; i++) {
			for (int j = 0; j < iSizeY; j++) {
				pi2DimTable[i][j] = i + j;
			}
		}

		for (int i = 0; i < iSizeX; i++) {
			for (int j = 0; j < iSizeY; j++) {
				cout << pi2DimTable[i][j] << " ";
			}
			cout << endl;
		}*/

		b_dealloc_table_2_dim(pi2DimTable, iSizeX);
	}
}

void v_zad_4() {
	CTable c_tab;
	CTable c_tab_param("test_table", 5);
	CTable c_tab_copy(c_tab_param);

	cout << "Modyfikacja przez wskaŸnik:" << endl;
	v_mod_tab(&c_tab_param, 10);

	cout << "Modyfikacja przez wartoœæ:" << endl;
	v_mod_tab(c_tab_copy, 20);

	CTable* pc_new_tab = c_tab_param.pcClone();

	delete pc_new_tab;
}

void v_mod() {
	CTable c_tab("first_table", 0);
	c_tab.vAddElement(1);
	c_tab.vAddElement(2);
	c_tab.vAddElement(3);

	CTable* pcMul2 = c_tab.pcMul2();
	delete pcMul2;
}

int main() {
	//v_zad_1();
	//v_zad_2_and_3();
	v_zad_4();
	//v_mod();


	return 0;
}