import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


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

			try 	{
			    	FileReader fr = new FileReader(inputfile);
			        BufferedReader br = new BufferedReader(fr);

			        String record = new String();

					//! THe first few lines of the file are allowed to be comments, staring with a // symbol.
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


			// Code to compute an adjacency matrix (variable a)

		 	 // l is the number of nodes in the graph minus one
		    	 int l = e.length-1;
		 // Create the adjacency matrix a of size nxn, where n is the number of nodes
		    	 boolean[][] a = new boolean[n][n];

		 	 // Go through every element in a
		    	 for (int i = 0; i < n; i++) {
		    		 for (int j = 0; j < n; j++) {
		 			 // Go through every edge in e
		    			 for (int k = 0; k < l+1; k++) {
		 // If an edge represents the current place in the matrix, then set a to true
		    				 if (e[k].u == i+1 && e[k].v == j+1) {
		    					 a[i][j] = true;
		 					 // Adjacency matrices are symmetric
		    					 a[j][i] = true;
		    				 }
		    			 }
		 // If we haven't already set a to true, then set it to false. Otherwise it would be empty there.
		    			 if (!a[i][j]) { a[i][j] = false; }
		    		 }
		    	 }
		    	 // Optional: print the adjacency matrix to console
		    	 // for (int i = 0; i < n; i++) {
		    		//  String line = "";
		    		//  for (int j = 0; j < n; j++) {
		    		// 	 if (a[i][j]) { line += "1 "; }
		    		// 	 else   	  { line += "0 "; }
		    		//  }
		    		//  System.out.println(line);
		    	 // }

					 //initiates L for testing
					 int[] L = new int[0];
					 //tests finished method
					 //System.out.println(finished(L,n));

					 //test Degree method
					 System.out.println(Degree(12, a, a.length));
					 //test indexOfMax
					 int[] testArray1 = {1, 0, 7, 7, 4, 2};
					 int[] testArray2 = {1, 9, 7, 8, 4, 2};

					 System.out.println(indexOfMax(testArray2));


		}
	/**
	"finished" function, which takes as input
	 - a list of integers L[] representing which nodes have which colours already.
	 Every node in every graph is labelled 1, 2, ... n, which means they can be referenced as an array.
	 L[0] is thus the colour of node 1.
	 L[0] is an integer representing the colour (1 is the first colour, say blue. Then 2 is red, etc).
	 The function returns true if all nodes have a colour.
	**/

	//compares the length of coloured element list to the number of vertices
	public static boolean finished(int[] L, int n){
			return (L.length == n);
    }
// "next node" function, which takes as input
// - a list of integers L[]: which nodes have which colours
// - an integer N representing the current node
// - an adjacency matrix boolean a[][]
// The function returns an integer M telling which node should be chosen next.
// Again, for now it can be random or something,
// but later it will be helpful to keep this as a separate piece of code,
// when choosing the next might be more complicated.

	public static int nextNodeWP(int[] L, int N, int[][] adj, int[] Deg){
		// welsh powel
		int M = N + 1;
		for (int i = 0; i < L.length - 1; ) {

		}
		return M;
	}
	public static int Degree(int nodeNum, boolean[][]AdMatrix, int vertices){
      int k=0; // Variable to count the node's neighbours
      for(int i=0; i<vertices; i++)// V= number of vertices
			{
   	 if(AdMatrix[nodeNum][i]==true) k++; // it can be (0,1)

   	 /* if(AdMatrix[i][nodeNum]==true)// but also (1,0) and both of them are connected to the node 0, thus are valid.
   	 k++; */
      }
			return k;
    }
		//
		// calculateDegree function, taking as input [Alain]
		// - int[] L, list of the colours of all vertices
		// - boolean[][] a, adjacency matrix
		// The function returns an array the same size and order as L,
		// which contains the degree of each vertex, ie how many vertices are adjacent to it. Use the Degree() function.

		public static int[] calculateDegree(int[] colourlist, boolean[][] adjMatrix, int vertices){
			// define array
			int[] D = new int[colourlist.length];
			//fill the degree array
			for (int i=0; i<colourlist.length; i++) {
				D[i] = Degree(i, adjMatrix, vertices);
			}
			//return the (unsorted) array of degrees
			return D;
		}
		// calculateUncolouredDegree [Alain] function, taking as input
		// - int[] L, list of the colours of all vertices
		// - boolean[][] a, adjacency matrix
		// The function returns an array the same size and order as L,
		// which contains how many uncoloured vertices in L that each uncoloured vertex in L is adjacent to.
		// coloured verices will get value -1
		// (I am pretty sure this is the correct interpretation of this line. See section 3.6, page 4 of Alain's paper, and read the first sentence of step 4. Doublecheck me?)

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
		public static int toUncolouredDegree(int nodeNum, boolean[][]adMatrix, int[] colourlist){
			int count = 0;
			// increase count only if adjecent and the adjecent node is uncoloured
			for(int i=0; i < adMatrix.length; i++){
   	 		if(adMatrix[nodeNum][i]==true && colourlist[i] == 0) count++;
      }
			return count;
		}

		// index of max value in array
		public static int indexOfMax(int[] A){
				 int maxAt = 0;

				 for (int i = 0; i < A.length; i++) {
    	 		maxAt = A[i] > A[maxAt] ? i : maxAt;
					}
				 return maxAt;
   		 }


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



}
