/**
 * class that contains ingredients for calculating the chromatic number of
 * undirected graphs
 */
import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.Math;

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

    /**
     * provides a graph coloring based on the degree of saturation algorithm
     *
     * @param adjacencyMatrix
     * @return the the list with a coloring that can be seen as an upper bound
     */
    public static int[] colorDSATUR(boolean[][] adjacencyMatrix) {
        int[] cL = new int[adjacencyMatrix.length]; // containing all vertices with their color initially 0

        //INSERT CODE
        return cL;
    }

    /**
     * get degree of single vertex
     *
     * @param adjacencyMatrix the matrix it is about
     * @param vertexIndex     the vertex index 0 to n
     */
    public static int singleVertexDegree(boolean[][] adjacencyMatrix, int vertexIndex) {
        int k = 0; // Variable to count the node's neighbours

        for (int i = 0; i < adjacencyMatrix.length; i++)// V = number of vertices
        {
            if (adjacencyMatrix[vertexIndex][i])// it can be (0,1)
                k++;
        }
        return k;
    }

    /**
     * generates array where index number represents a vertex, and the value its degree
     *
     * @param adjacencyMatrix
     * @return array as described*/
    public static int[] makeDegreeSet(boolean[][] adjacencyMatrix) {
        // define array
        int[] D = new int[adjacencyMatrix.length];
        // fill the degree array with vertex index with the value as it's degree
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            D[i] = singleVertexDegree(adjacencyMatrix, i);
        }
        //return the (unsorted) array of degrees
        return D;
    }
    public static int singleVertexSaturation(boolean[][] adjacencyMatrix, int[] colorList, int vertexIndex){
        ArrayList<Integer> colors = new ArrayList<>(); // will contain unique color set of adjacent colored vertices

        for (int i = 0; i < adjacencyMatrix.length; i++)// V = number of vertices
        {
            // must check for adjacency first
            // then see if the vertex is colored i.e. color != 0
            // finally this color must be different from the ones seen before in order to count
            if (adjacencyMatrix[vertexIndex][i] && colorList[i] != 0 && !colors.contains(colorList[i]))// it can be (0,1)
                colors.add(colorList[i]); // color is added to the list
        }
        return colors.size();
    }
}