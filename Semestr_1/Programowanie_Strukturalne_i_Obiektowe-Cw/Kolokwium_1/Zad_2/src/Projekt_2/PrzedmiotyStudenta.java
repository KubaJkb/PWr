package Projekt_2;

public class PrzedmiotyStudenta {

    private String nazwaPrzedmiotu;
    private int liczbaGodzin;
    private float punktyECTS;

    public PrzedmiotyStudenta(String nazwaPrzedmiotu, int liczbaGodzin, float punktyECTS) {
        this.nazwaPrzedmiotu = nazwaPrzedmiotu;
        this.liczbaGodzin = liczbaGodzin;
        this.punktyECTS = punktyECTS;
    }

    public void setNazwaPrzedmiotu(String nazwaPrzedmiotu) {
        this.nazwaPrzedmiotu = nazwaPrzedmiotu;
    }

    public void setLiczbaGodzin(int liczbaGodzin) {
        this.liczbaGodzin = liczbaGodzin;
    }

    public void setPunktyECTS(float punktyECTS) {
        this.punktyECTS = punktyECTS;
    }

    public float getPunktyECTS() {
        return punktyECTS;
    }

    public String getNazwaPrzedmiotu() {
        return nazwaPrzedmiotu;
    }

    public int getLiczbaGodzin() {
        return liczbaGodzin;
    }

    public void getStan() {
        System.out.println("Przedmiot: " + getNazwaPrzedmiotu());
        System.out.println("Ilosc godzin: " + getLiczbaGodzin());
        System.out.println("Punkty ECTS: " + getPunktyECTS());

    }
}
