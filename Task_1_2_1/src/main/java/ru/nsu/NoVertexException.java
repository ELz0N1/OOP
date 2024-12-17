package ru.nsu;

/**
 * Thrown if graph doesn't contain given vertex.
 */
public class NoVertexException extends RuntimeException {

    public NoVertexException(int vertex) {
        super("Graph doesn't contain vertex: " + vertex);
    }
}
