
import java.io.*;
import java.util.*;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.awt.Color;
import javax.swing.JFrame;
import java.lang.Math;
import java.util.concurrent.ThreadLocalRandom;


public class Drawing extends Canvas {
	
	public final static boolean DEBUG = false;	
	public final static String COMMENT = "//";
										   
	static int width;
	static int height;
	static int nodeSize;
	public static ColEdge[] e = new ColEdge[20000];
	public static int[] L;
	//public static int[] L = {1, 1, 1, 1, 2, 2, 3, 3, 1, 2, 1, 2, 3, 1, 1, 2, 4, 2, 2, 4, 1, 5, 2, 1, 3, 4, 5, 1, 4, 3, 3, 1, 6, 4, 4, 2, 4, 5, 5, 5, 1, 5, 3, 4, 6, 3, 3, 7, 5, 1, 6, 6, 3, 4, 2, 5, 6, 3, 2, 4, 5, 4, 3, 2, 6, 3, 6, 7}; // graph 01
	//public static int[] L = {2, 3, 1, 4, 1, 5, 1, 6, 1, 2, 7, 8, 1, 2, 2, 2, 3, 3, 4, 3, 3, 9, 2, 3, 3, 4, 4, 5, 4, 5, 4, 4, 10, 5, 5, 1, 5, 1, 2, 5}; // graph 02
	//public static int[] L = {2, 3, 1, 1, 2, 2, 3, 3, 1, 1, 2, 3, 3, 3, 2, 1, 3, 1, 1, 3, 2, 1, 1, 1, 1, 3, 3, 1, 1, 3, 2, 1, 1, 2, 1, 3, 3, 3, 1, 1, 1, 2, 1, 2, 1, 2, 1, 1, 3, 3, 1, 2, 1, 2, 1, 2, 1, 2, 1, 1}; // graph03
	//public static int[] L = {10, 3, 2, 1, 2, 2, 2, 2, 6, 1, 4, 1, 3, 1, 1, 2, 3, 1, 1, 4, 1, 7, 3, 1, 5, 2, 1, 3, 1, 1, 2, 2, 3, 2, 1, 4, 4, 6, 4, 8, 5, 5, 1, 2, 4, 2, 2, 1, 5, 3, 2, 5, 4, 1, 6, 3, 2, 6, 9, 5, 7, 2, 2, 2, 2, 2, 10, 5, 6, 5, 2, 5, 2, 1, 6}; // graph 04
	//public static int[] L = {1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 2, 2, 1, 2, 1, 1, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 3, 1, 4, 2}; // graph 05
	//public static int[] L = {51, 1, 16, 1, 32, 1, 1, 25, 32, 1, 33, 16, 51, 16, 16, 33, 16, 1, 1, 35, 40, 1, 1, 16, 33, 1, 32, 16, 32, 1, 17, 1, 16, 31, 16, 32, 33, 16, 1, 1, 32, 51, 10, 16, 39, 31, 16, 1, 1, 1, 39, 9, 34, 16, 32, 16, 34, 1, 16, 31, 32, 1, 16, 25, 1, 1, 33, 16, 1, 1, 16, 16, 38, 1, 31, 1, 16, 34, 1, 16, 1, 17, 1, 38, 1, 1, 9, 17, 1, 16, 23, 34, 1, 40, 1, 16, 25, 1, 1, 1, 33, 16, 1, 1, 1, 1, 16, 16, 34, 26, 1, 29, 1, 48, 1, 36, 14, 1, 1, 16, 16, 16, 36, 16, 35, 16, 1, 17, 36, 1, 1, 1, 1, 1, 1, 11, 1, 16, 16, 16, 16, 48, 1, 1, 24, 16, 1, 35, 1, 16, 17, 1, 1, 35, 17, 18, 1, 1, 18, 1, 1, 1, 1, 1, 1, 1, 17, 17, 1, 26, 35, 1, 1, 35, 38, 1, 17, 1, 16, 1, 1, 33, 1, 16, 1, 1, 1, 1, 16, 1, 1, 16, 48, 1, 17, 33, 17, 1, 1, 1, 1, 27, 17, 26, 34, 1, 16, 17, 34, 1, 8, 16, 16, 16, 1, 1, 1, 34, 35, 1, 1, 16, 48, 1, 17, 24, 16, 1, 1, 32, 1, 33, 1, 16, 35, 16, 34, 1, 31, 16, 10, 1, 1, 1, 36, 1, 31, 1, 1, 17, 1, 16, 16, 40, 16, 16, 1, 39, 16, 32, 39, 1, 1, 18, 33, 1, 1, 28, 32, 34, 16, 1, 16, 1, 1, 1, 1, 31, 17, 12, 23, 24, 1, 1, 1, 36, 1, 13, 1, 16, 48, 1, 1, 36, 1, 18, 1, 1, 29, 16, 16, 1, 41, 1, 1, 34, 16, 5, 32, 30, 17, 1, 34, 18, 1, 16, 1, 25, 1, 39, 28, 1, 51, 35, 1, 29, 1, 26, 1, 33, 1, 16, 17, 17, 1, 32, 1, 1, 1, 16, 1, 1, 17, 31, 1, 1, 39, 1, 28, 34, 1, 1, 1, 1, 38, 1, 23, 1, 1, 1, 31, 1, 29, 23, 1, 36, 1, 1, 16, 18, 17, 1, 1, 17, 1, 16, 37, 1, 1, 46, 6, 38, 1, 35, 16, 1, 1, 17, 1, 1, 16, 48, 34, 32, 16, 18, 1, 42, 34, 37, 1, 29, 1, 1, 38, 1, 49, 34, 1, 16, 1, 1, 24, 33, 39, 1, 1, 1, 1, 39, 1, 16, 1, 1, 8, 30, 1, 16, 34, 16, 33, 1, 1, 1, 1, 1, 1, 1, 17, 46, 16, 17, 16, 1, 17, 1, 1, 1, 16, 17, 1, 16, 31, 24, 1, 1, 47, 39, 1, 1, 18, 18, 1, 1, 16, 1, 1, 1, 39, 1, 1, 16, 39, 1, 1, 1, 1, 1, 1, 38, 24, 36, 1, 18, 19, 1, 16, 1, 1, 1, 16, 34, 1, 31, 1, 17, 1, 1, 33, 1, 1, 36, 49, 1, 39, 37, 1, 26, 37, 40, 1, 11, 34, 1, 17, 1, 16, 1, 17, 1, 1, 28, 1, 40, 35, 18, 35, 18, 1, 37, 38, 1, 16, 17, 1, 1, 16, 35, 1, 39, 19, 1, 16, 31, 16, 1, 1, 1, 43, 1, 33, 1, 38, 1, 16, 1, 1, 1, 18, 1, 17, 33, 16, 15, 1, 1, 24, 25, 17, 35, 30, 1, 36, 1, 38, 1, 17, 17, 18, 39, 49, 19, 1, 5, 35, 16, 18, 16, 16, 18, 1, 32, 16, 16, 1, 18, 1, 33, 1, 1, 1, 1, 1, 1, 1, 17, 1, 38, 1, 28, 9, 10, 1, 1, 19, 1, 17, 7, 18, 36, 25, 38, 16, 16, 17, 39, 16, 17, 32, 39, 17, 1, 1, 39, 1, 16, 16, 17, 17, 1, 1, 1, 1, 16, 16, 16, 16, 1, 18, 1, 33, 17, 1, 17, 40, 1, 28, 1, 1, 1, 7, 1, 1, 17, 16, 29, 17, 39, 31, 1, 1, 9, 1, 18, 1, 1, 40, 1, 17, 1, 18, 1, 36, 1, 18, 39, 40, 1, 1, 1, 41, 34, 18, 1, 35, 35, 30, 1, 1, 1, 16, 31, 13, 16, 1, 1, 1, 1, 39, 44, 38, 35, 13, 40, 31, 1, 31, 38, 36, 1, 1, 10, 39, 23, 1, 36, 36, 17, 39, 32, 16, 36, 1, 40, 25, 7, 6, 37, 23, 1, 16, 26, 1, 39, 11, 6, 40, 35, 36, 32, 39, 1, 37, 1, 40, 1, 36, 25, 1, 1, 1, 38, 32, 16, 1, 39, 1, 32, 32, 40, 27, 2, 1, 40, 31, 40, 8, 17, 32, 39, 34, 16, 5, 1, 1, 1, 1, 1, 41, 41, 50, 1, 17, 32, 31, 1, 1, 16, 1, 1, 16, 35, 1, 1, 34, 16, 1, 35, 1, 2, 36, 32, 35, 45, 35, 39, 3, 4, 37, 23, 40, 39, 3, 17, 1, 33, 17, 1, 1, 4, 32, 1, 17, 40, 1, 15, 15, 18, 19, 1, 32, 17, 20, 21, 22, 1, 4, 14, 14, 32, 40, 11, 20, 17, 12, 12, 1, 1, 13, 13, 40, 20, 1, 3, 2, 2}; // graph 06 o_o
	//public static int[] L = {1, 2, 1, 2, 2, 2, 1, 1, 1, 2, 1, 1, 3, 3, 6, 2, 3, 4, 6, 4, 2, 3, 6, 2, 6, 4, 4, 7, 3, 4, 1, 5, 4, 5, 2, 8, 4, 3, 8, 3, 3, 6, 2, 1, 5, 4, 5, 5, 6, 4, 5, 6, 5, 3, 8, 7, 6, 4, 7, 1, 1, 4, 8, 7, 5, 7, 5, 3, 8, 9, 1, 3, 5, 4, 7, 9, 9, 6, 7, 8, 2}; // graph 07
	//public static int[] L = {2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 2, 2, 1, 1, 2, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 2, 1, 2, 1, 2, 2, 3, 2, 1, 1, 2, 2, 1, 1, 2, 1, 1, 1, 3, 2, 2, 1, 1, 2, 3, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 1, 1, 2, 1, 2, 2, 1, 1, 3, 2, 2, 1, 2, 1, 1, 2, 1}; // graph 08
	//public static int[] L = {8, 11, 9, 1, 6, 7, 12, 2, 13, 11, 6, 2, 6, 3, 5, 5, 7, 15, 10, 14, 12, 16, 10, 4, 8, 13, 3, 9, 15, 8}; // graph 09
	//public static int[] L = {5, 3, 4, 4, 1, 5, 4, 5, 1, 2, 3, 2, 3, 1, 2, 4, 3, 3, 2, 4, 1, 1, 2, 3, 3, 5, 2, 1, 1, 5, 4, 6, 2, 3, 1, 3, 4, 2, 3, 1}; // graph 10
	//public static int[] L = {2, 5, 1, 9, 4, 4, 12, 1, 3, 15, 10, 4, 13, 6, 8, 6, 11, 1, 8, 10, 15, 5, 14, 14, 7, 5, 5, 16, 12, 7, 11, 7, 9, 13, 13, 4, 3, 14, 6, 7, 12, 6, 16, 8, 15, 3, 2, 16, 10, 17, 2, 11, 2, 14, 10, 11, 3, 12, 9, 8}; // graph 11
	//public static int[] L = {1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 2, 2, 1, 2, 1, 1, 1, 1, 2, 2, 2, 1, 2, 2, 2, 1, 1, 3, 2, 2, 2, 1, 1, 3, 2, 2, 1, 3, 1, 2, 1, 3, 3, 3, 1, 3, 4, 3, 2, 1, 5, 3, 1, 2, 2, 4, 4, 4, 1, 5, 2, 2, 3, 2, 3, 4, 3, 1, 4, 3, 4, 3, 4, 4, 4, 3, 4, 3, 3, 4, 4, 2, 4, 4, 3, 2, 3, 5, 2, 3, 4, 3, 5, 4, 4, 4, 5, 5, 6, 5, 2, 4, 1, 4, 3, 1, 4, 2, 3, 5, 1, 5, 4, 3, 6, 5, 4, 5, 5, 6, 1, 3, 4, 2, 4, 2, 4, 6, 5, 3, 6, 5, 4, 3, 6, 7, 6, 2, 5, 4, 3, 6, 5, 5, 6, 1, 1, 3, 4, 5, 5, 4, 5, 3, 6, 2, 4, 1, 5, 5, 6, 5, 1, 5, 5, 3, 4, 6, 7, 6, 2, 4, 1, 3, 6, 5, 6, 2, 7, 6, 6, 6, 5, 5, 6, 2, 6, 5, 6, 3, 2, 7, 5, 7, 5, 5, 8, 4, 3, 4, 6, 7, 6, 7, 6}; // graph 12
	//public static int[] L = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31}; // graph 13
	//public static int[] L = {3, 1, 1, 2, 3, 2, 1, 1, 2, 4, 1, 2, 3, 3, 1, 1, 4, 4, 1, 3, 4, 1, 1, 1, 1, 3, 3, 2, 3, 2, 3, 1, 1, 1, 4, 2, 1, 2, 3, 2, 2, 5, 3, 2, 2, 3, 1, 3, 3, 4, 4, 3, 2, 1, 2, 2, 1, 2, 4, 4, 2, 1, 3, 2, 2, 1, 1, 3, 4, 1, 3, 5, 1, 3, 4, 1, 3, 2, 2, 2}; // graph 14
	//public static int[] L = {1, 1, 1, 2, 2, 2, 3, 3, 1, 2, 3, 1, 4, 4, 3, 3, 2}; // graph 15
	//public static int[] L = {9, 1, 1, 1, 2, 2, 4, 3, 5, 3, 2, 4, 5, 7, 3, 1, 5, 3, 9, 1, 5, 4, 6, 7, 3, 2, 8, 8, 2, 3, 6, 2, 6, 4, 7, 1, 7, 6, 2, 8, 1, 7, 4, 1, 8, 9, 6, 5, 2, 5, 6, 4, 2, 2, 2}; // graph 16
	//public static int[] L = {1, 2, 1, 1, 2, 2, 3, 3, 1, 2}; // graph 17
	//public static int[] L = {8, 3, 5, 3, 1, 2, 2, 3, 5, 2, 3, 4, 4, 2, 4, 2, 2, 3, 2, 3, 3, 5, 2, 6, 7, 5, 2, 6, 6, 2, 7, 2, 2, 3, 2, 1, 2, 5, 4, 3, 2, 2, 3, 3, 6, 2, 3, 2, 4, 2, 5, 4, 2, 4, 3, 2, 4, 6, 3, 2, 3, 6, 4, 2, 3, 2, 3, 2, 2, 4, 4, 2, 3, 8, 2, 2, 2, 5, 5, 5, 4, 4, 3, 1, 2, 6, 3, 3, 2, 7, 2, 5, 7, 5, 2, 2, 4, 2, 2, 5, 3, 3, 3, 4, 3, 6, 2, 3, 2, 6, 3, 2, 6, 3, 2, 2, 5, 4, 4, 3, 3, 2, 4, 5, 3, 4, 5, 3, 2, 3, 6, 6, 4, 2, 1, 2, 3, 6, 3, 2, 3, 3, 3, 4, 3, 3, 2, 2, 4, 3, 7, 3, 3, 2, 4, 2, 3, 2, 2, 2, 6, 7, 4, 5, 4, 2, 1, 2, 2, 3, 2, 7, 3, 2, 7, 3, 4, 1, 2, 3, 2, 8, 5, 4, 1, 4, 1, 2, 4, 2, 2, 1, 1, 2}; // graph 18
	//public static int[] L = {28, 3, 1, 26, 8, 8, 1, 13, 27, 7, 13, 15, 3, 26, 5, 16, 13, 13, 13, 26, 16, 23, 13, 1, 17, 15, 13, 1, 23, 1, 15, 26, 8, 14, 18, 21, 17, 28, 26, 1, 13, 24, 8, 14, 8, 19, 13, 8, 13, 1, 8, 15, 17, 9, 22, 15, 15, 18, 17, 17, 26, 25, 26, 27, 1, 13, 15, 24, 15, 16, 20, 15, 20, 17, 9, 6, 17, 1, 15, 26, 18, 27, 1, 16, 16, 8, 1, 15, 15, 27, 15, 27, 19, 28, 4, 13, 18, 13, 13, 22, 26, 26, 20, 13, 13, 15, 13, 10, 27, 27, 9, 18, 15, 15, 16, 28, 16, 21, 13, 10, 1, 13, 8, 15, 8, 15, 1, 7, 1, 17, 15, 12, 15, 5, 12, 10, 14, 13, 13, 15, 15, 1, 13, 9, 11, 1, 15, 27, 3, 12, 2, 13, 16, 13, 13, 15, 18, 17, 15, 1, 26, 13, 17, 2, 11, 15, 18, 5, 14, 18, 7, 16, 16, 8, 4, 25, 16, 8, 1, 6, 6, 11, 1, 15, 26, 15, 18, 4, 2, 2}; // graph 19
	//public static int[] L = {1, 1, 1, 1, 1, 1, 1, 2, 3, 1, 2, 2, 3, 1, 2, 2, 3, 2, 2, 3, 1, 1, 2, 2, 3, 3, 1, 3, 2, 2, 4, 4, 3, 4, 3, 4, 3, 4, 4, 3, 4, 4}; // graph 20
	
