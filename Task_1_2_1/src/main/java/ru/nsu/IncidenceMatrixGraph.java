package ru.nsu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for managing incidence matrix graph.
 */
public class IncidenceMatrixGraph implements Graph {

    /**
     * Class representing an edge in the graph.
     */
    private record Edge(int source, int destination) {

        /**
         * Gets source vertex of edge.
         *
         * @return source vertex of edge
         */
        public int getSource() {
            return source;
        }

        /**
         * Gets destination vertex of edge.
         *
         * @return destination vertex of edge
         */
        public int getDestination() {
            return destination;
        }

        @Override
        public String toString() {
            return "(" + source + " -> " + destination + ")";
        }
    }

    private final Map<Integer, Integer> vertexIndexMap;
    private final List<Integer> vertices;
    private final List<Edge> edges;
    private int vertexCapacity = 16;
    private int edgesCapacity = 16;
    private int[][] incidenceMatrix = new int[vertexCapacity][edgesCapacity];

    /**
     * Constructor of incidence matrix graph.
     */
    public IncidenceMatrixGraph() {
        this.vertexIndexMap = new HashMap<>();
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    @Override
    public void addVertex(int vertex) {
        if (!hasVertex(vertex)) {
            vertexIndexMap.put(vertex, vertices.size());
            vertices.add(vertex);
            updateIncidenceMatrix();
        }
    }

    @Override
    public void removeVertex(int vertex) throws NoVertexException {
        if (!hasVertex(vertex)) {
            throw new NoVertexException(vertex);
        }
        vertexIndexMap.remove(vertex);
        vertices.remove((Integer) vertex);
        edges.removeIf(edge -> edge.getSource() == vertex || edge.getDestination() == vertex);
        updateIncidenceMatrix();
    }

    @Override
    public void addEdge(int source, int destination) {
        if (!hasVertex(source)) {
            addVertex(source);
        }
        if (!hasVertex(destination)) {
            addVertex(destination);
        }
        edges.add(new Edge(source, destination));
        updateIncidenceMatrix();
    }

    @Override
    public void removeEdge(int source, int destination) throws NoVertexException {
        if (!hasVertex(source)) {
            throw new NoVertexException(source);
        }
        if (!hasVertex(destination)) {
            throw new NoVertexException(destination);
        }
        edges.removeIf(edge -> edge.getSource() == source && edge.getDestination() == destination);
        updateIncidenceMatrix();
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        List<Integer> neighbors = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.getSource() == vertex) {
                neighbors.add(edge.getDestination());
            }
        }
        return neighbors;
    }

    @Override
    public boolean hasVertex(int vertex) {
        return vertexIndexMap.containsKey(vertex);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int vertex = 0; vertex < vertices.size(); vertex++) {
            sb.append(vertex).append(": ").append(getNeighbors(vertex)).append("\n");
        }
        return sb.toString();
    }

    /**
     * Resizes and fills in incidence matrix in graph.
     */
    private void updateIncidenceMatrix() {
        int numVertices = vertices.size();
        int numEdges = edges.size();

        if (numVertices > vertexCapacity) {
            vertexCapacity *= 2;
        }
        if (numEdges > edgesCapacity) {
            edgesCapacity *= 2;
        }
        incidenceMatrix = new int[vertexCapacity][edgesCapacity];

        for (int j = 0; j < numEdges; j++) {
            Edge edge = edges.get(j);
            int sourceIndex = vertexIndexMap.get(edge.getSource());
            int destinationIndex = vertexIndexMap.get(edge.getDestination());

            incidenceMatrix[sourceIndex][j] = 1;
            incidenceMatrix[destinationIndex][j] = -1;
        }
    }

    public Integer getVertexByIndex(int index) {
        for (Map.Entry<Integer, Integer> entry : vertexIndexMap.entrySet()) {
            if (entry.getValue() == index) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public List<Integer> getGraphVertices() {
        List<Integer> neighbors = new ArrayList<>();
        for (Integer value : vertexIndexMap.values()) {
            neighbors.add(getVertexByIndex(value));
        }
        return neighbors;
    }
}