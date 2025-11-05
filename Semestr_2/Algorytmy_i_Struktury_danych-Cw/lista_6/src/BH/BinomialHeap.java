package BH;

public class BinomialHeap {

    // Member variables of this class
    private Node nodes;
    private int size;

    // Constructor of this class
    public BinomialHeap() {
        nodes = null;
        size = 0;
    }

    // Checking if heap is empty
    public boolean isEmpty() {
        return nodes == null;
    }

    // Method 1
    // To get the size
    public int getSize() {
        return size;
    }

    // Method 2
    // Clear heap
    public void makeEmpty() {
        nodes = null;
        size = 0;
    }

    // Method 3
    // To insert
    public void insert(int value) {

        if (value > 0) {
            Node temp = new Node(value);
            if (nodes == null) {
                nodes = temp;
                size = 1;
            } else {
                unionNodes(temp);
                size++;
            }
        }
    }

    // Method 4
    // To unite two binomial heaps
    private void merge(Node binHeap) {
        Node temp1 = nodes, temp2 = binHeap;

        while ((temp1 != null) && (temp2 != null)) {

            if (temp1.degree == temp2.degree) {

                Node tmp = temp2;
                temp2 = temp2.sibling;
                tmp.sibling = temp1.sibling;
                temp1.sibling = tmp;
                temp1 = tmp.sibling;
            } else {

                if (temp1.degree < temp2.degree) {

                    if ((temp1.sibling == null)
                            || (temp1.sibling.degree
                            > temp2.degree)) {
                        Node tmp = temp2;
                        temp2 = temp2.sibling;
                        tmp.sibling = temp1.sibling;
                        temp1.sibling = tmp;
                        temp1 = tmp.sibling;
                    } else {
                        temp1 = temp1.sibling;
                    }
                } else {
                    Node tmp = temp1;
                    temp1 = temp2;
                    temp2 = temp2.sibling;
                    temp1.sibling = tmp;

                    if (tmp == nodes) {
                        nodes = temp1;
                    }
                }
            }
        }

        if (temp1 == null) {
            temp1 = nodes;

            while (temp1.sibling != null) {
                temp1 = temp1.sibling;
            }
            temp1.sibling = temp2;
        }
    }

    // Method 5
    // For union of nodes
    private void unionNodes(Node binHeap) {
        merge(binHeap);

        Node prevTemp = null, temp = nodes,
                nextTemp = nodes.sibling;

        while (nextTemp != null) {

            if ((temp.degree != nextTemp.degree)
                    || ((nextTemp.sibling != null)
                    && (nextTemp.sibling.degree
                    == temp.degree))) {
                prevTemp = temp;
                temp = nextTemp;
            } else {

                if (temp.val <= nextTemp.val) {
                    temp.sibling = nextTemp.sibling;
                    nextTemp.parent = temp;
                    nextTemp.sibling = temp.child;
                    temp.child = nextTemp;
                    temp.degree++;
                } else {

                    if (prevTemp == null) {
                        nodes = nextTemp;
                    } else {
                        prevTemp.sibling = nextTemp;
                    }

                    temp.parent = nextTemp;
                    temp.sibling = nextTemp.child;
                    nextTemp.child = temp;
                    nextTemp.degree++;
                    temp = nextTemp;
                }
            }
            nextTemp = temp.sibling;
        }
    }

    // Method 6
    // To return minimum key
    public int findMinimum() {
        return nodes.findMinNode().val;
    }

    // Method 7
    // To delete a particular element */
    public void delete(int value) {

        if ((nodes != null)
                && (nodes.findANodeWithKey(value) != null)) {
            decreaseKeyValue(value, findMinimum() - 1);
            extractMin();
        }
    }

    // Method 8
    // To decrease key with a given value */
    public void decreaseKeyValue(int old_value,
                                 int new_value) {
        Node temp
                = nodes.findANodeWithKey(old_value);
        if (temp == null)
            return;
        temp.val = new_value;
        Node tempParent = temp.parent;

        while ((tempParent != null)
                && (temp.val < tempParent.val)) {
            int z = temp.val;
            temp.val = tempParent.val;
            tempParent.val = z;

            temp = tempParent;
            tempParent = tempParent.parent;
        }
    }

    // Method 9
    // To extract the node with the minimum key
    public int extractMin() {
        if (nodes == null)
            return -1;

        Node temp = nodes, prevTemp = null;
        Node minNode = nodes.findMinNode();

        while (temp.val != minNode.val) {
            prevTemp = temp;
            temp = temp.sibling;
        }

        if (prevTemp == null) {
            nodes = temp.sibling;
        } else {
            prevTemp.sibling = temp.sibling;
        }

        temp = temp.child;
        Node fakeNode = temp;

        while (temp != null) {
            temp.parent = null;
            temp = temp.sibling;
        }

        if ((nodes == null) && (fakeNode == null)) {
            size = 0;
        } else {
            if ((nodes == null) && (fakeNode != null)) {
                nodes = fakeNode.reverse(null);
                size = nodes.getSize();
            } else {
                if ((nodes != null) && (fakeNode == null)) {
                    size = nodes.getSize();
                } else {
                    unionNodes(fakeNode.reverse(null));
                    size = nodes.getSize();
                }
            }
        }

        return minNode.val;
    }

    // Method 10
    // To display heap
    public void displayHeap() {
        System.out.print("\nHeap : ");
        displayHeap(nodes);
        System.out.println("\n");
    }

    private void displayHeap(Node r) {
        if (r != null) {
            displayHeap(r.child);
            System.out.print(r.val + " ");
            displayHeap(r.sibling);
        }
    }
}
