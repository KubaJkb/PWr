package Projekt;

import java.time.LocalDateTime;

public abstract class Zegarek {
    //składowe
    private int godzina;
    private int minuta;
    private int sekunda;
    private String kolorKoperty;
    private double srednicaTarczy;
    private String kolorTarczy;


    //konstruktor domyślny
    public Zegarek() {
        godzina = 12;
        minuta = 30;
        sekunda = 0;
        kolorKoperty = "srebrny";
        srednicaTarczy = 39.5;
        kolorTarczy = "biały";
    }

    //konstruktor przeciążony
    public Zegarek(int godzina, int minuta, int sekunda, String kolorKoperty, double srednicaTarczy, String kolorTarczy) {
        this.godzina = godzina;
        this.minuta = minuta;
        this.sekunda = sekunda;
        this.kolorKoperty = kolorKoperty;
        this.srednicaTarczy = srednicaTarczy;
        this.kolorTarczy = kolorTarczy;
    }

    //Metody
    public void pokazCzas() {
        System.out.print("Ustawiony czas: ");
        if (godzina > 9) System.out.print("0");
        System.out.print(godzina + ":");
        if (minuta > 9) System.out.print("0");
        System.out.print(minuta + ":");
        if (sekunda > 9) System.out.print("0");
        System.out.println(sekunda);
    }
    public boolean czyPoprawnyCzas() {
        LocalDateTime now = LocalDateTime.now();
        if (godzina == now.getHour() && minuta == now.getMinute() && sekunda == now.getSecond()) {
            return true;
        } else {
            return false;
        }
    }
    public void ustawCzas(int h, int m, int s) {
        godzina = h;
        minuta = m;
        sekunda = s;
    }

    public void oddajDoSerwisu() {
        Zegarmistrz.pelnySerwis(this);
    }
    public abstract void oddajDoNaprawy();

    //Settery
    public void setGodzina(int godzina) {
        this.godzina = godzina;
    }
    public void setMinuta(int minuta) {
        this.minuta = minuta;
    }
    public void setSekunda(int sekunda) {
        this.sekunda = sekunda;
    }
    public void setKolorKoperty(String kolorKoperty) {
        this.kolorKoperty = kolorKoperty;
    }
    public void setSrednicaTarczy(double srednicaTarczy) {
        this.srednicaTarczy = srednicaTarczy;
    }
    public void setKolorTarczy(String kolorTarczy) {
        this.kolorTarczy = kolorTarczy;
    }

    //Gettery
    public int getGodzina() {
        return godzina;
    }
    public int getMinuta() {
        return minuta;
    }
    public int getSekunda() {
        return sekunda;
    }
    public String getKolorKoperty() {
        return kolorKoperty;
    }
    public double getSrednicaTarczy() {
        return srednicaTarczy;
    }
    public String getKolorTarczy() {
        return kolorTarczy;
    }
    public void getStan() {
        System.out.print("Ustawiony czas: ");
        if (godzina < 10) System.out.print("0");
        System.out.print(godzina + ":");
        if (minuta < 10) System.out.print("0");
        System.out.print(minuta + ":");
        if (sekunda <  10) System.out.print("0");
        System.out.println(sekunda);
        System.out.println("Kolor koperty: " + kolorKoperty);
        System.out.println("Średnica tarczy: " + srednicaTarczy + " mm");
        System.out.println("Kolor tarczy: " + kolorTarczy);
    }

}
