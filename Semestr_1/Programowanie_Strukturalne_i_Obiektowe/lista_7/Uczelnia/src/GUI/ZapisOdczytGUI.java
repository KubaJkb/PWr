package GUI;

import Uczelnia.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class ZapisOdczytGUI extends JFrame {
    private UczelniaGUI uczelniaGUI;
    private JComboBox<String> operationComboBox;
    private JComboBox<String> listComboBox;
    private JTextArea resultTextArea;

    public ZapisOdczytGUI(UczelniaGUI uczelniaGUI) {
        this.uczelniaGUI = uczelniaGUI;

        // Initialize components
        operationComboBox = new JComboBox<>(new String[]{"Zapisz", "Odczytaj"});
        listComboBox = new JComboBox<>(new String[]{"Osoby", "Kursy"});
        resultTextArea = new JTextArea(10, 30);

        // Set layout manager
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Add components to the frame
        add(new JLabel("Wybierz opcję:"));
        add(operationComboBox);
        add(new JLabel("Wybierz listę:"));
        add(listComboBox);
        add(new JLabel("Wynik:"));
        add(new JScrollPane(resultTextArea));

        // Set up action listener for the execute button
        JButton executeButton = new JButton("Wykonaj");
        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeOperation();
            }
        });
        add(executeButton);

        // Set frame properties
        setTitle("Zapisywanie i odczytywanie");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void executeOperation() {
        String selectedOperation = (String) operationComboBox.getSelectedItem();
        String selectedList = (String) listComboBox.getSelectedItem();

        if ("Zapisz".equals(selectedOperation)) {
            zapisz(selectedList);
        } else if ("Odczytaj".equals(selectedOperation)) {
            odczytaj(selectedList);
        }
    }

    private void zapisz(String listType) {
        switch (listType) {
            case "Osoby":
                try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("osoby.ser"))) {
                    os.writeObject(uczelniaGUI.uczelnia.getOsoby());
                    resultTextArea.setText("Zapisano listę osób do pliku: osoby.ser");
                } catch (IOException e) {
                    e.printStackTrace();
                    resultTextArea.setText("Błąd podczas zapisywania listy osób");
                }
                break;
            case "Kursy":
                try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("kursy.ser"))) {
                    os.writeObject(uczelniaGUI.uczelnia.getKursy());
                    resultTextArea.setText("Zapisano listę kursów do pliku: kursy.ser");
                } catch (IOException e) {
                    e.printStackTrace();
                    resultTextArea.setText("Błąd podczas zapisywania listy kursów");
                }
                break;
            default:
                resultTextArea.setText("Wybrano nieprawidłową listę!");
        }
    }

    private void odczytaj(String listType) {
        switch (listType) {
            case "Osoby":
                try (ObjectInputStream is = new ObjectInputStream(new FileInputStream("osoby.ser"))) {
                    uczelniaGUI.uczelnia.setOsoby((ArrayList<Osoba>) is.readObject());
                    resultTextArea.setText("Odczytano listę osób z pliku: osoby.ser");
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    resultTextArea.setText("Błąd podczas odczytywania listy osób");
                }
                break;
            case "Kursy":
                try (ObjectInputStream is = new ObjectInputStream(new FileInputStream("kursy.ser"))) {
                    uczelniaGUI.uczelnia.setKursy((ArrayList<Kurs>) is.readObject());
                    resultTextArea.setText("Odczytano listę kursów z pliku: kursy.ser");
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    resultTextArea.setText("Błąd podczas odczytywania listy kursów");
                }
                break;
            default:
                resultTextArea.setText("Wybrano nieprawidłową listę!");
        }
    }
}

