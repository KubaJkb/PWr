package DataProcessing;

import GUI.*;
import Strategia.*;
import Uczelnia.*;
import Obserwator.*;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Scanner;

public class Uczelnia {
    private ArrayList<Osoba> osoby;
    private ArrayList<Kurs> kursy;

    private ArrayList<Obserwator> obserwatorzy;
    private StrategiaZapisNaKurs strategia;

    UczelniaGUI uczelniaGUI;


    public void dodajObserwatora(Obserwator obserwator) {
        obserwatorzy.add(obserwator);
    }

    public void usunObserwatora(Obserwator obserwator) {
        obserwatorzy.remove(obserwator);
    }

    public void powiadomObserwatorow(String komunikat, JPanel panel) {
        for (Obserwator obserwator : obserwatorzy) {
            obserwator.powiadom(komunikat, panel);
        }
    }

    public Uczelnia() {
        osoby = new ArrayList<>();
        kursy = new ArrayList<>();
        obserwatorzy = new ArrayList<>();
        dodajObserwatora(new LogObserwator());
    }

    public void menu() {
        uczelniaGUI = new UczelniaGUI(this);
    }

    public void wyszukaj() {
        WyszukajGUI wyszukajGUI = new WyszukajGUI(uczelniaGUI);
    }

    public void wyswietl() {
        WyswietlGUI wyswietlGUI = new WyswietlGUI(uczelniaGUI);
    }

    public void utworzObiekt() {
        UtworzObiektGUI utworzObiektGUI = new UtworzObiektGUI(uczelniaGUI);
    }

    public void usunElement() {
        UsunElementGUI usunElementGUI = new UsunElementGUI(uczelniaGUI);
    }

//    public void przypiszKurs() {
//        PrzypiszKursGUI przypiszKursGUI = new PrzypiszKursGUI(uczelniaGUI);
//    }
//        Scanner skaner = new Scanner(System.in);
//
//        System.out.println("\nWybierz studenta: ");
//        for (int i = 0; i < osoby.size(); i++) {
//            if (osoby.get(i) instanceof Student) {
//                System.out.println((i + 1) + " :  " + osoby.get(i).getImie() + " " + osoby.get(i).getNazwisko() + "   " + osoby.get(i).getPesel());
//            }
//        }
//        System.out.print("Wybór opcji: ");
//        int s = skaner.nextInt();
//        skaner.nextLine();
//
//
//        if (s <= 0 || s > osoby.size() || !(osoby.get(s - 1) instanceof Student)) {
//            System.out.println("Podałeś niepoprawną wartość!");
//            return;
//        } else if (((Student) osoby.get(s - 1)).getKurs().size() == kursy.size()) {
//            System.out.println("Student jest już zapisany na wszystkie możliwe kursy!");
//            return;
//        }
//
//        System.out.println("\nWybierz co chcesz zrobić: ");
//        System.out.print("""
//                1 :  Dodanie wybranego kursu
//                2 :  Zapis na wszystkie kursy wg wybranej strategii
//                """);
//        System.out.print("Wybór opcji: ");
//        String wybor = skaner.nextLine();
//
//        switch (wybor) {
//            case "1":
//                System.out.println("\nPodaj kurs który chcesz przypisać: ");
//                for (int i = 0; i < kursy.size(); i++) {
//                    boolean czyStudentJuzZapisany = false;
//                    for (Kurs krs : ((Student) osoby.get(s - 1)).getKurs()) {
//                        if (krs.equals(kursy.get(i))) {
//                            czyStudentJuzZapisany = true;
//                            break;
//                        }
//                    }
//
//                    if (!czyStudentJuzZapisany) {
//                        System.out.println((i + 1) + " :  " + kursy.get(i).getNazwaKursu() + "   ECTS: " + kursy.get(i).getPunktyECTS());
//                    }
//                }
//                int k = skaner.nextInt();
//                skaner.nextLine();
//
//                dodajKurs(s - 1, k - 1);
//
//                break;
//            case "2":
//                System.out.println("\nPodaj strategię wg której ma się odbyć zapis na kursy: ");
//                System.out.print("""
//                        1 :  Zapis po nazwie kursu alfabetycznie
//                        2 :  Zapis po punktach ECTS malejąco
//                        3 :  Zapis po liczbie publikacji prowadzącego malejąco
//                        """);
//                wybor = skaner.nextLine();
//
//                switch (wybor) {
//                    case "1":
//                        setStrategia(new ZapisAlfabetycznie());
//                        break;
//                    case "2":
//                        setStrategia(new ZapisPunktyECTS());
//                        break;
//                    case "3":
//                        setStrategia(new ZapisProwadzacy());
//                        break;
//                    default:
//                        System.out.println("Podałeś niepoprawną wartość!");
//                        return;
//                }
//
//                osoby.set(s - 1, strategia.zapiszNaKursy(this, s - 1));
//
//                break;
//            default:
//                System.out.println("Podałeś niepoprawną wartość!");
//        }
//
//    }
//
//public void dodajKurs(int nrStudenta, int nrKursu) {
//        boolean czyStudentJuzZapisany = false;
//        for (Kurs krs : ((Student) osoby.get(nrStudenta)).getKurs()) {
//            if (krs.equals(kursy.get(nrKursu))) {
//                czyStudentJuzZapisany = true;
//                break;
//            }
//        }
//
//        if (czyStudentJuzZapisany) {
//            System.out.println("Nie można przypisać studentowi tego kursu, ponieważ już jest na niego zapisany!");
//        } else {
//            ((Student) osoby.get(nrStudenta)).nowyKurs(kursy.get(nrKursu));
////            powiadomObserwatorow("Przypisano kurs " + kursy.get(nrKursu).getNazwaKursu() + " do studenta " + osoby.get(nrStudenta).getImie() + " " + osoby.get(nrStudenta).getNazwisko());
//        }
//    }

