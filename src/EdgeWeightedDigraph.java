
public class EdgeWeightedDigraph {
		private final int V;
		private int E;
		private Bag<DirectedEdge>[] adj;
		private IntersectionNode[] nodes;
		
		public EdgeWeightedDigraph(int V)
		{
			if (V < 0) throw new IllegalArgumentException("Number of vertices cannot be less than zero");
			this.V = V;
			this.E = 0;
			this.adj = (Bag<DirectedEdge>[]) new Bag[V];
			this.nodes = new IntersectionNode[V];
			for(int i = 0; i < V; i++)
			{
				this.adj[i] = new Bag<DirectedEdge>();
				//this.nodes[i] = new IntersectionNode(i);
			}
		}
		
		public EdgeWeightedDigraph(GraphData adjacencyMatrix) {
			this(adjacencyMatrix.V());//calls upper constructor			 
			double[][] adjacencyList = adjacencyMatrix.adjacencyMatix();
			for(int i = 0; i < V; i++)
			{
				for (int j = 0; j < V; j++) {
					double weight = adjacencyList[i][j];
					if (weight > 0) {
						DirectedEdge e = new DirectedEdge(i, j,  weight);
						addEdge(e);
					}
				}
			}
		}
		
		private void addEdge(DirectedEdge e) {
			int v = e.from(); //source
			adj[v].add(e);
			E++;
		}
		
		/**
		 * 
		 * @return the number of vertices in the graph
		 */
		public int V() {
			return this.V; 
		}
		
		/**
		 * 
		 * @return the number of edges in the graph
		 */
		public int E() {
			return this.E;
		}
		
		public Iterable<DirectedEdge> adj(int v) {
			if(v < 0 || v >= V) throw new IndexOutOfBoundsException("Vertex must be present in graph");
			Bag<DirectedEdge> list = new Bag<DirectedEdge>();
			for(DirectedEdge e : this.adj[v]) {
				list.add(e);
			}
			return list;
		}
		
		public Iterable<DirectedEdge> edges()
		{
			Bag<DirectedEdge> list = new Bag<DirectedEdge>();
			for(int v = 0; v < this.V; v++)	{
				for(DirectedEdge e : adj[v]) {
					//System.out.println(e.toString());
					list.add(e);
				}
			}
			return list;			
		}
		
		public int outdegree (int v)
		{
			if(v < 0 || v >= V) throw new IndexOutOfBoundsException("Vertice out of range");
			return this.adj[v].size();
		}
		
		public void printEdges()
		{
			
		}
		
	
}
