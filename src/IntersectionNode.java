
public class IntersectionNode {
	private int v; //source vertex
	private boolean isFuelingStation;
	
	private DirectedEdge edgeFrom;
	private double distanceTo;
	private double distanceToEmpty = 9;
	private double remainingDistance;

	public IntersectionNode(int v, boolean isFuelStation) {
		this.v = v;
		this.distanceTo = Double.POSITIVE_INFINITY;
		setFuelingStation(isFuelStation);
	}
	
	public void setFuelingStation(Boolean r) {
		this.isFuelingStation = r;
		if (this.isFuelingStation) 
			this.remainingDistance = this.distanceToEmpty;
		else
			this.remainingDistance = 0;
	}
	
	public double getDistanceTo() {
		return this.distanceTo;
	}
	
	public double getRemainingDistance() {
		return this.remainingDistance;
	}
	
	public void setRemainingDistance(double rd) {
		if(this.isFuelingStation)
			this.remainingDistance = this.distanceToEmpty;
		else
			this.remainingDistance = rd;
	}
	
	public void setDistanceTo(double d, DirectedEdge e) {
		this.distanceTo = d;
		this.edgeFrom = e;
	}
	
	public int getSourceVertex() {
		return this.v;
	}
	
	public Boolean isParent() {
		return this.edgeFrom == null;
	}
	
	public Boolean isFuelStation() {
		return this.isFuelingStation;
	}
	
	public int getPreceedingVertex() {
		if(isParent()) 
			throw new IllegalArgumentException("parent has no preceeding edge");
		return this.edgeFrom.from();
	}
	
	public double  getWeight() {
		if(isParent()) 
			throw new IllegalArgumentException("parent has no preceeding edge");
		return this.edgeFrom.weight();
	}
	
	public void setStartingNode() {
		this.distanceTo = 0;
	}
	
}
