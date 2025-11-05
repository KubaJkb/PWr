package RBT;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class RedBlackTree<T> implements Tree<T> {
    private Node root;
    private Node nil;
    private final Comparator<T> comparator;

    public RedBlackTree(Comparator<T> comparator) {
        this.comparator = comparator;

        nil = new Node(null);
        nil.color = 'B';

        root = nil;
    }

    private void leftRotate(Node x) {
        Node y = x.right;

        x.right = y.left;
        if (y.left != nil) {
            y.left.parent = x;
        }

        y.parent = x.parent;
        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        }

        y.left = x;
        x.parent = y;
    }

    private void rightRotate(Node x) {
        Node y = x.left;

        x.left = y.right;
        if (y.right != nil) {
            y.right.parent = x;
        }

        y.parent = x.parent;
        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        }

        y.right = x;
        x.parent = y;
    }

    @Override
    public void insert(T val) {
        Node z = new Node(val);
        z.left = nil;
        z.right = nil;

        Node y = null;
        Node x = root;
        while (x != nil) {
            y = x;
            if (comparator.compare(z.val, x.val) < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        z.parent = y;
        if (y == null) {
            root = z;
        } else if (comparator.compare(z.val, y.val) < 0) {
            y.left = z;
        } else {
            y.right = z;
        }

        insertFixup(z);
    }

    private void insertFixup(Node z) {
        while (z.parent != null && z.parent.color == 'R') {

            if (z.parent == z.parent.parent.left) { // z ma wujka po prawej
                Node y = z.parent.parent.right;
                // Red uncle
                if (y.color == 'R') {
                    z.parent.flipColor();
                    z.parent.parent.flipColor();
                    y.flipColor();
                    z = z.parent.parent;
                } else { // y.color == 'B'
                    // Black uncle - triangle
                    if (z == z.parent.right) {
                        z = z.parent;
                        leftRotate(z);
                    }
                    // Black uncle - line
                    z.parent.flipColor();
                    z.parent.parent.flipColor();
                    rightRotate(z.parent.parent);
                }
            } else { // z ma wujka po lewej
                Node y = z.parent.parent.left;
                if (y.color == 'R') {
                    z.parent.flipColor();
                    z.parent.parent.flipColor();
                    y.flipColor();
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.left) {
                        z = z.parent;
                        rightRotate(z);
                    }
                    z.parent.flipColor();
                    z.parent.parent.flipColor();
                    leftRotate(z.parent.parent);
                }
            }
        }
        root.color = 'B';
    }

    @Override
    public void delete(T data) {
        Node z = search(data);

        if (z == nil) {
            return;
        }

        Node y = z;
        char yOrigColor = y.color;
        Node x;


        if (z.left == nil) {            // Przypadek 1)
            x = z.right;
            transplant(z, z.right);
        } else if (z.right == nil) {    // Przypadek 2)
            x = z.left;
            transplant(z, z.left);
        } else {                        // Przypadek 3)
            y = getMinNode(z.right);
            yOrigColor = y.color;
            x = y.right;

            if (y.parent == z) {
                x.parent = y;
            } else {
                transplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }

            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }

        if (yOrigColor == 'B') {
            deleteFixup(x);
        }

    }

    private Node search(T elem) {
        Node node = root;
        int cmp;
        while (node != nil && (cmp = comparator.compare(elem, node.val)) != 0) {
            node = cmp < 0 ? node.left : node.right;
        }
        return node;
    }

    private void transplant(Node locationNode, Node pasteNode) {
        if (locationNode.parent == null) {
            root = pasteNode;
        } else if (locationNode == locationNode.parent.left) {
            locationNode.parent.left = pasteNode;
        } else {
            locationNode.parent.right = pasteNode;
        }

        pasteNode.parent = locationNode.parent;
    }

    private Node getMinNode(Node node) {
        if (node.left != nil) {
            node = node.left;
        }
        return node;
    }

    private void deleteFixup(Node x) {
        while (x != root && x.color == 'B') {
            if (x == x.parent.left) {
                Node w = x.parent.right;
                // Przypadek 1)
                if (w.color == 'R') {
                    w.color = 'B';
                    x.parent.color = 'R';
                    leftRotate(x.parent);
                    w = x.parent.right;
                }
                // Przypadek 2)
                if (w.left.color == 'B' && w.right.color == 'B') {
                    w.color = 'R';
                    x = x.parent;   // ZMIANA X
                } else {
                    // Przypadek 3)
                    if (w.right.color == 'B') {
                        w.left.color = 'B';
                        w.color = 'R';
                        rightRotate(w);
                        w = x.parent.right;
                    }
                    // Przypadek 4)
                    w.color = x.parent.color;
                    x.parent.color = 'B';
                    w.right.color = 'B';
                    leftRotate(x.parent);
                    x = root;   // KONIEC PĘTLI
                }
            } else {
                Node w = x.parent.left;
                // Przypadek 1)
                if (w.color == 'R') {
                    w.color = 'B';
                    x.parent.color = 'R';
                    rightRotate(x.parent);
                    w = x.parent.left;
                }
                // Przypadek 2)
                if (w.right.color == 'B' && w.left.color == 'B') {
                    w.color = 'R';
                    x = x.parent;   // ZMIANA X
                } else {
                    // Przypadek 3)
                    if (w.left.color == 'B') {
                        w.right.color = 'B';
                        w.color = 'R';
                        leftRotate(w);
                        w = x.parent.left;
                    }
                    // Przypadek 4)
                    w.color = x.parent.color;
                    x.parent.color = 'B';
                    w.left.color = 'B';
                    rightRotate(x.parent);
                    x = root;   // KONIEC PĘTLI
                }
            }
        }
        x.color = 'B';
    }

    @Override
    public void printTree() {
        if (root == nil) {
            return;
        }

        int height = getHeight(root);
        int width = 4;
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        for (int level = 0; level < height; level++) {
            int levelNodes = (int) Math.pow(2, level);
            int leadingSpaces = 0;
            if (level < height - 1) {
                leadingSpaces = width/2 + width * ((int) Math.pow(2, height - level - 2) - 1);
            }
            int spaceBetween = 0;
            if (level > 0 && level < height - 1) {
                spaceBetween = ((int) Math.pow(2, height - 1) * width - levelNodes * width - 2 * leadingSpaces) / (levelNodes - 1);
            }

            printSpaces(leadingSpaces);

            for (int i = 0; i < levelNodes; i++) {
                Node node = queue.poll();
                if (node != nil) {
                    System.out.print(node);

                    queue.add(node.left != null ? node.left : nil);
                    queue.add(node.right != null ? node.right : nil);
                } else {
                    printSpaces(width);
                    queue.add(nil);
                    queue.add(nil);
                }

                if (i < levelNodes - 1) {
                    printSpaces(spaceBetween);
                }
            }
            System.out.println();
        }
    }

    private void printSpaces(int count) {
        for (int i = 0; i < count; i++) {
            System.out.print(" ");
        }
    }

    private int getHeight(Node node) {
        if (node == nil) {
            return 0;
        }
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    class Node {
        public static final String ANSI_RESET = "\u001B[0m";
        public static final String ANSI_RED = "\u001B[31m";
        public static final String ANSI_BLUE = "\u001B[34m";

        T val;
        char color;

        Node left;
        Node right;
        Node parent;

        public Node(T val) {
            this.val = val;
            color = 'R';
            left = null;
            right = null;
            parent = null;
        }

        public void flipColor() {
            this.color = (color == 'R' ? 'B' : 'R');
        }

        @Override
        public String toString() {
            if (color == 'R') {
                if (val instanceof Integer && (Integer) val < 10) {
                    return "{" + ANSI_RED + val + ANSI_RESET + "} ";
                } else {
                    return "{" + ANSI_RED + val + ANSI_RESET + "}";
                }
            } else if (color == 'B') {
                if (val instanceof Integer && (Integer) val < 10) {
                    return "{" + ANSI_BLUE + val + ANSI_RESET + "} ";
                } else {
                    return "{" + ANSI_BLUE + val + ANSI_RESET + "}";
                }
            }
            return "{" + val + "}";
        }
    }
}
