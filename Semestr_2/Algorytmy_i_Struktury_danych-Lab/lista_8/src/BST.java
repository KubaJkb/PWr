import java.util.Comparator;
import java.util.NoSuchElementException;

public class BST<T> {
    private final Comparator<T> _comparator;
    private Node _root;

    private int biggestInbalance;
    private Node biggestInbalanceNode;
    private int biggestInbalanceHeight;

    public BST(Comparator<T> comp) {
        _comparator = comp;
        _root = null;

        biggestInbalance = 0;
        biggestInbalanceNode = null;
        biggestInbalanceHeight = 0;
    }

    public T find(T elem) {
        Node node = search(_root, elem);
        return node == null ? null : node.value;
    }

    private Node search(Node node, T elem) {
        int cmp;
        if (node == null || (cmp = _comparator.compare(elem, node.value)) == 0) {
            return node;
        } else if (cmp < 0) {
            return search(node.left, elem);
        } else { // cmp > 0
            return search(node.right, elem);
        }
    }

    public T getMin() {
        if (_root == null) {
            throw new NoSuchElementException();
        }

        return getMin(_root).value;
    }

    private Node getMin(Node node) {
        if (node.left == null) {
            return node;
        } else {
            return getMin(node.left);
        }
    }

    public T getMax() {
        if (_root == null) {
            throw new NoSuchElementException();
        }

        return getMax(_root).value;
    }

    private Node getMax(Node node) {
        if (node.right == null) {
            return node;
        } else {
            return getMax(node.right);
        }
    }

    // inOrderWalk *** ITERACYJNIE ***
//    public <R> void inOrderWalk(IExecutor<T, R> exec) {
//        if (_root == null) {
//            return;
//        }
//
//        Stack<Node> stack = new Stack<>();
//        Node current = _root;
//
//        while (current != null || !stack.isEmpty()) {
//            while (current != null) {
//                stack.push(current);
//                current = current.left;
//            }
//            current = stack.pop();
//            exec.execute(current.value);
//            current = current.right;
//        }
//    }

    public <R> void inOrderWalk(IExecutor<T, R> exec) {
        inOrderWalk(_root, exec);
    }

    private <R> void inOrderWalk(Node node, IExecutor<T, R> exec) {
        if (node != null) {
            inOrderWalk(node.left, exec);
            exec.execute(node.value);
            inOrderWalk(node.right, exec);
        }
    }

    public T successor(T elem) {
        Node current = _root;
        Node successor = null;

        while (current != null) {
            int cmp = _comparator.compare(elem, current.value);
            if (cmp == 0) {
                if (current.right != null) {
                    successor = getMin(current.right);
                }
                break;
            } else if (cmp < 0) {
                successor = current;
                current = current.left;
            } else { // cmp > 0
                current = current.right;
            }
        }

        return successor == null ? null : successor.value;
    }

    public void insert(T elem) {
        Node newNode = new Node(elem);
        if (_root == null) {
            _root = newNode;
            return;
        }

        Node current = _root;
        Node parent = null;

        while (current != null) {
            parent = current;
            int cmp = _comparator.compare(elem, current.value);
            if (cmp < 0) {
                current = current.left;
            } else if (cmp > 0) {
                current = current.right;
            } else { // cmp == 0
                throw new DuplicateElementException(/*elem.toString()*/);
            }
        }

        int cmp = _comparator.compare(elem, parent.value);
        if (cmp < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
    }

    public void delete(T elem) {
        if (_root == null) {
            return;
        }

        Node current = _root;
        Node parent = null;

        while (current != null) {
            int cmp = _comparator.compare(elem, current.value);
            if (cmp == 0) {
                deleteNode(current, parent);
                return;
            } else if (cmp < 0) {
                parent = current;
                current = current.left;
            } else { // cmp > 0
                parent = current;
                current = current.right;
            }
        }
    }

    private void deleteNode(Node current, Node parent) {
        if (current.left == null || current.right == null) {
            Node child = (current.left != null) ? current.left : current.right;
            if (parent == null) {
                _root = child;
            } else if (parent.left == current) {
                parent.left = child;
            } else { // parent.right == current
                parent.right = child;
            }
        } else {
            detachMin(current, current.right);
        }
    }

    private void detachMin(Node del, Node node) {
        Node parent = null;
        while (node.left != null) {
            parent = node;
            node = node.left;
        }
        // node to następnik

        del.value = node.value;
        if (parent == null) {
            del.right = node.right;
        } else {
            parent.left = node.right;
        }
    }


    private int treeHeight(Node node) {
        if (node == null) {
            return 0;
        }

        int leftHeight = treeHeight(node.left);
        int rightHeight = treeHeight(node.right);
        int height = Math.max(leftHeight, rightHeight) + 1;

        int heightDifference = Math.abs(leftHeight - rightHeight);

        if (heightDifference >= biggestInbalance) {
            if (heightDifference == biggestInbalance) {
                if (height > biggestInbalanceHeight) {
                    biggestInbalanceNode = node;
                    biggestInbalanceHeight = height;
                }
            } else {
                biggestInbalance = heightDifference;
                biggestInbalanceNode = node;
                biggestInbalanceHeight = height;
            }
        }

        return height;
    }

    public BST<T> mostInbalancedSubtree() {
        treeHeight(_root);

        BST<T> bst = new BST<>(_comparator);
        bst._root = biggestInbalanceNode;
        return bst;
    }


    // -------------  WYJĄTKI   &   KLASA WĘZŁA KOPCA  -------------

    private static class DuplicateElementException extends RuntimeException {
        public DuplicateElementException() {
            super("Duplicate element");
        }
    }

    class Node {
        T value; // element
        Node left;
        Node right;

        Node(T value) {
            this.value = value;
        }

        Node(T value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "{" + value + "}";
        }
    }

}