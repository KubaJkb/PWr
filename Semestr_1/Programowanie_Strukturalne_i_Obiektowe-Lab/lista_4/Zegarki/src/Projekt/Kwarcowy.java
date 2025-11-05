package Projekt;

public class Kwarcowy extends Zegarek {
    private boolean czyUszkodzony;
    private long pozostalyCzasBaterii;
    private boolean czyPosiadaPodswietlenie;

    private Pasek pasek = null;


    public Kwarcowy() {
        czyUszkodzony = false;
        pozostalyCzasBaterii = 500;
        czyPosiadaPodswietlenie = true;
    }

    public Kwarcowy(int godzina, int minuta, int sekunda, String kolorKoperty, double srednicaTarczy, String kolorTarczy, boolean czyUszkodzony, long pozostalyCzasBaterii, boolean czyPosiadaPodswietlenie) {
        super(godzina, minuta, sekunda, kolorKoperty, srednicaTarczy, kolorTarczy);
        this.czyUszkodzony = czyUszkodzony;
        this.pozostalyCzasBaterii = pozostalyCzasBaterii;
        this.czyPosiadaPodswietlenie = czyPosiadaPodswietlenie;
    }

    public void stanBaterii() {
        if (pozostalyCzasBaterii > 0) {
            System.out.println("Bateria wystarczy jeszcze na " + pozostalyCzasBaterii + " dni działania zegarka");
        } else {
            System.out.println("Bateria jest rozładowana!");
        }
    }
    public void podswietlTarcze() {
        if (czyPosiadaPodswietlenie && pozostalyCzasBaterii > 0) {
            System.out.println("Tarcza zegarka została podświetlona.");
        } else if (czyPosiadaPodswietlenie) {
            System.out.println("Zegarek ma rozładowaną baterię, więc nie można użyć podświetlenia.");
        } else {
            System.out.println("Zegarek nie posiada funkcji podświetlenia!");
        }
    }

    @Override
    public void oddajDoNaprawy() {
        Zegarmistrz.naprawZegarek(this);
    }

    public void setCzyUszkodzony(boolean czyUszkodzony) {
        this.czyUszkodzony = czyUszkodzony;
    }
    public void setPozostalyCzasBaterii(long pozostalyCzasBaterii) {
        this.pozostalyCzasBaterii = pozostalyCzasBaterii;
    }
    public void setCzyPosiadaPodswietlenie(boolean czyPosiadaPodswietlenie) {
        this.czyPosiadaPodswietlenie = czyPosiadaPodswietlenie;
    }
    public void kupPasek(String material, double dlugosc, String kolor, boolean czyZapiety) {
        pasek = new Pasek(material, dlugosc, kolor, czyZapiety);
    }

    public boolean getCzyUszkodzony() {
        return czyUszkodzony;
    }
    public long getPozostalyCzasBaterii() {
        return pozostalyCzasBaterii;
    }
    public boolean getCzyPosiadaPodswietlenie() {
        return czyPosiadaPodswietlenie;
    }
    public Pasek getPasek() {
        return pasek;
    }
    public void getStan() {
        System.out.println("--------------------------------------------------");
        System.out.println("To jest zegarek kwarcowy");
        super.getStan();
        System.out.print("Czy jest uszkodzony: ");
        if (czyUszkodzony) System.out.println("tak");
        else  System.out.println("nie");
        System.out.println("Pozostały czas działania baterii: " + pozostalyCzasBaterii + " dni");
        System.out.print("Czy posiada podświetlenie tarczy: ");
        if (czyPosiadaPodswietlenie) System.out.println("tak");
        else  System.out.println("nie");
        if (pasek != null) {
            pasek.getStan();
        }
        System.out.println("--------------------------------------------------\n");
    }

}
