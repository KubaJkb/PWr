package Uczelnia;

import java.util.ArrayList;
import java.util.Scanner;

public class Student extends Osoba {
    private String nrIndeksu;
    private int rokStudiow;
    private boolean czyErasmus;
    private boolean czy1Stopnia;
    private boolean czyStacjonarne;
    private ArrayList<Kurs> kursy;

    public Student() {
        nrIndeksu = "280000";
        rokStudiow = 1;
        czyErasmus = false;
        czy1Stopnia = true;
        czyStacjonarne = true;
        kursy = new ArrayList<>();
    }

    public Student(String imie, String nazwisko, String pesel, int wiek, char plec, String nrIndeksu, int rokStudiow, boolean czyErasmus, boolean czy1Stopnia, boolean czyStacjonarne) {
        super(imie, nazwisko, pesel, wiek, plec);
        this.nrIndeksu = nrIndeksu;
        this.rokStudiow = rokStudiow;
        this.czyErasmus = czyErasmus;
        this.czy1Stopnia = czy1Stopnia;
        this.czyStacjonarne = czyStacjonarne;
        kursy = new ArrayList<>();
    }

    public static Student KonsolaStudent(ArrayList<Kurs> kursy) {
        System.out.println("\nTworzenie obiektu klasy Student");
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
        System.out.print("Podaj numer indeksu: ");
        String nrIndeksu = skaner.nextLine();
        System.out.print("Podaj rok studiów: ");
        int rokStudiow = skaner.nextInt();
        skaner.nextLine();
        System.out.print("Podaj czy jest uczestnikiem programu ERASMUS (true/false): ");
        boolean czyErasmus = skaner.nextBoolean();
        skaner.nextLine();
        System.out.print("Podaj czy jest na studiach 1. stopnia (true/false): ");
        boolean czy1Stopnia = skaner.nextBoolean();
        skaner.nextLine();
        System.out.print("Podaj czy jest na studiach stacjonarnych (true/false): ");
        boolean czyStacjonarne = skaner.nextBoolean();

        return new Student(imie, nazwisko, pesel, wiek, plec, nrIndeksu, rokStudiow, czyErasmus, czy1Stopnia, czyStacjonarne);
    }

    public String getNrIndeksu() {
        return nrIndeksu;
    }
    public int getRokStudiow() {
        return rokStudiow;
    }
    public boolean getCzyErasmus() {
        return czyErasmus;
    }
    public boolean getCzy1Stopnia() {
        return czy1Stopnia;
    }
    public boolean getCzyStacjonarne() {
        return czyStacjonarne;
    }
    public ArrayList<Kurs> getKurs() {
        return kursy;
    }
    public void getStan() {
        System.out.println("\n**************************************************");
        System.out.println("To jest student");
        super.getStan();
        System.out.print("Nr indeksu: " + nrIndeksu
            + "\nRok studiów: " + rokStudiow
            + "\nCzy jest uczestnikiem programu ERASMUS: ");
        if (czyErasmus) {
            System.out.println("Tak");
        } else {
            System.out.println("Nie");
        }
        System.out.print("Rodzaj studiów: ");
        if (czy1Stopnia) {System.out.print("I-stopnia ");} else {System.out.print("II-stopnia ");}
        if (czyStacjonarne) {System.out.println("\tStacjonarne");} else {System.out.println(" \tNiestacjonarne");}
        for (Kurs kurs : kursy){
            kurs.getStan();
        }
        System.out.println("**************************************************\n");
    }

    public void setNrIndeksu(String nrIndeksu) {
        this.nrIndeksu = nrIndeksu;
    }
    public void setRokStudiow(int rokStudiow) {
        this.rokStudiow = rokStudiow;
    }
    public void setCzyErasmus(boolean czyErasmus) {
        this.czyErasmus = czyErasmus;
    }
    public void setCzy1Stopnia(boolean czy1Stopnia) {
        this.czy1Stopnia = czy1Stopnia;
    }
    public void setCzyStacjonarne(boolean czyStacjonarne) {
        this.czyStacjonarne = czyStacjonarne;
    }
    public void nowyKurs(Kurs kurs) {
        kursy.add(kurs);
    }

}