    public void sortuj() {
        SortujGUI sortujGUI = new SortujGUI(uczelniaGUI);
    }

    public void usunDuplikaty() {
        UsunDuplikatyGUI usunDuplikatyGUI = new UsunDuplikatyGUI(uczelniaGUI);
    }

    public void zapiszodczytaj() {
        ZapisOdczytGUI zapisOdczytGUI = new ZapisOdczytGUI(uczelniaGUI);
    }








//        Scanner skaner = new Scanner(System.in);
//
//        System.out.println("\nWybierz typ elementu który chcesz usunąć: ");
//        System.out.print("""
//                1 :  Pracownik
//                2 :  Student
//                3 :  Kurs
//                """);
//        System.out.print("Wybór opcji: ");
//        String wybor = skaner.nextLine();
//
//        ArrayList<Osoba> znalezioneOsoby;
//        ArrayList<Kurs> znalezioneKursy;
//        switch (wybor) {
//            case "1":
//                znalezioneOsoby = wyszukajPracownik();
//                if (znalezioneOsoby.isEmpty()) {
//                    System.out.println("Nie znaleziono takiego Pracownika.");
//                }
//                for (Osoba osoba : znalezioneOsoby) {
////                    powiadomObserwatorow("Usunięto Pracownika: " + osoba.getImie() + " " + osoba.getNazwisko());
//                    osoby.remove(osoba);
//                }
//                break;
//            case "2":
//                znalezioneOsoby = wyszukajStudent();
//                if (znalezioneOsoby.isEmpty()) {
//                    System.out.println("Nie znaleziono takiego Studenta.");
//                }
//                for (Osoba osoba : znalezioneOsoby) {
////                    powiadomObserwatorow("Usunięto Studenta: " + osoba.getImie() + " " + osoba.getNazwisko());
//                    osoby.remove(osoba);
//                }
//                break;
//            case "3":
//                znalezioneKursy = wyszukajKurs();
//                if (znalezioneKursy.isEmpty()) {
//                    System.out.println("Nie znaleziono takiego Kursu.");
//                }
//                for (Kurs kurs : znalezioneKursy) {
////                    powiadomObserwatorow("Usunięto Kurs: " + kurs.getNazwaKursu());
//                    kursy.remove(kurs);
//                }
//                break;
//            default:
//                System.out.println("Podałeś niepoprawną wartość!");
//        }
//    }


    public ArrayList<Osoba> getOsoby() {
        return osoby;
    }

    public ArrayList<Osoba> getPracownicy() {
        ArrayList<Osoba> result = new ArrayList<>();
        for (Osoba osoba : osoby) {
            if (osoba instanceof PracownikUczelni) {
                result.add(osoba);
            }
        }
        return result;
    }

    public ArrayList<Osoba> getStudenci() {
        ArrayList<Osoba> result = new ArrayList<>();
        for (Osoba osoba : osoby) {
            if (osoba instanceof Student) {
                result.add(osoba);
            }
        }
        return result;
    }

    public void addOsoba(Osoba osoba) {
        osoby.add(osoba);
    }

    public void removeOsoba(Osoba osoba) {
        osoby.remove(osoba);
    }

    public void addKurs(Kurs kurs) {
        kursy.add(kurs);
    }

    public void removeKurs(Kurs kurs) {
        kursy.remove(kurs);
    }

    public ArrayList<Kurs> getKursy() {
        return kursy;
    }

    public void setOsoby(ArrayList<Osoba> osoby) {
        this.osoby = osoby;
    }

    public void setKursy(ArrayList<Kurs> kursy) {
        this.kursy = kursy;
    }

    public ArrayList<Obserwator> getObserwatorzy() {
        return obserwatorzy;
    }

    public void setObserwatorzy(ArrayList<Obserwator> obserwatorzy) {
        this.obserwatorzy = obserwatorzy;
    }

    public StrategiaZapisNaKurs getStrategia() {
        return strategia;
    }

    public void setStrategia(StrategiaZapisNaKurs strategia) {
        this.strategia = strategia;
    }
}
