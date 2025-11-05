import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MaxHeap<T extends Comparable<T>> implements BinaryHeap<T> {

    private TreeNode<T> root;
    private int size;

    public MaxHeap() {
        this.root = null;
        this.size = 0;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public void add(T element) {
        if (isEmpty()) {
            root = new TreeNode<>(element);
            size++;
            return;
        }

        TreeNode<T> parent = getParent(++size);
        if (size % 2 == 0) {
            parent.left = new TreeNode<>(element);
        } else {
            parent.right = new TreeNode<>(element);
        }

        swim(size);
    }

    @Override
    public T maximum() {
        if (isEmpty()) {
            throw new EmptyHeapException();
        }

        T maxElement = root.val;

        removeMax();

        return maxElement;
    }

    public void widePrint() {
        Queue<TreeNode<T>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode<T> tempNode = queue.poll();
            System.out.print(tempNode.val + " ");

            if (tempNode.left != null) {
                queue.add(tempNode.left);
            }

            if (tempNode.right != null) {
                queue.add(tempNode.right);
            }
        }
    }

    private TreeNode<T> get(int index) {
        List<Integer> steps = new LinkedList<>();
        while (index > 1) {
            steps.add(index % 2);
            index /= 2;
        }

        TreeNode<T> current = root;
        while (!steps.isEmpty()) {
            if (steps.removeLast() == 0) {
                current = current.left;
            } else { // step == 1
                current = current.right;
            }
        }

        return current;
    }

    private TreeNode<T> getParent(int index) {
        if (index <= 1) {
            return null;
        }

        return get(index / 2);
    }

    private void swim(int index) {
        if (index <= 1) {
            return;
        }

        TreeNode<T> current = get(index);
        TreeNode<T> parent = getParent(index);

        while (index > 1 && current.val.compareTo(parent.val) > 0) {
            swap(current, parent);

            index /= 2;
            current = parent;
            parent = getParent(index);
        }
    }

    private boolean isEmpty() {
        return root == null;
    }

    private void removeMax() {
        if (size == 1) {
            clear();
            return;
        }

        swap(root, get(size));

        TreeNode<T> parent = getParent(size);
        if (size % 2 == 0) {
            parent.left = null;
        } else { // size % 2 == 1
            parent.right = null;
        }

        size--;

        sink(1);
    }

    private void sink(int index) {
        TreeNode<T> current = root;

        while (current.left != null) {
            if (current.right != null && current.right.val.compareTo(current.val) > 0 && current.right.val.compareTo(current.left.val) > 0) {
                swap(current, current.right);
                current = current.right;
            } else if (current.left.val.compareTo(current.val) > 0) {
                swap(current, current.left);
                current = current.left;
            } else {
                break;
            }
        }

    }

    private void swap(TreeNode<T> node1, TreeNode<T> node2) {
        T temp = node1.val;
        node1.val = node2.val;
        node2.val = temp;
    }


    // ------------- KLASA WĘZŁA KOPCA -------------
    private static class TreeNode<T> {
        private T val; // Wartość węzła
        private TreeNode<T> left; // Lewy potomek
        private TreeNode<T> right; // Prawy potomek

        public TreeNode(T val) {
            this.val = val;
            this.left = null;
            this.right = null;
        }
    }

    private static class EmptyHeapException extends RuntimeException {
        public EmptyHeapException() {
            super("Heap is empty");
        }
    }

}
