package Uczelnia;

import java.util.ArrayList;

public class Kurs {
    private String nazwaKursu;
    private String imieProwadzacego;
    private String nazwiskoProwadzacego;
    private int punktyECTS;

    public Kurs() {
        nazwaKursu = "Programowanie";
        imieProwadzacego = "Janusz";
        nazwiskoProwadzacego = "Nowak";
        punktyECTS = 5;
    }

    public Kurs(String nazwaKursu, String imieProwadzacego, String nazwiskoProwadzacego, int punktyECTS, ArrayList<Osoba> osoby) {
        this.nazwaKursu = nazwaKursu;
        boolean prowadzacyIstnieje = false;
        for (Osoba osoba : osoby) {
            if (osoba instanceof PracownikUczelni){
                if (osoba.getImie().equals(imieProwadzacego) && osoba.getNazwisko().equals(nazwiskoProwadzacego)) {
                    prowadzacyIstnieje = true;
                    this.imieProwadzacego = imieProwadzacego;
                    this.nazwiskoProwadzacego = nazwiskoProwadzacego;
                    break;
                }
            }
        }
        if (!prowadzacyIstnieje) {
            throw new IllegalArgumentException("Dane prowadzącego kursy nie zgadzają się z danymi żadnego pracownika uczelni!");
        }
        this.punktyECTS = punktyECTS;
    }

    public String getNazwaKursu() {
        return nazwaKursu;
    }
    public String getImieProwadzacego() {
        return imieProwadzacego;
    }
    public String getNazwiskoProwadzacego() {
        return nazwiskoProwadzacego;
    }
    public int getPunktyECTS() {
        return punktyECTS;
    }
    public void getStan() {
        System.out.println("----------------------------------------");
        System.out.println("Nazwa kursu: " + nazwaKursu
        + "\nProwadzący: " + imieProwadzacego + " " + nazwiskoProwadzacego
        + "\nPunkty ECTS: " + punktyECTS);
    }

    public void setNazwaKursu(String nazwaKursu) {
        this.nazwaKursu = nazwaKursu;
    }
    public void setImieProwadzacego(String imieProwadzacego) {
        this.imieProwadzacego = imieProwadzacego;
    }
    public void setNazwiskoProwadzacego(String nazwiskoProwadzacego) {
        this.nazwiskoProwadzacego = nazwiskoProwadzacego;
    }
    public void setPunktyECTS(int punktyECTS) {
        this.punktyECTS = punktyECTS;
    }
}
