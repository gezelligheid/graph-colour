import java.io.*;
import java.util.*;

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
					 System.out.println(finished(L,n));

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

	



}
