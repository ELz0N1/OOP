package ru.nsu;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestAdjacencyListGraph {

    private AdjacencyListGraph graph;

    @BeforeEach
    public void setUp() {
        graph = new AdjacencyListGraph();
    }

    @Test
    public void testAddVertex() {
        graph.addVertex(1);
        Assertions.assertTrue(graph.hasVertex(1));
        Assertions.assertFalse(graph.hasVertex(2));
        Assertions.assertEquals(1, graph.getGraphVertices().size());
    }

    @Test
    public void testRemoveVertex() throws NoVertexException {
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(1, 2);

        graph.removeVertex(1);
        Assertions.assertFalse(graph.hasVertex(1));
        Assertions.assertTrue(graph.hasVertex(2));
        Assertions.assertTrue(graph.getNeighbors(2).isEmpty());
    }

    @Test
    public void testRemoveNonExistentVertex() {
        Exception exception = Assertions.assertThrows(NoVertexException.class,
            () -> graph.removeVertex(1));
        Assertions.assertEquals("Graph doesn't contain vertex: 1", exception.getMessage());
    }

    @Test
    public void testAddEdge() {
        graph.addEdge(1, 2);
        Assertions.assertTrue(graph.hasVertex(1));
        Assertions.assertTrue(graph.hasVertex(2));
        Assertions.assertEquals(List.of(2),
            graph.getNeighbors(1));
    }

    @Test
    public void testRemoveEdge() throws NoVertexException {
        graph.addEdge(1, 2);
        graph.removeEdge(1, 2);
        Assertions.assertFalse(graph.getNeighbors(1).contains(2));
    }

    @Test
    public void testRemoveEdgeWithNonExistentSource() {
        Exception exception = Assertions.assertThrows(NoVertexException.class,
            () -> graph.removeEdge(1, 2));
        Assertions.assertEquals("Graph doesn't contain vertex: 1", exception.getMessage());
    }

    @Test
    public void testRemoveEdgeWithNonExistentDestination() throws NoVertexException {
        graph.addVertex(1);
        Exception exception = Assertions.assertThrows(NoVertexException.class,
            () -> graph.removeEdge(1, 2));
        Assertions.assertEquals("Graph doesn't contain vertex: 2", exception.getMessage());
    }

    @Test
    public void testGetNeighbors() {
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        List<Integer> neighbors = graph.getNeighbors(1);
        Assertions.assertTrue(neighbors.contains(2));
        Assertions.assertTrue(neighbors.contains(3));
        Assertions.assertEquals(2, neighbors.size());
    }

    @Test
    public void testGetGraphVertices() {
        graph.addVertex(1);
        graph.addVertex(2);
        List<Integer> vertices = graph.getGraphVertices();
        Assertions.assertEquals(2, vertices.size());
        Assertions.assertTrue(vertices.contains(1));
        Assertions.assertTrue(vertices.contains(2));
    }

    @Test
    void testTopologicalSort() throws CyclicGraphException {
        graph.addEdge(3, 1);
        graph.addEdge(2, 3);
        graph.addEdge(5, 2);
        graph.addEdge(5, 0);
        graph.addEdge(4, 0);
        graph.addEdge(4, 1);
        TopologicalSort tp = new TopologicalSort();
        List<Integer> sorted = tp.sort(graph);
        List<Integer> expected = List.of(5, 4, 2, 3, 1, 0);
        Assertions.assertEquals(expected, sorted);
    }

    @Test
    void testTopSortWithCyclicGraph() throws CyclicGraphException {
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 0);
        TopologicalSort tp = new TopologicalSort();
        Exception exception = Assertions.assertThrows(CyclicGraphException.class,
            () -> tp.sort(graph));
        Assertions.assertEquals("Graph contains cycle!", exception.getMessage());
    }

    @Test
    void testReadFromFile() throws Exception {
        Path path = Path.of("build/resources/test/tests/testAdjacencyListGraph.txt");
        Files.writeString(path, """
            0 1
            1 2""");
        graph.readFromFile(path);
        Assertions.assertEquals(List.of(1), graph.getNeighbors(0));
        Assertions.assertEquals(List.of(2), graph.getNeighbors(1));
    }

    @Test
    void testToString() {
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

        graph.removeVertex(0);
        graph.removeVertex(1);
        graph.removeVertex(2);
        graph.removeVertex(3);
        graph.removeVertex(4);
        graph.removeVertex(5);
        Assertions.assertEquals("", graph.toString());
    }

    @Test
    void testEqualsTrue() {
        graph.addEdge(3, 1);
        graph.addEdge(2, 3);
        graph.addEdge(5, 2);
        graph.addEdge(5, 0);
        graph.addEdge(4, 0);
        graph.addEdge(4, 1);

        AdjacencyListGraph otherGraph = new AdjacencyListGraph();
        otherGraph.addEdge(3, 1);
        otherGraph.addEdge(2, 3);
        otherGraph.addEdge(5, 2);
        otherGraph.addEdge(5, 0);
        otherGraph.addEdge(4, 0);
        otherGraph.addEdge(4, 1);

        Assertions.assertTrue(graph.equals(otherGraph));
    }

    @Test
    void testEqualsFalse() {
        graph.addEdge(3, 1);
        graph.addEdge(2, 3);
        graph.addEdge(5, 2);
        graph.addEdge(5, 3);
        graph.addEdge(5, 0);
        graph.addEdge(2, 0);

        AdjacencyListGraph otherGraph = new AdjacencyListGraph();
        otherGraph.addEdge(0, 1);
        otherGraph.addEdge(0, 2);
        otherGraph.addEdge(5, 2);
        otherGraph.addEdge(5, 0);
        otherGraph.addEdge(4, 0);
        otherGraph.addEdge(4, 1);

        Assertions.assertFalse(graph.equals(otherGraph));
    }
}