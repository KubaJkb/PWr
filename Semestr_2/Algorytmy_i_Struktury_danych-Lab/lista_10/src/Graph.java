import java.util.*;

class Graph {
    class Vertex {
        String label;
        int amount;
        boolean baseProduct;
        String unit;

        Vertex(String label, int amount, boolean baseProduct, String unit) {
            this.label = label;
            this.amount = amount;
            this.baseProduct = baseProduct;
            this.unit = unit;
        }

        @Override
        public String toString() {
            return label + ": " + amount + " " + unit;
        }
    }

    class Edge {
        Vertex destination;
        double weight;

        Edge(Vertex destination, double weight) {
            this.destination = destination;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "(" + destination + ", " + weight + ")";
        }
    }

    private Map<Vertex, List<Edge>> graph = new HashMap<>();


    public void addVertex(String label, int amount, boolean baseProduct, String unit) {
        graph.put(new Vertex(label, amount, baseProduct, unit), new LinkedList<>());
    }

    public void addEdge(String source, String destination, double weight) {

        if (getVertex(source) == null || getVertex(destination) == null) {
            return;
        }

        graph.get(getVertex(source)).add(new Edge(getVertex(destination), weight));
    }

    Vertex getVertex(String label) {
        for (Map.Entry<Vertex, List<Edge>> entry : graph.entrySet()) {
            if (entry.getKey().label.equals(label)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void calculateIngredients() {
        List<Vertex> baseProducts = new LinkedList<>();

        for (Vertex vertex : graph.keySet()) {
            if (vertex.baseProduct) {
                baseProducts.add(vertex);
            } else if (vertex.amount > 0) {
                dfs(vertex);
            }
        }

        System.out.println("\n--- ŁĄCZNA ILOŚĆ PODSTAWOWYCH SKŁADNIKÓW ---");
        for (Vertex vertex : baseProducts) {
            System.out.println(vertex);
        }
    }

    private void dfs(Vertex source) {
        List<Edge> edges = graph.get(source);
        for (Edge edge : edges) {
            edge.destination.amount += (int) Math.ceil(source.amount * edge.weight);
            if (!edge.destination.baseProduct) {
                dfs(edge.destination);
            }
        }
        source.amount = 0;
    }

}
