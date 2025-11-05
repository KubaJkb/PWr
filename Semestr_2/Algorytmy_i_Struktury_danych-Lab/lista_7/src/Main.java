import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        test1();
        test2();
        test3();

//        modTest();

    }

    static void modTest() {
        MaxHeap<Integer> maxHeap = new MaxHeap<>();

        maxHeap.add(100);
        maxHeap.add(19);
        maxHeap.add(36);
        maxHeap.add(17);
        maxHeap.add(3);
        maxHeap.add(25);
        maxHeap.add(1);
        maxHeap.add(2);
        maxHeap.add(7);

        maxHeap.widePrint();

    }

    static void test1() {
        MaxHeap<Integer> maxHeap = new MaxHeap<>();

        maxHeap.add(100);
        maxHeap.add(19);
        maxHeap.add(36);
        maxHeap.add(17);
        maxHeap.add(3);
        maxHeap.add(25);
        maxHeap.add(1);
        maxHeap.add(2);
        maxHeap.add(7);

        System.out.println("Utworzono kopiec");

        maxHeap.add(49);

        System.out.println("Dodano element i naprawiono kopiec");

        System.out.print(maxHeap.maximum() + " ");

        System.out.println("Usunięto największy element i naprawiono kopiec");
    }

    static void test2() {
        MaxHeap<Integer> maxHeap = new MaxHeap<>();

        maxHeap.add(1);
        maxHeap.maximum();

        System.out.println("Dodano i usunięto jeden element");
    }

    static void test3() {
        MaxHeap<Integer> maxHeap = new MaxHeap<>();

        maxHeap.add(1);
        maxHeap.add(2);
        maxHeap.clear();

        System.out.println("Dodano dwa elementy i wyczyszczono kopiec");
    }
}
