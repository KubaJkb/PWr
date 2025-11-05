package GUI;

import Uczelnia.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UtworzObiektGUI extends JFrame {
    private UczelniaGUI uczelniaGUI;

    public UtworzObiektGUI(UczelniaGUI uczelniaGUI) {
        this.uczelniaGUI = uczelniaGUI;

        // Set layout manager
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Add components to the frame
        add(new JLabel("Utwórz nowy obiekt:"));

        JComboBox<String> classComboBox = new JComboBox<>(new String[]{"Pracownik Badawczo-Dydaktyczny", "Pracownik Administracyjny", "Student", "Kurs"});
        add(classComboBox);

        JButton createButton = new JButton("Utwórz");
        add(createButton);

        // Set up action listener for the create button
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createObject(classComboBox.getSelectedIndex());
            }
        });

        // Set frame properties
        setTitle("UtworzObiekt GUI");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createObject(int selectedIndex) {
        switch (selectedIndex) {
            case 0:
                uczelniaGUI.uczelnia.addOsoba(KonsolaPracownikBadawczoDydaktyczny());
                break;
            case 1:
                uczelniaGUI.uczelnia.addOsoba(KonsolaPracownikAdministracyjny());
                break;
            case 2:
                uczelniaGUI.uczelnia.addOsoba(KonsolaStudent(uczelniaGUI.uczelnia.getKursy()));
                break;
            case 3:
                uczelniaGUI.uczelnia.addKurs(KonsolaKurs(uczelniaGUI.uczelnia.getOsoby()));
                break;
            default:
                JOptionPane.showMessageDialog(this, "Niepoprawna wartość!");
        }
    }

    private PracownikBadawczoDydaktyczny KonsolaPracownikBadawczoDydaktyczny() {
        JTextField imieField = new JTextField();
        JTextField nazwiskoField = new JTextField();
        JTextField peselField = new JTextField();
        JTextField wiekField = new JTextField();
        JTextField plecField = new JTextField();
        JTextField stazPracyField = new JTextField();
        JTextField pensjaField = new JTextField();
        JTextField stanowiskoField = new JTextField();
        JTextField liczbaPublikacjiField = new JTextField();

        Object[] message = {
                "Imię:", imieField,
                "Nazwisko:", nazwiskoField,
                "PESEL:", peselField,
                "Wiek:", wiekField,
                "Płeć (M/K):", plecField,
                "Staż pracy (w latach):", stazPracyField,
                "Pensja:", pensjaField,
                "Stanowisko:", stanowiskoField,
                "Liczba publikacji:", liczbaPublikacjiField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Podaj dane", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String imie = imieField.getText();
                String nazwisko = nazwiskoField.getText();
                String pesel = peselField.getText();
                int wiek = Integer.parseInt(wiekField.getText());
                char plec = plecField.getText().charAt(0);
                int stazPracy = Integer.parseInt(stazPracyField.getText());
                double pensja = Double.parseDouble(pensjaField.getText());
                String stanowisko = stanowiskoField.getText();
                int liczbaPublikacji = Integer.parseInt(liczbaPublikacjiField.getText());

                return new PracownikBadawczoDydaktyczny(imie, nazwisko, pesel, wiek, plec, stazPracy, pensja, stanowisko, liczbaPublikacji);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Niepoprawny format danych!");
            }
        }
        return null;
    }

    private Osoba KonsolaPracownikAdministracyjny() {
        JTextField imieField = new JTextField();
        JTextField nazwiskoField = new JTextField();
        JTextField peselField = new JTextField();
        JTextField wiekField = new JTextField();
        JTextField plecField = new JTextField();
        JTextField stazPracyField = new JTextField();
        JTextField pensjaField = new JTextField();
        JTextField stanowiskoField = new JTextField();
        JTextField liczbaNadgodzinField = new JTextField();

        Object[] message = {
                "Imię:", imieField,
                "Nazwisko:", nazwiskoField,
                "Pesel:", peselField,
                "Wiek:", wiekField,
                "Płeć (M/K):", plecField,
                "Staż pracy (lata):", stazPracyField,
                "Pensja:", pensjaField,
                "Stanowisko:", stanowiskoField,
                "Liczba nadgodzin:", liczbaNadgodzinField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Tworzenie obiektu klasy PracownikAdministracyjny",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                String imie = imieField.getText();
                String nazwisko = nazwiskoField.getText();
                String pesel = peselField.getText();
                int wiek = Integer.parseInt(wiekField.getText());
                char plec = plecField.getText().charAt(0);
                int stazPracy = Integer.parseInt(stazPracyField.getText());
                double pensja = Double.parseDouble(pensjaField.getText());
                String stanowisko = stanowiskoField.getText();
                int liczbaNadgodzin = Integer.parseInt(liczbaNadgodzinField.getText());

                return new PracownikAdministracyjny(imie, nazwisko, pesel, wiek, plec, stazPracy, pensja, stanowisko, liczbaNadgodzin);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Wprowadzono niepoprawne dane!");
            }
        }

        return null;
    }

    private Osoba KonsolaStudent(ArrayList<Kurs> kursy) {
        JTextField imieField = new JTextField();
        JTextField nazwiskoField = new JTextField();
        JTextField peselField = new JTextField();
        JTextField wiekField = new JTextField();
        JTextField plecField = new JTextField();
        JTextField nrIndeksuField = new JTextField();
        JTextField rokStudiowField = new JTextField();
        JTextField czyErasmusField = new JTextField();
        JTextField czy1StopniaField = new JTextField();
        JTextField czyStacjonarneField = new JTextField();

        Object[] message = {
                "Imię:", imieField,
                "Nazwisko:", nazwiskoField,
                "Pesel:", peselField,
                "Wiek:", wiekField,
                "Płeć (M/K):", plecField,
                "Numer indeksu:", nrIndeksuField,
                "Rok studiów:", rokStudiowField,
                "Czy ERASMUS (true/false):", czyErasmusField,
                "Czy 1 stopnia (true/false):", czy1StopniaField,
                "Czy stacjonarne (true/false):", czyStacjonarneField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Tworzenie obiektu klasy Student",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                String imie = imieField.getText();
                String nazwisko = nazwiskoField.getText();
                String pesel = peselField.getText();
                int wiek = Integer.parseInt(wiekField.getText());
                char plec = plecField.getText().charAt(0);
                String nrIndeksu = nrIndeksuField.getText();
                int rokStudiow = Integer.parseInt(rokStudiowField.getText());
                boolean czyErasmus = Boolean.parseBoolean(czyErasmusField.getText());
                boolean czy1Stopnia = Boolean.parseBoolean(czy1StopniaField.getText());
                boolean czyStacjonarne = Boolean.parseBoolean(czyStacjonarneField.getText());

                return new Student(imie, nazwisko, pesel, wiek, plec, nrIndeksu, rokStudiow, czyErasmus, czy1Stopnia, czyStacjonarne);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Wprowadzono niepoprawne dane!");
            }
        }

        return null;
    }

    private Kurs KonsolaKurs(ArrayList<Osoba> osoby) {
        JTextField nazwaKursuField = new JTextField();
        JTextField imieProwadzacegoField = new JTextField();
        JTextField nazwiskoProwadzacegoField = new JTextField();
        JTextField punktyECTSField = new JTextField();

        Object[] message = {
                "Nazwa kursu:", nazwaKursuField,
                "Imię prowadzącego:", imieProwadzacegoField,
                "Nazwisko prowadzącego:", nazwiskoProwadzacegoField,
                "Punkty ECTS:", punktyECTSField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Tworzenie obiektu klasy Kurs",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                String nazwaKursu = nazwaKursuField.getText();
                String imieProwadzacego = imieProwadzacegoField.getText();
                String nazwiskoProwadzacego = nazwiskoProwadzacegoField.getText();
                int punktyECTS = Integer.parseInt(punktyECTSField.getText());

                return new Kurs(nazwaKursu, imieProwadzacego, nazwiskoProwadzacego, punktyECTS, osoby);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Wprowadzono niepoprawne dane!");
            }
        }

        return null;
    }
    // Add other methods (KonsolaPracownikBadawczoDydaktyczny, KonsolaPracownikAdministracyjny, KonsolaStudent, KonsolaKurs) here.


}

