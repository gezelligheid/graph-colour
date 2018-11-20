#GCP
ultimate graph colouring algos


-- Functions needed for p2 games --


all game modes

{

	/**
	* function to display all the edges of the graph, coloured correctly, must check whether the current colouration is legal
	* @param Edge[] e - a list of all edges, with e.u and e.v are the indices+1 of the vertices of this edge
	* @param Vertex[] V - a list of all vertices, with V.x and V.y as the coordinates
	* @return
	*/
	
	/**
	* function to display all the vertices of the graph, coloured according to the player
	* @param Vertex[] V - a list of all vertices, with V.x and V.y as its coordinates and V.colour[3] its rgb colour. See the end of the file Drawing.java for the full Vertex class.
	* @return
	*/
	
	/**
	* function to display the selection box, and which colour is currently selected
	* @param int[3] selectedColour - the rgb values of the selected colour
	* @param int[][3] availableColours - the rgb values of all available colours
	* @return
	*/
	
	/**
	* function to check whether the player has clicked on a vertex
	* @param int[2] clickPosition - the coordinates of the player's click
	* @param Vertex[] V - a list of all vertices
	* @return int index - the index of the vertex that the player clicked, or else -1 if none was
	*/
	
	/**
	* function to check whether the player has clicked on a colour in the selection box
	* @param int[2] clickPosition - the coordinates of the player's click
	* @param some representation of the colour selection box
	* @return int index - the index of the colour in the selection box that was clicked, or else -1 if none was
	*/
	
	/**
	* function to generate random graphs
	* @param int vertices - number of vertices
	* @param int edges - number of edges
	* @return boolean[][] a - an adjacency matrix representing the graph
	*/
	
	[Thomas] Something like this ?
	public boolean randomGraph() {
	System.out.println("Type number of vertices");
		int vertices = in.nextInt();
	System.out.println("Type number of edges");
		int edges = in.nextInt();
	return boolean[vertices][edges];
	}
	
}

1st two game modes: bitter end, best upper bound

{

	/**
	* hint function for when you can choose which node to colour next
	* @param many probably
	* @return int index - the index of the vertex that the hint suggests
	*/
	
}

3rd game mode: random order

{

	/**
	* function to choose an order in the graph
	* @param boolean[][] a - an adjacency matrix representing the graph
	* @return int[] order - a list of integers, where the first integer is the index of the first node that must be coloured, etc.
	*/
	
	/**
	* hint function for when you're forced to colour a node
	* @param many probably
	* @return int[3] colour - which colour we suggest that you colour this node
	*/
}
