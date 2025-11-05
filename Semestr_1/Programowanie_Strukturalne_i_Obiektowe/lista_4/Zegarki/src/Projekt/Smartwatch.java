package Projekt;

public class Smartwatch extends Zegarek {
    private int procentNaladowania;
    private double spaloneKalorie;
    private int liczbaKrokow;
    private boolean czyMaOsloneEkranu;


    public Smartwatch() {
        procentNaladowania = 100;
        spaloneKalorie = 0;
        liczbaKrokow = 1000;
        czyMaOsloneEkranu = false;
    }

    public Smartwatch(int godzina, int minuta, int sekunda, String kolorKoperty, double srednicaTarczy, String kolorTarczy, int procentNaladowania, double spaloneKalorie, int liczbaKrokow, boolean czyMaOsloneEkranu) {
        super(godzina, minuta, sekunda, kolorKoperty, srednicaTarczy, kolorTarczy);
        this.procentNaladowania = procentNaladowania;
        this.spaloneKalorie = spaloneKalorie;
        this.liczbaKrokow = liczbaKrokow;
        this.czyMaOsloneEkranu = czyMaOsloneEkranu;
    }

    public void pomiarTetna(int tetno) {
        System.out.println("Twoje tętno wynosi " + tetno + " bpm");
    }
    public void wyswietlKalorie() {
        if (spaloneKalorie >= 500) {
            System.out.println("Gratulacje! Dzisiaj spaliłeś aż " + spaloneKalorie + " kalorii.");
        } else {
            System.out.println("Dzisiaj spaliłeś tylko " + spaloneKalorie + " kalorii. Zalecana jest większa aktywność fizyczna.");
        }
    }
    public void wyswietlPogode(String pogoda, double temperatura) {
        System.out.print("Dzisiaj będzie " + pogoda);
        System.out.println(" \tŚrednia temperatura będzie wynosić około " + temperatura + "°C");
    }
    public void wyswietlKroki() {
        if (liczbaKrokow >= 10000) {
            System.out.println("Aktualna liczba kroków to " + liczbaKrokow + ". Osiągnąłeś już swój dzienny cel kroków!");
        } else {
            System.out.println("Aktualna liczba kroków to " + liczbaKrokow + ". Do osiągnięcia swojego dziennego celu brakuje " + (10000 - liczbaKrokow) + " kroków!");
        }
    }

    @Override
    public void oddajDoNaprawy() {
        Zegarmistrz.zalozOsloneEkranu(this);
    }

    public void setProcentNaladowania(int procentNaladowania) {
        this.procentNaladowania = procentNaladowania;
    }
    public void setSpaloneKalorie(double spaloneKalorie) {
        this.spaloneKalorie = spaloneKalorie;
    }
    public void setLiczbaKrokow(int liczbaKrokow) {
        this.liczbaKrokow = liczbaKrokow;
    }
    public void setCzyMaOsloneEkranu(boolean czyMaOsloneEkranu) {
        this.czyMaOsloneEkranu = czyMaOsloneEkranu;
    }

    public int getProcentNaladowania() {
        return procentNaladowania;
    }
    public double getSpaloneKalorie() {
        return spaloneKalorie;
    }
    public int getLiczbaKrokow() {
        return liczbaKrokow;
    }
    public boolean getCzyMaOsloneEkranu() {
        return czyMaOsloneEkranu;
    }
    public void getStan() {
        System.out.println("--------------------------------------------------");
        System.out.println("To jest smartwatch");
        super.getStan();
        System.out.println("Naładowanie baterii: " + procentNaladowania + "%");
        System.out.print("Zegarek ");
        if (czyMaOsloneEkranu) System.out.println("ma osłonę ekranu");
        else System.out.println("nie ma osłony ekranu");
        System.out.println("Dzisiaj udało Ci się już spalić: " + spaloneKalorie + " kalorii");
        System.out.println("Do tej pory przeszedłeś już " + liczbaKrokow + " kroków");
        System.out.println("--------------------------------------------------\n");
    }

}
