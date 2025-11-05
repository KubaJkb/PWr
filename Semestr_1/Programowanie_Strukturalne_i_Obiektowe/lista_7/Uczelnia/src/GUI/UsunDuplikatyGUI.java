package GUI;

import Uczelnia.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;

public class UsunDuplikatyGUI extends JFrame {
    private UczelniaGUI uczelniaGUI;
    private JTextArea resultTextArea;

    public UsunDuplikatyGUI(UczelniaGUI uczelniaGUI) {
        this.uczelniaGUI = uczelniaGUI;

        // Initialize components
        resultTextArea = new JTextArea(10, 30);

        // Set layout manager
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Add components to the frame
        add(new JLabel("Wynik:"));
        add(new JScrollPane(resultTextArea));

        // Set up action listener for the execute button
        JButton executeButton = new JButton("Wykonaj");
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usunDuplikaty();
            }
        });
        add(executeButton);

        // Set frame properties
        setTitle("Usuwanie Duplikatów");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void usunDuplikaty() {
        ArrayList<Osoba> osobyBezDuplikatow = new ArrayList<>();
        osobyBezDuplikatow.addAll(usunDuplikatyStudent());
        osobyBezDuplikatow.addAll(usunDuplikatyPracownik());
        uczelniaGUI.uczelnia.setOsoby(osobyBezDuplikatow);
        resultTextArea.setText("Usunięto duplikaty Studentów i Pracowników");
    }

    private ArrayList<Osoba> usunDuplikatyStudent() {
        HashSet<Student> uniqueStudent = new HashSet<>();
        for (Osoba osoba : uczelniaGUI.uczelnia.getOsoby()) {
            if (osoba instanceof Student) {
                uniqueStudent.add((Student) osoba);
            }
        }
        return new ArrayList<>(uniqueStudent);
    }

    private ArrayList<Osoba> usunDuplikatyPracownik() {
        HashSet<PracownikUczelni> uniquePracownik = new HashSet<>();
        for (Osoba osoba : uczelniaGUI.uczelnia.getOsoby()) {
            if (osoba instanceof PracownikAdministracyjny) {
                uniquePracownik.add((PracownikAdministracyjny) osoba);
            } else if (osoba instanceof PracownikBadawczoDydaktyczny) {
                uniquePracownik.add((PracownikBadawczoDydaktyczny) osoba);
            }
        }
        return new ArrayList<>(uniquePracownik);
    }

}

