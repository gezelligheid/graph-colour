import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.Math;

class ColEdge
{
	int u;
	int v;
}

public class ReadGraph
{
	public final static boolean DEBUG = false;

	public final static String COMMENT = "//";

	public static void main( String args[] )
	{
		if( args.length < 1 )
		{
			System.out.println("Error! No filename specified.");
			System.exit(0);
		}


		String inputfile = args[0];

		boolean seen[] = null;

		//! n is the number of vertices in the graph
		int n = -1;

		//! m is the number of edges in the graph
		int m = -1;

		//! e will contain the edges of the graph
		ColEdge e[] = null;

		try {
			FileReader fr = new FileReader(inputfile);
			BufferedReader br = new BufferedReader(fr);

			String record = new String();

			//! The first few lines of the file are allowed to be comments, staring with a // symbol.
			//! These comments are only allowed at the top of the file.

			//! -----------------------------------------
			while ((record = br.readLine()) != null)
			{
				if( record.startsWith("//") ) continue;
				break; // Saw a line that did not start with a comment -- time to start reading the data in!
			}

			if( record.startsWith("VERTICES = ") )
			{
				n = Integer.parseInt( record.substring(11) );
				if(DEBUG) System.out.println(COMMENT + " Number of vertices = "+n);
			}

			seen = new boolean[n+1];

			record = br.readLine();

			if( record.startsWith("EDGES = ") )
			{
				m = Integer.parseInt( record.substring(8) );
				if(DEBUG) System.out.println(COMMENT + " Expected number of edges = "+m);
			}

			e = new ColEdge[m];

			for( int d=0; d<m; d++)
			{
				if(DEBUG) System.out.println(COMMENT + " Reading edge "+(d+1));
				record = br.readLine();
				String data[] = record.split(" ");
				if( data.length != 2 )
				{
					System.out.println("Error! Malformed edge line: "+record);
					System.exit(0);
				}
				e[d] = new ColEdge();

				e[d].u = Integer.parseInt(data[0]);
				e[d].v = Integer.parseInt(data[1]);

				seen[ e[d].u ] = true;
				seen[ e[d].v ] = true;

				if(DEBUG) System.out.println(COMMENT + " Edge: "+ e[d].u +" "+e[d].v);

			}

			String surplus = br.readLine();
			if( surplus != null )
			{
				if( surplus.length() >= 2 ) if(DEBUG) System.out.println(COMMENT + " Warning: there appeared to be data in your file after the last edge: '"+surplus+"'");
			}

		}
		catch (IOException ex)
		{
		// catch possible io errors from readLine()
			System.out.println("Error! Problem reading file "+inputfile);
			System.exit(0);
		}

		for( int x=1; x<=n; x++ )
			{
			if( seen[x] == false )
				{
				if(DEBUG) System.out.println(COMMENT + " Warning: vertex "+x+" didn't appear in any edge : it will be considered a disconnected vertex on its own.");
				}
			}

		//! At this point e[0] will be the first edge, with e[0].u referring to one endpoint and e[0].v to the other
		//! e[1] will be the second edge...
		//! (and so on)
		//! e[m-1] will be the last edge
		//!
		//! there will be n vertices in the graph, numbered 1 to n

		//! INSERT YOUR CODE HERE!

		//for (int i = 0; i < e.length-1; i++) { System.out.println(e[i].u + " " + e[i].v); }

		// Pseudocode

		int l = e.length - 1;
		/*int nodes = 0;
		{
			int maxU = 0;
			int maxV = 0;
			for (int i = 0; i < l; i++) { maxU = Math.max(maxU, e[i].u); }
			for (int i = 0; i < l; i++) { maxV = Math.max(maxV, e[i].v); }

			nodes = Math.max(maxU, maxV);
		}*/

		// Create the adjacency matrix
		boolean[][] a = new boolean[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < l+1; k++) {
					if (e[k].u == i+1 && e[k].v == j+1) {
						a[i][j] = true;
						a[j][i] = true;
					}
				}
				if (!a[i][j]) { a[i][j] = false; }
			}
		}

		/*System.out.println("-- Adjacency Matrix --");
			for (int i = 0; i < n; i++) {
				String line = "";
				for (int j = 0; j < n; j++) {
					if (a[i][j]) { line += "1 "; }
					else		 { line += "0 "; }
				}
				System.out.println(line);
			}*/

		// timekeeping
		final long startTime = System.currentTimeMillis();

		// L is an array of integers representing the colours of all the nodes.
		// The colour (represented by an integer) of node 1 is L[0].
		int[] L = new int[n];
		for (int i = 0; i < n; i++) {
			L[i] = 0; // All nodes begin colourless
		}

		// Choose a starting node
		int currentNode = startingNode(L);

		// A list of integers representing which colours have been used so far.
		// so if three vertices have colour 1 and two vertices have colour 2, then colours == {1, 2}
		int[] colours = new int[n]; // The number of colours can only be smaller than the number of vertices

		try {

			System.out.println("\n-- List of vertices and their colours, greedy algorithm --");

			int chromaticNumber = colourTheGraph(currentNode, a, L, colours);

			System.out.println("An upper bound of the chromatic number of this graph is " + chromaticNumber + "!");
		}
		catch(java.lang.StackOverflowError error) {
			System.out.println("! Stack Overflow Error: unable to find an upper bound !");
		}

		final long endTime = System.currentTimeMillis();

		System.out.println("Execution time: " + (endTime - startTime)*10 + " ms");

		System.out.println("\n-- List of vertices and their colours, exact algorithm --");

		// Begin pseudocode
		// 1 Let the set V = { x : x is a vertex of our graph }, ie V is the set of all vertices
		// 2 Let the set R = { x : x is a colour used in the graph }, ie R is the set of all colours used
		// 3 Calculate the degree of every vertex
		// 4 Choose an active colour and remember it.
		// 5 Select the vertex with the largest degree and colour it with the active colour
		// {
		// 6 (A) Let the set U = { x : x is adjacent to the selected vertex }, ie U is the set of all vertices adjacent to the selected vertex
		// 7 Let the set V' = V \ (U union {selected vertex}), or V' = { x : x is not the selected vertex AND x is not adjacent to the selected vertex }
		// ie V' is all vertices not adjacent to the selected vertex (and not including the selected vertex)
		// 8 For every vertex in V', calculate how many vertices in U it is adjacent to.
		// 9 Select the vertex in V' that has the most number of vertices in U that it is adjacent to. Colour it with the same active colour.
		// 10 This vertex and all vertices adjacent to it are removed from V' and are added to the set U.
		// 11 If V' is not empty, return to (A)
		// 12 Else if there does not exist an uncoloured vertex, the programme is terminated.
		// 13 Else the next colour in the colour set is selected as the active colour.
		// 14 Then calculate the number of adjacent vertices for every single uncoloured vertex left.
		// 15 Select the uncoloured vertex with the maximum adjacent vertices. (If there are more than one, then select the vertex with the highest degree.)
		// 16 Return to (A)
		// }

		final long startTime2 = System.currentTimeMillis();

		// 1 New L[] list of the colours of the vertices
		int[] L2 = new int[n];
		for (int i = 0; i < n; i++) {
			L2[i] = 0;
		}

		// 2 New colours list
		int[] colours2 = new int[n];

		// 3 Calculate the degree of every node
		int[] degreeOfAllNodes = calculateDegree(L2, a);

		// 4 Choose our first colour
		int activeColour = 1;
		colours2[0] = 1;

		// 5 Find the node with the maximum degree
		currentNode = indexOfMax(degreeOfAllNodes);
		// and set it to the active colour
		L2[currentNode] = activeColour;

		// Run the algorithm!
		int chromaticNumber2 = performExactAlgorithm(currentNode, activeColour, a, L2, colours2);

		final long endTime2 = System.currentTimeMillis();

		// and display the result
		if (chromaticNumber2 > 0) {
			System.out.println("The chromatic number of this graph is exactly " + chromaticNumber2 + "!");
		} else {
			System.out.println("A lower bound for the chromatic number of this graph is " + -chromaticNumber2 + "!");
		}

		System.out.println("Execution time: " + (endTime2 - startTime2)*10 + " ms");

		// System.out.println("Total number of vertices: " + n);
	}


	public static int colourTheGraph(int currentNode, boolean[][] a, int[] L, int[] colours) {
		// Returns the chromatic number of a graph with adjacency matrix a
		// the colours of the nodes are contained in L[] and
		// colours[] is a list of all the colours used so far
		// Each recursive iteration colours currentNode and changes it to the next node


		// Number of vertices
		int n = a[0].length;

		// Way upper bound is the number of vertices
		int chromaticNumber = n;

		// If there are no colours, simply set the colour of the current node to 1
		boolean hasNoColours = true;
		for (int i = 0; i < colours.length; i++) {
			if (colours[i] != 0) {
				hasNoColours = false;
			}
		}
		if (hasNoColours) {
			L[0] = 1;
			colours[0] = 1;

			// and then call the function again on the next node
			currentNode = nextNode(a, L, currentNode);
			return colourTheGraph(currentNode, a, L, colours);
		}

		// Otherwise, choose a colour we haven't chosen yet
		// Check every neighbour
		for (int j = 0; j < n; j++) {
			if (currentNode < n) {
				if (a[currentNode][j]) {
					// Check every colour
					int colour = checkEveryColour(currentNode, a, L, colours);
					if (colour != -1) {
						// If there is a colour that the node doesn't border,
						// set it to that colour.
						L[currentNode] = colour;
					} else {
						// find the max colour
						int maxC = 0;
						for (int i = 0; i < colours.length; i++) {
							maxC = Math.max(maxC, colours[i]);
						}

						// We checked every colour and got nothing. Give it a new colour
						L[currentNode-1] = maxC+1;
						// and we need to add the colour to the list of colours
						int count = 0;
						for (int i = 0; i < colours.length; i++) {
							if (colours[i] != 0) { count++; }
						}
						colours[count] = maxC+1;
					}
				}
			}
		}

					// Display the colouring
			//String line = "";
			//for (int i = 0; i < L.length; i++) {
				//line += L[i] + " ";
			//}
			//System.out.println("L[]: " + line);


		// End everything if it's finished
		if (finished(L, n)) {
			chromaticNumber = colours.length;
			int count = 0;
			for (int i = 0; i < colours.length; i++) {
				if (colours[i] != 0) { count++; }
			}
			chromaticNumber = count-1;

			// Display the colouring
			String line = "";
			for (int i = 0; i < L.length; i++) {
				line += L[i] + " ";
			}
			System.out.println("L[]: " + line);

			return chromaticNumber;
		}

		// Otherwise call the function again
		currentNode = nextNode(a, L, currentNode);
		return colourTheGraph(currentNode, a, L, colours);
	}


	public static int performExactAlgorithm(int currentNode, int activeColour, boolean[][] a, int[] L2, int[] colours2) {

		int chromaticNumber = 0;

		try {

			// useful variable
			int n = L2.length;

			// 7 Let set V' equal the non-adjacent vertices of the current node
			int degreeOfCurrentNode = Degree(currentNode, a);
			int[] Vprime = getNonAdjacentVertices(currentNode, a, L2, degreeOfCurrentNode);

			// 11 Repeat this section as long as Vprime is not empty
			while (numberOfNonzeroElements(Vprime) > 0) {
				// 6 Let set U equal the adjacent vertices of the current node
				int[] U = getAdjacentVertices(currentNode, a, degreeOfCurrentNode);

				// 8 Make a new set the same size and order as Vprime
				// telling how many vertices in U each vertex in Vprimt is adjacent to
				int[] VprimeAdjacentToU = calculateAdjacent(Vprime, U, a);

				// 9 Select the vertex with the highest value in VprimeAdjacentToU
				int indexOfMaxOfVprimeAdjacentToU = indexOfMax(VprimeAdjacentToU);
				currentNode = Vprime[indexOfMaxOfVprimeAdjacentToU];

				// and colour it!
				L2[currentNode] = activeColour;

				// 10 Remove the current vertex and all vertices adjacent to it from Vprime and U
				int[] tempSet = new int[numberOfNonzeroElements(Vprime)];
				int k = 0;
				for (int i = 0; i < Vprime.length; i++) {
					boolean b = true;
					for (int j = 0; j < U.length; j++) {
						if (Vprime[i] == U[j] || Vprime[i] == currentNode) {
							// If Vprime[i] is the same as any element in U,
							// we want to delete it
							b = false;
						}
					}
					if (b) {
						tempSet[k] = Vprime[i];
						k++;
					}
				}

				System.out.println("N: " + currentNode);
				String write = "";
				for (int i = 0; i < U.length; i++) {
					write += U[i] + " ";
				}
				System.out.println("U[]: " + write);

				Vprime = tempSet;
				// And U is deleted, though this is assumed since it is set to a new value at the beginning of the while loop
			}

			// Display the last line
				String write = "";
				for (int i = 0; i < L2.length; i++) {
					write += L2[i] + " ";
				}
				System.out.println("L2[]: " + write);

			// Now that all vertices of the active colour are coloured, we can check to see if it is finished
			// 12 If there does not exist an uncoloured vertex, we're done
			chromaticNumber = 0;
			if (finished(L2, n)) {
				for (int i = 0; i < colours2.length; i++) {
					if (colours2[i] != 0) { chromaticNumber++; }
				}
				// Display the last line
				String line = "";
				for (int i = 0; i < L2.length; i++) {
					line += L2[i] + " ";
				}
				System.out.println("L2[]: " + line);
			}


			else {
				// 13 Otherwise pick the next colour and add it to the list
				if (activeColour < n) {
					activeColour++;
					colours2[activeColour-1] = activeColour;
				}

				// 14 Then calculate the uncoloured degrees, how many uncoloured vertices each uncoloured vertex is adjacent to.
				int[] uncolouredDegrees = calculateUncolouredDegrees(L2, a);
				// 15 Select the next node, the maximally connected uncoloured vertex (only considering other uncoloured vertices)

				int nextNode = indexOfMax(uncolouredDegrees);
				if (nextNode == currentNode) {
					//nextNode = indexOfMax(calculateDegree(L2, a));
					uncolouredDegrees[currentNode] = -1;
					nextNode = indexOfMax(uncolouredDegrees);
				} else {
					// we need to actually colour it in too, since the while loop only colours the NEXT one in
					L2[nextNode] = activeColour;
				}

				// Try it with the next colour
				return performExactAlgorithm(nextNode, activeColour, a, L2, colours2);
			}

			return chromaticNumber;
		}
		catch (java.lang.StackOverflowError e) {
			// Lower bound algorithm
			for (int i = 0; i < colours2.length; i++) {
				if (colours2[i] != 0) { chromaticNumber++; }
			}
			return -chromaticNumber;
		}
	}


	// Functions ----------------------
	/**
		* function that compares the resultmatrix to the adjacency matrix and tells
		* if there are conflicting colours
		*
		* @param boolean[][] adMatrix takes the adjecency matrix
		* @param int[] colourlist the lis of all colours outputted by the algorithm
		* @return int[] list containing 0 and
		*/
	public static int[] compareResult(boolean adMatrix[][], int[] colourlist){
		// go over each vertex and look for adjacent nodes
		int[] conflicts = new int[colourlist.length];
		for (int i = 0; i < adMatrix.length; ) {

		}
		// for all nodes that are adjacent compare the colour of the adjacent nodes
		// if a coflict is found, both vertices will get a +1 to their value in the
		// list to be returned
		return conflicts;
	}

	public static int checkEveryColour(int currentNode, boolean[][] a, int[] L, int[] colours) {
		// Returns the first colour that the current node is not adjacent to
		// Otherwise returns -1
		// Check every single colour
		int count = 0;
		for (int i = 0; i < colours.length; i++) {
			if (colours[i] != 0) { count++; }
		}
		for (int k = 1; k < count; k++) {
			// If the node is not adjacent to any node with that colour,
			if (!isAdjacentToColour(currentNode, a, L, k)) {
				// and stop checking every colour
				return k;
			}
		}
		return -1;
	}

	// Lydia - Returns true if any neighbour of node N has the colour C.
	public static boolean isAdjacentToColour(int N, boolean a[][], int L[], int C){
		for (int i = 0; i < a.length; i++) {
			if (a[N][i] == true) {
				if (L[i] == C && C != 0) {
					return true;
				}
			}
		}
		return false;
	}

	// Alain - checks whether the graph is finished being coloured
	//compares the length of coloured element list to the number of vertices.
	public static boolean finished(int[] L, int n){
		int count = 0;

		for (int i = 0; i < L.length; i++) {
			if (L[i] != 0) {
				count++;
			}
		}
        return (count == n);
    }

	// Spriha - Code to compute the degree of the node
	public static int Degree(int nodeNum,boolean[][]AdMatrix)
	{
	  int k=0; // Variable to count the node's neighbours

	  for(int i=0;i<AdMatrix[0].length;i++)// V= number of vertices
	  {
		if(AdMatrix[nodeNum][i]==true)// it can be (0,1)
		k++;
		/* if(AdMatrix[i][nodeNum]==true)// but also (1,0) and both of them are connected to the node 0, thus are valid.
		k++; */
	  }

	  return k;
	}

	/*public static boolean isPossible(boolean[][] a, int[] L, int C, int N) {
		// Takes the list of the colours of the vertices L
		// and an input colour C.
		// Determines whether or not it is possible to place colour C at node N
		for (int l = 1; l < a[0].length; l++) {
			if (a[N][l] == true && C == L[l]) {
				return false;
			}
			return true;
		}
	}*/

	public static int nextNode(boolean[][] a, int[] L, int N){
		int M;
		if (N < L.length) {
			M = N+1;
		} else {
			M = N;
		}
        return M;
        // depends on the chosen algorithm.

    }

	/*public static int nextNode(boolean[][] a, int[] L, int N){
		// calculate the degrees of all uncoloured vertices
        int[] degreeList = 	calculateUncolouredDegrees(L, a);
		// set all adjecent nodes to -1
		for (int i = 0; i < degreeList.length; i++) {
			if (a[N][i]) degreeList[i] = -1;
		}
		// pick andreturns index of the highest degree node that is not adjecent to N
        return indexOfMax(degreeList);
    }*/

	public static int startingNode(int[] L) {
		// Takes as input the list of vertex colours and returns the number of a
		// starting vertex
		return 0;
	}





	// Functions for the second algorithm, RLS --------------

	// Code to compute getAdjacentVertices

	public static int [] getAdjacentVertices(int nodeNum,boolean [][]AdMatrix, int degreeOftheNode )
	{
		int[] U = new int [AdMatrix.length];
		int j=0;
		for(int i=0;i<AdMatrix.length; i++)
		{
			if(AdMatrix[nodeNum][i]==true)
			{
				U[j]=i;
				j++;
			}
		}
	return U;
	}



	// Code to compute getNonAdjacentVertices

	public static int [] getNonAdjacentVertices(int nodeNum, boolean [][]AdMatrix, int[] L2, int degreeOftheNode)
	{
		int V[]= new int [AdMatrix.length-degreeOftheNode-1]; // n=total number of Vertices
		int j=0;
		for(int i=0;i<AdMatrix.length;i++)
		{
			if(AdMatrix[nodeNum][i]==false && nodeNum != i && L2[i] == 0)
			{
				V[j]=i;
				j++;
			}
		}
		return V;
	}



	//Code to compute calculateUncolouredDegree

	public static int[] calculateUncolouredDegrees(int[] colourlist, boolean[][] adjMatrix){
		// define length
		int uncolouredLength = colourlist.length;
		// set working value for coloured vertices
		final int COLOURED = -1;
		// define uncoloured degree array
		int[] UD = new int[uncolouredLength];
		//fill the degree array
		for (int i=0; i<uncolouredLength; i++) {
			// set coloured vertices to degree -1
			if (colourlist[i] != 0) UD[i] = COLOURED;
			// set uncoloured vertices to the degree of uncoloured adjecencies
			else UD[i] = toUncolouredDegree(i, adjMatrix, colourlist);
		}
		//return the (unsorted) array of degrees
		return UD;
	}
	// adjacency of node to uncoloured nodes
	public static int toUncolouredDegree(int nodeNum, boolean[][]adMatrix, 	int[] colourlist){
		int count = 0;
		// increase count only if adjecent and the adjecent node is uncoloured
		for(int i=0; i < adMatrix.length; i++){
			if(adMatrix[nodeNum][i]==true && colourlist[i] == 0) count++;
		}
		return count;
	}


	//Code to compute calculateDegree

	public static int[] calculateDegree(int[] colourlist, boolean[][] adjMatrix){
		// define array
		int[] D = new int[colourlist.length];
		// fill the degree array
		for (int i=0; i<colourlist.length; i++) {
			D[i] = Degree(i, adjMatrix);
		}
		//return the (unsorted) array of degrees
		return D;
	}


	//Code to compute indexOfMax

	public static int indexOfMax(int[] A){
		int maxAt = 0;

		for (int i = 0; i < A.length; i++) {
			// if (A[i] > A[maxAt]) { maxAt = i; } else { maxAt = maxAt }
			maxAt = (A[i] > A[maxAt]) ? i : maxAt;
		}
		return maxAt;
	}


	//Code to compute indexOfMaxMultipleArrays

	public static int indexOfMaxMultipleArrays(int[] A, int[] B){
		// variables for max indices of A,B
		int indexOfMaxA = indexOfMax(A);
		int indexOfMaxB = indexOfMax(B);
		// checks if The max in A occurs multiple times
		for(int i = indexOfMaxA; i <A.length; i++){
			if (A[indexOfMaxA] == A[i]) {
				if (A[i] == B[indexOfMaxB]) {
					// generate a random number to decide which to return
					double rnum = ThreadLocalRandom.current().nextDouble(0,1);
					if (rnum > 0.5) return indexOfMaxB;
					else return indexOfMaxA;
				}
				return indexOfMaxB;
			}
		}
		if (A[indexOfMaxA] == B[indexOfMaxB]) return indexOfMaxB;
		return indexOfMaxA;
	}

	public static int[] calculateAdjacent(int[] V, int[] U, boolean[][] a) {
		// The return matrix, the same size and order as V
		int[] A = new int[V.length];
		// For every element in V
		for (int i = 0; i < V.length; i++) {
			// Check every element in U
			for (int j = 0; j < U.length; j++) {
				// If element v is adjacent to element u
				if (a[V[i]][U[j]] && V[i] != 0 && U[j] != 0) {
					// Increase by one the element a in A that
					// corresponds to element v in V
					A[i]++;
				}
			}
		}
		return A;
	}

	public static int numberOfNonzeroElements(int[] A) {
		// Takes a generic array of integers and returns the number of nonzero elements in that array
		int count = 0;
		for (int i = 0; i < A.length; i++) {
			if (A[i] != 0) { count++; }
		}
		return count;
	}
}











