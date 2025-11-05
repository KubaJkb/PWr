package Projekt_Main;

import Projekt.*;

public class  Main {
    public static void main(String[] args) {
        Zegarek[] zegarek = new Zegarek[4];
        zegarek[0] = new Kwarcowy(10, 0, 24, "różowe-złoto", 40,
                "biały", true, 200, true);
        zegarek[1] = new Mechaniczny(6, 15, 40, "złoty", 38,
                "zielony", true, false, 28);
        zegarek[2] = new Mechaniczny(3, 0, 4, "srebrny", 42,
                "niebieski", false, false, 21);
        zegarek[3] = new Smartwatch(21, 33, 12, "czarny", 44,
                "czarny", 65, 501.7, 827,false);

        for (int i = 0; i < zegarek.length; i++) {
            zegarek[i].getStan();
        }

        System.out.println("\nSprawdzenie baterii i podświetlanie tarczy 1 zegarka:");
        if (zegarek[0] instanceof Kwarcowy){
            ((Kwarcowy) zegarek[0]).stanBaterii();
            ((Kwarcowy) zegarek[0]).podswietlTarcze();
        }

        System.out.println("\nNarkęcanie 2 zegarka dwa razy:");
        if (zegarek[1] instanceof Mechaniczny) {
            ((Mechaniczny) zegarek[1]).nakrecZegarek();
            ((Mechaniczny) zegarek[1]).nakrecZegarek();
        }

        System.out.println("\nPokazywanie stanu datownika 3 zegarka:");
        if (zegarek[2] instanceof Mechaniczny) {
            ((Mechaniczny) zegarek[2]).pokazDzien();
        }

        System.out.println("\nSprawdzenie tętna, kalorii, kroków i pogody na 4 zegarku:");
        if (zegarek[3] instanceof Smartwatch){
            ((Smartwatch) zegarek[3]).pomiarTetna(78);
            ((Smartwatch) zegarek[3]).wyswietlKalorie();
            ((Smartwatch) zegarek[3]).wyswietlKroki();
            ((Smartwatch) zegarek[3]).wyswietlPogode("pochmurnie", 8);
        }

        //Tworzenie pasków
        if (zegarek[0] instanceof Kwarcowy || zegarek[0] instanceof Mechaniczny) {
            ((Kwarcowy) zegarek[0]).kupPasek("sztuczna skóra", 62, "biały", true);
        }
        if (zegarek[1] instanceof Mechaniczny || zegarek[1] instanceof Kwarcowy) {
            ((Mechaniczny) zegarek[1]).kupPasek("metal", 78, "srebrny", false);
        }
        if (zegarek[2] instanceof Mechaniczny || zegarek[2] instanceof Kwarcowy) {
            ((Mechaniczny) zegarek[2]).kupPasek("skóra", 68.5, "czarny", false);
        }

        System.out.println("\nZapinanie pasków: ");
        for (int i = 0; i < 3; i++) {
            if (zegarek[i] instanceof Mechaniczny && ((Mechaniczny) zegarek[i]).getPasek() != null) {
                ((Mechaniczny) zegarek[i]).getPasek().zapnij();
            }
            else if (zegarek[i] instanceof Kwarcowy && ((Kwarcowy) zegarek[i]).getPasek() != null) {
                ((Kwarcowy) zegarek[i]).getPasek().zapnij();
            }
        }

        for (int i = 0; i < zegarek.length; i++) {
            zegarek[i].oddajDoSerwisu();
            zegarek[i].getStan();
         }

    }
}
