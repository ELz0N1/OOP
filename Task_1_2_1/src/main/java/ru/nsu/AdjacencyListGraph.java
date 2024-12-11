package ru.nsu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;

/**
 * Class for managing adjacency list graph.
 */
public class AdjacencyListGraph implements Graph {

    private final HashMap<Integer, List<Integer>> adjacencyList;

    /**
     * Constructor of adjacency list graph.
     */
    public AdjacencyListGraph() {
        adjacencyList = new HashMap<>();
    }

    @Override
    public void addVertex(int vertex) {
        if (!hasVertex(vertex)) {
            adjacencyList.put(vertex, new ArrayList<>());
        }
    }

    @Override
    public void removeVertex(int vertex) throws NoVertexException {
        if (hasVertex(vertex)) {
            adjacencyList.remove(vertex);

            for (List<Integer> neighbors : adjacencyList.values()) {
                if (neighbors.contains(vertex)) {
                    neighbors.remove((Integer) vertex);
                }
            }
        } else {
            throw new NoVertexException(vertex);
        }
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
    public void removeEdge(int source, int destination) throws NoVertexException {
        if (!hasVertex(source)) {
            throw new NoVertexException(source);
        }
        if (!hasVertex(destination)) {
            throw new NoVertexException(destination);
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
    public List<Integer> getGraphVertices() {
        return new ArrayList<>(adjacencyList.keySet());
    }

}
