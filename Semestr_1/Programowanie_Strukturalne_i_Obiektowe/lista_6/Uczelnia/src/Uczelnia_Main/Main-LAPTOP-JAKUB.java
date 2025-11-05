package Uczelnia_Main;

import Uczelnia.*;

public class Main {
    public static void main(String[] args) {
        //tworzenie obiektów klasy Osoba
        Uczelnia uczelnia = new Uczelnia();
        uczelnia.getOsoby().add(new Student("Mariusz", "Nowacki", "12345678901", 20, 'M', "280050", 2, false, true, true));
        uczelnia.getOsoby().add(new Student("Anna", "Kowalska", "23456789012", 25, 'K', "279950", 4, false, false, false));
        uczelnia.getOsoby().add(new Student("Barbara", "Kowalska", "23456789012", 28, 'K', "279950", 4, false, false, false));
        uczelnia.getOsoby().add(new Student("Agnieszka", "Kowalska", "23456789012", 30, 'K', "279950", 4, false, false, false));
        uczelnia.getOsoby().add(new PracownikAdministracyjny("Mariusz", "Pudzianowski", "34567890123", 40, 'M', 10, 9999, "Referent", 100));
        uczelnia.getOsoby().add(new PracownikBadawczoDydaktyczny("Krzysztof", "Stanowski", "45678901234", 50, 'M', 20, 12345, "Wykładowca", 47));
        uczelnia.getOsoby().add(new PracownikBadawczoDydaktyczny("Magda", "Gessler", "56789012345", 45, 'K', 15, 21435, "Profesor Zwyczajny", 47));

        //tworzenie obiektów klasy Kurs
        uczelnia.getKursy().add(new Kurs("Programowanie", "Krzysztof", "Stanowski", 6, uczelnia.getOsoby()));
        uczelnia.getKursy().add(new Kurs("Algebra", "Magda", "Gessler", 4, uczelnia.getOsoby()));

        //Przypisywanie kursów do studentów
        if (uczelnia.getOsoby().get(0) instanceof Student) {
            ((Student) uczelnia.getOsoby().get(0)).nowyKurs(uczelnia.getKursy().get(0));
            ((Student) uczelnia.getOsoby().get(0)).nowyKurs(uczelnia.getKursy().get(1));
        }
        if (uczelnia.getOsoby().get(1) instanceof Student) {
            ((Student) uczelnia.getOsoby().get(1)).nowyKurs(uczelnia.getKursy().get(1));
        }

        //Uruchomienie menu
        uczelnia.menu();

    }
}
















