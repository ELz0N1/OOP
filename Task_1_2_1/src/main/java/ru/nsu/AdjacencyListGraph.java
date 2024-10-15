package ru.nsu;

import java.util.*;

public class AdjacencyListGraph implements Graph {

    private final HashMap<Integer, List<Integer>> adjacencyList;
    private int maxVertexAmount;

    public AdjacencyListGraph() {
        adjacencyList = new HashMap<>();
        maxVertexAmount = -1;
    }

    @Override
    public void addVertex(int vertex) {
        if (!hasVertex(vertex)) {
            adjacencyList.put(vertex, new ArrayList<>());
            maxVertexAmount = Math.max(maxVertexAmount, vertex);
        }
    }

    @Override
    public void removeVertex(int vertex) {
        adjacencyList.remove(vertex);

        for (List<Integer> neighbors : adjacencyList.values()) {
            if (neighbors.contains(vertex)) {
                neighbors.remove(vertex);
            }
        }

        // че надо это не?
//        for (int i = maxVertexAmount - 1; i >= 0; i--) {
//            if (hasVertex(i)) {
//                maxVertexAmount = i;
//            }
//        }
    }

    @Override
    public void addEdge(int source, int destination) {
        if (!hasVertex(source)) {
            addVertex(source);
        }
        if (!hasVertex(destination)) {
            addVertex(destination);
        }
        adjacencyList.get(source).add(destination);
    }

    @Override
    public void removeEdge(int source, int destination) {
        if (!hasVertex(source) || !hasVertex(destination)) {
            return;
        }
        adjacencyList.get(source).remove(Integer.valueOf(destination));
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        return adjacencyList.getOrDefault(vertex, Collections.emptyList());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int vertex : adjacencyList.keySet()) {
            sb.append(vertex).append(": ").append(adjacencyList.get(vertex)).append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean hasVertex(int vertex) {
        return adjacencyList.containsKey(vertex);
    }

    @Override
    public int getMaxVertexNumber() {
        return maxVertexAmount;
    }
}
