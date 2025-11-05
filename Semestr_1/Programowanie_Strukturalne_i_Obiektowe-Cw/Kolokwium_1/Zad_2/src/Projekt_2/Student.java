package Projekt_2;

public class Student extends Osoba {

    private int nralbumu;
    private String kierunek;
    private PrzedmiotyStudenta[] przedmiotyStudenta = new PrzedmiotyStudenta[20];
    private int ilePrzedmiotow = 0;

    public Student() {
        nralbumu = 280000;
        kierunek = "informatyka";
    }

    public Student(String imie, String nazwisko, int wiek, int nralbumu, String kierunek) {
        super(imie, nazwisko, wiek);
        this.nralbumu = nralbumu;
        this.kierunek = kierunek;
    }

    @Override
    public boolean czySpozniony() {
        if (getWiek() > 19) {
            return true;
        }
        return false;
    }

    public void setNralbumu(int nralbumu) {
        this.nralbumu = nralbumu;
    }

    public void setKierunek(String kierunek) {
        this.kierunek = kierunek;
    }

    public void generujPrzedmiot(String przedmiot, int iloscGodzin, float ects) {
        przedmiotyStudenta[ilePrzedmiotow] = new PrzedmiotyStudenta(przedmiot, iloscGodzin, ects);
        ilePrzedmiotow++;
    }

    public int getNralbumu() {
        return nralbumu;
    }

    public String getKierunek() {
        return kierunek;
    }

    public PrzedmiotyStudenta[] getPrzedmiotyStudenta() {
        return przedmiotyStudenta;
    }

    public void getStan() {
        System.out.println("------------------------------");
        System.out.println("To jest student");
        super.getStan();
        System.out.println("Nr albumu: " + getNralbumu() + "\nKierunek: " + getKierunek());
        for (int i = 0; i < ilePrzedmiotow; i++) {
            System.out.println("********************");
            System.out.println("Przedmiot: ");
            przedmiotyStudenta[i].getStan();
        }
        System.out.println("------------------------------\n");

    }

}

