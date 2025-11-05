import java.util.*;

public class BTree<T> {
    private final Comparator<T> comparator;
    private final int t;
    private BTreeNode<T> root;

    public BTree(Comparator<T> comparator, int t) {
        this.comparator = comparator;
        this.t = t;
        this.root = null;
    }


    @SuppressWarnings({"ClassEscapesDefinedScope"})
    public BTreeNode<T> search(T k) {
        return (root == null) ? null : root.search(k);
    }

    public boolean insert(T k) {
        if (root == null) {  // 1) Korzeń jest pusty
            root = new BTreeNode<>(t, true);
            root.keys[0] = k;
            root.n = 1;
            return true;
        }

        BTreeNode<T> current = root;
        List<BTreeNode<T>> visitedNodes = new ArrayList<>();
        while (!current.leaf) {  // 2) Szukanie liścia do którego powinno trafić k
            visitedNodes.add(current);

            int i = current.findKeyPos(k);

            if (i < current.n && comparator.compare(k, current.keys[i]) == 0) {
                return false;
            }

            current = current.children[i];
        }
        visitedNodes.add(current);

        BTreeNode<T> nodeToInsert = new BTreeNode<>(t, true);
        nodeToInsert.n = 1;
        nodeToInsert.keys[0] = k;
        int j;
        for (j = visitedNodes.size() - 1; j > 0 && visitedNodes.get(j).isFull(); j--) { // 4) Dzielimy wszystkie pełne węzły i wstawiamy do nich k`
            nodeToInsert = splitInsert(nodeToInsert, visitedNodes.get(j));
        }

        if (visitedNodes.get(j) == root && root.isFull()) {  // 5) Korzeń też jest pełny
            root = splitInsert(nodeToInsert, root);
        } else {  // 3) Dotarliśmy do niepełnego węzła więc normalnie wstawiamy wartość
            visitedNodes.get(j).insertToNotFullNode(nodeToInsert);
        }

        return true;
    }

    private BTreeNode<T> splitInsert(BTreeNode<T> nodeToInsert, BTreeNode<T> current) {
        BTreeNode<T> retNode = new BTreeNode<>(t, false);
        retNode.n = 1;
        retNode.keys[0] = current.keys[t - 1];

        retNode.children[0] = new BTreeNode<>(t, current.leaf);
        retNode.children[0].n = t - 1;
        System.arraycopy(current.keys, 0, retNode.children[0].keys, 0, t - 1);
        System.arraycopy(current.children, 0, retNode.children[0].children, 0, t);

        retNode.children[1] = new BTreeNode<>(t, current.leaf);
        retNode.children[1].n = t - 1;
        System.arraycopy(current.keys, t, retNode.children[1].keys, 0, t - 1);
        System.arraycopy(current.children, t, retNode.children[1].children, 0, t);

        int i = 0;
        while (i < current.n && comparator.compare(nodeToInsert.keys[0], current.keys[i]) > 0) {
            i++;
        }

        if (i / t == 0) {  // Wstawiamy do lewego dziecka
            retNode.children[0].insertToNotFullNode(nodeToInsert);
        } else { // if (i / t = 1)  // Wstawiamy do prawego dziecka
            retNode.children[1].insertToNotFullNode(nodeToInsert);
        }

        return retNode;
    }

