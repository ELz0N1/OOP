package ru.nsu;

/**
 * Thrown if graph contains cycle in it.
 */
public class CyclicGraphException extends RuntimeException {

    public CyclicGraphException() {
        super("Graph contains cycle!");
    }
}
