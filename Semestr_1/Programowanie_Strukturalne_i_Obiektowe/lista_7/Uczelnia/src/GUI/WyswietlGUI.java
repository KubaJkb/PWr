package GUI;

import Uczelnia.*;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WyswietlGUI extends JFrame {
    private UczelniaGUI uczelniaGUI;
    private JComboBox<String> displayOptionComboBox;
    private JTextArea resultTextArea;

    public WyswietlGUI(UczelniaGUI uczelniaGUI) {
        this.uczelniaGUI = uczelniaGUI;

        // Initialize components
        displayOptionComboBox = new JComboBox<>(new String[]{"Wszyscy Pracownicy", "Wszyscy Studenci", "Wszystkie Kursy", "Wszyscy"});
        resultTextArea = new JTextArea(10, 30);

        // Set layout manager
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Add components to the frame
        add(new JLabel("Wybierz opcję wyświetlania:"));
        add(displayOptionComboBox);
        add(new JLabel("Wynik wyświetlania:"));
        add(new JScrollPane(resultTextArea));

        // Set up action listener for the display button
        JButton displayButton = new JButton("Wyświetl");
        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display();
            }
        });
        add(displayButton);

        // Set frame properties
        setTitle("Wyświetlanie");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void display() {
        String displayOption = (String) displayOptionComboBox.getSelectedItem();

        switch (displayOption) {
            case "Wszyscy Pracownicy":
                displayOsoby(uczelniaGUI.uczelnia.getPracownicy());
                break;
            case "Wszyscy Studenci":
                displayOsoby(uczelniaGUI.uczelnia.getStudenci());
                break;
            case "Wszystkie Kursy":
                displayKursy();
                break;
            case "Wszyscy":
                displayOsoby(uczelniaGUI.uczelnia.getOsoby());
                break;
            default:
                resultTextArea.setText("Invalid display option selected");
        }
    }

    private void displayOsoby(ArrayList<Osoba> osoby) {
        resultTextArea.setText(""); // Clear previous results
        for (Osoba osoba : osoby) {
            resultTextArea.append(osoba.toString() + "\n");
        }
    }

    private void displayKursy() {
        resultTextArea.setText(""); // Clear previous results
        for (Kurs kurs : uczelniaGUI.uczelnia.getKursy()) {
            resultTextArea.append(kurs.toString() + "\n");
        }
    }

    public static void main(String[] args) {
        // You may need to instantiate UczelniaGUI and pass it to WyswietlGUI
        // UczelniaGUI uczelniaGUI = new UczelniaGUI();
        // WyswietlGUI wyswietlGUI = new WyswietlGUI(uczelniaGUI);
    }
}