    public boolean delete(T k) {
        if (root == null) {  // 1) Korzeń jest pusty
            return false;
        }

        BTreeNode<T> nodeToDel = root;
        int posToDel = nodeToDel.findKeyPos(k);
        List<BTreeNode<T>> visitedNodes = new ArrayList<>();
        visitedNodes.add(root);
        boolean flag = posToDel < nodeToDel.n && comparator.compare(k, nodeToDel.keys[posToDel]) == 0;
        while (!flag) {
            if (nodeToDel.leaf) {  // 2) Nie znaleziono klucza do usunięcia
                return false;
            }

            nodeToDel = nodeToDel.children[posToDel];
            posToDel = nodeToDel.findKeyPos(k);

            visitedNodes.add(nodeToDel);
            flag = posToDel < nodeToDel.n && comparator.compare(k, nodeToDel.keys[posToDel]) == 0;
        }

        // Znaleziono węzeł i pozycję do usunięcia
        if (nodeToDel.leaf) {   // b. i. wartość k znajduje się w liściu
            nodeToDel.removePos(posToDel);
        } else {                // b. ii. wartość k znajduje się w węźle wewnętrznym
            if (nodeToDel.getPredecessor(k).isNotMin()) {        // 1. Usuwany jest poprzednik jeśli nie wymaga to naprawy
                BTreeNode<T> pred = nodeToDel.getPredecessor(k);
                nodeToDel.keys[posToDel] = pred.removePos(pred.n - 1);
            } else if (nodeToDel.getSuccessor(k).isNotMin()) {   // 1. Usuwany jest następnik jeśli nie wymaga to naprawy
                BTreeNode<T> succ = nodeToDel.getSuccessor(k);
                nodeToDel.keys[posToDel] = succ.removePos(0);
            } else {                                            // 2. Usuwany jest następnik i wymaga on naprawy
                BTreeNode<T> current = nodeToDel.children[posToDel + 1];
                visitedNodes.add(current);

                while (current.children[0] != null) {
                    current = current.children[0];
                    visitedNodes.add(current);
                }

                nodeToDel.keys[posToDel] = current.removePos(0);
            }
        }

        int j = visitedNodes.size() - 1;
        BTreeNode<T> current = visitedNodes.get(j);
        BTreeNode<T> parent;

        while (j > 0 && current.isTooSmall()) {

            parent = visitedNodes.get(j - 1);
            int pos = 0;
            while (parent.children[pos] != current) {
                pos++;
            }

            if (pos > 0 && parent.children[pos - 1].isNotMin()) {               // 1. Lewy brat nie jest minimalny
                rotateFromLeft(parent, pos);
                return true;
            } else if (pos < parent.n && parent.children[pos + 1].isNotMin()) { // 2. Prawy brat nie jest minimalny
                rotateFromRight(parent, pos);
                return true;
            }
            // 3. Węzeł jest łączony z bratem
            if (pos > 0) {
                mergeNodes(parent, pos - 1);
            } else {
                mergeNodes(parent, pos);
            }
            j--;
            current = visitedNodes.get(j);
        }

        if (root.n == 0) {      // 3) W wyniku usuwania korzeń stał się pusty
            root = root.children[0];
        }

        return true;
    }

    private boolean mergeNodes(BTreeNode<T> parent, int pos) {
        BTreeNode<T> leftNode = parent.children[pos];
        BTreeNode<T> rightNode = parent.children[pos + 1];

        // Przenoszenie elementów do lewego węzła
        leftNode.keys[leftNode.n++] = parent.keys[pos];
        System.arraycopy(rightNode.keys, 0, leftNode.keys, leftNode.n, rightNode.n);
        System.arraycopy(rightNode.children, 0, leftNode.children, leftNode.n, rightNode.n + 1);
        leftNode.n += rightNode.n;

        // Przesuwanie elementów wewnątrz parent
        System.arraycopy(parent.keys, pos + 1, parent.keys, pos, parent.n - pos - 1);
        System.arraycopy(parent.children, pos + 2, parent.children, pos + 1, parent.n - pos - 1);
        parent.n -= 1;

        return parent.n < t - 1; // zwracamy prawdę gdy parent ma za małą ilość kluczy
    }

    private void rotateFromLeft(BTreeNode<T> parent, int pos) {
        BTreeNode<T> leftNode = parent.children[pos - 1];
        BTreeNode<T> rightNode = parent.children[pos];

        // Przenoszenie elementów do prawego dziecka
        System.arraycopy(rightNode.keys, 0, rightNode.keys, 1, rightNode.n);
        System.arraycopy(rightNode.children, 0, rightNode.children, 1, rightNode.n + 1);
        rightNode.keys[0] = parent.keys[pos - 1];
        rightNode.children[0] = leftNode.children[leftNode.n];
        rightNode.n += 1;

        // Przenoszenie klucza do rodzica
        parent.keys[pos - 1] = leftNode.keys[leftNode.n - 1];

        // Usuwanie elementu z lewego dziecka
        leftNode.removePos(leftNode.n - 1);
    }

