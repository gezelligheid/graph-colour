import java.util.*;

/**
 * Class to hold data about an undirected graph.
 * The purpose is to make the data more flexible, easier to modify and access efficiently
 * <p>
 * modified from https://codereview.stackexchange.com/questions/171029/finding-largest-graph-cliques-in-java
 * to fit the date structure of this program.
 *
 * @author Rodion "rodde" Efremov
 * @author Alain van Rijn
 */
public class Graph {

    /**
     * map interface chosen for it's easy acces, Set Interface prevents duplicate edges
     */
    private Map<Integer, Set<Integer>> adjacencyMap = new HashMap<>(); // holds information about the graph

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
     * @param vertex1 end1
     * @param vertex2 end2
     */
    public void edgeMake(int vertex1, int vertex2) {
        addVertex(vertex1); // both vertices added to the set
        addVertex(vertex2);
        adjacencyMap.get(vertex1).add(vertex2); // both vertices receive the edge
        adjacencyMap.get(vertex2).add(vertex1);
    }

    /**
     * create an adjacency map that contains only vertices that appear in some edge
     *
     * @param edges all known edges
     */
    public void fillAdjacencyMap(ColEdge[] edges) {
        for (ColEdge edge : edges) {
            edgeMake(edge.u - 1, edge.v - 1);
        }

    }

    /**
     * to allow more efficient clique finding, the vertices with only 1 edge are removed
     */
    public void removeCutVertices() {
        boolean found;
        LinkedList<Integer> isolated = new LinkedList<>();
        do {
            found = false;
            for (Map.Entry<Integer, Set<Integer>> entry : adjacencyMap.entrySet()) {
//                System.out.println("adjacency print key: " + entry.getKey() + "value: " +
//                entry.getValue() + "set size: " + entry.getValue().size());
                if (entry.getValue().size() == 1) {
//                    System.out.println("found cut vertex");
                    found = true; // loop yielded a cut vertex
                    // get the only vertex in the set
                    for (Integer integer : entry.getValue()) {
//                        System.out.println("started set iterator" + integer);
                        int removalSet = integer; // take the value of the set element
                        // create a temp set
                        Set<Integer> tempSet = adjacencyMap.get(removalSet);
                        tempSet.remove(entry.getKey()); // remove the cut vertex from this set
                        adjacencyMap.replace(removalSet, tempSet); // put the smaller set in place
                    }
                    isolated.add(entry.getKey());


                }

            }
            // remove isolated vertices
            for (Integer vert : isolated) {
                adjacencyMap.remove(vert);
            }
            isolated.clear();
        } while (found);
    }

    /**
     * @return the set of vertices
     */
    public Set<Integer> vertexSet() {
        return Collections.unmodifiableSet(adjacencyMap.keySet());
    }

    /**
     * @param vertex1 end1
     * @param vertex2 end2
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
