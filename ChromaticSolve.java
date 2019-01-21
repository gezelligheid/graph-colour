/**
 * contains logic to read in and find or else approximate the chromatic number of an undirected graph
 *
 * @author Steven Kelk
 * @author Alain van Rijn
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.*;

class ColEdge {
    int u;
    int v;
}


public class ChromaticSolve {

    public final static boolean DEBUG = false;

    public final static String COMMENT = "//";

    public static Graph myGraph;

    public static boolean[][] adjacencyMatrix;

    public static int[][] adjacencyIntegerMatrix;

    public static double density;

    public static int gellerLowerBound;

    public static int vertices;

    public static final String upperPrint = "NEW BEST UPPER BOUND = ";
    public static final String lowerPrint = "NEW BEST LOWER BOUND = ";
    public static final String exactPrint = "CHROMATIC NUMBER = ";

    public static int upperBound;
    public static int lowerBound;
    public static int chromaticNumber;

    public static int[] coloring;

    public static void main(String[] args) {
        final long startTime = System.currentTimeMillis();
        long end1 = startTime + 19000;
        if (args.length < 1) {
            System.out.println("Error! No filename specified.");
            System.exit(0);
        }


        String inputfile = args[0];

        boolean[] seen = null;

        //! n is the number of vertices in the graph
        int n = -1;

        //! m is the number of edges in the graph
        int m = -1;

        //! e will contain the edges of the graph
        ColEdge[] e = null;

        try {
            FileReader fr = new FileReader(inputfile);
            BufferedReader br = new BufferedReader(fr);

            String record = new String();

            //! THe first few lines of the file are allowed to be comments, staring with a // symbol.
            //! These comments are only allowed at the top of the file.

            //! -----------------------------------------
            while ((record = br.readLine()) != null) {
                if (record.startsWith("//")) continue;
                break; // Saw a line that did not start with a comment -- time to start reading the data in!
            }

            if (record.startsWith("VERTICES = ")) {
                n = Integer.parseInt(record.substring(11));
                if (DEBUG) System.out.println(COMMENT + " Number of vertices = " + n);
            }

            seen = new boolean[n + 1];

            record = br.readLine();

            if (record.startsWith("EDGES = ")) {
                m = Integer.parseInt(record.substring(8));
                if (DEBUG) System.out.println(COMMENT + " Expected number of edges = " + m);
            }

            e = new ColEdge[m];

            for (int d = 0; d < m; d++) {
                if (DEBUG) System.out.println(COMMENT + " Reading edge " + (d + 1));
                record = br.readLine();
                String[] data = record.split(" ");
                if (data.length != 2) {
                    System.out.println("Error! Malformed edge line: " + record);
                    System.exit(0);
                }
                e[d] = new ColEdge();

                e[d].u = Integer.parseInt(data[0]);
                e[d].v = Integer.parseInt(data[1]);

                seen[e[d].u] = true;
                seen[e[d].v] = true;

                if (DEBUG) System.out.println(COMMENT + " Edge: " + e[d].u + " " + e[d].v);

            }

            String surplus = br.readLine();
            if (surplus != null) {
                if (surplus.length() >= 2) if (DEBUG)
                    System.out.println(COMMENT + " Warning: there appeared to be data in your file after the last edge: '" + surplus + "'");
            }

        } catch (IOException ex) {
            // catch possible io errors from readLine()
            System.out.println("Error! Problem reading file " + inputfile);
            System.exit(0);
        }

        for (int x = 1; x <= n; x++) {
            if (!seen[x]) {
                if (DEBUG)
                    System.out.println(COMMENT + " Warning: vertex " + x + " didn't appear in any edge : it will be considered a disconnected vertex on its own.");
            }
        }

        //! At this point e[0] will be the first edge, with e[0].u referring to one endpoint and e[0].v to the other
        //! e[1] will be the second edge...
        //! (and so on)
        //! e[m-1] will be the last edge
        //!
        //! there will be n vertices in the graph, numbered 1 to n

        //! INSERT YOUR CODE HERE!

        // some variables
        adjacencyMatrix = ChromaticMethods.makeAdjacencyMatrix(n, e);
        adjacencyIntegerMatrix = ChromaticMethods.makeIntegerAdjacencyMatrix(n, e);
//        final ArrayList<Integer>[] adjacencyArrayList = ChromaticMethods.makeAdjacencyArrayList(n, e);
        density = (m / (n * (n - 1) / 2));
        gellerLowerBound = (int) (Math.pow(n, 2) / (Math.pow(n, 2) - 2 * m));
        myGraph = new Graph();
        myGraph.fillAdjacencyMap(n, e);
        vertices = n;


        // tournament strings


//        final LinkedList<Integer> simpleVerticeslist = ChromaticMethods.makeSimpleVerticesList(n);
//        LinkedList<Integer> candidates = simpleVerticeslist;
//        LinkedList<Integer> excluded = new LinkedList<>();
//        LinkedList<Integer> coloredVertices = new LinkedList<>();
//        LinkedList<LinkedList<Integer>> solutionSet = new LinkedList<>();

        // complete graphs have chromatic number equal to their number of vertices
        if (Math.abs(density - 1) < 0.000000001)
            System.out.println(exactPrint + n);
        else if (!ChromaticMethods.hasOddCycle(adjacencyMatrix, n, e[0].u - 1))
            System.out.println(exactPrint + 2);
        else {
            lowerBound = Math.max(3, gellerLowerBound);
            System.out.println(lowerPrint + lowerBound);

            coloring = ChromaticMethods.colorDSATUR(adjacencyMatrix);
            upperBound = ChromaticMethods.maxIntValueOfArray(coloring);
            System.out.println(upperPrint + upperBound);

            while (upperBound != lowerBound) {
                ExecutorService service = Executors.newFixedThreadPool(2);
                Future<Integer> future = service.submit(new Task1());
                Future<Integer> backtrack = service.submit(new Task2());

                try {
                    chromaticNumber = backtrack.get(20, TimeUnit.SECONDS);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } catch (ExecutionException e1) {
                    e1.printStackTrace();
                } catch (TimeoutException e1) {
                    e1.printStackTrace();
                }

                try {
                    lowerBound = future.get();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } catch (ExecutionException e1) {
                    e1.printStackTrace();
                }
            }


        }


        //testing the dsatur algo
////        System.out.println(Arrays.toString(coloring));
//        int upperBound = coloring[ChromaticMethods.indexOfMax(coloring)];
//        System.out.println("colors used: " + upperBound);
//        ChromaticMethods.showConflicts(adjacencyMatrix, coloring);

        // testing RLF
//        LinkedList<LinkedList<Integer>> solution = ChromaticMethods.colorRecursiveLargestFirst(adjacencyArrayList,
//                solutionSet,candidates,excluded, coloredVertices);

        // print the matrix to test
        //GenerateRandomGraph.matrix2DPrint(adjacencyMatrix);


        // testing the sorted degree map

//        Map<Integer,Integer> testMap = ChromaticMethods.unSortedHashDegreeSet(a);
//        System.out.println(testMap);
//        // testing single vertex saturation method
//        int[] fakeColorlist = {1, 0, 3, 0, 0, 0};
//        testing uncolored non adjacent method
//        ArrayList<Integer> uncoloredNonAdjacent = ChromaticMethods.uncoloredNotAdjacentSet(a,fakeColorlist,0);
//        System.out.println(uncoloredNonAdjacent);
//        // System.out.println("saturation test for vertex index 0: " + ChromaticMethods.singleVertexSaturation(a, fakeColorlist, 0));
//        int[] fakeSaturationlist = ChromaticMethods.uncoloredSaturations(a, fakeColorlist);
//        System.out.println("uncoloered saturation list: " + Arrays.toString(fakeSaturationlist));
//

        // testing backtracking exactness


//        System.out.println("the exact chromatic number is: " + upperBound);

        // testing the odd cycle
//        boolean hasOddCycle = ChromaticMethods.hasOddCycle(adjacencyMatrix,n,e[0].u - 1);
//        if (hasOddCycle) System.out.println("odd cycle found");
//        else System.out.println("no odd cycle found");
//        int[] degrees = ChromaticMethods.makeDegreeSet(adjacencyMatrix);

        // testing graph class
//        // testing clique finder
//        int[] clique = new  SparseGraphLargestCliqueFinder().computeLargestClique(myGraph);


//        System.out.println(Arrays.toString(clique));

        // testing vertex list
//        LinkedList vertexList = ChromaticMethods.makeSimpleVerticesList(n);
//        System.out.println(solution);
//        System.out.println("colors used: " + solution.size());

        // test print degree array
//        System.out.println("deg list: " + Arrays.toString(degrees));

        // testing the indexofmax method
//        //System.out.println("fakecolorlist index of max value: " + ChromaticMethods.indexOfMax(fakeColorlist));
//        //System.out.println("fakesaturation index of max value: " + ChromaticMethods.indexOfMax(fakeSaturationlist));
//
//        // testing the indices with same value method
//        //ArrayList<Integer> testlist = ChromaticMethods.elementsSameValue(fakeColorlist, 0);
//        //System.out.println(testlist);
//
//        // testing adjacent colors list
//        ArrayList<Integer> adjColorTest = ChromaticMethods.vertexAdjacentColorsSet(a, fakeColorlist, 0);
//        System.out.println(adjColorTest);
//
//        // testing vertex selection
//        int vC = ChromaticMethods.selectVertexDSATUR(fakeSaturationlist, degs);
//        System.out.println("the vertex to color is: " + vC);
//
//        // testing color assignment
//        int chosenColor = ChromaticMethods.assignColorDSATUR(a, fakeColorlist, 0);
//        System.out.println("the chosen color is: " + chosenColor);
//        // testing the degree set making
//        // int[] degSet = ChromaticMethods.makeDegreeSet(a);
//        // System.out.println(Arrays.toString(degSet));


    }

    static class Task1 implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            int[] clique = new SparseGraphLargestCliqueFinder().computeLargestClique(myGraph);
            return clique.length;
        }
    }

    static class Task2 implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            boolean possible = true; // the upper bound is always a solution knowing it's obtained correctly
            while (possible) {
                // reduce upper bound and check again
                possible = ChromaticMethods.isMColorable(adjacencyIntegerMatrix, upperBound - 1, vertices);
                if (possible) {
                    upperBound--;
                    System.out.println(upperPrint + upperBound);
                } else {
                    System.out.println(exactPrint + upperBound);
                    break;
                }
            }
            return chromaticNumber;
        }
    }


}