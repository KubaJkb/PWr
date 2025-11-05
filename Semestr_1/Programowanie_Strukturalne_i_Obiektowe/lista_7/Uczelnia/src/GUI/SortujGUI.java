package GUI;

import Uczelnia.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;

public class SortujGUI extends JFrame {
    private UczelniaGUI uczelniaGUI;
    private JComboBox<String> listComboBox;
    private JComboBox<String> criteriaComboBox;
    private JTextArea resultTextArea;

    public SortujGUI(UczelniaGUI uczelniaGUI) {
        this.uczelniaGUI = uczelniaGUI;

        // Initialize components
        listComboBox = new JComboBox<>(new String[]{"Osoby", "Kursy"});
        criteriaComboBox = new JComboBox<>();
        resultTextArea = new JTextArea(10, 30);

        // Set layout manager
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Add components to the frame
        add(new JLabel("Wybierz listę do posortowania:"));
        add(listComboBox);
        add(new JLabel("Wybierz kryteria sortowania:"));
        add(criteriaComboBox);
        add(new JLabel("Wynik sortowania:"));
        add(new JScrollPane(resultTextArea));

        // Set up action listener for the sort button
        JButton sortButton = new JButton("Sortuj");
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sort();
            }
        });
        add(sortButton);

        // Set frame properties
        setTitle("Sortowanie");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Set up criteria combo box based on the selected list
        listComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCriteriaComboBox();
            }
        });
        updateCriteriaComboBox();
    }

    private void updateCriteriaComboBox() {
        criteriaComboBox.removeAllItems();
        String selectedList = (String) listComboBox.getSelectedItem();

        if ("Osoby".equals(selectedList)) {
            criteriaComboBox.addItem("Nazwisko");
            criteriaComboBox.addItem("Nazwisko i Imię");
            criteriaComboBox.addItem("Nazwisko i Wiek");
        } else if ("Kursy".equals(selectedList)) {
            criteriaComboBox.addItem("Punkty ECTS");
            criteriaComboBox.addItem("Nazwisko Prowadzącego");
        }
    }

    private void sort() {
        String selectedList = (String) listComboBox.getSelectedItem();
        String selectedCriteria = (String) criteriaComboBox.getSelectedItem();

        if ("Osoby".equals(selectedList)) {
            sortOsoby(selectedCriteria);
        } else if ("Kursy".equals(selectedList)) {
            sortKursy(selectedCriteria);
        }
    }

    private void sortOsoby(String criteria) {
        Comparator<Osoba> comparator = null;

        switch (criteria) {
            case "Nazwisko":
                comparator = Comparator.comparing(Osoba::getNazwisko);
                break;
            case "Nazwisko i Imię":
                comparator = Comparator.comparing(Osoba::getNazwisko).thenComparing(Osoba::getImie);
                break;
            case "Nazwisko i Wiek":
                comparator = Comparator.comparing(Osoba::getNazwisko)
                        .thenComparing(Comparator.comparingInt(Osoba::getWiek).reversed());
                break;
        }

        if (comparator != null) {
            uczelniaGUI.uczelnia.getOsoby().sort(comparator);
            resultTextArea.setText("Psortowano osoby według " + criteria);
        } else {
            resultTextArea.setText("Nieprawidłowe kryteria wyszukiwania!");
        }
    }

    private void sortKursy(String criteria) {
        Comparator<Kurs> comparator = null;

        switch (criteria) {
            case "Punkty ECTS":
                comparator = Comparator.comparing(Kurs::getPunktyECTS);
                break;
            case "Nazwisko Prowadzącego":
                comparator = Comparator.comparing(Kurs::getNazwiskoProwadzacego);
                break;
        }

        if (comparator != null) {
            uczelniaGUI.uczelnia.getKursy().sort(comparator);
            resultTextArea.setText("Posortowano kursy według " + criteria);
        } else {
            resultTextArea.setText("Nieprawidłowe kryteria wyszukiwania!");
        }
    }
}
