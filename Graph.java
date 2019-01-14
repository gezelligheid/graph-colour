import java.util.*;

/**
 * Class to hold data about an undirected graph.
 * The purpose is to make the data more flexible, easier to modify and access efficiently
 */
public class Graph {

    /**
     * map interface chosen for it's easy acces, Set Interface prevents duplicate edges
     */
    public final Map<Integer, Set<Integer>> adjacencyMap = new HashMap<>(); // holds information about the graph

    /**
     * if a vertex does not exist in the map.
     * adds a single vertex to the set without adjacency.
     *
     * @param vertex given vertex
     */
    public void addVertex(int vertex) {
        if (!adjacencyMap.containsKey(vertex)) {
            adjacencyMap.put(vertex, new HashSet<>());
        }
    }

    /**
     * adds edge and immediately enlists the vertices specified
     *
     * @param vertex1
     * @param vertex2
     */
    public void edgeMake(int vertex1, int vertex2) {
        addVertex(vertex1); // both vertices added to the set
        addVertex(vertex2);
        adjacencyMap.get(vertex1).add(vertex2); // both vertices receive the edge
        adjacencyMap.get(vertex2).add(vertex1);
    }

    public void fillAdjacencyMap(int vertices, ColEdge[] edges) {
        for (ColEdge edge : edges) {
            edgeMake(edge.u - 1, edge.v - 1);
        }
        // add the remaining vertices that don't show up in any edge
        for (int i = 0; i < vertices; i++) {
            addVertex(i);
        }
    }

    /**
     * @return the set of vertices
     */
    public Set<Integer> vertexSet() {
        return Collections.unmodifiableSet(adjacencyMap.keySet());
    }

    /**
     * @return true whenever an edge between two vetices exist
     */
    public boolean edgeExists(int vertex1, int vertex2) {
        if (!adjacencyMap.containsKey(vertex1) || !adjacencyMap.containsKey(vertex2)) {
            return false;
        }
        return adjacencyMap.get(vertex1).contains(vertex2);
    }

    public int getSize() {
        return adjacencyMap.size();
    }


}
