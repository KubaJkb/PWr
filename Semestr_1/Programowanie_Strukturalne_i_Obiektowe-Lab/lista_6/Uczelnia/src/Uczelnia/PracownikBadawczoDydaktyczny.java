package Uczelnia;

import java.util.Scanner;

public class PracownikBadawczoDydaktyczny extends PracownikUczelni {
    private int liczbaPublikacji;

    public PracownikBadawczoDydaktyczny() {
        setStanowisko("Wykładowca");
        liczbaPublikacji = 7;
    }

    public PracownikBadawczoDydaktyczny(String imie, String nazwisko, String pesel, int wiek, char plec, int stazPracy, double pensja, String stanowisko, int liczbaPublikacji) {
        super(imie, nazwisko, pesel, wiek, plec, stazPracy, pensja);
        if (stanowisko.equals("Asystent") || stanowisko.equals("Adiunkt") || stanowisko.equals("Profesor Nadzwyczajny") || stanowisko.equals("Profesor Zwyczajny") || stanowisko.equals("Wykładowca")) {
            setStanowisko(stanowisko);
        } else {
            throw new IllegalArgumentException("Nieprawidłowa nazwa stanowiska!");
        }
        this.liczbaPublikacji = liczbaPublikacji;
    }

    public static PracownikBadawczoDydaktyczny KonsolaPracownikBadawczoDydaktyczny() {
        System.out.println("\nTworzenie obiektu klasy PracownikBadawczoDydaktyczny");
        Scanner skaner = new Scanner(System.in);
        System.out.print("Podaj imię: ");
        String imie = skaner.nextLine();
        System.out.print("Podaj nazwisko: ");
        String nazwisko = skaner.nextLine();
        System.out.print("Podaj pesel: ");
        String pesel = skaner.nextLine();
        System.out.print("Podaj wiek (liczba całkowita): ");
        int wiek = skaner.nextInt();
        skaner.nextLine();
        System.out.print("Podaj plec (M/K): ");
        char plec = skaner.nextLine().charAt(0);
        System.out.print("Podaj staż pracy (w latach): ");
        int stazPracy = skaner.nextInt();
        skaner.nextLine();
        System.out.print("Podaj pensję (z dokładnością do 2 miejsc po przecinku): ");
        double pensja = skaner.nextDouble();
        skaner.nextLine();
        System.out.print("Podaj stanowisko (Asystent, Adiunkt, Profesor Nadzwyczajny, Profesor Zwyczajny, Wykładowca): ");
        String stanowisko = skaner.nextLine();
        System.out.print("Podaj liczbę publikacji: ");
        int liczbaPublikacji = skaner.nextInt();
        skaner.nextLine();

        return new PracownikBadawczoDydaktyczny(imie, nazwisko, pesel, wiek, plec, stazPracy, pensja, stanowisko, liczbaPublikacji);
    }


    public int getLiczbaPublikacji() {
        return liczbaPublikacji;
    }
    public void getStan() {
        System.out.println("\n**************************************************");
        System.out.println("To jest pracownik badawczo-dydaktyczny");
        super.getStan();
        System.out.println("Liczba publikacji: " + liczbaPublikacji);
        System.out.println("**************************************************\n");
    }

    public void setLiczbaPublikacji(int liczbaPublikacji) {
        this.liczbaPublikacji = liczbaPublikacji;
    }
}
