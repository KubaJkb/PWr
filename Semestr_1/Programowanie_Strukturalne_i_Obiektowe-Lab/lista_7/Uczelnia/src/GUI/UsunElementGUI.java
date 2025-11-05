package GUI;

import Uczelnia.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UsunElementGUI extends JFrame {
    private JComboBox<String> elementComboBox;
    private JTextField valueTextField;
    private JButton usunButton;
    private JTextArea resultTextArea;

    private UczelniaGUI uczelniaGUI;

    public UsunElementGUI(UczelniaGUI uczelniaGUI) {
        this.uczelniaGUI = uczelniaGUI;

        setTitle("Usuwanie Elementu");
        setSize(350, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();
        addListeners();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        String[] elementOptions = {"Pracownik", "Student", "Kurs"};
        elementComboBox = new JComboBox<>(elementOptions);
        valueTextField = new JTextField(20);
        usunButton = new JButton("Usuń");
        resultTextArea = new JTextArea(10, 30);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Wybierz typ elementu:"));
        panel.add(elementComboBox);
        panel.add(new JLabel("Podaj wartość do usunięcia:"));
        panel.add(valueTextField);
        panel.add(usunButton);
        panel.add(new JLabel("Wynik operacji:"));
        panel.add(new JScrollPane(resultTextArea));

        add(panel);
    }

    private void addListeners() {
        usunButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usun();
            }
        });
    }

    private void usun() {
        String selectedElement = (String) elementComboBox.getSelectedItem();
        String searchValue = valueTextField.getText();

        StringBuilder resultText = new StringBuilder();
        switch (selectedElement) {
            case "Pracownik":
                ArrayList<Osoba> foundPracownicy = wyszukajPracownik(searchValue);
                if (foundPracownicy.isEmpty()) {
                    resultText.append("Nie znaleziono takiego Pracownika.");
                } else {
                    for (Osoba osoba : foundPracownicy) {
                        uczelniaGUI.uczelnia.getOsoby().remove(osoba);
                        resultText.append("Usunięto Pracownika: ").append(osoba.getImie()).append(" ").append(osoba.getNazwisko()).append("\n");
                    }
                }
                break;
            case "Student":
                ArrayList<Osoba> foundStudenci = wyszukajStudent(searchValue);
                if (foundStudenci.isEmpty()) {
                    resultText.append("Nie znaleziono takiego Studenta.");
                } else {
                    for (Osoba osoba : foundStudenci) {
                        uczelniaGUI.uczelnia.getOsoby().remove(osoba);
                        resultText.append("Usunięto Studenta: ").append(osoba.getImie()).append(" ").append(osoba.getNazwisko()).append("\n");
                    }
                }
                break;
            case "Kurs":
                ArrayList<Kurs> foundKursy = wyszukajKurs(searchValue);
                if (foundKursy.isEmpty()) {
                    resultText.append("Nie znaleziono takiego Kursu.");
                } else {
                    for (Kurs kurs : foundKursy) {
                        uczelniaGUI.uczelnia.getKursy().remove(kurs);
                        resultText.append("Usunięto Kurs: ").append(kurs.getNazwaKursu()).append("\n");
                    }
                }
                break;
            default:
                resultText.append("Podałeś niepoprawną wartość!");
        }

        resultTextArea.setText(resultText.toString());
    }

    private ArrayList<Osoba> wyszukajPracownik(String value) {
        ArrayList<Osoba> foundPracownicy = new ArrayList<>();

        for (Osoba osoba : uczelniaGUI.uczelnia.getOsoby()) {
            if (osoba instanceof PracownikUczelni pracownik) {
                if (pracownikMatches(pracownik, value)) {
                    foundPracownicy.add(osoba);
                }
            }
        }
        return foundPracownicy;
    }

    private boolean pracownikMatches(PracownikUczelni pracownik, String value) {
        return pracownik.getImie().equalsIgnoreCase(value)
                || pracownik.getNazwisko().equalsIgnoreCase(value)
                || pracownik.getStanowisko().equalsIgnoreCase(value)
                || String.valueOf(pracownik.getStazPracy()).equalsIgnoreCase(value)
                || String.valueOf(pracownik.getPensja()).equalsIgnoreCase(value)
                || (pracownik instanceof PracownikAdministracyjny &&
                String.valueOf(((PracownikAdministracyjny) pracownik).getLiczbaNadgodzin()).equalsIgnoreCase(value))
                || (pracownik instanceof PracownikBadawczoDydaktyczny &&
                String.valueOf(((PracownikBadawczoDydaktyczny) pracownik).getLiczbaPublikacji()).equalsIgnoreCase(value));
    }

    private ArrayList<Osoba> wyszukajStudent(String value) {
        ArrayList<Osoba> foundStudenci = new ArrayList<>();

        for (Osoba osoba : uczelniaGUI.uczelnia.getOsoby()) {
            if (osoba instanceof Student student) {
                if (studentMatches(student, value)) {
                    foundStudenci.add(osoba);
                }
            }
        }
        return foundStudenci;
    }

    private boolean studentMatches(Student student, String value) {
        return student.getImie().equalsIgnoreCase(value)
                || student.getNazwisko().equalsIgnoreCase(value)
                || student.getNrIndeksu().equalsIgnoreCase(value)
                || String.valueOf(student.getRokStudiow()).equalsIgnoreCase(value)
                || studentHasCourse(student, value);
    }

    private boolean studentHasCourse(Student student, String value) {
        for (Kurs kurs : student.getKurs()) {
            if (kurs.getNazwaKursu().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<Kurs> wyszukajKurs(String value) {
        ArrayList<Kurs> foundKursy = new ArrayList<>();

        for (Kurs kurs : uczelniaGUI.uczelnia.getKursy()) {
            if (kursMatches(kurs, value)) {
                foundKursy.add(kurs);
            }
        }
        return foundKursy;
    }

    private boolean kursMatches(Kurs kurs, String value) {
        return kurs.getNazwaKursu().equalsIgnoreCase(value)
                || (kurs.getImieProwadzacego() + " " + kurs.getNazwiskoProwadzacego()).equalsIgnoreCase(value)
                || String.valueOf(kurs.getPunktyECTS()).equalsIgnoreCase(value);
    }

}
