import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
//        test1Insertion();
//        test2Insertion();
//        test1Deletion();
        test2Deletion();
    }

    static void test1Insertion() {
        BTree<Integer> bTree = new BTree<>(Comparator.comparingInt(o -> o), 3);

        bTree.insert(1);
        bTree.insert(2);
        bTree.insert(10);
        bTree.insert(11);
        bTree.insert(12);
        bTree.insert(15);
        bTree.insert(16);
        bTree.insert(17);
        bTree.insert(25);
        bTree.insert(30);
        bTree.insert(40);
        bTree.insert(70);
        bTree.insert(80);
        bTree.insert(90);
        bTree.insert(100);
        bTree.insert(110);
        bTree.insert(120);

        bTree.insert(130);
        bTree.delete(130);

        bTree.insert(18);
        bTree.insert(19);
        bTree.insert(20);
        bTree.insert(21);
        bTree.insert(3);
        bTree.insert(13);

        bTree.insert(4);
        bTree.insert(5);
        bTree.insert(6);
        bTree.insert(7);
        bTree.insert(8);
        bTree.insert(9);
        bTree.insert(-30);
        bTree.insert(-20);
        bTree.insert(-10);
        bTree.traverse();

        bTree.insert(-15);
        bTree.traverse();
    }

    static void test2Insertion() {
        BTree<Integer> bTree = new BTree<>(Comparator.comparingInt(o -> o), 2);

        for (int i = 1; i <= 50; i++) {
            bTree.insert(i * 5);
        }
        bTree.traverse();
    }

    static void test1Deletion() {
        BTree<Integer> t = new BTree<>(Comparator.comparingInt(o -> o), 3);

        t.insert(1);
        t.insert(2);
        t.insert(10);
        t.insert(11);
        t.insert(12);
        t.insert(15);
        t.insert(16);
        t.insert(17);
        t.insert(25);
        t.insert(30);
        t.insert(40);
        t.insert(70);
        t.insert(80);
        t.insert(90);
        t.insert(100);
        t.insert(110);
        t.insert(120);

        t.insert(130);
        t.delete(130);

        t.insert(18);
        t.insert(19);
        t.insert(20);
        t.insert(21);
        t.insert(3);
        t.insert(13);

        System.out.println("Constructed tree is:");
        t.traverse();

        t.delete(3);
        System.out.println("\nTree after deletion of 3:");
        t.traverse();

        t.delete(17);
        System.out.println("\nTree after deletion of 17:");
        t.traverse();

        t.delete(12);
        System.out.println("\nTree after deletion of 12:");
        t.traverse();
    }

    static void test2Deletion() {
        BTree<Integer> t = new BTree<>(Comparator.comparingInt(o -> o), 3);

        t.insert(1);
        t.insert(2);
        t.insert(10);
        t.insert(11);
        t.insert(12);
        t.insert(15);
        t.insert(16);
        t.insert(17);
        t.insert(25);
        t.insert(30);
        t.insert(40);
        t.insert(70);
        t.insert(80);
        t.insert(90);
        t.insert(100);
        t.insert(110);
        t.insert(120);

        t.insert(130);
        t.delete(130);

        t.insert(18);
        t.insert(19);
        t.insert(20);
        t.insert(21);
        t.insert(3);
        t.insert(13);

        t.delete(13);

        System.out.println("Constructed tree is:");
        t.traverse();

        t.delete(10);
        System.out.println("\nTree after deletion of 10:");
        t.traverse();

        t.delete(20);
        System.out.println("\nDelete 20");
        t.traverse();

        t.delete(15);
        System.out.println("\nTree after deletion of 15:");
        t.traverse();

        t.insert(20);
        System.out.println("\nInsert 20");
        t.traverse();

        t.delete(25);
        System.out.println("\nTree after deletion of 25:");
        t.traverse();

        t.delete(21);
        System.out.println("\nTree after deletion of 21:");
        t.traverse();
    }
}
