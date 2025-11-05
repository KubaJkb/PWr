package Projekt;

import java.time.LocalDateTime;


public class Zegarmistrz {
    static LocalDateTime now = LocalDateTime.now();

    public static void pelnySerwis(Zegarek zegarek) {
        if (zegarek instanceof Mechaniczny) {

            naprawZegarek(zegarek);

            if (!((Mechaniczny) zegarek).getCzyNakrecony()) {
                ((Mechaniczny) zegarek).setCzyNakrecony(true);
                System.out.println("Zegarmistrz nakręcił zegarek.");
            }
        }
        else if (zegarek instanceof Kwarcowy) {

            naprawZegarek(zegarek);

            if (((Kwarcowy) zegarek).getPozostalyCzasBaterii() < 500) {
                ((Kwarcowy) zegarek).setPozostalyCzasBaterii(1000);
                System.out.println("Zegarmistrz wymienił baterię w zegarku.");
            }
        }
        else if (zegarek instanceof Smartwatch) {

            zalozOsloneEkranu(zegarek);

            if (((Smartwatch) zegarek).getProcentNaladowania() < 80) {
                ((Smartwatch) zegarek).setProcentNaladowania(100);
                System.out.println("Zegarmistrz naładował zegarek.");
            }
        }
        ustawAktualnyCzas(zegarek);
    }
    public static void naprawZegarek(Zegarek zegarek) {
        if (zegarek instanceof Mechaniczny) {
            if (((Mechaniczny) zegarek).getCzyUszkodzony()) {
                ((Mechaniczny) zegarek).setCzyUszkodzony(false);
                System.out.println("\nZegarmistrz naprawił uszkodzony zegarek.");
            } else {
                System.out.println("\nZegarek nie jest uszkodzony!");
            }
        }
        else if (zegarek instanceof Kwarcowy) {
            if (((Kwarcowy) zegarek).getCzyUszkodzony()) {
                ((Kwarcowy) zegarek).setCzyUszkodzony(false);
                System.out.println("\nZegarmistrz naprawił uszkodzony zegarek.");
            } else {
                System.out.println("\nZegarek nie jest uszkodzony!");
            }
        }
        else {
            System.out.println("\nZegarmistrz nie potrafi naprawić tego rodzaju zegarka!");
        }
    }
    public static void zalozOsloneEkranu(Zegarek zegarek) {
        if (!((Smartwatch) zegarek).getCzyMaOsloneEkranu()) {
            ((Smartwatch) zegarek).setCzyMaOsloneEkranu(true);
            System.out.println("\nZegarmistrz założył osłonę ekranu.");
        }
        else {
            System.out.println("\nZegarek ma już założoną osłonę ekranu!");
        }
    }
    static void ustawAktualnyCzas(Zegarek zegarek) {
        if (zegarek.czyPoprawnyCzas()) {
            return;
        }
        if (zegarek instanceof Smartwatch){
            zegarek.setGodzina(now.getHour());
        } else {
            zegarek.setGodzina(now.getHour()%12);
        }
        zegarek.setMinuta(now.getMinute());
        zegarek.setSekunda(now.getSecond());
        System.out.println("Zegarmistrz ustawił poprawną godzinę.");
    }

}
