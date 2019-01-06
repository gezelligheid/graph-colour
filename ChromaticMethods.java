/**
 * class that contains ingredients for calculating the chromatic number of
 * undirected graphs
 */

import java.util.*;

import static java.util.stream.Collectors.toMap;

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
        int[] degrees = makeDegreeSet(adjacencyMatrix); // set with vertices and their degree
        cL[indexOfMax(degrees)] = 1; // highest degree vertex get first color

        while (containsZero(cL)) {
            int[] uVS = uncoloredSaturations(adjacencyMatrix, cL); // set containing saturation levels of uncolored v
            int vChoice = selectVertexDSATUR(uVS, degrees); // vertex chosen to be colored
            cL[vChoice] = assignColorDSATUR(adjacencyMatrix, cL, vChoice); // color gets assigned
        }
        return cL;
    }

    public static int[] colorWelshPowell(boolean[][] adjacencyMatrix) {
        int[] cL = new int[adjacencyMatrix.length]; // containing all vertices with their color initially 0
        int[] degrees = makeDegreeSet(adjacencyMatrix); // set with vertices and their degree
        int activeColor = 1;

        while (containsZero(cL)) {
            int selectedVertex = indexOfMax(degrees); // select highest degree vertex
            cL[selectedVertex] = activeColor; // highest degree vertex get first color
            ArrayList<Integer> nonAdjacentSet = uncoloredNotAdjacentSet(adjacencyMatrix, cL, selectedVertex);
            while (nonAdjacentSet.size() > 0) {

            }
            degrees[selectedVertex] = -1; // as a means of excluding the vertex from selection
            activeColor++;
        }


        return cL;
    }


    /**
     * true if integer array contains a zero
     *
     * @param array
     * @return whether contains a zero value
     */
    public static boolean containsZero(int[] array) {
        for (int i1 : array) {
            if (i1 == 0) return true;
        }
        return false;
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
            if (adjacencyMatrix[vertexIndex][i])
                k++;
        }
        return k;
    }

    /**
     * generates array where index number represents a vertex, and the value its degree
     *
     * @param adjacencyMatrix
     * @return array as described
     */
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

    /**
     * creates a hashmap with keys as the vertex  index, and degrees as values.
     * sorted by value in descending order
     *
     * @param adjacencyMatrix the graph
     * @return the sorted hashmap
     */
    public static Map<Integer, Integer> sortedHashDegreeSet(boolean[][] adjacencyMatrix) {
        Map<Integer, Integer> degrees = new HashMap<>(); // the map  containing index as key and degree as value

        for (int i = 0; i < adjacencyMatrix[0].length; i++) {
            degrees.put(i, singleVertexDegree(adjacencyMatrix, i));
        }
        Map<Integer, Integer> sorted = degrees
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
        return sorted;
    }

    /**
     * creates a hashmap with keys as the vertex  index, and degrees as values.
     *
     *
     * @param adjacencyMatrix the graph
     * @return the hashmap
     */
    public static Map<Integer, Integer> unSortedHashDegreeSet(boolean[][] adjacencyMatrix) {
        Map<Integer, Integer> degrees = new HashMap<>(); // the map  containing index as key and degree as value

        for (int i = 0; i < adjacencyMatrix[0].length; i++) {
            degrees.put(i, singleVertexDegree(adjacencyMatrix, i));
        }
        return degrees;
    }


    /**
     * calculate the number of different colors among the colored vertices adjacent to a specified vertex
     * example: if a vertex is adjacent to 4 vertices with colors [1, (no color), 1, 2] then the saturation is 2.
     *
     * @param adjacencyMatrix the graph
     * @param colorList       the list containing current coloring
     * @param vertexIndex     desired vertex
     * @return degree of saturation
     */
    public static int singleVertexSaturation(boolean[][] adjacencyMatrix, int[] colorList, int vertexIndex) {
        ArrayList<Integer> colors = new ArrayList<>(); // will contain unique color set of adjacent colored vertices

        for (int i = 0; i < colorList.length; i++)// V = number of vertices
        {
            // must check for adjacency first
            // then see if the vertex is colored i.e. color != 0
            // finally this color must be different from the ones seen before in order to count
            if (adjacencyMatrix[vertexIndex][i])
                if (colorList[i] != 0)
                    if (!colors.contains(colorList[i])) {
                        colors.add(colorList[i]); // color is added to the list
                    }
        }
        return colors.size();
    }

    /**
     * uses singleVertexSaturation to create a list with -1's if a vertex is colored in order to
     * distinguish between unsaturated (0) and colored vertices (-1)
     *
     * @param adjacencyMatrix graph
     * @param colorList       current list of colorings
     * @return list with contents as described
     */
    public static int[] uncoloredSaturations(boolean[][] adjacencyMatrix, int[] colorList) {
        int[] uS = new int[colorList.length]; // create uncolored saturation array
        Arrays.fill(uS, -1); // -1 in order to distinguish between uncolored and unsaturated
        // loop over all vertices
        for (int i = 0; i < uS.length; i++) { // only uncolored vertices are considered
            if (colorList[i] == 0) uS[i] = singleVertexSaturation(adjacencyMatrix, colorList, i);
        }
        return uS;
    }

    /**
     * index of maximum value of given integer array
     *
     * @param array
     * @return the index number of the maximum value
     */
    public static int indexOfMax(int[] array) {
        int maxAt = 0; // default index

        for (int i = 0; i < array.length; i++) {
            maxAt = (array[i] > array[maxAt]) ? i : maxAt;
        }
        return maxAt;
    }

    /**
     * gives the indices of elements with the same value that have a higher index than the target element
     * usefull for checking of a maximum value is unique or not
     *
     * @param array        specified
     * @param elementIndex the element that should be checked
     * @return arraylist with indices(e.g. vertex indices)
     */
    public static ArrayList<Integer> elementsSameValue(int[] array, int elementIndex) {
        ArrayList<Integer> theSame = new ArrayList<>(); // initiate an empty arraylist

        for (int i = elementIndex; i < array.length; i++) { // only elements with a higher index are considered
            if (array[elementIndex] == array[i]) theSame.add(i);
        }
        return theSame;
    }

    /**
     * creates a set of all unique colors adjacent to a given vertex
     *
     * @param adjacencyMatrix graph
     * @param colorList       current coloring
     * @param vertex          specified
     * @return arraylist as desscribed
     */
    public static ArrayList<Integer> vertexAdjacentColorsSet(boolean[][] adjacencyMatrix, int[] colorList, int vertex) {
        ArrayList<Integer> adjColors = new ArrayList<>(); // will contain adjacent color numbers
        for (int i = 0; i < colorList.length; i++) {
            if (adjacencyMatrix[vertex][i] && colorList[i] > 0 && !(adjColors.contains(colorList[i])))
                adjColors.add(colorList[i]);
        }
        return adjColors;
    }

    public static ArrayList<Integer> uncoloredNotAdjacentSet(boolean[][] adjacencyMatrix, int[] colorList, int vertex) {
        ArrayList<Integer> set = new ArrayList<>(); // set to contain indices of uncolored non adjacent vertices
        for (int i = 0; i < colorList.length; i++) {
            if (!adjacencyMatrix[vertex][i] && colorList[i] == 0) { // vertex added iff non adjacent and uncolored
                set.add(i);
            }
        }
        return set;
    }

    /**
     * highest saturation vertex is selected.
     * if there are multiple higest, then the highest degree among them gets selected
     * if the degrees are also equal, the vertex that was first in the list will just be selected.
     *
     * @param saturationList created based on current coloring
     * @param degreeList     created at beginning of the program
     * @return the index of the vertex to be colored
     */
    public static int selectVertexDSATUR(int[] saturationList, int[] degreeList) {
        // the uncolored vertices with the largest number of adjacent colors are candidates for coloring
        ArrayList<Integer> candidates = elementsSameValue(saturationList, indexOfMax(saturationList));
        // if this set has only one element then this vertex will be colored
        if (candidates.size() == 1)
            return candidates.get(0);
        // otherwise the highest degree vertex is selected
        int vChoice = candidates.get(0);
        for (int i = 0; i < candidates.size(); i++) {
            if (degreeList[candidates.get(i)] > degreeList[vChoice])
                vChoice = candidates.get(i); // the highest degree vertex is selected
        }
        return vChoice;

    }

    /**
     * The color assigning for a given vertex happens by first creating a set containing all unique colors(numbers)
     * that are used by its adjacent vertices.
     * if the size of that list is equal to the highest known color it follows directly that a new color is assigned.
     * otherwise, we just pick the first/lowest number that it is not in the set.
     *
     * @param adjacencyMatrix the graph
     * @param colorList       current coloring
     * @param vertex          to color
     */
    public static int assignColorDSATUR(boolean[][] adjacencyMatrix, int[] colorList, int vertex) {
        int activeColor = 1; // default color
        int maxColor = colorList[indexOfMax(colorList)]; // number of colors in use
        ArrayList<Integer> adjSet = vertexAdjacentColorsSet(adjacencyMatrix, colorList, vertex);
        // new color is needed when all current colors are adjacent
        if (adjSet.size() == maxColor) return maxColor + 1;
        // otherwise we assign the first color that fits
        for (int i = 1; i <= maxColor; i++) {
            if (!adjSet.contains(i)) {
                activeColor = i;
                break;
            }
        }
        return activeColor; // the color number assigned to the vertex
    }

    /**
     * verifies that no colors are conflicting by iterationg over the adjacency matrix
     * on one side of the diagonal
     * creates a list of conflicts that has the conflicting elements
     *
     * @param adjacencyMatrix the graph
     * @param colorlist       the coloring to be tested
     */
    public static void showConflicts(boolean[][] adjacencyMatrix, int[] colorlist) {
        ArrayList<String> conflicts = new ArrayList<>();
        for (int i = 0; i < colorlist.length; i++) {
            for (int j = 0; j < i; j++) {
                if (adjacencyMatrix[i][j] && colorlist[i] == colorlist[j]) {
                    conflicts.add(j + "and " + i + ", ");
                }
            }
        }
        System.out.println("conflicting vertices: " + conflicts);
    }
}