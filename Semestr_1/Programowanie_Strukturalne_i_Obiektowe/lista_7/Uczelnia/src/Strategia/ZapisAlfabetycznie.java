package Strategia;

import DataProcessing.Uczelnia;
import Uczelnia.*;

import java.util.ArrayList;
import java.util.Comparator;

public class ZapisAlfabetycznie implements StrategiaZapisNaKurs {
    @Override
    public Student zapiszNaKursy(Uczelnia uczelnia, int nrStudenta) {
        Student student = (Student) uczelnia.getOsoby().get(nrStudenta);

        ArrayList<Kurs> kursy = uczelnia.getKursy();
        kursy.sort(Comparator.comparing(Kurs::getNazwaKursu));

        for (Kurs kurs : kursy) {
            boolean czyStudentJuzZapisany = false;
            for (Kurs krs : ((Student) uczelnia.getOsoby().get(nrStudenta)).getKurs()) {
                if (krs.equals(kurs)) {
                    czyStudentJuzZapisany = true;
                    break;
                }
            }

            if (!czyStudentJuzZapisany) {
                student.nowyKurs(kurs);
//                uczelnia.powiadomObserwatorow("Przypisano kurs " + kurs.getNazwaKursu() + " do studenta " + student.getImie() + " " + student.getNazwisko());
            }
        }

        return student;
    }
}
