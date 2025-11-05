package Projekt;

public class Pasek {
    private String material;
    private double dlugosc;
    private String kolor;
    private boolean czyZapiety;


    public Pasek() {
        material = "skóra";
        dlugosc = 162.5;
        kolor = "czarny";
        czyZapiety = false;
    }

    public Pasek(String material, double dlugosc, String kolor, boolean czyZapiety) {
        this.material = material;
        this.dlugosc = dlugosc;
        this.kolor = kolor;
        this.czyZapiety = czyZapiety;
    }

    public void odepnij() {
        if (czyZapiety) {
            czyZapiety = false;
            System.out.println("Udało się odpiąć pasek.");
        } else {
            System.out.println("Pasek jest już odpięty!");
        }
    }
    public void zapnij() {
        if (!czyZapiety) {
            czyZapiety = true;
            System.out.println("Udało się zapiąć pasek.");
        } else {
            System.out.println("Pasek jest już zapięty!");
        }
    }

    public void setMaterial(String material) {
        this.material = material;
    }
    public void setDlugosc(double dlugosc) {
        this.dlugosc = dlugosc;
    }
    public void setKolor(String kolor) {
        this.kolor = kolor;
    }
    public void setCzyZapiety(boolean czyZapiety) {
        this.czyZapiety = czyZapiety;
    }

    public String getMaterial() {
        return material;
    }
    public double getDlugosc() {
        return dlugosc;
    }
    public String getKolor() {
        return kolor;
    }
    public boolean getCzyZapiety() {
        return czyZapiety;
    }
    public void getStan() {
        System.out.println("********************");
        System.out.println("Materiał paska: " + material);
        System.out.println("Długość paska: " + dlugosc + " mm");
        System.out.println("Kolor paska: " + kolor);
        System.out.print("Pasek ");
        if (!czyZapiety) System.out.print("nie ");
        System.out.println("jest zapięty");
    }

}
