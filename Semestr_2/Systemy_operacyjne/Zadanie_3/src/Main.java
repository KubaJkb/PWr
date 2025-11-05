import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int NUMBER_OF_PAGES = 100;
        int NUMBER_OF_REFERENCES = 1000;
        List<Page> pageReferences = createListOfReferences(NUMBER_OF_REFERENCES, NUMBER_OF_PAGES, 0.6);

        List<Integer> NUMBERS_OF_FRAMES;
        NUMBERS_OF_FRAMES = new ArrayList<>(List.of(3, 5, 7, 10, 50, 70));

        for (int i : NUMBERS_OF_FRAMES) {
            Algorithms a = new Algorithms(i, pageReferences);
            System.out.println("\nLiczba braków strony dla " + i + " ramek: ");
            System.out.println("FIFO: " + a.FIFO() + " OPT: " + a.OPT() + " LRU: " + a.LRU() + " ALRU: " + a.ALRU() + " RAND: " + a.RAND());
        }

    }

//    public static List<Page> createListOfReferences(int numberOfReferences, int NUMBER_OF_PAGES) {
//        ArrayList<Page> listOfReferences = new ArrayList<>();
//        Random rand = new Random();
//
//        for (int i = 0; i < numberOfReferences; i++) {
//            listOfReferences.add(new Page(rand.nextInt(NUMBER_OF_PAGES) + 1, 1));
//        }
//
//        return listOfReferences;
//    }

    public static List<Page> createListOfReferences(int numberOfReferences, int NUMBER_OF_PAGES, double locality) {
        ArrayList<Page> listOfReferences = new ArrayList<>();
        Random rand = new Random();
        int lastReference = rand.nextInt(NUMBER_OF_PAGES) + 1; // Początkowe odwołanie

        for (int i = 0; i < numberOfReferences; i++) {
            if (rand.nextDouble() < locality) { // Uwzględnienie lokalności
                // Generowanie odwołań do stron w sąsiedztwie poprzedniego odwołania
                int delta = rand.nextInt(3) - 1; // Losowa wartość -1, 0 lub 1
                int newReference = lastReference + delta;

                // Upewnienie się, że odwołanie znajduje się w zakresie stron
                if (newReference < 1) {
                    newReference = 1;
                } else if (newReference > NUMBER_OF_PAGES) {
                    newReference = NUMBER_OF_PAGES;
                }

                listOfReferences.add(new Page(newReference, 1));
                lastReference = newReference;
            } else {
                // Odwołanie do losowej strony
                listOfReferences.add(new Page(rand.nextInt(NUMBER_OF_PAGES) + 1, 1));
            }
        }

        return listOfReferences;
    }


}
