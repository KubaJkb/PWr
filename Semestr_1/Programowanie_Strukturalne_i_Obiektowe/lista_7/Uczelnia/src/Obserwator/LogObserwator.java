package Obserwator;

import javax.swing.*;

public class LogObserwator implements Obserwator {

    @Override
    public void powiadom(String komunikat, JPanel panel) {

        JOptionPane.showMessageDialog(panel, "--- Log: " + komunikat + " ---");

    }
}
