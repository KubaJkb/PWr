
public class Main {
    public static void main(String[] args) {
        Graph g = new Graph();

        g.addVertex("Mąka", 0, true, "g");
        g.addVertex("Jajka", 1, true, "");
        g.addVertex("Mleko", 15, true, "ml");
        g.addVertex("Jabłka", 0, true, "");
        g.addVertex("Cynamon", 0, true, "g");

        g.addVertex("Ciasto naleśnikowe", 130, false, "g");
        g.addVertex("Jabłka z cynamonem", 15, false, "g");

        g.addVertex("Naleśniki z jabłkami", 10, false, "");


        g.addEdge("Naleśniki z jabłkami", "Ciasto naleśnikowe", 80);
        g.addEdge("Naleśniki z jabłkami", "Jabłka z cynamonem", 20);

        g.addEdge("Ciasto naleśnikowe", "Mąka", 100.0/350);
        g.addEdge("Ciasto naleśnikowe", "Jajka", 1.0/350);
        g.addEdge("Ciasto naleśnikowe", "Mleko", 200.0/350);

        g.addEdge("Jabłka z cynamonem", "Jabłka", 1.0/200);
        g.addEdge("Jabłka z cynamonem", "Cynamon", 20.0/200);

        g.calculateIngredients();
    }
}
