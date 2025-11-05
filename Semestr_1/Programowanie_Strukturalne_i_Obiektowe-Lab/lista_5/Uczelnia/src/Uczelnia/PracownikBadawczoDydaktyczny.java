package Uczelnia;

import java.util.ArrayList;
import java.util.List;

public class PracownikBadawczoDydaktyczny extends PracownikUczelni {
    //private ArrayList<String> mozliweStanowiska = new ArrayList<>("")
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
    public void getStan() {
        System.out.println("**************************************************");
        System.out.println("To jest pracownik badawczo-dydaktyczny");
        super.getStan();
        System.out.println("Liczba publikacji: " + liczbaPublikacji);
        System.out.println("**************************************************\n");
    }

    public void setLiczbaPublikacji(int liczbaPublikacji) {
        this.liczbaPublikacji = liczbaPublikacji;
    }
}
