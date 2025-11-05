package Projekt;

public class Mechaniczny extends Zegarek {
    private boolean czyUszkodzony;
    private boolean czyNakrecony;
    private int dzienDatownika;

    private Pasek pasek = null;


    public Mechaniczny() {
        czyUszkodzony = false;
        czyNakrecony = true;
        dzienDatownika = 10;
    }

    public Mechaniczny(int godzina, int minuta, int sekunda, String kolorKoperty, double srednicaTarczy, String kolorTarczy, boolean czyUszkodzony, boolean czyNakrecony, int dzienDatownika) {
        super(godzina, minuta, sekunda, kolorKoperty, srednicaTarczy, kolorTarczy);
        this.czyUszkodzony = czyUszkodzony;
        this.czyNakrecony = czyNakrecony;
        this.dzienDatownika = dzienDatownika;
    }

    public void nakrecZegarek() {
        if (czyNakrecony) {
            System.out.println("Nie można wykonać tej czynności, ponieważ zegarek jest już nakręcony!");
        } else {
            czyNakrecony = true;
            System.out.println("Zegarek został nakręcony pomyślnie!");
        }
    }
    public void pokazDzien() {
        System.out.println("Dzień miesiąca ustawiony na zegarku to: " + dzienDatownika);
    }

    @Override
    public void oddajDoNaprawy() {
        Zegarmistrz.naprawZegarek(this);
    }

    public void setCzyUszkodzony(boolean czyUszkodzony) {
        this.czyUszkodzony = czyUszkodzony;
    }
    public void setCzyNakrecony(boolean czyNakrecony) {
        this.czyNakrecony = czyNakrecony;
    }
    public void setDzienDatownika(int dzienDatownika) {
        this.dzienDatownika = dzienDatownika;
    }
    public void kupPasek(String material, double dlugosc, String kolor, boolean czyZapiety) {
        pasek = new Pasek(material, dlugosc, kolor, czyZapiety);
    }

    public boolean getCzyUszkodzony() {
        return czyUszkodzony;
    }
    public boolean getCzyNakrecony() {
        return czyNakrecony;
    }
    public int getDzienDatownika() {
        return dzienDatownika;
    }
    public Pasek getPasek() {
        return pasek;
    }
    public void getStan() {
        System.out.println("--------------------------------------------------");
        System.out.println("To jest zegarek mechaniczny");
        super.getStan();
        System.out.print("Czy zegarek jest uszkodzony: ");
        if (czyUszkodzony) System.out.println("tak");
        else  System.out.println("nie");
        System.out.print("Czy zegarek jest nakręcony: ");
        if (czyNakrecony) System.out.println("tak");
        else System.out.println("nie");
        System.out.println("Dzień miesiąca na datowniku: " + dzienDatownika);
        if (pasek != null) {
            pasek.getStan();
        }
        System.out.println("--------------------------------------------------\n");
    }

}
