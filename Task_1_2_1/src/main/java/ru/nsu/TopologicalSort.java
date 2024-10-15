package ru.nsu;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Class implementing topological sort.
 */
public class TopologicalSort {

    /**
     * Performs depth-first search.
     *
     * @param vertex  first vertex;
     * @param graph   current graph;
     * @param visited array of visited vertices;
     * @param stack   current stack.
     */
    private static void dfs(int vertex, Graph graph, boolean[] visited, Stack<Integer> stack) {
        visited[vertex] = true;
        for (int neighbour : graph.getNeighbors(vertex)) {
            if (!visited[neighbour]) {
                dfs(neighbour, graph, visited, stack);
            }
        }
        stack.push(vertex);
    }

    /**
     * Implements topological sort via dfs.
     *
     * @param graph graph to be sorted.
     * @return sorted graph.
     */
    public static List<Integer> topSort(Graph graph) {
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[graph.getMaxVertexNumber() + 1];
        for (int i = 0; i <= graph.getMaxVertexNumber(); i++) {
            if (graph.hasVertex(i) && !visited[i]) {
                dfs(i, graph, visited, stack);
            }
        }
        int n = stack.size();
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            result.add(stack.pop());
        }
        return result;
    }
}