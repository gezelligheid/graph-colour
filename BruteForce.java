/** https://www.geeksforgeeks.org/m-coloring-problem-backtracking-5/

This is the pseudocode for the BruteForce algorithm

Input:
1) A 2D array graph[V][V] where V is the number of vertices in graph and graph[V][V] is adjacency matrix representation of the graph. A value graph[i][j] is 1 if there is a direct edge from i to j, otherwise graph[i][j] is 0.
2) An integer m which is maximum number of colors that can be used.

Output:
An array color[V] that should have numbers from 1 to m. color[i] should represent the color assigned to the ith vertex. The code should also return false if the graph cannot be colored with m colors


1. 	Function to generate adjacency matrix(can be taken from phase 1 code)

2. 	A utility function to check if the current color assignment is safe for vertex v 

3. 	A recursive utility function to solve m coloring  problem
a.	I. base case: If all vertices are assigned a color then return true 
	II. consider this vertex v and try different colors 
	III. Check if assignment of color c to v is fine
	IV. recur to assign colors to rest of the vertices 
	V. If assigning color c doesn't lead to a solution then remove it 
b.  If no color can be assigned to this vertex then return false 

4. 	This function solves the m Coloring problem using Backtracking. It mainly uses graphColoringUtil()to solve the problem. It returns false if the m colors cannot be assigned, otherwise return true and  prints assignments of colors to all vertices.Please note that there  may be more than one solutions, this function prints one of the feasible solutions.
a. 	// Initialize all color values as 0. This initialization is needed correct functioning of isSafe()
b. 	Call graphColoringUtil() for vertex 0
c. 	Print the solution 

5. 	A utility function to print solution
 
 */
