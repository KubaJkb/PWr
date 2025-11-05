//package GUI;
//
//import Uczelnia.*;
//import Strategia.*;
//
//import javax.swing.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class PrzypiszKursGUI extends JFrame {
//    private JComboBox<String> studentComboBox;
//    private JComboBox<String> actionComboBox;
//    private JTextArea resultTextArea;
//    private JButton performButton;
//
//    private UczelniaGUI uczelniaGUI;
//
//    public PrzypiszKursGUI(UczelniaGUI uczelniaGUI) {
//        this.uczelniaGUI = uczelniaGUI;
//
//        setTitle("Przypisanie Kursu");
//        setSize(400, 350);
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//
//        initComponents();
//        addListeners();
//
//        setLocationRelativeTo(null);
//        setVisible(true);
//    }
//
//    private void initComponents() {
//        studentComboBox = new JComboBox<>();
//        actionComboBox = new JComboBox<>(new String[]{"Dodanie wybranego kursu", "Zapis na wszystkie kursy wg wybranej strategii"});
//        resultTextArea = new JTextArea(10, 30);
//        performButton = new JButton("Wykonaj");
//
//        // Populate studentComboBox with student names
//        for (Osoba osoba : uczelniaGUI.uczelnia.getOsoby()) {
//            if (osoba instanceof Student) {
//                studentComboBox.addItem(osoba.getImie() + " " + osoba.getNazwisko() + " " + osoba.getPesel());
//            }
//        }
//
//        JPanel panel = new JPanel();
//        panel.add(new JLabel("Wybierz studenta:"));
//        panel.add(studentComboBox);
//        panel.add(new JLabel("Wybierz co chcesz zrobić:"));
//        panel.add(actionComboBox);
//        panel.add(performButton);
//        panel.add(new JLabel("Wynik operacji:"));
//        panel.add(new JScrollPane(resultTextArea));
//
//        add(panel);
//    }
//
//    private void addListeners() {
//        performButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                przypiszKurs();
//            }
//        });
//    }
//
//    private void przypiszKurs() {
//        int selectedStudentIndex = studentComboBox.getSelectedIndex();
//        if (selectedStudentIndex == -1) {
//            resultTextArea.setText("Nie wybrano studenta!");
//            return;
//        }
//
//        int selectedActionIndex = actionComboBox.getSelectedIndex();
//        if (selectedActionIndex == -1) {
//            resultTextArea.setText("Nie wybrano akcji!");
//            return;
//        }
//
//        switch (selectedActionIndex) {
//            case 0:
//                performAddSpecificCourse(selectedStudentIndex);
//                break;
//            case 1:
//                performEnrollBasedOnStrategy(selectedStudentIndex);
//                break;
//            default:
//                resultTextArea.setText("Nieznana akcja!");
//        }
//    }
//
//    private void performAddSpecificCourse(int selectedStudentIndex) {
//        int selectedStudentNumber = selectedStudentIndex + 1;
//
//        // Display available courses for selection
//        StringBuilder coursesInfo = new StringBuilder("Dostępne kursy:\n");
//        for (int i = 0; i < uczelniaGUI.uczelnia.getKursy().size(); i++) {
//            coursesInfo.append((i + 1)).append(" : ").append(uczelniaGUI.uczelnia.getKursy().get(i).getNazwaKursu())
//                    .append("   ECTS: ").append(uczelniaGUI.uczelnia.getKursy().get(i).getPunktyECTS()).append("\n");
//        }
//
//        int selectedCourseNumber = Integer.parseInt(JOptionPane.showInputDialog(this, coursesInfo.toString()));
//
//        // Ensure the selected course number is valid
//        if (selectedCourseNumber <= 0 || selectedCourseNumber > uczelniaGUI.uczelnia.getKursy().size()) {
//            resultTextArea.setText("Podałeś niepoprawną wartość!");
//            return;
//        }
//
//        uczelniaGUI.uczelnia.dodajKurs(selectedStudentNumber, selectedCourseNumber - 1);
//        resultTextArea.setText("Przypisano kurs " + uczelniaGUI.uczelnia.getKursy().get(selectedCourseNumber - 1).getNazwaKursu() +
//                " do studenta " + uczelniaGUI.uczelnia.getOsoby().get(selectedStudentNumber - 1).getImie() + " " +
//                uczelniaGUI.uczelnia.getOsoby().get(selectedStudentNumber - 1).getNazwisko());
//    }
//
//    private void performEnrollBasedOnStrategy(int selectedStudentIndex) {
//
//        // Display available strategies for selection
//        Object[] strategies = { "Zapis po nazwie kursu alfabetycznie", "Zapis po punktach ECTS malejąco",
//                "Zapis po liczbie publikacji prowadzącego malejąco" };
//        String selectedStrategy = (String) JOptionPane.showInputDialog(this, "Wybierz strategię:",
//                "Wybór strategii", JOptionPane.QUESTION_MESSAGE, null, strategies, strategies[0]);
//
//        if (selectedStrategy == null) {
//            // User closed the dialog or canceled
//            resultTextArea.setText("Anulowano wybór strategii.");
//            return;
//        }
//
//        StrategiaZapisNaKurs strategy;
//        switch (selectedStrategy) {
//            case "Zapis po nazwie kursu alfabetycznie":
//                strategy = new ZapisAlfabetycznie();
//                break;
//            case "Zapis po punktach ECTS malejąco":
//                strategy = new ZapisPunktyECTS();
//                break;
//            case "Zapis po liczbie publikacji prowadzącego malejąco":
//                strategy = new ZapisProwadzacy();
//                break;
//            default:
//                resultTextArea.setText("Nieznana strategia!");
//                return;
//        }
//
//        uczelniaGUI.uczelnia.getOsoby().set(selectedStudentIndex, strategy.zapiszNaKursy(uczelniaGUI.uczelnia, selectedStudentIndex));
//        resultTextArea.setText("Zapisano na kursy wg strategii: " + selectedStrategy);
//    }
//
//
//}
//