	//public static int[][] V = new int[L.length][2];
	public static Vertex[] V;
	
	// Colours
	public static Color[] colours = {Color.blue, Color.cyan, Color.darkGray, Color.gray, Color.green,
										   Color.lightGray, Color.magenta, Color.orange, Color.pink, Color.red, Color.white,
										   Color.yellow, Color.black};	
	
    public static void main(String[] args) {
		
		// ----
		
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
		//e = null;
		
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
		
		// --------------------
		
		// Manually inputting the result of the algorithm
		
		switch (args[0]) {
			case "graph01.txt":
				L = new int[]{1, 1, 1, 1, 2, 2, 3, 3, 1, 2, 1, 2, 3, 1, 1, 2, 4, 2, 2, 4, 1, 5, 2, 1, 3, 4, 5, 1, 4, 3, 3, 1, 6, 4, 4, 2, 4, 5, 5, 5, 1, 5, 3, 4, 6, 3, 3, 7, 5, 1, 6, 6, 3, 4, 2, 5, 6, 3, 2, 4, 5, 4, 3, 2, 6, 3, 6, 7}; // graph 01
				break;
			case "graph02.txt":
				L = new int[]{2, 3, 1, 4, 1, 5, 1, 6, 1, 2, 7, 8, 1, 2, 2, 2, 3, 3, 4, 3, 3, 9, 2, 3, 3, 4, 4, 5, 4, 5, 4, 4, 10, 5, 5, 1, 5, 1, 2, 5}; // graph 02
				break;
			case "graph03.txt":
				L = new int[]{2, 3, 1, 1, 2, 2, 3, 3, 1, 1, 2, 3, 3, 3, 2, 1, 3, 1, 1, 3, 2, 1, 1, 1, 1, 3, 3, 1, 1, 3, 2, 1, 1, 2, 1, 3, 3, 3, 1, 1, 1, 2, 1, 2, 1, 2, 1, 1, 3, 3, 1, 2, 1, 2, 1, 2, 1, 2, 1, 1}; // graph03
				break;
			case "graph04.txt":
				L = new int[]{10, 3, 2, 1, 2, 2, 2, 2, 6, 1, 4, 1, 3, 1, 1, 2, 3, 1, 1, 4, 1, 7, 3, 1, 5, 2, 1, 3, 1, 1, 2, 2, 3, 2, 1, 4, 4, 6, 4, 8, 5, 5, 1, 2, 4, 2, 2, 1, 5, 3, 2, 5, 4, 1, 6, 3, 2, 6, 9, 5, 7, 2, 2, 2, 2, 2, 10, 5, 6, 5, 2, 5, 2, 1, 6}; // graph 04
				break;
			case "graph05.txt":
				L = new int[]{1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 1, 1, 1, 2, 2, 1, 2, 1, 1, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 3, 1, 4, 2}; // graph 05
				break;
			case "graph06.txt":
				L = new int[]{51, 1, 16, 1, 32, 1, 1, 25, 32, 1, 33, 16, 51, 16, 16, 33, 16, 1, 1, 35, 40, 1, 1, 16, 33, 1, 32, 16, 32, 1, 17, 1, 16, 31, 16, 32, 33, 16, 1, 1, 32, 51, 10, 16, 39, 31, 16, 1, 1, 1, 39, 9, 34, 16, 32, 16, 34, 1, 16, 31, 32, 1, 16, 25, 1, 1, 33, 16, 1, 1, 16, 16, 38, 1, 31, 1, 16, 34, 1, 16, 1, 17, 1, 38, 1, 1, 9, 17, 1, 16, 23, 34, 1, 40, 1, 16, 25, 1, 1, 1, 33, 16, 1, 1, 1, 1, 16, 16, 34, 26, 1, 29, 1, 48, 1, 36, 14, 1, 1, 16, 16, 16, 36, 16, 35, 16, 1, 17, 36, 1, 1, 1, 1, 1, 1, 11, 1, 16, 16, 16, 16, 48, 1, 1, 24, 16, 1, 35, 1, 16, 17, 1, 1, 35, 17, 18, 1, 1, 18, 1, 1, 1, 1, 1, 1, 1, 17, 17, 1, 26, 35, 1, 1, 35, 38, 1, 17, 1, 16, 1, 1, 33, 1, 16, 1, 1, 1, 1, 16, 1, 1, 16, 48, 1, 17, 33, 17, 1, 1, 1, 1, 27, 17, 26, 34, 1, 16, 17, 34, 1, 8, 16, 16, 16, 1, 1, 1, 34, 35, 1, 1, 16, 48, 1, 17, 24, 16, 1, 1, 32, 1, 33, 1, 16, 35, 16, 34, 1, 31, 16, 10, 1, 1, 1, 36, 1, 31, 1, 1, 17, 1, 16, 16, 40, 16, 16, 1, 39, 16, 32, 39, 1, 1, 18, 33, 1, 1, 28, 32, 34, 16, 1, 16, 1, 1, 1, 1, 31, 17, 12, 23, 24, 1, 1, 1, 36, 1, 13, 1, 16, 48, 1, 1, 36, 1, 18, 1, 1, 29, 16, 16, 1, 41, 1, 1, 34, 16, 5, 32, 30, 17, 1, 34, 18, 1, 16, 1, 25, 1, 39, 28, 1, 51, 35, 1, 29, 1, 26, 1, 33, 1, 16, 17, 17, 1, 32, 1, 1, 1, 16, 1, 1, 17, 31, 1, 1, 39, 1, 28, 34, 1, 1, 1, 1, 38, 1, 23, 1, 1, 1, 31, 1, 29, 23, 1, 36, 1, 1, 16, 18, 17, 1, 1, 17, 1, 16, 37, 1, 1, 46, 6, 38, 1, 35, 16, 1, 1, 17, 1, 1, 16, 48, 34, 32, 16, 18, 1, 42, 34, 37, 1, 29, 1, 1, 38, 1, 49, 34, 1, 16, 1, 1, 24, 33, 39, 1, 1, 1, 1, 39, 1, 16, 1, 1, 8, 30, 1, 16, 34, 16, 33, 1, 1, 1, 1, 1, 1, 1, 17, 46, 16, 17, 16, 1, 17, 1, 1, 1, 16, 17, 1, 16, 31, 24, 1, 1, 47, 39, 1, 1, 18, 18, 1, 1, 16, 1, 1, 1, 39, 1, 1, 16, 39, 1, 1, 1, 1, 1, 1, 38, 24, 36, 1, 18, 19, 1, 16, 1, 1, 1, 16, 34, 1, 31, 1, 17, 1, 1, 33, 1, 1, 36, 49, 1, 39, 37, 1, 26, 37, 40, 1, 11, 34, 1, 17, 1, 16, 1, 17, 1, 1, 28, 1, 40, 35, 18, 35, 18, 1, 37, 38, 1, 16, 17, 1, 1, 16, 35, 1, 39, 19, 1, 16, 31, 16, 1, 1, 1, 43, 1, 33, 1, 38, 1, 16, 1, 1, 1, 18, 1, 17, 33, 16, 15, 1, 1, 24, 25, 17, 35, 30, 1, 36, 1, 38, 1, 17, 17, 18, 39, 49, 19, 1, 5, 35, 16, 18, 16, 16, 18, 1, 32, 16, 16, 1, 18, 1, 33, 1, 1, 1, 1, 1, 1, 1, 17, 1, 38, 1, 28, 9, 10, 1, 1, 19, 1, 17, 7, 18, 36, 25, 38, 16, 16, 17, 39, 16, 17, 32, 39, 17, 1, 1, 39, 1, 16, 16, 17, 17, 1, 1, 1, 1, 16, 16, 16, 16, 1, 18, 1, 33, 17, 1, 17, 40, 1, 28, 1, 1, 1, 7, 1, 1, 17, 16, 29, 17, 39, 31, 1, 1, 9, 1, 18, 1, 1, 40, 1, 17, 1, 18, 1, 36, 1, 18, 39, 40, 1, 1, 1, 41, 34, 18, 1, 35, 35, 30, 1, 1, 1, 16, 31, 13, 16, 1, 1, 1, 1, 39, 44, 38, 35, 13, 40, 31, 1, 31, 38, 36, 1, 1, 10, 39, 23, 1, 36, 36, 17, 39, 32, 16, 36, 1, 40, 25, 7, 6, 37, 23, 1, 16, 26, 1, 39, 11, 6, 40, 35, 36, 32, 39, 1, 37, 1, 40, 1, 36, 25, 1, 1, 1, 38, 32, 16, 1, 39, 1, 32, 32, 40, 27, 2, 1, 40, 31, 40, 8, 17, 32, 39, 34, 16, 5, 1, 1, 1, 1, 1, 41, 41, 50, 1, 17, 32, 31, 1, 1, 16, 1, 1, 16, 35, 1, 1, 34, 16, 1, 35, 1, 2, 36, 32, 35, 45, 35, 39, 3, 4, 37, 23, 40, 39, 3, 17, 1, 33, 17, 1, 1, 4, 32, 1, 17, 40, 1, 15, 15, 18, 19, 1, 32, 17, 20, 21, 22, 1, 4, 14, 14, 32, 40, 11, 20, 17, 12, 12, 1, 1, 13, 13, 40, 20, 1, 3, 2, 2}; // graph 06 o_o
				break;
			case "graph07.txt":
				L = new int[]{1, 2, 1, 2, 2, 2, 1, 1, 1, 2, 1, 1, 3, 3, 6, 2, 3, 4, 6, 4, 2, 3, 6, 2, 6, 4, 4, 7, 3, 4, 1, 5, 4, 5, 2, 8, 4, 3, 8, 3, 3, 6, 2, 1, 5, 4, 5, 5, 6, 4, 5, 6, 5, 3, 8, 7, 6, 4, 7, 1, 1, 4, 8, 7, 5, 7, 5, 3, 8, 9, 1, 3, 5, 4, 7, 9, 9, 6, 7, 8, 2}; // graph 07
				break;
			case "graph08.txt":
				L = new int[]{2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 2, 2, 1, 1, 2, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2, 2, 1, 2, 1, 2, 2, 3, 2, 1, 1, 2, 2, 1, 1, 2, 1, 1, 1, 3, 2, 2, 1, 1, 2, 3, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 1, 1, 2, 1, 2, 2, 1, 1, 3, 2, 2, 1, 2, 1, 1, 2, 1}; // graph 08
				break;
			case "graph09.txt":
				L = new int[]{8, 11, 9, 1, 6, 7, 12, 2, 13, 11, 6, 2, 6, 3, 5, 5, 7, 15, 10, 14, 12, 16, 10, 4, 8, 13, 3, 9, 15, 8}; // graph 09
				break;
			case "graph10.txt":
				L = new int[]{5, 3, 4, 4, 1, 5, 4, 5, 1, 2, 3, 2, 3, 1, 2, 4, 3, 3, 2, 4, 1, 1, 2, 3, 3, 5, 2, 1, 1, 5, 4, 6, 2, 3, 1, 3, 4, 2, 3, 1}; // graph 10
				break;
			case "graph11.txt":
				L = new int[]{2, 5, 1, 9, 4, 4, 12, 1, 3, 15, 10, 4, 13, 6, 8, 6, 11, 1, 8, 10, 15, 5, 14, 14, 7, 5, 5, 16, 12, 7, 11, 7, 9, 13, 13, 4, 3, 14, 6, 7, 12, 6, 16, 8, 15, 3, 2, 16, 10, 17, 2, 11, 2, 14, 10, 11, 3, 12, 9, 8}; // graph 11
				break;
			case "graph12.txt":
				L = new int[]{1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1, 2, 2, 1, 2, 1, 1, 1, 1, 2, 2, 2, 1, 2, 2, 2, 1, 1, 3, 2, 2, 2, 1, 1, 3, 2, 2, 1, 3, 1, 2, 1, 3, 3, 3, 1, 3, 4, 3, 2, 1, 5, 3, 1, 2, 2, 4, 4, 4, 1, 5, 2, 2, 3, 2, 3, 4, 3, 1, 4, 3, 4, 3, 4, 4, 4, 3, 4, 3, 3, 4, 4, 2, 4, 4, 3, 2, 3, 5, 2, 3, 4, 3, 5, 4, 4, 4, 5, 5, 6, 5, 2, 4, 1, 4, 3, 1, 4, 2, 3, 5, 1, 5, 4, 3, 6, 5, 4, 5, 5, 6, 1, 3, 4, 2, 4, 2, 4, 6, 5, 3, 6, 5, 4, 3, 6, 7, 6, 2, 5, 4, 3, 6, 5, 5, 6, 1, 1, 3, 4, 5, 5, 4, 5, 3, 6, 2, 4, 1, 5, 5, 6, 5, 1, 5, 5, 3, 4, 6, 7, 6, 2, 4, 1, 3, 6, 5, 6, 2, 7, 6, 6, 6, 5, 5, 6, 2, 6, 5, 6, 3, 2, 7, 5, 7, 5, 5, 8, 4, 3, 4, 6, 7, 6, 7, 6}; // graph 12
				break;
			case "graph13.txt":
				L = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31}; // graph 13
				break;
			case "graph14.txt":
				L = new int[]{3, 1, 1, 2, 3, 2, 1, 1, 2, 4, 1, 2, 3, 3, 1, 1, 4, 4, 1, 3, 4, 1, 1, 1, 1, 3, 3, 2, 3, 2, 3, 1, 1, 1, 4, 2, 1, 2, 3, 2, 2, 5, 3, 2, 2, 3, 1, 3, 3, 4, 4, 3, 2, 1, 2, 2, 1, 2, 4, 4, 2, 1, 3, 2, 2, 1, 1, 3, 4, 1, 3, 5, 1, 3, 4, 1, 3, 2, 2, 2}; // graph 14
				break;
			case "graph15.txt":
				L = new int[]{1, 1, 1, 2, 2, 2, 3, 3, 1, 2, 3, 1, 4, 4, 3, 3, 2}; // graph 15
				break;
			case "graph16.txt":
				L = new int[]{9, 1, 1, 1, 2, 2, 4, 3, 5, 3, 2, 4, 5, 7, 3, 1, 5, 3, 9, 1, 5, 4, 6, 7, 3, 2, 8, 8, 2, 3, 6, 2, 6, 4, 7, 1, 7, 6, 2, 8, 1, 7, 4, 1, 8, 9, 6, 5, 2, 5, 6, 4, 2, 2, 2}; // graph 16
				break;
			case "graph17.txt":
				//L = new int[]{1, 2, 1, 1, 2, 2, 3, 3, 1, 2}; // graph 17
				L = new int[]{1, 2, 3, 1, 1, 2, 2, 3, 2, 1};
				break;
			case "graph18.txt":
				L = new int[]{8, 3, 5, 3, 1, 2, 2, 3, 5, 2, 3, 4, 4, 2, 4, 2, 2, 3, 2, 3, 3, 5, 2, 6, 7, 5, 2, 6, 6, 2, 7, 2, 2, 3, 2, 1, 2, 5, 4, 3, 2, 2, 3, 3, 6, 2, 3, 2, 4, 2, 5, 4, 2, 4, 3, 2, 4, 6, 3, 2, 3, 6, 4, 2, 3, 2, 3, 2, 2, 4, 4, 2, 3, 8, 2, 2, 2, 5, 5, 5, 4, 4, 3, 1, 2, 6, 3, 3, 2, 7, 2, 5, 7, 5, 2, 2, 4, 2, 2, 5, 3, 3, 3, 4, 3, 6, 2, 3, 2, 6, 3, 2, 6, 3, 2, 2, 5, 4, 4, 3, 3, 2, 4, 5, 3, 4, 5, 3, 2, 3, 6, 6, 4, 2, 1, 2, 3, 6, 3, 2, 3, 3, 3, 4, 3, 3, 2, 2, 4, 3, 7, 3, 3, 2, 4, 2, 3, 2, 2, 2, 6, 7, 4, 5, 4, 2, 1, 2, 2, 3, 2, 7, 3, 2, 7, 3, 4, 1, 2, 3, 2, 8, 5, 4, 1, 4, 1, 2, 4, 2, 2, 1, 1, 2}; // graph 18
				break;
			case "graph19.txt":
				L = new int[]{28, 3, 1, 26, 8, 8, 1, 13, 27, 7, 13, 15, 3, 26, 5, 16, 13, 13, 13, 26, 16, 23, 13, 1, 17, 15, 13, 1, 23, 1, 15, 26, 8, 14, 18, 21, 17, 28, 26, 1, 13, 24, 8, 14, 8, 19, 13, 8, 13, 1, 8, 15, 17, 9, 22, 15, 15, 18, 17, 17, 26, 25, 26, 27, 1, 13, 15, 24, 15, 16, 20, 15, 20, 17, 9, 6, 17, 1, 15, 26, 18, 27, 1, 16, 16, 8, 1, 15, 15, 27, 15, 27, 19, 28, 4, 13, 18, 13, 13, 22, 26, 26, 20, 13, 13, 15, 13, 10, 27, 27, 9, 18, 15, 15, 16, 28, 16, 21, 13, 10, 1, 13, 8, 15, 8, 15, 1, 7, 1, 17, 15, 12, 15, 5, 12, 10, 14, 13, 13, 15, 15, 1, 13, 9, 11, 1, 15, 27, 3, 12, 2, 13, 16, 13, 13, 15, 18, 17, 15, 1, 26, 13, 17, 2, 11, 15, 18, 5, 14, 18, 7, 16, 16, 8, 4, 25, 16, 8, 1, 6, 6, 11, 1, 15, 26, 15, 18, 4, 2, 2}; // graph 19
				break;
			case "graph20.txt":
				L = new int[]{1, 1, 1, 1, 1, 1, 1, 2, 3, 1, 2, 2, 3, 1, 2, 2, 3, 2, 2, 3, 1, 1, 2, 2, 3, 3, 1, 3, 2, 2, 4, 4, 3, 4, 3, 4, 3, 4, 4, 3, 4, 4}; // graph 20
				break;
			default:
				break;
		}
		
