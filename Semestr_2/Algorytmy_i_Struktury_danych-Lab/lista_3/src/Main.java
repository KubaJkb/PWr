public class Main {
    public static void main(String[] args) {

//        testEmptyList();
//        testSingleElementList();
        testEvenNumberOfElements();
//        testOddNumberOfElements();
    }

    private static void testEmptyList() {
        System.out.println("Testing empty list:");
        OneWayCompressingList4<Integer> list = new OneWayCompressingList4<>(3);
        System.out.println("Size of list: " + list.size());
        System.out.println("Is list empty? " + list.isEmpty());
        System.out.println();
    }

    private static void testSingleElementList() {
        System.out.println("Testing single element list:");
        OneWayCompressingList4<String> list = new OneWayCompressingList4<>(3);
        list.add("One");
        System.out.println("Size of list: " + list.size());
        System.out.println("Is list empty? " + list.isEmpty());
        System.out.println("Element at index 0: " + list.get(0));
        System.out.println();
    }

    private static void testEvenNumberOfElements() {
        System.out.println("Testing list with even number of elements:");
        OneWayCompressingList4<Integer> list = new OneWayCompressingList4<>(3);
        for (int i = 0; i < 7; i++) {
            list.add(i);
        }
        System.out.println("Size of list: " + list.size());
        System.out.println("Is list empty? " + list.isEmpty());
        for (int i = 0; i < list.size(); i++) {
            System.out.println("Element at index " + i + ": " + list.get(i));
        }
        System.out.println();
    }

    private static void testOddNumberOfElements() {
        System.out.println("Testing list with odd number of elements:");
        OneWayCompressingList4<Character> list = new OneWayCompressingList4<>(3);
        for (char c = 'A'; c <= 'F'; c++) {
            list.add(c);
        }
        System.out.println("Size of list: " + list.size());
        System.out.println("Is list empty? " + list.isEmpty());
        for (int i = 0; i < list.size(); i++) {
            System.out.println("Element at index " + i + ": " + list.get(i));
        }
        System.out.println();
    }
}
