import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.Math;

class ColEdge {
    int u;
    int v;
}


public class ChromaticSolve {

    public final static boolean DEBUG = true;

    public final static String COMMENT = "//";

    public static void main(String[] args) {
        final long startReading = System.currentTimeMillis();
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
        final long doneReading = System.currentTimeMillis(); // tracking reading time
        System.out.println("Reading time: " + (doneReading - startReading) + " ms");

        final long startTime = System.currentTimeMillis(); // timing
        // calling the adjacencymatrix creator

        boolean[][] adjacencyMatrix = ChromaticMethods.makeAdjacencyMatrix(n, e);
//        int[][] adjacencyIntegerMatrix = ChromaticMethods.makeIntegerAdjacencyMatrix(n,e);
//        Matrix matrix =



        System.out.println("--");
        //testing the dsatur algo
//        int[] coloring = ChromaticMethods.colorDSATUR(adjacencyMatrix);
//        System.out.println(Arrays.toString(coloring));
//        System.out.println("colors used: " + coloring[ChromaticMethods.indexOfMax(coloring)]);
//        ChromaticMethods.showConflicts(adjacencyMatrix, coloring);


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
        final long startTest = System.currentTimeMillis(); // timing
        // testing the odd cycle
        boolean hasOddCycle = ChromaticMethods.hasOddCycle(adjacencyMatrix,n,e[0].u - 1);
        if (hasOddCycle) System.out.println("odd cycle found");
        else System.out.println("no odd cycle found");
        int[] degrees = ChromaticMethods.makeDegreeSet(adjacencyMatrix);
        final long endTest = System.currentTimeMillis(); // timing
        System.out.println("testTime: " + (endTest - startTest) + " ms");
//        degs[3] = 4;
        System.out.println("deg list: " + Arrays.toString(degrees));
//        // testing the indexofmax method
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
        final long endTime = System.currentTimeMillis();

        System.out.println("Execution time: " + (endTime - startTime) + " ms");
    }

}