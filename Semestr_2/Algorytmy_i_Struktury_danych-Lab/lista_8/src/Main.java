import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        modtest1();
        modtest2();
        modtest3();
    }

    static void test1() {
        BST<Integer> tree = new BST<>(new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        tree.insert(7);
        tree.insert(5);
        tree.insert(2);
        tree.insert(10);
        tree.insert(12);
        IntegerToStringExec exec = new IntegerToStringExec();
        tree.inOrderWalk(exec);
        System.out.println(exec.getResult());
    }

    static void test2() {
        BST<Integer> tree = new BST<>(new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        tree.insert(7);
        tree.insert(6);
        tree.insert(10);
        tree.insert(3);
        tree.insert(8);
        tree.insert(12);
        tree.insert(4);
        tree.insert(9);
        tree.insert(5);

        tree.delete(7);
        tree.delete(8);
        tree.delete(10);

        System.out.println();
    }

    static void modtest1() {
        BST<Integer> tree = new BST<>(new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        tree.insert(10);
        tree.insert(7);
        tree.insert(6);
        tree.insert(8);
        tree.insert(9);
        tree.insert(21);
        tree.insert(11);
        tree.insert(27);
        tree.insert(25);

        BST<Integer> newTree = tree.mostInbalancedSubtree();
        System.out.println();
    }

    static void modtest2() {
        BST<Integer> tree = new BST<>(new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        tree.insert(7);
        tree.insert(5);
        tree.insert(3);
        tree.insert(2);
        tree.insert(4);
        tree.insert(10);
        tree.insert(8);
        tree.insert(12);
        tree.insert(14);
        tree.insert(13);

        BST<Integer> newTree = tree.mostInbalancedSubtree();
        System.out.println();
    }

    static void modtest3() {
        BST<Integer> tree = new BST<>(new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });

        tree.insert(1);

        BST<Integer> newTree = tree.mostInbalancedSubtree();
        System.out.println();
    }

}
