import java.util.ArrayList;
import java.util.List;

public class IntermediatePath {
	private String stringPath;
	private IntersectionNode[] nodes;
	private List<PathComponent> paths;
	private int s;
	private int d;
	private double totalWeight;
		
	public IntermediatePath(int sourceNode, int destinationNode, IntersectionNode[] nodes) {
		this.nodes = nodes;
		this.s = sourceNode;
		this.d = destinationNode;
		initialize();
	}
	
	private void initialize() {
		int k = this.d;		
		paths = new ArrayList<PathComponent>();
		if(this.nodes[k].getDistanceTo() < Double.POSITIVE_INFINITY) {
			stringPath = "";
			while(k != this.s) {
				int p = this.nodes[k].getPreceedingVertex();	
				totalWeight += this.nodes[k].getWeight();
				stringPath = (this.nodes[p].isFuelStation() ? "(Refuelling Stop),\n" : "") +
						String.format("%d -> %d", p, k)
						+ (d == k ? "" : ",\n") + stringPath;
				paths.add(0, new PathComponent(p, k));
				k = p;
			}	
			//pathSeries.add(0, s);
		}
	}
	
	public double getRemainingDistance() {
		return this.nodes[d].getRemainingDistance();
	}
	
	public double getDistanceTo() {
		return this.nodes[d].getDistanceTo();
	}
	
	public List<PathComponent> getPathComponents() {
		return this.paths;
	}
	
	public String getPath() {
		return this.stringPath;		
	}
	
	public int getSource() {
		return this.s;
	}
	
	public int getDestination() {
		return this.d;
	}
	
	public Double getTotalWeight() {
		return this.totalWeight;		
	}
}
