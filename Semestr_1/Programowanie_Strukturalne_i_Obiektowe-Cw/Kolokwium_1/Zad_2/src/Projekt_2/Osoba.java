package Projekt_2;

public abstract class Osoba {

    private String imie;
    private String nazwisko;
    private int wiek;

    public Osoba() {
        imie = "Jan";
        nazwisko = "Kowalski";
        wiek = 30;
    }

    public Osoba(String imie, String nazwisko, int wiek) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.wiek = wiek;
    }

    public abstract boolean czySpozniony();

    public void setImie(String imie) {
        this.imie = imie;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public void setWiek(int wiek) {
        this.wiek = wiek;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public int getWiek() {
        return wiek;
    }

    public void getStan() {
        System.out.println("Imie: " + getImie() + "\t" + "Nazwisko: "
                + getNazwisko() + "\n" + "Wiek: " + getWiek());

    }

}