		// --------------------
		
		//int n = L.length;
		
		// Create the adjacency matrix
		boolean[][] a = new boolean[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < e.length; k++) {
					if (e[k].u == i+1 && e[k].v == j+1) {
						a[i][j] = true;
						a[j][i] = true;
					}
				}
				if (!a[i][j]) { a[i][j] = false; }
			}
		}
		
		
		
		width = 800;
		height = 800;
		nodeSize = 16;
		
		// Order the nodes
		int[] DegV = calculateDegree(L, a);
		int[] order = new int[DegV.length];
		for (int i = 0; i < DegV.length; i++) {
			int maxIndex = 0;
			for (int j = 0; j < DegV.length; j++) {
				maxIndex = (DegV[j] > maxIndex) ? i : maxIndex;
			}
			order[i] = maxIndex;
			DegV[maxIndex] = -1;
		}
		
		// Make sure there are enough colours
		int maxOfL = 0;
		for (int i = 0; i < L.length; i++) {
			maxOfL = (L[i] > maxOfL) ? L[i] : maxOfL;
		}
		if (maxOfL > colours.length) {
			colours = makeColours(maxOfL, L.length);
		}
		
		//V = new int[L.length][2];
		V = new Vertex[L.length];
		
		for (int i = 0; i < L.length; i++) {
			//V[i][0] = (int) Math.round(ThreadLocalRandom.current().nextDouble(0,1)*width);
			//V[i][1] = (int) Math.round(ThreadLocalRandom.current().nextDouble(0,1)*height);
			V[order[i]] = new Vertex();
			
			//int r = ((width/2)/L.length)*i;
			//double theta = ThreadLocalRandom.current().nextDouble(0,1)*2*Math.PI;
			
			V[order[i]].x = (int) Math.round(ThreadLocalRandom.current().nextDouble(0,1)*(width-nodeSize));
			V[order[i]].y = (int) Math.round(ThreadLocalRandom.current().nextDouble(0,1)*(height-nodeSize));
			//V[order[i]].x = width/2 + (int) Math.abs(Math.round(r * Math.sin(theta)));
			//V[order[i]].y = height/2 + (int) Math.abs(Math.round(r * Math.cos(theta)));
		}
		
		for (int i = 0; i < L.length; i++) {
			for (int j = 0; j < L.length; j++) {
				while (i != j && distance(V[i], V[j]) < nodeSize*8) {
					V[j].x = (int) Math.round(Math.random()*width);
					V[j].y = (int) Math.round(Math.random()*height);
				}
				break;
			}
		}
		
		// Create colours
		
		JFrame frame = new JFrame("My Drawing");
        Canvas canvas = new Drawing();
        canvas.setSize(width, height);
        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);
    }

    public void paint(Graphics g) {	
		
		// Clear the screen
		super.paint(g);
	
		// Every edge
		for (int i = 0; i < e.length-1; i++) {
			g.setColor(Color.black);
			g.drawLine(V[e[i].u-1].x+nodeSize/2, V[e[i].u-1].y+nodeSize/2, V[e[i].v-1].x+nodeSize/2, V[e[i].v-1].y+nodeSize/2);
		}
		
		// Every vertex
		for (int i = 0; i < V.length; i++) {
			// Draw it
			g.setColor(colours[L[i]-1]);
			g.fillOval(V[i].x, V[i].y, nodeSize, nodeSize);
			g.setColor(Color.white);
			g.drawString(Integer.toString(i+1), V[i].x+nodeSize*1/4+1, V[i].y+nodeSize*3/4+1);
			g.setColor(Color.black);
			g.drawString(Integer.toString(i+1), V[i].x+nodeSize*1/4, V[i].y+nodeSize*3/4);
			
			// Update its position
			/*for (int j = 0; j < L.length; j++) {
				if (i != j) {
					//V[i].applyForce(V[j]);
					for (int k = 0; k < e.length; k++) {
						if (e[k].u == i && e[k].v == j) {
							V[i].applyConnection(V[j]);
						}
					}
				}
			}
			V[i].updatePos(width, height);*/
		}
		
		try {
			Thread.sleep(1000);
		} 
		catch(InterruptedException e) {  }
		//paint(g);
    }
	
	public void update(Graphics g) {
		// Update its position
		/*for (int i = 0; i < V.length; i++) {
			for (int j = 0; j < L.length; j++) {
				if (i != j) {
					V[i].applyForce(V[j]);
					for (int k = 0; k < e.length; k++) {
						if (e[k].u == i && e[k].v == j) {
							V[i].applyConnection(V[j]);
						}
					}
				}
			}
		}*/
	}
	
	public static float distance(Vertex W, Vertex U) {
		return (float) Math.pow(Math.pow(W.x-U.x, 2) + Math.pow(W.y-U.y, 2), 0.5);
	}
	
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
	
	public static Color[] makeColours(int nColours, int nVertices) {
		Color[] c = new Color[nColours];
		for (int i = 0; i < nColours; i++) {
			int increment = (255*3)/nColours;
			if (i < nColours/3.0) {
				c[i] = new Color(i*increment, 45, 45);
			} else if (i < nColours*(2.0/3.0)) {
				int j = (int) Math.round(nColours/3.0);
				c[i] = new Color(45, (i-j)*increment, 45);
			} else {
				int k = (int) Math.round(nColours*(2.0/3.0));
				c[i] = new Color(45, 45, (i-k)*increment);
			}
		}
		return c;
	}
}

