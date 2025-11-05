package Uczelnia;

public abstract class Osoba {
    private String imie;
    private String nazwisko;
    private String pesel;
    private int wiek;
    private char plec;

    public Osoba() {
        imie = "Jan";
        nazwisko = "Kowalski";
        pesel = "93010100017";
        wiek = 30;
        plec = 'M';
    }

    public Osoba(String imie, String nazwisko, String pesel, int wiek, char plec) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pesel = pesel;
        this.wiek = wiek;
        if (plec == 'M' || plec == 'K') {
            this.plec = plec;
        } else {
            throw new IllegalArgumentException("Nieprawidłowa nazwa płci!");
        }
    }

    public String getImie() {
        return imie;
    }
    public String getNazwisko() {
        return nazwisko;
    }
    public String getPesel() {
        return pesel;
    }
    public int getWiek() {
        return wiek;
    }
    public char getPlec() {
        return plec;
    }
    public void getStan() {
        System.out.print("Imię: " + imie + " \tNazwisko: " + nazwisko
            + "\nPesel: " + pesel
            + "\nWiek: " + wiek + " lat \tPłeć: ");
        if (plec=='M') {System.out.println("Mężczyzna");} else {System.out.println("Kobieta");}
    }

    public void setImie(String imie) {
        this.imie = imie;
    }
    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }
    public void setPesel(String pesel) {
        this.pesel = pesel;
    }
    public void setWiek(int wiek) {
        this.wiek = wiek;
    }
    public void setPlec(char plec) {
        this.plec = plec;
    }

}
