package ru.nsu;

import java.util.*;

public class IncidenceMatrixGraph implements Graph {

    private int[][] incidenceMatrix;
    private boolean[] vertexConsisted;
    private int numVertices;
    private int numEdges;
    private int vertexCapacity;
    private int edgesCapacity;
    private int maxVertexAmount;


    public IncidenceMatrixGraph() {
        numVertices = 0;
        numEdges = 0;
        vertexCapacity = 16;
        edgesCapacity = 16;
        incidenceMatrix = new int[vertexCapacity][edgesCapacity];
        vertexConsisted = new boolean[vertexCapacity];
        maxVertexAmount = -1;
    }

    private int[][] copyGraph() {
        int[][] newGraph = new int[vertexCapacity][edgesCapacity];
        for (int i = 0; i < incidenceMatrix.length; i++) {
            System.arraycopy(incidenceMatrix[i], 0, newGraph[i], 0, incidenceMatrix[i].length);
        }
        return newGraph;
    }

    private void resizeGraphNodes(Integer node) {
        while (node >= vertexCapacity) {
            vertexCapacity *= 2;
        }
        incidenceMatrix = copyGraph();
        boolean[] newConsistedNodes = new boolean[vertexCapacity];
        System.arraycopy(vertexConsisted, 0, newConsistedNodes, 0, vertexConsisted.length);
        vertexConsisted = newConsistedNodes;
    }

    private void resizeGraphEdges() {
        edgesCapacity *= 2;
        incidenceMatrix = copyGraph();
    }

    @Override
    public void addVertex(int vertex) {
        if (vertex >= vertexCapacity) {
            resizeGraphNodes(vertex);
        }
        maxVertexAmount = Math.max(vertex, maxVertexAmount);
        vertexConsisted[vertex] = true;
        numVertices++;
    }


    @Override
    public void removeVertex(int vertex) {
        if (hasVertex(vertex)) {
            for (int i = 0; i < numEdges; i++) {
                if (incidenceMatrix[vertex][i] != 0) {
                    for (int j = 0; j < vertexCapacity; j++) {
                        incidenceMatrix[j][i] = 0;
                    }
                }
            }
            vertexConsisted[vertex] = false;
            numVertices--;

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
        if (numEdges >= edgesCapacity) {
            resizeGraphEdges();
        }
        incidenceMatrix[source][numEdges] = 1;
        incidenceMatrix[destination][numEdges] = -1;
        numEdges++;
    }


    @Override
    public void removeEdge(int source, int destination) {
        int edge = -1;
        for (int j = 0; j < numEdges; j++) {
            if (incidenceMatrix[source][j] == 1 && incidenceMatrix[destination][j] == -1) {
                edge = j;
                break;
            }
        }
        if (edge == -1) {
            return;
        }
        numEdges--;
        incidenceMatrix[source][edge] = 0;
        incidenceMatrix[destination][edge] = 0;
    }


    @Override
    public List<Integer> getNeighbors(int vertex) {
        ArrayList<Integer> neighbors = new ArrayList<>();
        if (!hasVertex(vertex)) {
            return neighbors;
        }
        for (int i = 0; i < numEdges; i++) {
            if (incidenceMatrix[vertex][i] == 1) {
                for (int j = 0; j < vertexCapacity; j++) {
                    if (incidenceMatrix[j][i] == -1) {
                        neighbors.add(j);
                    }
                }
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
        return vertex >= 0 && vertex < vertexCapacity && vertexConsisted[vertex];
    }

    @Override
    public int getMaxVertexNumber() {
        return maxVertexAmount;
    }
}