class ColEdge {
	int u;
	int v;
	
	ColEdge(int u_, int v_) {
		u = u_;
		v = v_;
	}
	
	ColEdge() {}
}

class Vertex {
	int x;
	int y;
	
	double[] pos = new double[2];
	double[] vel = new double[2];
	double[] acc = new double[2];
	
	Vertex(int x_, int y_) {
		x = x_;
		y = y_; 
		pos[0] = (double) x_; pos[1] = (double) y_;
		vel[0] = 0.0; vel[1] = 0.0;
		acc[0] = 0.0; acc[1] = 0.0;
	}
	
	Vertex() {
		x = 0;
		y = 0; 
		pos[0] = 0.0; pos[1] = 0.0;
		vel[0] = 0.0; vel[1] = 0.0;
		acc[0] = 0.0; acc[1] = 0.0;
	}
	
	public void applyConnection(Vertex v) {
		acc[0] += (double) ((x - v.x) - 10)/100.0;
		acc[1] += (double) ((y - v.y) - 10)/100.0;
	}
	
	public void updatePos(int width, int height, int nodeSize) {
		vel[0] += acc[0]; vel[1] += acc[1];
		pos[0] += vel[0]; pos[1] += vel[1];
		acc[0] = 0.0; acc[1] = 0.0;
		
		x = (int) Math.round(pos[0]); y = (int) Math.round(pos[1]);
		int boundaryX = width - nodeSize;
		int boundaryY = height - nodeSize;
		
		if (x > width) {
			x = width;
			pos[0] = width;
			vel[0] = -vel[0];
		} else if (x < 0) {
			x = 0;
			pos[0] = 0;
			vel[0] = -vel[0];
		} else if (y > height) {
			y = height;
			pos[1] = height;
			vel[1] = -vel[1];
		} else if (y < 0) {
			y = 0;
			pos[1] = 0;
			vel[1] = -vel[1];
		}
	}
}