package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import DataProcessing.Uczelnia;

public class UczelniaGUI extends JFrame {
    public Uczelnia uczelnia;

    public UczelniaGUI(Uczelnia uczelnia) {
        this.uczelnia = uczelnia;

        setTitle("Uczelnia");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        class MainPanel extends JPanel implements ActionListener {

            private JFrame parentFrame;

            final JButton button1 = new JButton("Wyszukaj obiekt");
            final JButton button2 = new JButton("Wyświetl obiekty klasy");
            final JButton button3 = new JButton("Utwórz nowy obiekt");
            final JButton button4 = new JButton("Usuń obiekt");
//            final JButton button5 = new JButton("Przypisz kurs do studenta");
            final JButton button6 = new JButton("Posortuj listę");
            final JButton button7 = new JButton("Usuń duplikaty");
            final JButton button8 = new JButton("Zapisz/Odczytaj dane do/z pliku");
            final JButton button0 = new JButton("Zakończ");

            MainPanel(JFrame parentFrame) {
                this.parentFrame = parentFrame;

                createMainPanel();
            }

            public void createMainPanel(){
                removeAll();

                GridBagLayout gbl = new GridBagLayout();
                setLayout(gbl);

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.insets = new Insets(10,10,10,10);

                // Add buttons for each option
                gbc.gridx = 0; gbc.gridy = 0;
                add(button1, gbc);

                gbc.gridx = 1; gbc.gridy = 0;
                add(button2, gbc);

                gbc.insets.top = 20;
                gbc.gridx = 0; gbc.gridy = 1;
                gbc.gridwidth = 2;
                add(new JSeparator(SwingConstants.HORIZONTAL), gbc);
                gbc.insets.top = 20;

                gbc.gridwidth = 1;
                gbc.gridx = 0; gbc.gridy = 2;
                add(button3, gbc);

                gbc.gridx = 1; gbc.gridy = 2;
                add(button4, gbc);

                gbc.insets.top = 10;

//                gbc.gridx = 0; gbc.gridy = 3;
//                gbc.gridwidth = 2;
//                add(button5, gbc);

                gbc.insets.top = 20;
                gbc.gridx = 0; gbc.gridy = 4;
                gbc.gridwidth = 2;
                add(new JSeparator(SwingConstants.HORIZONTAL), gbc);
                gbc.insets.top = 20;


                gbc.gridwidth = 1;
                gbc.gridx = 0; gbc.gridy = 5;
                add(button6, gbc);

                gbc.gridx = 1; gbc.gridy = 5;
                add(button7, gbc);

                gbc.insets.top = 20;
                gbc.gridx = 0; gbc.gridy = 6;
                gbc.gridwidth = 2;
                add(new JSeparator(SwingConstants.HORIZONTAL), gbc);
                gbc.insets.top = 20;

                gbc.gridx = 0; gbc.gridy = 7;
                add(button8, gbc);

                gbc.insets.top = 90;

                gbc.gridx = 0; gbc.gridy = 8;
                add(button0, gbc);

                button1.addActionListener(this);
                button2.addActionListener(this);
                button3.addActionListener(this);
                button4.addActionListener(this);
//                button5.addActionListener(this);
                button6.addActionListener(this);
                button7.addActionListener(this);
                button8.addActionListener(this);
                button0.addActionListener(this);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();

                if (source == button0) {
                    System.exit(0);
                } else if (source == button1) {
                    uczelnia.wyszukaj();
                } else if (source == button2) {
                    uczelnia.wyswietl();
                } else if (source == button3) {
                    uczelnia.utworzObiekt();
                } else if (source == button4) {
                    uczelnia.usunElement();
//                } else if (source == button5) {
//                    uczelnia.przypiszKurs();
                } else if (source == button6) {
                    uczelnia.sortuj();
                } else if (source == button7) {
                    uczelnia.usunDuplikaty();
                } else if (source == button8) {
                    uczelnia.zapiszodczytaj();
                }
            }
        }

        MainPanel mainPanel = new MainPanel(this);
        add(mainPanel);

        setVisible(true);
    }

}
