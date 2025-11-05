package Uczelnia;

import java.text.DecimalFormat;

public abstract class PracownikUczelni extends Osoba {
    private String stanowisko;
    private int stazPracy;
    private double pensja;

    public PracownikUczelni() {
        stazPracy = 10;
        pensja = 7000;
    }

    public PracownikUczelni(String imie, String nazwisko, String pesel, int wiek, char plec, int stazPracy, double pensja) {
        super(imie, nazwisko, pesel, wiek, plec);
        this.stazPracy = stazPracy;
        this.pensja = pensja;
    }

    public String getStanowisko() {
        return stanowisko;
    }
    public int getStazPracy() {
        return stazPracy;
    }
    public double getPensja() {
        return pensja;
    }
    public void getStan() {
        super.getStan();
        System.out.println("Stanowisko: " + stanowisko
                + "\nStaż pracy: " + stazPracy + " lat"
                + "\nPensja: " + (new DecimalFormat("0.00")).format(pensja) + " zł");
    }

    public void setStanowisko(String stanowisko) {
        this.stanowisko = stanowisko;
    }
    public void setStazPracy(int stazPracy) {
        this.stazPracy = stazPracy;
    }
    public void setPensja(double pensja) {
        this.pensja = pensja;
    }

}
