/**
 * class that contains ingredients for calculating the chromatic number of
 * undirected graphs
 */

import java.util.*;

import static java.util.stream.Collectors.toMap;

public class ChromaticMethods {


    public static LinkedList<Integer>[] makeAdjacencyList(int vertices, ColEdge[] edges) {
        LinkedList<Integer>[] adjacencyList = new LinkedList[vertices];
        for (int i = 0; i < vertices; i++) {
            adjacencyList[i] = new LinkedList<Integer>();
        }
        for (ColEdge edge : edges) {
            adjacencyList[edge.v - 1].push(edge.u - 1);
            adjacencyList[edge.u - 1].push(edge.v - 1);
        }
        return adjacencyList;
    }

    public static ArrayList<Integer>[] makeAdjacencyArrayList(int vertices, ColEdge[] edges) {
        ArrayList<Integer>[] adjacencyList = new ArrayList[vertices];
        for (int i = 0; i < vertices; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
        for (ColEdge edge : edges) {
            adjacencyList[edge.v - 1].add(edge.u - 1);
            adjacencyList[edge.u - 1].add(edge.v - 1);
        }
        return adjacencyList;
    }

    public static LinkedList<Integer> makeSimpleVerticesList(int vertices) {
        LinkedList<Integer> verticesList = new LinkedList<>();
        for (int i = 0; i < vertices; i++) {
            verticesList.add(i);
        }
        return verticesList;
    }

    /**
     * method to create a boolean adjacency matrix out of the specified edges
     *
     * @param vertices number of vertices of a graph
     * @param edges    object containing the indexnumbers of two vertices that have an edge beetween them
     * @return the adjacency matrix of the graph
     */

    public static boolean[][] makeAdjacencyMatrix(int vertices, ColEdge[] edges) {
        boolean[][] adjacencyMatrix = new boolean[vertices][vertices];
        for (ColEdge edge : edges) {
            adjacencyMatrix[edge.u - 1][edge.v - 1] = true; // edges are numbered 1 to n so have to be corrected
            adjacencyMatrix[edge.v - 1][edge.u - 1] = true;
        }
        return adjacencyMatrix;
    }

    /**
     * method to create an integer adjacency matrix out of the specified edges
     *
     * @param vertices number of vertices of a graph
     * @param edges    object containing the indexnumbers of two vertices that have an edge beetween them
     * @return the adjacency matrix of the graph
     */

    public static int[][] makeIntegerAdjacencyMatrix(int vertices, ColEdge[] edges) {
        int[][] adjacencyMatrix = new int[vertices][vertices];
        for (ColEdge edge : edges) {
            adjacencyMatrix[edge.u - 1][edge.v - 1] = 1; // edges are numbered 1 to n so have to be corrected
            adjacencyMatrix[edge.v - 1][edge.u - 1] = 1;
        }
        return adjacencyMatrix;
    }

    /**
     * to detect odd cycles
     * the starting vertex preferably chosen in a connected part for extra speed
     * <p>
     * <p>
     * inspired by: https://www.geeksforgeeks.org/check-graphs-cycle-odd-length/
     *
     * @param adjacencyMatrix the graph
     * @param vertices        mumber of vertices of the graph
     * @param start           starting vertex index
     */
    public static boolean hasOddCycle(boolean[][] adjacencyMatrix, int vertices, int start) {
        // define local variables
        int[] colorArray = new int[vertices];
        Arrays.fill(colorArray, -1); // -1 means no color assigned, 1 means the first color, 0 the second.
        // pick a starting vertex
        colorArray[start] = 1; // color with the first color

        // keep track of all vertices so that all components are searched
        LinkedList allVertices = makeSimpleVerticesList(vertices);
        // create a queue of vertex numbers abd add the starting vertex
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(start);
        allVertices.removeFirstOccurrence(start);

        while (!allVertices.isEmpty()) {
            if (queue.isEmpty()) {
                queue.add((Integer) allVertices.get(0));
                allVertices.remove(0);
            }
            while (!queue.isEmpty()) {
                // take a vertex from the queue
                int toCheck = queue.peek();
//                System.out.println("vertex to check is: " + toCheck);
                queue.pop();

                // find non colored adjacent vertices
                for (int i = 0; i < vertices; i++) {
                    // whenever and edge from the vertex toCheck to another exists and that vertex is uncolored
                    if (adjacencyMatrix[toCheck][i] && colorArray[i] == -1) {
                        // the alternate color is assigned to this vertex
                        colorArray[i] = 1 - colorArray[toCheck];
//                    if (ChromaticSolve.DEBUG)
//                        System.out.println((1 - colorArray[toCheck]) + " assigned to vertex index " + i);
                        queue.push(i);
                    }
                    // otherwise an edge exists and the same color is used
                    else if (adjacencyMatrix[toCheck][i] && colorArray[toCheck] == colorArray[i])
                        return true; // the odd cycle is found
                }
            }
        }
        // the graph is two colorable
        return false;
    }

    /**
     * Recursive largest first algorithm
     *
     * @param adjacencyArrayList the graph
     * @param solution           color sets containing vertices
     * @param candidates         possible vertices to color with active color
     * @param excluded           uncolored vertices that can't be colored with the active color
     * @param alreadyColored     keeps track og already colored vertices
     * @return the solution set
     */
    public static LinkedList<LinkedList<Integer>> colorRecursiveLargestFirst(ArrayList<Integer>[] adjacencyArrayList,
                                                                             LinkedList<LinkedList<Integer>> solution,
                                                                             LinkedList<Integer> candidates,
                                                                             LinkedList<Integer> excluded,
                                                                             LinkedList<Integer> alreadyColored) {
        if (candidates.isEmpty()) return solution;

        while (!candidates.isEmpty()) {
            // create and add new color class at the end of the list of classes
            LinkedList<Integer> currentColorClass = new LinkedList<>();
            while (!candidates.isEmpty()) {
                // choose vertex with max degree in candidates
                int vertexChoice = candidates.peekFirst();
                int vertexChoiceDegree = adjacencyArrayList[vertexChoice].size();
                for (int i = 1; i < candidates.size(); i++) {
                    if (adjacencyArrayList[candidates.get(i)].size() > vertexChoiceDegree) {
                        vertexChoice = candidates.get(i);
                        vertexChoiceDegree = adjacencyArrayList[vertexChoice].size();
                    }
                }
                // put vertex in its color set
                currentColorClass.add(vertexChoice);
                alreadyColored.add(vertexChoice);
                // remove colored vertex from candidates
                candidates.removeFirstOccurrence(vertexChoice);
                // add adjacent vertices to the exclusion set and remove from candidates
                for (int i = 0; i < adjacencyArrayList[vertexChoice].size(); i++) {
                    int workingVertex = adjacencyArrayList[vertexChoice].get(i);
                    if (!excluded.contains(workingVertex) && !alreadyColored.contains(workingVertex))
                        excluded.add(workingVertex); // uncolored vertices that can not be colored with same color
                    candidates.removeFirstOccurrence(workingVertex);
                }
            }
            solution.add(currentColorClass);
            candidates = (LinkedList<Integer>) excluded.clone();
            excluded.clear();
        }
        return colorRecursiveLargestFirst(adjacencyArrayList, solution, candidates, excluded, alreadyColored);
    }

    public static void maximumClique(LinkedList<Integer> candidates, LinkedList<Integer> colorList) {
        while (!candidates.isEmpty()) {
            break;
        }

    }

    /**
     * provides a graph coloring based on the degree of saturation algorithm
     *
     * @param adjacencyMatrix the graph
     * @return the the list with a coloring that can be seen as an upper bound
     */
    public static int[] colorDSATUR(boolean[][] adjacencyMatrix) {
        int[] colorList = new int[adjacencyMatrix.length]; // containing all vertices with their color initially 0
        int[] degrees = makeDegreeSet(adjacencyMatrix); // set with vertices and their degree
        colorList[indexOfMax(degrees)] = 1; // highest degree vertex get first color

        while (containsZero(colorList)) {
            int[] uncoloredVertexSaturations = uncoloredSaturations(adjacencyMatrix, colorList); // set containing saturation levels of uncolored v
            // uncolored vertex with largest number of different colors among it's adjacent vertices is selected
            // a tie is broken by selecting the highest degree among equal saturated candidates
            int vChoice = selectVertexDSATUR(uncoloredVertexSaturations, degrees);
            colorList[vChoice] = assignColorDSATUR(adjacencyMatrix, colorList, vChoice); // color gets assigned
        }
        return colorList;
    }

    /**
     * not fully implemented
     */
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
        int[] DegreeSet = new int[adjacencyMatrix.length];
        // fill the degree array with vertex index with the value as it's degree
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = i + 1; j < adjacencyMatrix.length; j++) {
                if (adjacencyMatrix[i][j]) {
                    DegreeSet[i]++;
                    DegreeSet[j]++;
                }
            }
        }
        //return the (unsorted) array of degrees
        return DegreeSet;
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
        int[] uncoloredSaturations = new int[colorList.length]; // create uncolored saturation array
        Arrays.fill(uncoloredSaturations, -1); // -1 in order to distinguish between uncolored and unsaturated
        // loop over all vertices
        for (int i = 0; i < uncoloredSaturations.length; i++) { // only uncolored vertices are considered
            if (colorList[i] == 0) uncoloredSaturations[i] = singleVertexSaturation(adjacencyMatrix, colorList, i);
        }
        return uncoloredSaturations;
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
        ArrayList<Integer> adjacentColorsSet = vertexAdjacentColorsSet(adjacencyMatrix, colorList, vertex);
        // new color is needed when all current colors are adjacent
        if (adjacentColorsSet.size() == maxColor) return maxColor + 1;
        // otherwise we assign the first color that fits
        for (int i = 1; i <= maxColor; i++) {
            if (!adjacentColorsSet.contains(i)) {
                activeColor = i;
                break;
            }
        }
        return activeColor; // the color number assigned to the vertex
    }

    /**
     * A utility function to check if the current
     * color assignment is safe for a vertex
     *
     * @param vertex    to check
     * @param color     the chosen color
     * @param graph
     * @param colorList the current colors in use
     * @return whether a color can safely be assigned
     * @author Abhishek Shankhadhar
     * @author Alain van Rijn
     */
    static boolean isSafe(int vertex, int[][] graph, int[] colorList,
                          int color) {
        for (int i = 0; i < colorList.length; i++)
            if (graph[vertex][i] == 1 && color == colorList[i])
                return false;
        return true;
    }

    /**
     * A recursive utility function to solve m
     * coloring  problem
     *
     * @param colorList current coloring
     * @param vertex the vertex to consider
     * @param graph the adjacency matrix
     * @param upperBound the number of colors tried to use
     * @author Abhishek Shankhadhar
     * @author Alain van Rijn
     */
    static boolean graphColoringUtil(int[][] graph, int upperBound,
                                     int[] colorList, int vertex) {
        /* base case: If all vertices are assigned
           a color then return true */
        if (vertex == colorList.length)
            return true;

        /* Consider this vertex v and try different
           colors */
        for (int c = 1; c <= upperBound; c++) {
            /* Check if assignment of color c to v
               is fine*/
            if (isSafe(vertex, graph, colorList, c)) {
                colorList[vertex] = c;

                /* recur to assign colors to rest
                   of the vertices */
                if (graphColoringUtil(graph, upperBound,
                        colorList, vertex + 1))
                    return true;

                /* If assigning color c doesn't lead
                   to a solution then remove it */
                colorList[vertex] = 0;
            }
        }

        /* If no color can be assigned to this vertex
           then return false */
        return false;
    }

    /**
     * This function solves the m Coloring problem using
     * Backtracking. It mainly uses graphColoringUtil()
     * to solve the problem. It returns false if the m
     * colors cannot be assigned, otherwise return true.
     * Please note that there  may be more than one
     * solutions.
     * <p>
     * based on: https://www.geeksforgeeks.org/m-coloring-problem-backtracking-5/
     *
     * @param upperBound best upper bound so far
     * @param vertices order of the graph
     * @param graph the adjacency matrix
     * @return whether a graph is colorable for the upper bound provided
     * @author Abhishek Shankhadhar
     * @author Alain van Rijn
     */
    public static boolean isMColorable(int[][] graph, int upperBound, int vertices) {
        // Initialize all color values as 0. This
        // initialization is needed correct functioning
        // of isSafe()
        int[] colorArray = new int[vertices];
        for (int i = 0; i < vertices; i++)
            colorArray[i] = 0;

        // Call graphColoringUtil() for vertex 0
        if (!graphColoringUtil(graph, upperBound, colorArray, 0)) {
            System.out.println("Solution does not exist");
            return false;
        }

        return true;
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