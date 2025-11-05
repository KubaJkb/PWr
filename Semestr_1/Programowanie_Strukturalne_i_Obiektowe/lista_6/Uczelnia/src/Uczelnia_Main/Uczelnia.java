package Uczelnia_Main;

import Strategia.StrategiaZapisNaKurs;
import Strategia.ZapisAlfabetycznie;
import Strategia.ZapisProwadzacy;
import Strategia.ZapisPunktyECTS;
import Uczelnia.*;
import Obserwator.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Uczelnia {
    private ArrayList<Osoba> osoby;
    private ArrayList<Kurs> kursy;

    private ArrayList<Obserwator> obserwatorzy = new ArrayList<>();
    private StrategiaZapisNaKurs strategia;


    public void dodajObserwatora(Obserwator obserwator) {
        obserwatorzy.add(obserwator);
    }

    public void usunObserwatora(Obserwator obserwator) {
        obserwatorzy.remove(obserwator);
    }

    public void powiadomObserwatorow(String komunikat) {
        for (Obserwator obserwator : obserwatorzy) {
            obserwator.powiadom(komunikat);
        }
    }

    public Uczelnia() {
        osoby = new ArrayList<>();
        kursy = new ArrayList<>();
    }

    void menu() {
        dodajObserwatora(new LogObserwator());
        Scanner skaner = new Scanner(System.in);

        while (true) {
            System.out.println("\nWybierz co chcesz zrobić: ");
            System.out.print("""
                    1 :  Wyszukaj obiekt
                    2 :  Wyświetl wszystkie obiekty klasy
                    3 :  Utwórz nowy obiekt
                    4 :  Przypisz kurs do studenta
                    5 :  Zapisz dane do pliku
                    6 :  Odczytaj dane z pliku
                    7 :  Posortuj listę
                    8 :  Usuń obiekt
                    0 :  Zakończ
                    """);
            System.out.print("Wybór opcji: ");
            String wybor = skaner.nextLine();

            switch (wybor) {
                case "0":
                    return;
                case "1":
                    wyszukaj();
                    break;
                case "2":
                    wyswietl();
                    break;
                case "3":
                    utworzObiekt();
                    break;
                case "4":
                    przypiszKurs();
                    break;
                case "5":
                    zapisz();
                    break;
                case "6":
                    odczytaj();
                    break;
                case "7":
                    sortuj();
                    break;
                case "8":
                    usun();
                    break;
                default:
                    System.out.println("Podałeś niepoprawną wartość!");
            }
        }
    }

    void wyszukaj() {
        Scanner skaner = new Scanner(System.in);

        System.out.println("\nPodaj rodzaj wyszukiwania: ");
        System.out.print("""
                1 :  Pracownik
                2 :  Student
                3 :  Kurs
                """);
        System.out.print("Wybór opcji: ");
        String wybor = skaner.nextLine();

        ArrayList<Osoba> znalezioneOsoby;
        ArrayList<Kurs> znalezioneKursy;
        switch (wybor) {
            case "1":
                znalezioneOsoby = wyszukajPracownik();
                if (znalezioneOsoby.isEmpty()) {
                    powiadomObserwatorow("Nie znaleziono takiego Pracownika.");
                }
                for (Osoba osoba : znalezioneOsoby) {
                    osoba.getStan();
                }
                break;
            case "2":
                znalezioneOsoby = wyszukajStudent();
                if (znalezioneOsoby.isEmpty()) {
                    powiadomObserwatorow("Nie znaleziono takiego Studenta.");
                }
                for (Osoba osoba : znalezioneOsoby) {
                    osoba.getStan();
                }
                break;
            case "3":
                znalezioneKursy = wyszukajKurs();
                if (znalezioneKursy.isEmpty()) {
                    System.out.println("Nie znaleziono takiego Kursu.");
                }
                for (Kurs kurs : znalezioneKursy) {
                    kurs.getStan();
                }
                break;
            default:
                System.out.println("Podałeś niepoprawną wartość!");
        }
    }

    ArrayList<Osoba> wyszukajPracownik() {
        Scanner skaner = new Scanner(System.in);
        ArrayList<Osoba> listaOsoby = new ArrayList<>();

        System.out.println("\nWybierz po jakiej składowej chcesz wyszukać pracownika: ");
        System.out.print("""
                1 :  Nazwisko
                2 :  Imię
                3 :  Stanowisko
                4 :  Staż pracy
                5 :  Pensja
                6 :  Liczba nadgodzin
                7 :  Liczba publikacji
                """);
        System.out.print("Wybór opcji: ");
        String wybor = skaner.nextLine();

        switch (wybor) {
            case "1":
                System.out.print("Podaj nazwisko: ");
                break;
            case "2":
                System.out.print("Podaj imię: ");
                break;
            case "3":
                System.out.print("Podaj stanowisko: ");
                break;
            case "4":
                System.out.print("Podaj staż pracy (w latach): ");
                break;
            case "5":
                System.out.print("Podaj pensję (z dokładnością do dwóch cyfr po przecinku): ");
                break;
            case "6":
                System.out.print("Podaj liczbę nadgodzin: ");
                break;
            case "7":
                System.out.print("Podaj liczbę publikacji: ");
                break;
            default:
                System.out.println("Podałeś niepoprawną wartość!");
                return null;
        }
        String wartosc = skaner.nextLine();

        for (Osoba osoba : osoby) {
            if (osoba instanceof PracownikUczelni pracownik) {
                switch (wybor) {
                    case "1":
                        if (pracownik.getNazwisko().equals(wartosc)) {
                            listaOsoby.add(pracownik);
                        }
                        break;
                    case "2":
                        if (pracownik.getImie().equals(wartosc)) {
                            listaOsoby.add(pracownik);
                        }
                        break;
                    case "3":
                        if (pracownik.getStanowisko().equals(wartosc)) {
                            listaOsoby.add(pracownik);
                        }
                        break;
                    case "4":
                        if (pracownik.getStazPracy() == Integer.parseInt(wartosc)) {
                            listaOsoby.add(pracownik);
                        }
                        break;
                    case "5":
                        if (pracownik.getPensja() == Double.parseDouble(wartosc)) {
                            listaOsoby.add(pracownik);
                        }
                        break;
                    case "6":
                        if (pracownik instanceof PracownikAdministracyjny) {
                            if (((PracownikAdministracyjny) pracownik).getLiczbaNadgodzin() == Integer.parseInt(wartosc)) {
                                listaOsoby.add(pracownik);
                            }
                        }
                        break;
                    case "7":
                        if (pracownik instanceof PracownikBadawczoDydaktyczny) {
                            if (((PracownikBadawczoDydaktyczny) pracownik).getLiczbaPublikacji() == Integer.parseInt(wartosc)) {
                                listaOsoby.add(pracownik);
                            }
                        }
                        break;
                }
            }
        }
        return listaOsoby;
    }

    ArrayList<Osoba> wyszukajStudent() {
        Scanner skaner = new Scanner(System.in);
        ArrayList<Osoba> listaOsoby = new ArrayList<>();

        System.out.println("\nWybierz po jakiej składowej chcesz wyszukać studenta: ");
        System.out.print("""
                1 :  Nazwisko
                2 :  Imię
                3 :  Numer indeksu
                4 :  Rok Studiów
                5 :  Nazwa kursu
                """);
        System.out.print("Wybór opcji: ");
        String wybor = skaner.nextLine();

        switch (wybor) {
            case "1":
                System.out.print("Podaj nazwisko: ");
                break;
            case "2":
                System.out.print("Podaj imię: ");
                break;
            case "3":
                System.out.print("Podaj numer indeksu: ");
                break;
            case "4":
                System.out.print("Podaj rok studiów: ");
                break;
            case "5":
                System.out.print("Podaj nazwę kursu: ");
                break;
            default:
                System.out.println("Podałeś niepoprawną wartość!");
                return null;
        }
        String wartosc = skaner.nextLine();

        for (Osoba osoba : osoby) {
            if (osoba instanceof Student student) {
                switch (wybor) {
                    case "1":
                        if (student.getNazwisko().equals(wartosc)) {
                            listaOsoby.add(student);
                        }
                        break;
                    case "2":
                        if (student.getImie().equals(wartosc)) {
                            listaOsoby.add(student);
                        }
                        break;
                    case "3":
                        if (student.getNrIndeksu().equals(wartosc)) {
                            listaOsoby.add(student);
                        }
                        break;
                    case "4":
                        if (student.getRokStudiow() == Integer.parseInt(wartosc)) {
                            listaOsoby.add(student);
                        }
                        break;
                    case "5":
                        for (Kurs kurs : student.getKurs()) {
                            if (kurs.getNazwaKursu().equals(wartosc)) {
                                listaOsoby.add(student);
                            }
                        }
                        break;
                }
            }
        }
        return listaOsoby;
    }

    ArrayList<Kurs> wyszukajKurs() {
        Scanner skaner = new Scanner(System.in);
        ArrayList<Kurs> listaKursy = new ArrayList<>();

        System.out.println("\nWybierz po jakiej składowej chcesz wyszukać kurs: ");
        System.out.print("""
                1 :  Nazwa
                2 :  Prowadzący
                3 :  Punkty ECTS
                """);
        System.out.print("Wybór opcji: ");
        String wybor = skaner.nextLine();

        switch (wybor) {
            case "1":
                System.out.print("Podaj nazwę kursu: ");
                break;
            case "2":
                System.out.print("Podaj imię i nazwisko prowadzącego: ");
                break;
            case "3":
                System.out.print("Podaj punty ECTS: ");
                break;
            default:
                System.out.println("Podałeś niepoprawną wartość!");
                return null;
        }
        String wartosc = skaner.nextLine();

        for (Kurs kurs : kursy) {
            switch (wybor) {
                case "1":
                    if (kurs.getNazwaKursu().equals(wartosc)) {
                        listaKursy.add(kurs);
                    }
                    break;
                case "2":
                    if ((kurs.getImieProwadzacego() + " " + kurs.getNazwiskoProwadzacego()).equals(wartosc)) {
                        listaKursy.add(kurs);
                    }
                    break;
                case "3":
                    if (kurs.getPunktyECTS() == Integer.parseInt(wartosc)) {
                        listaKursy.add(kurs);
                    }
                    break;
            }
        }
        return listaKursy;
    }

    void wyswietl() {
        Scanner skaner = new Scanner(System.in);

        System.out.println("\nWybierz co chcesz wyświetlić: ");
        System.out.print("""
                1 :  Wszystkich Pracowników
                2 :  Wszystkich Studentów
                3 :  Wszystkie Kursy
                4 :  Wszystkie Osoby
                """);
        System.out.print("Wybór opcji: ");
        String wybor = skaner.nextLine();

        switch (wybor) {
            case "1":
                for (Osoba osoba : osoby) {
                    if (osoba instanceof PracownikUczelni) {
                        osoba.getStan();
                    }
                }
                break;
            case "2":
                for (Osoba osoba : osoby) {
                    if (osoba instanceof Student) {
                        osoba.getStan();
                    }
                }
                break;
            case "3":
                for (Kurs kurs : kursy) {
                    kurs.getStan();
                }
                break;
            case "4":
                for (Osoba osoba : osoby) {
                    osoba.getStan();
                }
                break;
            default:
                System.out.println("Podałeś niepoprawną wartość!");
        }
    }

    void utworzObiekt() {
        Scanner skaner = new Scanner(System.in);

        System.out.println("\nWybierz jakiej klasy obiekt chcesz utworzyć: ");
        System.out.print("""
                1 :  Pracownik Badawczo-Dydaktyczny
                2 :  Pracownik Administracyjny
                3 :  Student
                4 :  Kurs
                """);
        System.out.print("Wybór opcji: ");
        String wybor = skaner.nextLine();

        switch (wybor) {
            case "1":
                osoby.add(PracownikBadawczoDydaktyczny.KonsolaPracownikBadawczoDydaktyczny());
                powiadomObserwatorow("Dodano do listy \"osoby\" nowy obiekt z klasy " + osoby.getLast().getClass());
                break;
            case "2":
                osoby.add(PracownikAdministracyjny.KonsolaPracownikAdministracyjny());
                powiadomObserwatorow("Dodano do listy \"osoby\" nowy obiekt z klasy " + osoby.getLast().getClass());
                break;
            case "3":
                osoby.add(Student.KonsolaStudent(kursy));
                powiadomObserwatorow("Dodano do listy \"osoby\" nowy obiekt z klasy " + osoby.getLast().getClass());
                break;
            case "4":
                kursy.add(Kurs.KonsolaKurs(osoby));
                powiadomObserwatorow("Dodano do listy \"kursy\" nowy obiekt z klasy " + kursy.getLast().getClass());
                break;
            default:
                System.out.println("Podałeś niepoprawną wartość!");
        }
    }

    void przypiszKurs() {
        Scanner skaner = new Scanner(System.in);

        System.out.println("\nWybierz studenta: ");
        for (int i = 0; i < osoby.size(); i++) {
            if (osoby.get(i) instanceof Student) {
                System.out.println((i + 1) + " :  " + osoby.get(i).getImie() + " " + osoby.get(i).getNazwisko() + "   " + osoby.get(i).getPesel());
            }
        }
        System.out.print("Wybór opcji: ");
        int s = skaner.nextInt();
        skaner.nextLine();


        if (s <= 0 || s > osoby.size() || !(osoby.get(s - 1) instanceof Student)) {
            System.out.println("Podałeś niepoprawną wartość!");
            return;
        } else if (((Student) osoby.get(s - 1)).getKurs().size() == kursy.size()) {
            System.out.println("Student jest już zapisany na wszystkie możliwe kursy!");
            return;
        }

        System.out.println("\nWybierz co chcesz zrobić: ");
        System.out.print("""
                1 :  Dodanie wybranego kursu
                2 :  Zapis na wszystkie kursy wg wybranej strategii
                """);
        System.out.print("Wybór opcji: ");
        String wybor = skaner.nextLine();

        switch (wybor) {
            case "1":
                System.out.println("\nPodaj kurs który chcesz przypisać: ");
                for (int i = 0; i < kursy.size(); i++) {
                    boolean czyStudentJuzZapisany = false;
                    for (Kurs krs : ((Student) osoby.get(s - 1)).getKurs()) {
                        if (krs.equals(kursy.get(i))) {
                            czyStudentJuzZapisany = true;
                            break;
                        }
                    }

                    if (!czyStudentJuzZapisany) {
                        System.out.println((i + 1) + " :  " + kursy.get(i).getNazwaKursu() + "   ECTS: " + kursy.get(i).getPunktyECTS());
                    }
                }
                int k = skaner.nextInt();
                skaner.nextLine();

                dodajKurs(s - 1, k - 1);

                break;
            case "2":
                System.out.println("\nPodaj strategię wg której ma się odbyć zapis na kursy: ");
                System.out.print("""
                        1 :  Zapis po nazwie kursu alfabetycznie
                        2 :  Zapis po punktach ECTS malejąco
                        3 :  Zapis po liczbie publikacji prowadzącego malejąco
                        """);
                wybor = skaner.nextLine();

                switch (wybor) {
                    case "1":
                        setStrategia(new ZapisAlfabetycznie());
                        break;
                    case "2":
                        setStrategia(new ZapisPunktyECTS());
                        break;
                    case "3":
                        setStrategia(new ZapisProwadzacy());
                        break;
                    default:
                        System.out.println("Podałeś niepoprawną wartość!");
                        return;
                }

                osoby.set(s - 1, strategia.zapiszNaKursy(this, s - 1));

                break;
            default:
                System.out.println("Podałeś niepoprawną wartość!");
        }

    }

    void dodajKurs(int nrStudenta, int nrKursu) {
        boolean czyStudentJuzZapisany = false;
        for (Kurs krs : ((Student) osoby.get(nrStudenta)).getKurs()) {
            if (krs.equals(kursy.get(nrKursu))) {
                czyStudentJuzZapisany = true;
                break;
            }
        }

        if (czyStudentJuzZapisany) {
            System.out.println("Nie można przypisać studentowi tego kursu, ponieważ już jest na niego zapisany!");
        } else {
            ((Student) osoby.get(nrStudenta)).nowyKurs(kursy.get(nrKursu));
            powiadomObserwatorow("Przypisano kurs " + kursy.get(nrKursu).getNazwaKursu() + " do studenta " + osoby.get(nrStudenta).getImie() + " " + osoby.get(nrStudenta).getNazwisko());
        }
    }

    //strumienie są zamykane automatycznie w trakcie korzystania z bloku try-with-resources
    void zapisz() {
        Scanner skaner = new Scanner(System.in);

        System.out.println("\nWybierz którą listę chcesz zapisać: ");
        System.out.print("""
                1 :  osoby
                2 :  kursy
                """);
        System.out.print("Wybór opcji: ");
        String wybor = skaner.nextLine();

        switch (wybor) {
            case "1":
                try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("osoby.ser"))) {
                    os.writeObject(osoby);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Zapisano listę osób do pliku: osoby.ser");
                break;
            case "2":
                try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("kursy.ser"))) {
                    os.writeObject(kursy);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Zapisano listę kursów do pliku: kursy.ser");
                break;
            default:
                System.out.println("Podałeś niepoprawną wartość!");
        }
    }

    void odczytaj() {
        Scanner skaner = new Scanner(System.in);

        System.out.println("\nWybierz którą listę chcesz odczytać: ");
        System.out.print("""
                1 :  osoby
                2 :  kursy
                """);
        System.out.print("Wybór opcji: ");
        String wybor = skaner.nextLine();

        switch (wybor) {
            case "1":
                try (ObjectInputStream is = new ObjectInputStream(new FileInputStream("osoby.ser"))) {
                    ArrayList<Osoba> odczytaneOsoby = ((ArrayList<Osoba>) is.readObject());
                    for (Osoba osoba : odczytaneOsoby) {
                        osoba.getStan();
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case "2":
                try (ObjectInputStream is = new ObjectInputStream(new FileInputStream("kursy.ser"))) {
                    ArrayList<Kurs> odczytaneKursy = ((ArrayList<Kurs>) is.readObject());
                    for (Kurs kurs : odczytaneKursy) {
                        kurs.getStan();
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println("Podałeś niepoprawną wartość!");
        }
    }

    void sortuj() {
        Scanner skaner = new Scanner(System.in);

        System.out.println("\nWybierz którą listę chcesz posortować: ");
        System.out.print("""
                1 :  osoby
                2 :  kursy
                """);
        System.out.print("Wybór opcji: ");
        String wybor = skaner.nextLine();

        switch (wybor) {
            case "1":
                sortujOsoby();
                break;
            case "2":
                sortujKursy();
                break;
            default:
                System.out.println("Podałeś niepoprawną wartość!");
        }
    }

    void sortujOsoby() {
        Scanner skaner = new Scanner(System.in);

        System.out.println("\nWybierz według jakiej składowej chcesz wykonać sortowanie: ");
        System.out.print("""
                1 :  Nazwisko
                2 :  Nazwisko i Imię
                3 :  Nazwisko i Wiek
                """);
        System.out.print("Wybór opcji: ");
        String wybor = skaner.nextLine();

        switch (wybor) {
            case "1":
                Comparator<Osoba> komparatorOsobaNazwisko = Comparator.comparing(Osoba::getNazwisko);
                osoby.sort(komparatorOsobaNazwisko);
                System.out.println("Posortowano osoby według nazwiska");
                break;
            case "2":
                Comparator<Osoba> komparatorOsobaNazwiskoImie = Comparator.comparing(Osoba::getNazwisko).thenComparing(Osoba::getImie);
                osoby.sort(komparatorOsobaNazwiskoImie);
                System.out.println("Posortowano osoby według nazwiska i imienia");
                break;
            case "3":
                Comparator<Osoba> komparatorOsobaNazwiskoWiek = Comparator.comparing(Osoba::getNazwisko)
                        .thenComparing(Comparator.comparingInt(Osoba::getWiek).reversed());
                osoby.sort(komparatorOsobaNazwiskoWiek);
                System.out.println("Posortowano osoby według nazwiska i wieku");
                break;
            default:
                System.out.println("Podałeś niepoprawną wartość!");
        }
    }

    void sortujKursy() {
        Scanner skaner = new Scanner(System.in);

        System.out.println("\nWybierz według jakiej składowej chcesz wykonać sortowanie: ");
        System.out.print("""
                1 :  Punkty ECTS
                2 :  Nazwisko
                """);
        System.out.print("Wybór opcji: ");
        String wybor = skaner.nextLine();

        switch (wybor) {
            case "1":
                Comparator<Kurs> komparatorKursPunkty = Comparator.comparing(Kurs::getPunktyECTS);
                kursy.sort(komparatorKursPunkty);
                System.out.println("Posortowano kursy według punktów ECTS");
                break;
            case "2":
                Comparator<Kurs> komparatorKursNazwisko = Comparator.comparing(Kurs::getNazwiskoProwadzacego);
                kursy.sort(komparatorKursNazwisko);
                System.out.println("Posortowano kursy według Nazwiska Prowadzącego");
                break;
            default:
                System.out.println("Podałeś niepoprawną wartość!");
        }
    }

    void usun() {
        Scanner skaner = new Scanner(System.in);

        System.out.println("\nWybierz typ elementu który chcesz usunąć: ");
        System.out.print("""
                1 :  Pracownik
                2 :  Student
                3 :  Kurs
                """);
        System.out.print("Wybór opcji: ");
        String wybor = skaner.nextLine();

        ArrayList<Osoba> znalezioneOsoby;
        ArrayList<Kurs> znalezioneKursy;
        switch (wybor) {
            case "1":
                znalezioneOsoby = wyszukajPracownik();
                if (znalezioneOsoby.isEmpty()) {
                    System.out.println("Nie znaleziono takiego Pracownika.");
                }
                for (Osoba osoba : znalezioneOsoby) {
                    powiadomObserwatorow("Usunięto Pracownika: " + osoba.getImie() + " " + osoba.getNazwisko());
                    osoby.remove(osoba);
                }
                break;
            case "2":
                znalezioneOsoby = wyszukajStudent();
                if (znalezioneOsoby.isEmpty()) {
                    System.out.println("Nie znaleziono takiego Studenta.");
                }
                for (Osoba osoba : znalezioneOsoby) {
                    powiadomObserwatorow("Usunięto Studenta: " + osoba.getImie() + " " + osoba.getNazwisko());
                    osoby.remove(osoba);
                }
                break;
            case "3":
                znalezioneKursy = wyszukajKurs();
                if (znalezioneKursy.isEmpty()) {
                    System.out.println("Nie znaleziono takiego Kursu.");
                }
                for (Kurs kurs : znalezioneKursy) {
                    powiadomObserwatorow("Usunięto Kurs: " + kurs.getNazwaKursu());
                    kursy.remove(kurs);
                }
                break;
            default:
                System.out.println("Podałeś niepoprawną wartość!");
        }
    }


    public ArrayList<Osoba> getOsoby() {
        return osoby;
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
