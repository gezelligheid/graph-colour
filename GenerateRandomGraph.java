/**
 * used for generating and printing a randomly generated graph
 *
 * @author Alain van Rijn
 * */
import java.util.concurrent.ThreadLocalRandom;

public class GenerateRandomGraph {

    public static void main(String[] args) {
        try {
            boolean[][] test = randomGraph(4, 5); // 4 vertices can have 6 edges for example
            matrix2DPrint(test);
        } catch (TooManyEdgesException exception) {
            exception.printStackTrace();
        }


    }

    /**
     * generates a random graph with specified vertices and edges
     * all nodes are adjacent to themselves
     * example graph[1][3] = true means node 2 and 4 have an edge among them
     *
     * @param vertices the amount of vertices
     * @param edges    the amount of edges
     * @return an adjecency matrix with the vertices and their edges
     */
    public static boolean[][] randomGraph(int vertices, int edges) throws TooManyEdgesException {
        int v = vertices;
        int e = edges; //desired edges
        // test if the amount of edges is legal
        if (e > v * (v - 1) / 2) throw new TooManyEdgesException("too many edges specified");
        if (e < 0 || v < 0) throw new IllegalArgumentException("can't enter negative values");
            // if the amount of edges is legal we initiate the graph
            boolean[][] graph = identityMatrix(v);
        // we want again every node adjacent to itself so an identity matrix to start
        // then monte carlo style we make adjacency's that weren't there before
        int edgeCount = 0;
        // edges are created until desired amount is reached
        while (edgeCount < e) {
            int n1 = ThreadLocalRandom.current().nextInt(0, v); // upper bound is exclusive
            int n2 = ThreadLocalRandom.current().nextInt(0, v); //
            if (!graph[n1][n2]) {
                graph[n1][n2] = true;
                graph[n2][n1] = true; // adjacency matrix is symmetric
                edgeCount++;
            }
        }


        return graph;
    }

    /**
     * creates a simple identity matrix
     *
     * @param vertices number of vertices
     * @return an identitymatrix
     */
    public static boolean[][] identityMatrix(int vertices) {
        boolean[][] idMat = new boolean[vertices][vertices]; // default is false
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                if (i == j) {
                    idMat[i][j] = true; // diagonals are set to true
                }
            }
        }
        return idMat;
    }

    /**
     * simple way of printing a 2D matrix
     *
     * @param matrix the matrix
     */
    public static void matrix2DPrint(boolean[][] matrix) {
        System.out.print("{");
        for (int i = 0; i < matrix.length; i++) {
            System.out.println("{");
            for (int j = 0; j < matrix[i].length; j++) {
                if (j != (matrix[i].length - 1)) {
                    System.out.print(matrix[i][j] + ",");
                } else {
                    System.out.print(matrix[i][j]);
                }
            }
            if (i != (matrix.length - 1)) {
                System.out.print("},");
            } else {
                System.out.print("}");
            }
        }
        System.out.print("}");
    }
}