    private void rotateFromRight(BTreeNode<T> parent, int pos) {
        BTreeNode<T> leftNode = parent.children[pos];
        BTreeNode<T> rightNode = parent.children[pos + 1];

        // Przenoszenie elementów do lewego dziecka
        leftNode.keys[leftNode.n] = parent.keys[pos];
        leftNode.children[leftNode.n + 1] = rightNode.children[0];
        leftNode.n += 1;

        // Przenoszenie klucza do rodzica
        parent.keys[pos] = rightNode.keys[0];

        // Usuwanie elementu z lewego dziecka
        rightNode.removePos(0);
    }

    // Wyświetlanie drzewa poziomami
    public void traverse() {
        if (root == null) {
            return;
        }

        Queue<BTreeNode<T>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int nodeCount = queue.size();
            while (nodeCount > 0) {
                BTreeNode<T> node = queue.poll();
                for (int i = 0; i < Objects.requireNonNull(node).n; i++) {
                    System.out.print(node.keys[i] + " ");
                }
                if (!node.leaf) {
                    queue.addAll(Arrays.asList(node.children).subList(0, node.n + 1));
                }
                nodeCount--;
                System.out.print("| ");
            }
            System.out.println();
        }
    }

    // TODO -------------------------- KLASA WĘZŁA --------------------------

    class BTreeNode<E extends T> {
        E[] keys;
        BTreeNode<E>[] children;
        int t; // Minimalny stopień
        int n; // Aktualna liczba kluczy
        boolean leaf;

        @SuppressWarnings("unchecked")
        public BTreeNode(int t, boolean leaf) {
            this.keys = (E[]) new Object[2 * t - 1];
            this.children = (BTreeNode<E>[]) new BTreeNode[2 * t];
            this.t = t;
            this.n = 0;
            this.leaf = leaf;
        }

        boolean isFull() {
            return n == 2 * t - 1;
        }

        BTreeNode<E> search(E k) {
            int i = this.findKeyPos(k); // 2) Znajdywanie miejsca dla k wśród kluczy

            if (i < n && comparator.compare(k, keys[i]) == 0) { // a. Wartość występuje w analizowanym węźle
                return this;
            }

            if (leaf) { // b. i. Węzeł jest liściem
                return null;
            }

            return children[i].search(k); // b. i.. Zejście do poddrzewa
        }

        void insertToNotFullNode(BTreeNode<E> nodeToInsert) {
            int pos = this.findKeyPos(nodeToInsert.keys[0]);

            for (int i = n; i > pos; i--) {
                this.children[i + 1] = this.children[i];
                this.keys[i] = this.keys[i - 1];
            }
            this.n += 1;

            this.keys[pos] = nodeToInsert.keys[0];
            this.children[pos] = nodeToInsert.children[0];
            this.children[pos + 1] = nodeToInsert.children[1];
        }

        int findKeyPos(E k) {
            int i = 0;
            while (i < n && comparator.compare(k, keys[i]) > 0) {
                i++;
            }
            return i;
        }

        E removePos(int i) {
            E retVal = keys[i];
            for (int j = i + 1; j < this.n; j++) {
                this.keys[j - 1] = this.keys[j];
                this.children[j] = this.children[j + 1];
            }
            this.n -= 1;
            return retVal;
        }

        BTreeNode<E> getPredecessor(E k) {
            int i = findKeyPos(k);
            if (children[i] == null) {
                return null;
            }

            BTreeNode<E> current = children[i];
            while (current.children[current.n] != null) {
                current = current.children[current.n];
            }
            return current;
        }

        BTreeNode<E> getSuccessor(E k) {
            int i = findKeyPos(k);
            if (children[i + 1] == null) {
                return null;
            }

            BTreeNode<E> current = children[i];
            while (current.children[0] != null) {
                current = current.children[0];
            }
            return current;
        }

        boolean isTooSmall() {
            return n < t - 1;
        }

        boolean isNotMin() {
            return n > t - 1;
        }


        @Override
        public String toString() {
            String retStr = n + ".";
            if (leaf) {
                retStr += "leaf";
            }
            for (int i = 0; i < n; i++) {
                retStr += " " + keys[i];
            }
            return retStr;
        }
    }


}
