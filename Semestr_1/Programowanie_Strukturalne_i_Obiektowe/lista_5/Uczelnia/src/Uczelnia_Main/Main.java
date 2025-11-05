package Uczelnia_Main;

import Uczelnia.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Osoba> osoby = new ArrayList<>();
        ArrayList<Kurs> kursy = new ArrayList<>();

        //tworzenie obiektów klasy Osoba
        osoby.add(new Student("Mariusz", "Nowacki", "12345678901", 21, 'M', "280050", 2, false, true, true));
        osoby.add(new Student("Anna", "Kowalska", "23456789012", 24, 'K', "279950", 4, false, false, false));
        osoby.add(new PracownikAdministracyjny("Mariusz", "Pudzianowski", "34567890123", 40, 'M', 10, 9999, "Referent", 100));
        osoby.add(new PracownikBadawczoDydaktyczny("Krzysztof", "Stanowski", "45678901234", 50, 'M', 20, 12345, "Wykładowca", 47));
        osoby.add(new PracownikBadawczoDydaktyczny("Magda", "Gessler", "56789012345", 45, 'K', 15, 21435, "Profesor Zwyczajny", 47));

        //tworzenie obiektów klasy Kurs
        kursy.add(new Kurs("Programowanie", "Krzysztof", "Stanowski", 6, osoby));
        kursy.add(new Kurs("Algebra", "Magda", "Gessler", 4, osoby));

        //Przypisywanie kursów do studentów
        if (osoby.get(0) instanceof Student) {
            ((Student) osoby.get(0)).nowyKurs(kursy.get(0));
            ((Student) osoby.get(0)).nowyKurs(kursy.get(1));
        }
        if (osoby.get(1) instanceof Student) {
            ((Student) osoby.get(1)).nowyKurs(kursy.get(1));
        }

        //Wyszukiwanie osoby na podstawie wpisanych do konsoli kryteriów
        wyszukaj(osoby, kursy);
        
    }

    static void wyszukaj(ArrayList<Osoba> osoby, ArrayList<Kurs> kursy) {
        Scanner skaner = new Scanner(System.in);
        System.out.print("\nPodaj rodzaj wyszukiwania: ");
        String rodzaj = skaner.nextLine();
        System.out.print("Podaj składową po której chcesz znaleźć obiekt: ");
        String skladowa = skaner.nextLine();
        System.out.print("Podaj wartość składowej po któej chcesz wyszukać obiekt: ");
        String wartosc = skaner.nextLine();

        switch (rodzaj) {
            case "Pracownik":
                wyszukajPracownika(osoby, wartosc, skladowa);
                break;
            case "Student":
                wyszukajStudenta(osoby, wartosc, skladowa);
                break;
            case "Kurs":
                wyszukajKurs(kursy, wartosc, skladowa);
                break;
            default:
                throw new IllegalArgumentException("Nieobsługiwany rodzaj wyszukiwania: " + rodzaj);
        }
    }
    static void wyszukajPracownika(ArrayList<Osoba> osoby, String wartosc, String skladowa) {
        for (Osoba osoba : osoby) {
            if (osoba instanceof PracownikUczelni pracownik) {
                switch (skladowa) {
                    case "nazwisko":
                        if (pracownik.getNazwisko().equals(wartosc)) {
                            pracownik.getStan();
                        }
                        break;
                    case "imie":
                        if (pracownik.getImie().equals(wartosc)) {
                            pracownik.getStan();
                        }
                        break;
                    case "stanowisko":
                        if (pracownik.getStanowisko().equals(wartosc)) {
                            pracownik.getStan();
                        }
                        break;
                    case "stazPracy":
                        if (pracownik.getStazPracy() == Integer.parseInt(wartosc)) {
                            pracownik.getStan();
                        }
                        break;
                    case "pensja":
                        if (pracownik.getPensja() == Double.parseDouble(wartosc)) {
                            pracownik.getStan();
                        }
                        break;
                    case "liczbaNadgodzin":
                        if (pracownik instanceof PracownikAdministracyjny) {
                            if (((PracownikAdministracyjny) pracownik).getLiczbaNadgodzin() == Integer.parseInt(wartosc)) {
                                pracownik.getStan();
                            }
                        }
                        break;
                    case "liczbaPublikacji":
                        if (pracownik instanceof PracownikBadawczoDydaktyczny) {
                            if (((PracownikBadawczoDydaktyczny) pracownik).getLiczbaPublikacji() == Integer.parseInt(wartosc)) {
                                pracownik.getStan();
                            }
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("Nieobsługiwana składowa: " + skladowa);
                }
            }
        }
    }
    static void wyszukajStudenta(ArrayList<Osoba> osoby, String wartosc, String skladowa) {
        for (Osoba osoba : osoby) {
            if (osoba instanceof Student student) {
                switch (skladowa) {
                    case "nazwisko":
                        if (student.getNazwisko().equals(wartosc)) {
                            student.getStan();
                        }
                        break;
                    case "imie":
                        if (student.getImie().equals(wartosc)) {
                            student.getStan();
                        }
                        break;
                    case "nrIndeksu":
                        if (student.getNrIndeksu().equals(wartosc)) {
                            student.getStan();
                        }
                        break;
                    case "rokStudiow":
                        if (student.getRokStudiow() == Integer.parseInt(wartosc)) {
                            student.getStan();
                        }
                        break;
                    case "nazwaKursu":
                        for (Kurs kurs : student.getKurs()) {
                            if (kurs.getNazwaKursu().equals(wartosc)) {
                                student.getStan();
                            }
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("Nieobsługiwana składowa: " + skladowa);
                }
            }
        }
    }
    static void wyszukajKurs(ArrayList<Kurs> kursy, String wartosc, String skladowa) {
        for (Kurs kurs : kursy) {
            switch (skladowa) {
                case "nazwaKursu":
                    if (kurs.getNazwaKursu().equals(wartosc)) {
                        kurs.getStan();
                    }
                    break;
                case "prowadzacy":
                    if ((kurs.getImieProwadzacego() + " " + kurs.getNazwiskoProwadzacego()).equals(wartosc)) {
                        kurs.getStan();
                    }
                    break;
                case "punktyECTS":
                    if (kurs.getPunktyECTS() == Integer.parseInt(wartosc)) {
                        kurs.getStan();
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Nieobsługiwana składowa: " + skladowa);
            }
        }
    }

    static void wyswietl(ArrayList<Osoba> osoby, ArrayList<Kurs> kursy) {
        Scanner myObj = new Scanner(System.in);
        System.out.print("\nPodaj co chcesz wyświetlić: ");
        String rodzaj = myObj.nextLine();

        switch (rodzaj) {
            case "Pracownik":
                for (Osoba osoba : osoby){
                    if (osoba instanceof PracownikUczelni){
                        osoba.getStan();
                    }
                }
                break;
            case "Student":
                for (Osoba osoba : osoby){
                    if (osoba instanceof Student){
                        osoba.getStan();
                    }
                }
                break;
            case "Kurs":
                for (Kurs kurs : kursy){
                    kurs.getStan();
                }
                break;
            default:
                throw new IllegalArgumentException("Nieobsługiwany typ danych: " + rodzaj);
        }
    }
}