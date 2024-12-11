package ru.nsu;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Interface for graphs.
 */
public interface Graph {

    /**
     * Adds a vertex to the graph if the graph doesn't contain it.
     *
     * @param vertex the vertex to be added.
     */
    void addVertex(int vertex);

    /**
     * Removes a vertex from the graph.
     *
     * @param vertex the vertex to be removed.
     * @throws NoVertexException if graph doesn't contain given vertex.
     */
    void removeVertex(int vertex) throws NoVertexException;

    /**
     * Adds an edge from one vertex to another. If graph doesn't contain the vertices of an edge
     * they will be added.
     *
     * @param source      the vertex where the edge starts.
     * @param destination the vertex where the edge ends.
     */
    void addEdge(int source, int destination);

    /**
     * Removes an edge from one vertex to another.
     *
     * @param source      the vertex where the edge starts.
     * @param destination the vertex where the edge ends.
     * @throws NoVertexException if graph doesn't contain given vertices.
     */
    void removeEdge(int source, int destination) throws NoVertexException;

    /**
     * Returns a list of all neighbors of a given vertex.
     *
     * @param vertex the vertex whose neighbors are to be returned.
     * @return a list of neighbors of the given vertex.
     */
    List<Integer> getNeighbors(int vertex);

    /**
     * Reads the graph structure from a file.
     *
     * @param path the path to the file containing the graph data.
     * @throws IOException if an I/O error occurs while reading the file.
     */
    default void readFromFile(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path);
        for (String line : lines) {
            String[] parts = line.split(" ");
            int source = Integer.parseInt(parts[0]);
            int destination = Integer.parseInt(parts[1]);
            addEdge(source, destination);
        }
    }

    /**
     * Returns a string representation of the graph.
     *
     * @return a string representation of the graph.
     */
    String toString();

    /**
     * Checks if graph consists the given vertex.
     *
     * @param vertex current vertex.
     * @return true if there is the vertex in the graph.
     */
    boolean hasVertex(int vertex);

    /**
     * Gets all vertices graph consists.
     *
     * @return list of all graph vertices.
     */
    List<Integer> getGraphVertices();
}