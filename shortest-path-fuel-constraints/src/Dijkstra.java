import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Dijkstra {
	
	private static IntersectionNode[] distTo = null;
	private static MinPriorityQueue<Double> Q;
	private static int distanceToEmpty = 9;
	private static int remainingDistance = 7;
	private static int source = 0;
	private static int destination = 4;
	
	
	public static void DijkstraFunc(EdgeWeightedDigraph G, int s, int d, double remainingDistance, double distanceToEmpty, int[] fuelData)
	{
		int V = G.V();		
		
		if (s < 0 || s > V - 1)
			throw new IllegalArgumentException(String.format("vertex(%d) does not exist in graph (source vertex)", s));
		
		if (d < 0 || d > V - 1)
			throw new IllegalArgumentException(String.format("vertex(%d) does not exist in graph (destination vertex)", s));
		
		for(DirectedEdge e : G.edges()) { //E
			if(e.weight() < 0)
				throw new IllegalArgumentException(String.format("%d -> %d (%f) has a negative weight", e.from(), e.to(), e.weight()));
		}
		
		for(int i = 0; i < fuelData.length; i++) {
			if (fuelData[i] < 0 || fuelData[i] > V - 1)
				throw new IllegalArgumentException(String.format("vertex(%d) does not exist in graph (fuel station)", fuelData[i]));
		}
		
		//Attempt to find the shortest path(s->d) without traversing through a fuel-station, f(k), where k lies in 0 <= k < V
		IntermediatePath path0 = findPath(G, s, d, remainingDistance, distanceToEmpty, fuelData);
		System.out.format("weight(G) = %f", path0.getDistanceTo());
	}
	
	/**
	 * Form a graph (1) between s and every fuel stations, (2) between every fuel station and destination node
	 * We are creating another adjacency matrix, and we execute Dijsktra's modified algorithm on this graph to find shortest path (s->d)
	 * @param sourceGraph
	 * @param s
	 * @param d
	 * @param remainingDistance
	 * @param distanceToEmpty
	 * @param fuelStations
	 */
	private static IntermediatePath findPath(EdgeWeightedDigraph G, int s, int d, double remainingDistance, double distanceToEmpty, int[] fuelData) {
		int V = G.V();
		int P = 0;
		int[] nFuelTable = new int[fuelData.length];
		int[] translationTable;
		double[][] adjacencyMatrix;
		String[][] printMatrix;
		
		initialize(s, fuelData, V);
		for(int v = 0 ; v < V; v++) {
			if(v == s || v == d || distTo[v].isFuelStation())
				P++;	
		}
		
		translationTable = new int[P];
		adjacencyMatrix = new double[P][P]; 
		printMatrix = new String[P][P];
		int j = 0;
		int f = 0;
		for(int v = 0; v < V; v++) {
			if(v == s || v == d || distTo[v].isFuelStation()) {
				if(distTo[v].isFuelStation()) {
					nFuelTable[f] = j;
					f++;
				}
				//update s & d to the values in our new graph
				if(v == s) s = j;
				else if (v == d) d = j;			
				translationTable[j] = v;
				j++;
			}
		}

		/**
		 * O(V^2)
		 * 
		 * 	
		 */
		for(int row = 0; row < P; row++) {
			//no need to calculate shortest path from destination
			for(int column = 0; column < P; column++) {			
				//shortest path from node to itself is 0
				if(translationTable[row] == translationTable[column])
					continue;
				//String path0 = String.format("%d -> %d", translationTable[row], translationTable[column]);
				//Note: remainingDistance applies to only source->f(k), if we are calculating f(k) to any other node, then we have distanceToEmpty, since "we are at fuel station node".
				IntermediatePath path = Dijsktra(G, translationTable[row], translationTable[column], translationTable[row] == s ? remainingDistance : distanceToEmpty, distanceToEmpty, fuelData);
				Double distanceTo = path.getDistanceTo();
				if(distanceTo > 0 && path.getDistanceTo() <= Double.POSITIVE_INFINITY) { // <= distanceToEmpty ??
					adjacencyMatrix[row][column] = path.getDistanceTo();					
					printMatrix[row][column] = path.getPath();
				}
			}
		}

		/**
		 * Form a graph(G) from (1) s to all fuel stations, (2) fuel stations to destination
		 * Run Dijsktra's algorithm one more time over this graph to find solution.
		 */
		GraphData mMatrix = new GraphData(translationTable.length, adjacencyMatrix); 
		EdgeWeightedDigraph mG = new EdgeWeightedDigraph(mMatrix);
		//System.out.println(printMatrix[s][d]);
			
		IntermediatePath path0 = Dijsktra(mG, s, d, remainingDistance, distanceToEmpty, nFuelTable);
		//Since the graph formed is of mG
		List<PathComponent> pathComponents = path0.getPathComponents();
		for(int i = 0; i < pathComponents.size(); i++) {
			System.out.println(printMatrix[pathComponents.get(i).getSource()][pathComponents.get(i).getDestination()]);
		}
		
		return path0;
	}
	
	private static void initialize(int s, int[] fuelData, int V) {
		distTo = new IntersectionNode[V];
		for(int v = 0; v < V; v++) 
			distTo[v] = new IntersectionNode(v, false);
		
		distTo[s].setStartingNode();
		distTo[s].setRemainingDistance(remainingDistance);
		
		for(int i = 0; i < fuelData.length; i++) {
			distTo[fuelData[i]].setFuelingStation(true);
		}
	}
	
	/**
	 * 
	 * @param G
	 * @param s (index) of source vertex
	 */
	public static IntermediatePath Dijsktra(EdgeWeightedDigraph G, int s, int d, double remainingDistance, double distanceToEmpty, int[] fuelData)
	{
		int V = G.V();		
		//0, 1, 2, 3, 4 are vertex nodes, the index (n) represents nth vertex
		/**
		 * O(V)
		 */
		initialize(s, fuelData, V);
		

		/**
		 * Initially, put the source node on the stack and initialize distance = 0.0 (so extractMin() selects vertex(s) first)
		 */
		Q = new MinPriorityQueue<Double>(V);
		Q.insert(s, 0.0);
		
		while(!Q.isEmpty()) {
			int u = Q.extractMin(); // O(VlgV)
			for(DirectedEdge e : G.adj(u)) { //O(E), aggregate analysis
				relax(e);
			}
		}	
		return new IntermediatePath(s, d, distTo);
	}
	
	private static void relax(DirectedEdge e)
	{
		/**
		 * Edge e, represents the street edge from intersection(v) to intersection(w) with a weight of e.weight()
		 */
		int v = e.from();
		int w = e.to();		
		
		/**
		 * The distance to the adjacent node = distanceTo (current node) + weight of the street edge
		 */
		double d = distTo[v].getDistanceTo() + e.weight(); //distance to adjacent node
		
		/**
		 * r represents remaining distance from current node
		 * nr represents the remaining distance of adjacent node
		 * if nr >=0, adjacent node is reachable
		 */
		double r = distTo[v].getRemainingDistance();
		double nr = r - e.weight(); 
		
		/**
		 * Condition: relaxedDistance(adjacentNode) < existingDistance(adjacentNode) AND remainingDistance(currentNode) >= e.weight() (must be reachable)
		 */
		if(d < distTo[w].getDistanceTo() && nr >= 0) {				
			distTo[w].setDistanceTo(d, e);
			distTo[w].setRemainingDistance(nr);
			if (Q.containsKey(w))
				Q.decreaseKey(w, d); //O(ElgV)
			else
				Q.insert(w, d); //O(ElgV)
		}
		
	}
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean debugMode = false;
		
		try {
			if(debugMode == false) {
				Scanner s = new Scanner(System.in);
				System.out.println("Enter \"REMAINING_DISTANCE_TO_EMPTY\"");
				remainingDistance = s.nextInt();
				System.out.println("Enter \"DRIVING_RANGE_VEHICLE\"");
				distanceToEmpty = s.nextInt();
				System.out.println("Enter \"SOURCE NODE, (0...V-1), IT FOLLOWS 0-BASED INDEX\"");
				source = s.nextInt();
				System.out.println("Enter \"DESTINATION NODE, (0...V-1), IT FOLLOWS 0-BASED INDEX\"");
				destination = s.nextInt();	
				s.close();
			}
			
			EdgeWeightedDigraph G = new EdgeWeightedDigraph(GraphUtility.readGraph());
			DijkstraFunc(G, source, destination,  remainingDistance, distanceToEmpty, GraphUtility.readFuelInfo());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}
