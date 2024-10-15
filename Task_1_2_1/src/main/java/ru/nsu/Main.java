package ru.nsu;

public class Main {

    public static void main(String[] args) {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph();

        graph.addEdge(3, 1);
        graph.addEdge(2, 3);
        graph.addEdge(5, 2);
        graph.addEdge(5, 0);
        graph.addEdge(4, 0);
        graph.addEdge(4, 1);

        System.out.println("Граф после добавления вершин и ребер:");
        System.out.println(graph);

        System.out.println(graph.getNeighbors(5));
    }
}