package ru.nsu;

import java.util.ArrayList;
import java.util.List;

public class AdjacencyMatrixGraph implements Graph {

    private int[][] adjacencyMatrix;
    private boolean[] vertexConsisted;
    private int numVertices;
    private int capacity;
    private int maxVertexAmount;

    public AdjacencyMatrixGraph() {
        numVertices = 0;
        capacity = 16;
        adjacencyMatrix = new int[capacity][capacity];
        vertexConsisted = new boolean[capacity];
        maxVertexAmount = -1;
    }

    private void resizeGraph(int vertex) {
        while (vertex >= capacity) {
            capacity *= 2;
        }
        int[][] extendedGraph = new int[capacity][capacity];
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            System.arraycopy(adjacencyMatrix[i], 0, extendedGraph[i], 0, adjacencyMatrix.length);
        }
        adjacencyMatrix = extendedGraph;
        boolean[] newConsistedNodes = new boolean[capacity];
        System.arraycopy(vertexConsisted, 0, newConsistedNodes, 0, vertexConsisted.length);
        vertexConsisted = newConsistedNodes;
    }

    @Override
    public void addVertex(int vertex) {
        if (vertex >= capacity) {
            resizeGraph(vertex);
        }
        maxVertexAmount = Math.max(vertex, maxVertexAmount);
        vertexConsisted[vertex] = true;
        numVertices++;
    }

    @Override
    public void removeVertex(int vertex) {
        if (hasVertex(vertex)) {
            for (int i = 0; i < capacity; i++) {
                adjacencyMatrix[i][vertex] = 0;
                adjacencyMatrix[vertex][i] = 0;
            }
            numVertices--;
            vertexConsisted[vertex] = false;
            if (numVertices <= 0) {
                maxVertexAmount = -1;
            }
            for (int i = maxVertexAmount - 1; i >= 0; i--) {
                if (hasVertex(i)) {
                    maxVertexAmount = i;
                }
            }
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
        adjacencyMatrix[source][destination] = 1;
    }

    @Override
    public void removeEdge(int source, int destination) {
        if (!hasVertex(source) || !hasVertex(destination)) {
            return;
        }
        adjacencyMatrix[source][destination] = 0;
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        ArrayList<Integer> neighbors = new ArrayList<>();
        if (!hasVertex(vertex)) {
            return neighbors;
        }
        for (int i = 0; i < capacity; i++) {
            if (adjacencyMatrix[vertex][i] == 1) {
                neighbors.add(i);
            }
        }
        return neighbors;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int vertex = 0; vertex < numVertices; vertex++) {
            sb.append(vertex).append(": ").append(getNeighbors(vertex)).append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean hasVertex(int vertex) {
        return vertex >= 0 && vertex < capacity && vertexConsisted[vertex];
    }

    @Override
    public int getMaxVertexNumber() {
        return maxVertexAmount;
    }
}