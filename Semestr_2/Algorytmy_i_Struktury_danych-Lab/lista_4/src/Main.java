import LinkedList.TwoWayLinkedList;

import javax.lang.model.element.Element;

public class Main {
    public static void main(String[] args) {

        modTest1();
        modTest2();
        modTest3();

    }

    public static void listTest() {

        // Create a new instance of TwoWayLinkedList
        TwoWayLinkedList<Integer> list = new TwoWayLinkedList<>();

        // Test adding elements
        list.add(1);
        list.add(2);
        list.add(3);

        // Test adding elements at specific index
        list.add(1, 10);
        list.add(3, 20);

        // Test size and printing the list
        System.out.println("Size of the list: " + list.size());
        System.out.println("Elements of the list:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println("Element at index " + i + ": " + list.get(i));
        }

        // Test removing elements
        list.remove(2);
        list.remove(Integer.valueOf(20));

        // Test size and printing the modified list
        System.out.println("\nSize of the modified list: " + list.size());
        System.out.println("Elements of the modified list:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println("Element at index " + i + ": " + list.get(i));
        }

        // Test iteration using iterator
        System.out.println("\nIterating through the list using iterator:");
        for (Integer value : list) {
            System.out.println("Element value: " + value);
        }

        // Test iteration using list iterator
        System.out.println("\nIterating through the list using list iterator (forward):");
        java.util.ListIterator<Integer> iterator = list.listIterator();
        while (iterator.hasNext()) {
            System.out.println("Element value: " + iterator.next());
        }

        System.out.println("\nIterating through the list using list iterator (backward):");
        while (iterator.hasPrevious()) {
            System.out.println("Element value: " + iterator.previous());
        }
    }
    public static void modTest1() {
        TwoWayLinkedList<Integer> list = new TwoWayLinkedList<>();
        int n;

        for (int i = 0; i < 7; i++) {
            list.add(i + 1);
        }
        System.out.println("\nLista:");
        for (Integer num : list) {
            System.out.print(num + " ");
        }
        System.out.println();

        n = 3;
        list.rotateLeft(n);
        System.out.println("\n Rotate: " + n);
        for (Integer num : list) {
            System.out.print(num + " ");
        }

        n = 4;
        list.rotateLeft(n);
        System.out.println("\n Rotate: " + n);
        for (Integer num : list) {
            System.out.print(num + " ");
        }

        n = 10;
        list.rotateLeft(n);
        System.out.println("\n Rotate: " + n);
        for (Integer num : list) {
            System.out.print(num + " ");
        }

        n = 4;
        list.rotateLeft(n);
        System.out.println("\n Rotate: " + n);
        for (Integer num : list) {
            System.out.print(num + " ");
        }

        n = 0;
        list.rotateLeft(n);
        System.out.println("\n Rotate: " + n);
        for (Integer num : list) {
            System.out.print(num + " ");
        }

        n = 7;
        list.rotateLeft(n);
        System.out.println("\n Rotate: " + n);
        for (Integer num : list) {
            System.out.print(num + " ");
        }

    }
    public static void modTest2() {
        TwoWayLinkedList<Object> list = new TwoWayLinkedList<>();
        int n;

        System.out.println("\nLista:");
        for (Object num : list) {
            System.out.print(num + " ");
        }
        System.out.println();

        n = 3;
        list.rotateLeft(n);
        System.out.println("\n Rotate: " + n);
        for (Object num : list) {
            System.out.print(num + " ");
        }

        n = 4;
        list.rotateLeft(n);
        System.out.println("\n Rotate: " + n);
        for (Object num : list) {
            System.out.print(num + " ");
        }

        n = 10;
        list.rotateLeft(n);
        System.out.println("\n Rotate: " + n);
        for (Object num : list) {
            System.out.print(num + " ");
        }

        list.add(null);
        list.add(null);
        list.add(null);
        System.out.println("\nLista:");
        for (Object num : list) {
            System.out.print(num + " ");
        }
        System.out.println();

        n = 4;
        list.rotateLeft(n);
        System.out.println("\n Rotate: " + n);
        for (Object num : list) {
            System.out.print(num + " ");
        }

        n = 0;
        list.rotateLeft(n);
        System.out.println("\n Rotate: " + n);
        for (Object num : list) {
            System.out.print(num + " ");
        }

        n = 7;
        list.rotateLeft(n);
        System.out.println("\n Rotate: " + n);
        for (Object num : list) {
            System.out.print(num + " ");
        }

    }
    public static void modTest3() {
        TwoWayLinkedList<Character> list = new TwoWayLinkedList<>();
        int n;

        list.add('a');
        list.add('b');
        list.add('c');
        list.add('d');
        list.add('e');
        list.add('f');

        System.out.println("\nLista:");

        for (Character elem : list) {
            System.out.print(elem + " ");
        }
        System.out.println();

        n = 3;
        list.rotateLeft(n);
        System.out.println("\n Rotate: " + n);
        for (Character elem : list) {
            System.out.print(elem + " ");
        }

        n = 4;
        list.rotateLeft(n);
        System.out.println("\n Rotate: " + n);
        for (Character num : list) {
            System.out.print(num + " ");
        }

        n = 10;
        list.rotateLeft(n);
        System.out.println("\n Rotate: " + n);
        for (Character num : list) {
            System.out.print(num + " ");
        }

        n = 4;
        list.rotateLeft(n);
        System.out.println("\n Rotate: " + n);
        for (Character num : list) {
            System.out.print(num + " ");
        }

        n = 0;
        list.rotateLeft(n);
        System.out.println("\n Rotate: " + n);
        for (Character num : list) {
            System.out.print(num + " ");
        }

        n = 7;
        list.rotateLeft(n);
        System.out.println("\n Rotate: " + n);
        for (Character num : list) {
            System.out.print(num + " ");
        }

    }
}