// More Pseudocode
// The idea of this algorithm is that it colours all red nodes at the same time before it moves on to blue.
// Once a node is coloured red, all of the adjacent nodes are removed from the possibility of being chosen as the next red node.
// Then once no nodes can possibly be coloured red, it moves on to blue.
// The algorithm chooses which node to colour red next by determining the degree. Later on it uses a sort-of partial degree which doesn't care about non-adjacent vertices.
// We should follow this once closely so that everything is exact
//
// Let the set V = { x : x is a vertex of our graph }, ie V is the set of all vertices
// Let the set R = { x : x is a colour used in the graph }, ie R is the set of all colours used
// Calculate the degree of every vertex
// Choose an active colour and remember it.
// Select the vertex with the largest degree and colour it with the active colour
// {
// (A) Let the set U = { x : x is adjacent to the selected vertex }, ie U is the set of all vertices adjacent to the selected vertex
// Let the set V' = V \ (U union {selected vertex}), or V' = { x : x is not the selected vertex AND x is not adjacent to the selected vertex }
// ie V' is all vertices not adjacent to the selected vertex (and not including the selected vertex)
// For every vertex in V', calculate how many vertices in U it is adjacent to.
// Select the vertex in V' that has the most number of vertices in U that it is adjacent to. Colour it with the same active colour.
// This vertex and all vertices adjacent to it are removed from V' and are added to the set U.
// If V' is not empty, return to (A)
// Else if there does not exist an uncoloured vertex, the programme is terminated.
// Else the next colour in the colour set is selected as the active colour.
// Then calculate the number of adjacent vertices for every single uncoloured vertex left.
// Select the uncoloured vertex with the maximum adjacent vertices. (If there are more than one, then select the vertex with the highest degree.)
// Return to (A)
// }
//

