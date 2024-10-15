package ru.nsu;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestAdjacencyListGraph {

    private AdjacencyListGraph graph;

    @Test
    void testAddVertex() {
        graph = new AdjacencyListGraph();
        graph.addVertex(0);
        Assertions.assertTrue(graph.getNeighbors(0).isEmpty());
    }

    @Test
    void testRemoveVertex() {
        graph = new AdjacencyListGraph();
        graph.addEdge(0, 1);
        graph.addEdge(1, 0);
        graph.addEdge(2, 0);
        graph.addVertex(3);

        graph.removeVertex(0);
        Assertions.assertTrue(graph.getNeighbors(0).isEmpty());
    }

    @Test
    void testAddEdge() {
        graph = new AdjacencyListGraph();
        graph.addEdge(0, 1);
        Assertions.assertEquals(List.of(1), graph.getNeighbors(0));
    }

    @Test
    void testRemoveEdge() {
        graph = new AdjacencyListGraph();
        graph.addEdge(0, 1);

        graph.removeEdge(0, 1);
        Assertions.assertTrue(graph.getNeighbors(0).isEmpty());
    }

    @Test
    void testTopologicalSort() {
        graph = new AdjacencyListGraph();
        graph.addEdge(3, 1);
        graph.addEdge(2, 3);
        graph.addEdge(5, 2);
        graph.addEdge(5, 0);
        graph.addEdge(4, 0);
        graph.addEdge(4, 1);
        List<Integer> sorted = TopologicalSort.topSort(graph);
        List<Integer> expected = List.of(5, 4, 2, 3, 1, 0);
        Assertions.assertEquals(expected, sorted);
    }

    @Test
    void testReadFromFile() throws Exception {
        Path path = Path.of("build/resources/test/tests/testAdjacencyListGraph.txt");
        Files.writeString(path, """
            0 1
            1 2""");
        graph = new AdjacencyListGraph();
        graph.readFromFile(path);
        Assertions.assertEquals(List.of(1), graph.getNeighbors(0));
        Assertions.assertEquals(List.of(2), graph.getNeighbors(1));
    }

    @Test
    void testToString() {
        graph = new AdjacencyListGraph();
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
