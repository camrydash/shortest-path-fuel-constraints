
public class GraphData {
	private final int V;
	private final double[][] adjacencyMatix;
	
	public GraphData(int V, double[][] arrayMatrix) {
		if (V < 0) throw new IllegalArgumentException("Vertices cannot be negative");
		this.V = V;
		this.adjacencyMatix = arrayMatrix;
	}
	
	public double[][] adjacencyMatix() {
		return this.adjacencyMatix;
	}
	
	public int V() {
		return this.V;
	}
}