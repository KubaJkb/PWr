import BH.BinomialHeap;
import RBT.RedBlackTree;
import RBT.Tree;

import java.util.Comparator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

//        zad1();
        zad2();
//        zad3();

    }

    static void zad1() {
        Tree<Integer> redBlackTree = new RedBlackTree<>(new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });

        Scanner scanner = new Scanner(System.in);

        for (int i = 1; i <= 20; i++) {
            redBlackTree.insert(i);
            redBlackTree.printTree();

            scanner.nextLine();

        }

    }

    static void zad2() {
        Tree<Integer> redBlackTree = new RedBlackTree<>(new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });

        Scanner scanner = new Scanner(System.in);

        redBlackTree.insert(15);
        redBlackTree.printTree();
        scanner.nextLine();
        redBlackTree.insert(10);
        redBlackTree.printTree();
        scanner.nextLine();
        redBlackTree.insert(5);
        redBlackTree.printTree();
        scanner.nextLine();
        redBlackTree.insert(20);
        redBlackTree.printTree();
        scanner.nextLine();
        redBlackTree.insert(30);
        redBlackTree.printTree();
        scanner.nextLine();
        redBlackTree.insert(35);
        redBlackTree.printTree();
        scanner.nextLine();
        redBlackTree.insert(7);
        redBlackTree.printTree();
        scanner.nextLine();
        redBlackTree.insert(6);
        redBlackTree.printTree();
        scanner.nextLine();
        redBlackTree.insert(8);
        redBlackTree.printTree();
        scanner.nextLine();
        redBlackTree.insert(3);
        redBlackTree.printTree();
        scanner.nextLine();
        redBlackTree.insert(33);
        redBlackTree.printTree();
        scanner.nextLine();
        redBlackTree.delete(8);
        redBlackTree.printTree();
        scanner.nextLine();
        redBlackTree.delete(10);
        redBlackTree.printTree();
        scanner.nextLine();
        redBlackTree.delete(33);
        redBlackTree.printTree();
        scanner.nextLine();

    }

    static void zad3() {
        BinomialHeap binHeap = new BinomialHeap();

        binHeap.insert(1);
        binHeap.displayHeap();
        binHeap.insert(10);
        binHeap.displayHeap();
        binHeap.insert(4);
        binHeap.displayHeap();
        binHeap.insert(15);
        binHeap.displayHeap();
        binHeap.insert(5);
        binHeap.displayHeap();
        binHeap.insert(8);
        binHeap.displayHeap();
        binHeap.insert(3);
        binHeap.displayHeap();
        binHeap.insert(2);
        binHeap.displayHeap();
        binHeap.insert(14);
        binHeap.displayHeap();
        binHeap.insert(9);
        binHeap.displayHeap();
        binHeap.insert(7);
        binHeap.displayHeap();
        binHeap.extractMin();
        binHeap.displayHeap();
        binHeap.extractMin();
        binHeap.displayHeap();
        binHeap.extractMin();
        binHeap.displayHeap();
        binHeap.extractMin();
        binHeap.displayHeap();

    }
}
