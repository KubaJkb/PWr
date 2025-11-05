package BH;

public class Node {

    public int val;
    public int degree;

    public Node parent;
    public Node sibling;
    public Node child;

    // Constructor of this class
    public Node(int k) {
        val = k;
        degree = 0;
        parent = null;
        sibling = null;
        child = null;
    }

    // Method 1
    // To reverse
    public Node reverse(Node sibl) {
        Node ret;
        if (sibling != null)
            ret = sibling.reverse(this);
        else
            ret = this;
        sibling = sibl;
        return ret;
    }

    // Method 2
    // To find minimum node
    public Node findMinNode() {

        // this keyword refers to current instance itself
        Node x = this, y = this;
        int min = x.val;

        while (x != null) {
            if (x.val < min) {
                y = x;
                min = x.val;
            }

            x = x.sibling;
        }

        return y;
    }

    // Method 3
    // To find node with key value
    public Node findANodeWithKey(int value) {

        Node temp = this, node = null;

        while (temp != null) {
            if (temp.val == value) {
                node = temp;
                break;
            }

            if (temp.child == null)
                temp = temp.sibling;

            else {
                node = temp.child.findANodeWithKey(value);
                if (node == null)
                    temp = temp.sibling;
                else
                    break;
            }
        }

        return node;
    }

    // Method 4
    // To get the size
    public int getSize() {
        return (
                1 + ((child == null) ? 0 : child.getSize())
                    + ((sibling == null) ? 0 : sibling.getSize())
        );
    }
}
