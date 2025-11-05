package Obserwator;

public class LogObserwator implements Obserwator {

    @Override
    public void powiadom(String komunikat) {
        System.out.println("--- Log: " + komunikat + " ---");
    }
}
