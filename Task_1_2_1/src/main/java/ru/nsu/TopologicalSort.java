package ru.nsu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class implementing topological sort.
 */
public class TopologicalSort {

    /**
     * Topological sort.
     *
     * @param graph graph to be sorted.
     * @return sorted graph.
     * @throws CyclicGraphException if graph consists cycles.
     */
    public List<Integer> sort(Graph graph) throws CyclicGraphException {
        List<Integer> sorted = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        Set<Integer> visiting = new HashSet<>();

        for (Integer vertex : graph.getGraphVertices()) {
            if (visited.contains(vertex)) {
                continue;
            }
            if (!dfs(graph, vertex, visited, visiting, sorted)) {
                throw new CyclicGraphException();
            }
        }
        Collections.reverse(sorted);
        return sorted;
    }

    /**
     * Performs depth-first search for topological sort.
     *
     * @param graph    graph which contains vertices for which computes depth-first search.
     * @param vertex   vertex for which computes depth-first search.
     * @param visited  set of visited vertices.
     * @param visiting set of currently visiting vertices.
     * @param sorted   list of vertices in sorted order.
     * @return {@code false} if cycle found in graph else {@code true}.
     */
    private static boolean dfs(Graph graph, Integer vertex, Set<Integer> visited,
        Set<Integer> visiting, List<Integer> sorted) {
        if (visiting.contains(vertex)) {
            return false;
        }
        if (visited.contains(vertex)) {
            return true;
        }
        visiting.add(vertex);
        for (Integer neighbor : graph.getNeighbors(vertex)) {
            if (!dfs(graph, neighbor, visited, visiting, sorted)) {
                return false;
            }
        }
        visiting.remove(vertex);
        visited.add(vertex);
        sorted.add(vertex);
        return true;
    }
}