
public class DirectedEdge implements Comparable<DirectedEdge> {
	private final int v;
	private final int w;
	private final double weight;
	
	public DirectedEdge(int v, int w, double weight)
	{
		if (v < 0) throw new IndexOutOfBoundsException("Vertex  names must be non-negative");
		if (w < 0) throw new IndexOutOfBoundsException("Vertex  names must be non-negative");
		if (Double.isNaN(weight)) throw new IllegalArgumentException("Weight is NaN");
        this.v = v;
        this.w = w;
        this.weight = weight;
	}
	
	public int from()
	{
		return this.v;
	}
	
	public char fromChar()
	{
		return (char)(this.v + 65);
	}
	
	public int to()
	{
		return this.w;
	}
	
	public char toChar()
	{
		return (char)(this.w + 65);
	}
	
	public double weight()
	{
		return this.weight;
	}
	
	public String toString()
	{
		return String.format("%s -> %s (%f)", this.fromChar(), this.toChar(), this.weight);
	}

	@Override
	public int compareTo(DirectedEdge o) {
		// TODO Auto-generated method stub
		return this.weight < o.weight ? 0 : 1;
	}
	

}
