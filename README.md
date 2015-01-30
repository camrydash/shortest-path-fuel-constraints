# shortest-path-fuel-constraints
You are given a weighted connected graph of cities with all edges having positive weights. Some cities (vertices) have a gas station whereas some don’t. You have a vehicle with the tank capacity of C. That is, with full tank, the car can travel for C units of distance. Assume that any city with a gas station can fill the vehicle's tank to full. Find out the shortest path between the a given source and a given destination. Assume that you start with a full tank.

## Instructions: 
Please import "adjacencymatrix.txt" AND "fuels.txt" in the project from eclipse, and edit these files with Eclipse.

"adjacencymatrix.txt" contains graph data.
* The first line represents number of vertices (V).
* The next lines represent the adjacency matrix. 
* Make sure that number of vertices = dimensions of adjacency matrix!!, otherwise the program will only consider the number of vertices defined in the first line.
* Please enter data in the format shown below in test data.
* It is recommended to edit "adjacencymatrix.txt" in Eclipse.

`
TEST DATA FOR "adjacencymatrix.txt"
---------------------------------
8
0	5	0	0	7	7	0	0
0	0	10	0	3	0	0	0
0	0	0	6	0	0	0	0
0	0	0	0	0	0	0	0
0	0	0	4	0	2	7	3
0	0	0	0	0	0	3	0
0	0	4	5	0	0	0	0
0	0	0	0	0	0	2	0
---------------------------------
`

"fuels.txt" contains fuel station data.
* Enter the indexes of the vertices that are fuel station nodes (0...V-1). Zero-based indexing is assumed!!
* I.E if 0 is entered, this corresponds to (first node) defined in the first row of the adjacency matrix!!.
* Multiple fuel station nodes can be defined by seperating with comma.
* Nodes cannot be added if they are not defined in the graph, G, (0...V-1)
*  Please enter data in the format shown below in test data.
* It is recommended to edit "fuels.txt" in Eclipse.

`
TEST DATA FOR "fuels.txt"
---------------------------------
1,3,4
---------------------------------
`