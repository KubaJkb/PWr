package Strategia;

import Uczelnia.*;
import Uczelnia_Main.*;

import java.util.*;

public class ZapisProwadzacy implements StrategiaZapisNaKurs {
    @Override
    public Student zapiszNaKursy(Uczelnia uczelnia, int nrStudenta) {
        Student student = (Student) uczelnia.getOsoby().get(nrStudenta);

        Comparator<Integer> keyComparator = Comparator.reverseOrder();
        TreeMap<Integer, Kurs> kursy = new TreeMap<>(keyComparator);
        for (Kurs kurs : uczelnia.getKursy()) {
            for (Osoba osoba : uczelnia.getOsoby()) {
                if (osoba instanceof PracownikBadawczoDydaktyczny pracownikUczelni) {
                    if (pracownikUczelni.getImie().equals(kurs.getImieProwadzacego()) && pracownikUczelni.getNazwisko().equals(kurs.getNazwiskoProwadzacego())) {
                        kursy.put(pracownikUczelni.getLiczbaPublikacji(), kurs);
                    }
                }
            }
        }

        for (int i = 0; i < kursy.size(); i++) {
            Kurs kurs = kursy.get(i);
            boolean czyStudentJuzZapisany = false;
            for (Kurs krs : ((Student) uczelnia.getOsoby().get(nrStudenta)).getKurs()) {
                if (krs.equals(kurs)) {
                    czyStudentJuzZapisany = true;
                    break;
                }
            }

            if (!czyStudentJuzZapisany) {
                student.nowyKurs(kurs);
                uczelnia.powiadomObserwatorow("Przypisano kurs " + kurs.getNazwaKursu() + " do studenta " + student.getImie() + " " + student.getNazwisko());
            }
        }

        return student;
    }
}
