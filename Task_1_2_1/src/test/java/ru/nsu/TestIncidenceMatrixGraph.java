package ru.nsu;

import org.junit.jupiter.api.Assertions;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
public class TestIncidenceMatrixGraph {

    private IncidenceMatrixGraph graph;

    @Test
    void testAddVertex() {
        graph = new IncidenceMatrixGraph();
        graph.addVertex(3);
        Assertions.assertEquals(0, graph.getNeighbors(3).size());
    }

    @Test
    void testRemoveVertex() {
        graph = new IncidenceMatrixGraph();
        graph.addEdge(0, 1);
        graph.removeVertex(1);
        Assertions.assertTrue(graph.getNeighbors(0).isEmpty());
    }

    @Test
    void testAddEdge() {
        graph = new IncidenceMatrixGraph();
        graph.addEdge(0, 1);
        Assertions.assertEquals(List.of(1), graph.getNeighbors(0));
    }

    @Test
    void testRemoveEdge() {
        graph = new IncidenceMatrixGraph();
        graph.addEdge(0, 1);
        graph.removeEdge(0, 1);
        Assertions.assertTrue(graph.getNeighbors(0).isEmpty());
    }

    @Test
    void testTopologicalSort() {
        graph = new IncidenceMatrixGraph();
        graph.addEdge(2, 1);
        graph.addEdge(4, 1);
        graph.addEdge(5, 4);
        graph.addEdge(3, 2);
        graph.addEdge(3, 6);
        List<Integer> sorted = TopologicalSort.topSort(graph);
        List<Integer> expected = List.of(5, 4, 3, 6, 2, 1);
        Assertions.assertEquals(expected, sorted);
    }

    @Test
    void testReadFromFile() throws Exception {
        Path path = Path.of("build/resources/test/tests/testIncidenceMatrixGraph.txt");
        Files.writeString(path, """
            2 1
            4 1
            5 4
            3 2
            3 6""");
        graph = new IncidenceMatrixGraph();
        graph.readFromFile(path);
        Assertions.assertEquals(List.of(2, 6), graph.getNeighbors(3));
    }

    @Test
    void testToString() {
        graph = new IncidenceMatrixGraph();
        graph.addEdge(5, 2);
        graph.addEdge(5, 0);
        graph.addEdge(4, 0);
        graph.addEdge(4, 1);
        graph.addEdge(2, 3);
        graph.addEdge(3, 1);
        String string = graph.toString();
        Assertions.assertTrue(string.contains("""
            0: []
            1: []
            2: [3]
            3: [1]
            4: [0, 1]
            5: [2, 0]
            """));
    }
}
