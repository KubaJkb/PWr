package Uczelnia;

import java.util.Scanner;

public class PracownikAdministracyjny extends PracownikUczelni {
    private int liczbaNadgodzin;

    public PracownikAdministracyjny() {
        setStanowisko("Specjalista");
        liczbaNadgodzin = 13;
    }

    public PracownikAdministracyjny(String imie, String nazwisko, String pesel, int wiek, char plec, int stazPracy, double pensja, String stanowisko, int liczbaNadgodzin) {
        super(imie, nazwisko, pesel, wiek, plec, stazPracy, pensja);
        if (stanowisko.equals("Referent") || stanowisko.equals("Specjalista") || stanowisko.equals("Starszy Specjalista")) {
            setStanowisko(stanowisko);
        } else {
            throw new IllegalArgumentException("Nieprawidłowa nazwa stanowiska!");
        }
        this.liczbaNadgodzin = liczbaNadgodzin;
    }

    public static PracownikAdministracyjny KonsolaPracownikAdministracyjny() {
        System.out.println("\nTworzenie obiektu klasy PracownikAdministracyjny");
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
        System.out.print("Podaj stanowisko (Referent, Specjalista, Starszy Specjalista): ");
        String stanowisko = skaner.nextLine();
        System.out.print("Podaj liczbę nadgodzin: ");
        int liczbaNadgodzin = skaner.nextInt();
        skaner.nextLine();

        return new PracownikAdministracyjny(imie, nazwisko, pesel, wiek, plec, stazPracy, pensja, stanowisko, liczbaNadgodzin);
    }

    public int getLiczbaNadgodzin() {
        return liczbaNadgodzin;
    }

    public void getStan() {
        System.out.println("\n**************************************************");
        System.out.println("To jest pracownik administracyjny");
        super.getStan();
        System.out.println("Liczba nadgodzin: " + liczbaNadgodzin);
        System.out.println("**************************************************\n");
    }

    public void setLiczbaNadgodzin(int liczbaNadgodzin) {
        this.liczbaNadgodzin = liczbaNadgodzin;
    }
}
