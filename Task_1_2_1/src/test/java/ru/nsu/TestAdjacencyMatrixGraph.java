package ru.nsu;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestAdjacencyMatrixGraph {

    private AdjacencyMatrixGraph graph;

    @BeforeEach
    public void setUp() {
        graph = new AdjacencyMatrixGraph();
    }

    @Test
    public void testAddVertex() {
        graph.addVertex(0);
        Assertions.assertTrue(graph.hasVertex(0));
        Assertions.assertEquals(1, graph.getGraphVertices().size());

        graph.addVertex(5);
        Assertions.assertTrue(graph.hasVertex(5));
        Assertions.assertEquals(2, graph.getGraphVertices().size());

        // Adding a vertex that triggers resizing
        graph.addVertex(20);
        Assertions.assertTrue(graph.hasVertex(20));
        Assertions.assertEquals(3, graph.getGraphVertices().size());
    }

    @Test
    public void testRemoveVertex() throws NoVertexException {
        graph.addVertex(0);
        graph.addVertex(1);
        graph.removeVertex(0);

        Assertions.assertFalse(graph.hasVertex(0));
        Assertions.assertTrue(graph.hasVertex(1));
        Assertions.assertEquals(1, graph.getGraphVertices().size());

        // Trying to remove a non-existent vertex
        Exception exception = Assertions.assertThrows(NoVertexException.class,
            () -> graph.removeVertex(0));
        Assertions.assertEquals("Graph doesn't contain vertex: 0", exception.getMessage());
    }

    @Test
    public void testAddEdge() {
        graph.addEdge(0, 1);
        Assertions.assertTrue(graph.hasVertex(0));
        Assertions.assertTrue(graph.hasVertex(1));

        List<Integer> neighbors = graph.getNeighbors(0);
        Assertions.assertTrue(neighbors.contains(1));

        graph.addEdge(2, 3);
        Assertions.assertTrue(graph.hasVertex(2));
        Assertions.assertTrue(graph.hasVertex(3));

        neighbors = graph.getNeighbors(2);
        Assertions.assertTrue(neighbors.contains(3));
    }

    @Test
    public void testRemoveEdge() throws NoVertexException {
        graph.addEdge(0, 1);
        graph.removeEdge(0, 1);

        List<Integer> neighbors = graph.getNeighbors(0);
        Assertions.assertFalse(neighbors.contains(1));
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
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);

        List<Integer> neighbors = graph.getNeighbors(0);
        Assertions.assertEquals(2, neighbors.size());
        Assertions.assertTrue(neighbors.contains(1));
        Assertions.assertTrue(neighbors.contains(2));

        neighbors = graph.getNeighbors(3);
        Assertions.assertTrue(neighbors.isEmpty());
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
        Path path = Path.of("build/resources/test/tests/testAdjacencyMatrixGraph.txt");
        Files.writeString(path, """
            2 1
            4 1
            5 4
            3 2
            3 6""");
        graph.readFromFile(path);
        Assertions.assertEquals(List.of(2, 6), graph.getNeighbors(3));
    }

    @Test
    public void testToString() {
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);

        String expectedOutput = "0: [1, 2]\n1: []\n2: []\n";
        Assertions.assertEquals(expectedOutput, graph.toString());

        graph.removeVertex(0);
        graph.removeVertex(1);
        graph.removeVertex(2);
        Assertions.assertEquals("", graph.toString());
    }

    @Test
    public void testGetGraphVertices() {
        graph.addVertex(0);
        graph.addVertex(1);
        List<Integer> vertices = graph.getGraphVertices();
        Assertions.assertEquals(2, vertices.size());
        Assertions.assertTrue(vertices.contains(0));
        Assertions.assertTrue(vertices.contains(1));

        // Remove one vertex and check again
        try {
            graph.removeVertex(0);
            vertices = graph.getGraphVertices();
            Assertions.assertEquals(1, vertices.size());
            Assertions.assertFalse(vertices.contains(0));
            Assertions.assertTrue(vertices.contains(1));
        } catch (NoVertexException e) {
            Assertions.fail("Should not have thrown an exception");
        }
    }

    @Test
    void testEqualsTrue() {
        graph.addEdge(3, 1);
        graph.addEdge(2, 3);
        graph.addEdge(5, 2);
        graph.addEdge(5, 0);
        graph.addEdge(4, 0);
        graph.addEdge(4, 1);

        AdjacencyMatrixGraph otherGraph = new AdjacencyMatrixGraph();
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
        graph.addEdge(5, 0);
        graph.addEdge(4, 0);
        graph.addEdge(4, 1);

        AdjacencyMatrixGraph otherGraph = new AdjacencyMatrixGraph();
        otherGraph.addEdge(0, 1);
        otherGraph.addEdge(0, 2);
        otherGraph.addEdge(5, 2);
        otherGraph.addEdge(5, 0);
        otherGraph.addEdge(4, 0);
        otherGraph.addEdge(4, 1);

        Assertions.assertFalse(graph.equals(otherGraph));
    }
}

