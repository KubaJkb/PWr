package Projekt_2;

public class Wykladowca extends Osoba {

    private String wykladanyPrzedmiot;
    private int lataDoswiadczenia;

    public Wykladowca() {
        wykladanyPrzedmiot = "informatyka";
        lataDoswiadczenia = 10;
    }
    public Wykladowca(String imie, String nazwisko, int wiek, String wykladanyPrzedmiot, int lataDoswiadczenia) {
        super(imie, nazwisko, wiek);
        this.wykladanyPrzedmiot = wykladanyPrzedmiot;
        this.lataDoswiadczenia = lataDoswiadczenia;
    }

    @Override
    public boolean czySpozniony() {
        if (getWiek() < 30) {
            return true;
        }
        return false;
    }

    public String getWykladanyPrzedmiot() {
        return wykladanyPrzedmiot;
    }

    public void setWykladanyPrzedmiot(String wykladanyPrzedmiot) {
        this.wykladanyPrzedmiot = wykladanyPrzedmiot;
    }

    public int getLataDoswiadczenia() {
        return lataDoswiadczenia;
    }

    public void setLataDoswiadczenia(int lataDoswiadczenia) {
        this.lataDoswiadczenia = lataDoswiadczenia;
    }

    public void getStan() {
        System.out.println("------------------------------");
        System.out.println("To jest wykładowca");
        super.getStan();
        System.out.println("Wykladany przedmiot: " + getWykladanyPrzedmiot());
        System.out.println("Lata doświadczenia: " + getLataDoswiadczenia());
        System.out.println("------------------------------\n");

    }


}

