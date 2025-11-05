import Mod.AccumulatingIterator;
import Zad1.*;
import Zad2.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("\nZAD 1");
        zad1();

        System.out.println("\nZAD 2");
        zad2();

        System.out.println("\nZAD 3");
        mod();

    }

    static void zad1() {
        Integer[] array1 = {1, 2, 3, 4, 5};
        Float[] array2 = {1.2f, -0.3f, 152.167f, -8675.1234f, 51.85f};
        Character[] array3 = {'a', 'b', 'c', 'd'};

        Table<Integer> table1 = new Table<>(array1);
        Table<Float> table2 = new Table<>(array2);
        Table<Character> table3 = new Table<>(array3);

        for (Integer element : table1) {
            System.out.println(element);
        }
        for (Float element : table2) {
            System.out.println(element);
        }
        for (Character element : table3) {
            System.out.println(element);
        }

    }

    static void zad2() {
        Integer[] array1 = {1, 2, 3};
        Table<Integer> table1 = new Table<>(array1);
        ShiftIterator<Integer> shiftIterator1 = new ShiftIterator<>(table1.iterator());
        for (int i = 0; i < 10; i++) {
            System.out.println(shiftIterator1.next());;
        }

        Character[] array2 = {'a'};
        Table<Character> table2 = new Table<>(array2);
        ShiftIterator<Character> shiftIterator2 = new ShiftIterator<>(table2.iterator());
        for (int i = 0; i < 2; i++) {
            System.out.println(shiftIterator2.next());
        }
    }

    static void mod() {
        Double[] list = {1.142, 2.415, 3.1, 4.0, 5.15, 6.72};
        Table<Double> table1 = new Table<>(list);
        AccumulatingIterator accIt = new AccumulatingIterator(table1.iterator());
        while (accIt.hasNext()) {
            System.out.println(accIt.next());
        }

        Double[] list2 = null;
        Table<Double> table2 = new Table<>(list2);
        AccumulatingIterator accIt2 = new AccumulatingIterator(table2.iterator());
        while (accIt2.hasNext()) {
            System.out.println(accIt2.next());
        }

    }
}
