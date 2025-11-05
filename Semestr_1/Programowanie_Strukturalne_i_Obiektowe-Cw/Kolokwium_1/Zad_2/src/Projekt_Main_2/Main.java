package Projekt_Main_2;

import Projekt_2.*;

public class Main {

    public static void main(String[] args) {
        int n = 5;
        Osoba[] osoby = new Osoba[n];

        osoby[0] = new Student("Michał", "Skalski", 19, 280001, "informatyka");
        osoby[1] = new Student("Maciej", "Kowal", 18, 280002, "zarzadzanie");
        osoby[2] = new Student("Antoni", "Dudek", 20, 280003, "informatyka");
        osoby[3] = new Wykladowca("Adam", "Dudak", 35, "informatyka", 12);
        osoby[4] = new Wykladowca("Bartłomiej", "Grzybowski", 40, "zarządzanie", 20);

        if (osoby[0] instanceof Student) {
            ((Student) osoby[0]).generujPrzedmiot("programowanie", 30, 5f);
            ((Student) osoby[0]).generujPrzedmiot("algebra", 28, 4f);
        }
        if (osoby[1] instanceof Student) {
            ((Student) osoby[1]).generujPrzedmiot("programowanie", 30, 5f);
            ((Student) osoby[1]).generujPrzedmiot("algebra", 28, 4f);
            ((Student) osoby[1]).generujPrzedmiot("analiza", 32, 3.5f);
        }
        if (osoby[2] instanceof Student) {
            ((Student) osoby[2]).generujPrzedmiot("programowanie", 30, 5f);
            ((Student) osoby[2]).generujPrzedmiot("algebra", 28, 4f);
            ((Student) osoby[2]).generujPrzedmiot("analiza", 32, 3.5f);
            ((Student) osoby[2]).generujPrzedmiot("fizyka", 14, 1.5f);
        }

        for (int i = 0; i < 5; i++) {
            osoby[i].getStan();
        }

        for (int i = 0; i < 5; i++) {
            System.out.print("Czy podana osoba spóźniła się na zajęcia: ");
            System.out.println(osoby[i].czySpozniony());
        }


    }
}