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


    @Override
    public int hashCode() {
        return nrIndeksu.hashCode();
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return nrIndeksu.equals(student.getNrIndeksu());
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
    public String toString() {
        String result = super.toString()+"\nNr indeksu: " + nrIndeksu
                + "\nRok studiów: " + rokStudiow
                + "\nCzy jest uczestnikiem programu ERASMUS: " + czyErasmus
                + "\nCzy I-stopnia: " + czy1Stopnia
                + "\nCzy stacjonarne: " + czyStacjonarne + "\n"
        ;

        for (Kurs kurs : kursy){
            result += kurs.toString();
        }

        return result;
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
