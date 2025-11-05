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


    public int getLiczbaPublikacji() {
        return liczbaPublikacji;
    }
    public String toString() {

        return (super.toString() + "\nLiczba publikacji: " + liczbaPublikacji + "\n");

    }

    public void setLiczbaPublikacji(int liczbaPublikacji) {
        this.liczbaPublikacji = liczbaPublikacji;
    }
}