// Functions needed -----------
//
// Convenient variables:
// int[] L - list of the colours of all vertices. L[0] is the colour of the first vertex, etc. The colours are represented as integers.
// boolean[][] a - adjacency matrix. That means that a[0][1] is true iff vertex 1 and vertex 2 share an edge.
// int[] colours - a list of all colours that have been used in the graph so far. All other elements in this array are 0.
// int n - total number of vertices
// int C - the active colour
// int N - the selected vertex
//
// getAdjacentVertices function, taking as input
// - int node, the desired vertex. All vertices adjacent to this node will be returned
// - boolean[][] a, adjacency matrix
// This function returns a set of integers int[] U, a list of which vertices are adjacent to the desired vertex
//
// getNonAdjacentVertices function, taking as input
// - int node, the desired vertex.
// - int[] L, list of the colours of all vertices
// - boolean[][] a, adjacency matrix
// - more? if you need
// The function returns a set of integers int[] V', a list of which vertices are not adjacent to the desired vertex, and which doesn't include the desired vertex
//
// calculateAdjacent function, taking as input
// - int[] V', a list of vertices not adjacent to the desired vertex
// - int[] U, a list of vertices adjacent to the desired vertex
// The function returns an array the same size and order as V', which contains how many vertices in U each vertex in V' is connected to.
//
// calculateUncolouredDegree function, taking as input
// - int[] L, list of the colours of all vertices
// - boolean[][] a, adjacency matrix
// The function returns an array the same size and order as L, which contains how many uncoloured vertices in L that each uncoloured vertex in L is adjacent to.
//
// calculateDegree function, taking as input
// - int[] L, list of the colours of all vertices
// - boolean[][] a, adjacency matrix
// The function returns an array the same size and order as L, which contains how many vertices in L that each vertex in L is adjacent to. Use the Degree() function.
//
// indexOfMax function, taking as input
// - int[] A, a generic list of integers
// The function returns the index (int) of the maximum element in A.
// If there are multiple elements with the same value as the maximum, choose one at random.
//
// indexOfMaxDuplicates function, taking as input
// - int[] A, a generic list of integers
// - int[] B, another generic list of integers of the same size and order as A
// The function returns the index (int) of the maximum element in A.
// If there are multiple elements with the same value as the maximum, choose the one with the highest value in B.
// If there are multiple elements which are the maximum with the same value in both A and B, then choose at random.
