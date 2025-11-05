package Uczelnia;

public class PracownikAdministracyjny extends PracownikUczelni {
    private int liczbaNadgodzin;

    public PracownikAdministracyjny() {
        setStanowisko("Specjalista");
        liczbaNadgodzin = 13;
    }

    public PracownikAdministracyjny(String imie, String nazwisko, String pesel, int wiek, char plec, int stazPracy, double pensja, String stanowisko, int liczbaNadgodzin) {
        super(imie, nazwisko, pesel, wiek, plec, stazPracy, pensja);
        if (stanowisko.equals("Referent") || stanowisko.equals("Specjalista") || stanowisko.equals("Starszy Specjalista")) {
            setStanowisko(stanowisko);
        } else {
            throw new IllegalArgumentException("Nieprawidłowa nazwa stanowiska!");
        }
        this.liczbaNadgodzin = liczbaNadgodzin;
    }

    public int getLiczbaNadgodzin() {
        return liczbaNadgodzin;
    }
    public void getStan() {
        System.out.println("**************************************************");
        System.out.println("To jest pracownik administracyjny");
        super.getStan();
        System.out.println("Liczba nadgodzin: " + liczbaNadgodzin);
        System.out.println("**************************************************\n");
    }

    public void setLiczbaNadgodzin(int liczbaNadgodzin) {
        this.liczbaNadgodzin = liczbaNadgodzin;
    }
}
