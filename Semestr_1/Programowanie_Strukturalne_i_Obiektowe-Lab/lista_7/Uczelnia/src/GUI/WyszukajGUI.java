package GUI;

import Uczelnia.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class WyszukajGUI extends JFrame {
    private JComboBox<String> searchTypeComboBox;
    private JTextField valueTextField;
    private JButton searchButton;
    private JTextArea resultTextArea;

    private UczelniaGUI uczelniaGUI;

    public WyszukajGUI(UczelniaGUI uczelniaGUI) {
        this.uczelniaGUI = uczelniaGUI;

        // Initialize components
        searchTypeComboBox = new JComboBox<>(new String[]{"Pracownik", "Student", "Kurs"});
        valueTextField = new JTextField(20);
        searchButton = new JButton("Wyszukaj");
        resultTextArea = new JTextArea(10, 30);

        // Set layout manager
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Add components to the frame
        add(new JLabel("Wybierz sposób wyszukiwania:"));
        add(searchTypeComboBox);
        add(new JLabel("Podaj wartość:"));
        add(valueTextField);
        add(searchButton);
        add(new JLabel("Wynik wyszukiwania:"));
        add(new JScrollPane(resultTextArea));

        // Set up action listener for the search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });

        // Set frame properties
        setTitle("Search GUI");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void performSearch() {
        String searchType = (String) searchTypeComboBox.getSelectedItem();
        String searchValue = valueTextField.getText();

        switch (searchType) {
            case "Pracownik":
                ArrayList<Osoba> foundPracownicy = wyszukajPracownik(searchValue);
                displaySearchResults(foundPracownicy);
                break;
            case "Student":
                ArrayList<Osoba> foundStudenci = wyszukajStudent(searchValue);
                displaySearchResults(foundStudenci);
                break;
            case "Kurs":
                ArrayList<Kurs> foundKursy = wyszukajKurs(searchValue);
                displaySearchResults(foundKursy);
                break;
            default:
                resultTextArea.setText("Nieprawidłowy typ wyszukiwania!");
        }
    }

    private ArrayList<Osoba> wyszukajPracownik(String value) {
        ArrayList<Osoba> foundPracownicy = new ArrayList<>();

        for (Osoba osoba : uczelniaGUI.uczelnia.getOsoby()) {
            if (osoba instanceof PracownikUczelni pracownik) {
                if (pracownikMatches(pracownik, value)) {
                    foundPracownicy.add(pracownik);
                }
            }
        }
        return foundPracownicy;
    }

    private boolean pracownikMatches(PracownikUczelni pracownik, String value) {
        // Implement the logic for matching based on the search criteria
        // Example: Match by name, position, salary, etc.
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
                    foundStudenci.add(student);
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
        if (student.getKurs() == null){
            return false;
        }
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

    private void displaySearchResults(ArrayList<?> results) {
        resultTextArea.setText(""); // Clear previous results
        if (results.isEmpty()) {
            resultTextArea.append("Nie znaleziono żadnego obiektu!");
        } else {
            for (Object result : results) {
                resultTextArea.append(result.toString() + "\n");
            }
        }
    }

}
