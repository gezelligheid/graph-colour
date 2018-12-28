/**
 * class that contains ingredients for calculating the chromatic number of
 * undirected graphs
 */
public class ChromaticMethods {

    /**
     * method to create an adjacency matrix out of the specified edges
     *
     * @param vertices number of vertices of a graph
     * @param edges    object containing the indexnumbers of two vertices that have an edge beetween them
     * @return the adjacency matrix of the graph
     */
    public static boolean[][] makeAdjacencyMatrix(int vertices, ColEdge[] edges) {
        int n = vertices;
        int l = edges.length;
        boolean[][] a = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < l; k++) {
                    if (edges[k].u == i + 1 && edges[k].v == j + 1) {
                        a[i][j] = true;
                        a[j][i] = true;
                    }
                }
                if (!a[i][j]) {
                    a[i][j] = false;
                }
            }
        }
        return a;
    }

}