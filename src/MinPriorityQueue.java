import java.util.ArrayList;
/**
 * Implementation of min-priority queue
 * 
 * @param <Key>
 */

public class MinPriorityQueue <Key extends Comparable<Key>>{
	public Key[] keys;
	public int[] pq;
	public int[] qp;
	private final int maxCapacity;
	public int N;
	
	public MinPriorityQueue(int capacity) {
		keys = (Key[]) new Comparable[capacity];	
		pq = new int[capacity];
		qp = new int[capacity];
		maxCapacity = capacity;
		for(int i = 0; i < capacity; i++) {
			qp[i] = -1; pq[i] = -1;
		}
	}
	
	public Boolean isEmpty() {
		return this.N == 0;
	}
		
	public int extractMin() {
		if(isEmpty()) {
			throw new IllegalArgumentException("No Element Exists");
		} else {		
			int minKeyIndex = pq[0];
			int lastKeyIndex = pq[N-1];						
			if(N > 1) {
				pq[0] = pq[N-1]; //increaseKey??
				qp[lastKeyIndex] = 0;
				pq[N-1] = -1;
				
			} else 
				pq[0] = -1;
			
			qp[minKeyIndex] = -1;
			N--;
			minHeapify(0);
			return minKeyIndex;
		}
	}
	
	public Key findMin() {
		if(isEmpty()) {
			return null;
		} else
			return keys[pq[0]];
	}
	
	/**
	 * Increase-Key
	 * @param key
	 * @param newObject
	 */
	public void insert(int key, Key newObject) {
		if (key < 0 || key >= maxCapacity)
			throw new IndexOutOfBoundsException();
		pq[N] = key;
		qp[key] = N;
		keys[key] = newObject;
		int loc = N;
		while (loc > 0 && keys[pq[parent(loc)]].compareTo(keys[pq[loc]]) > 0) {
			swap(parent(loc), loc);
			loc = parent(loc);
		}	
		N++;
	}
	

	/**
	 * O(1)
	 * Contains Key Value (Source Vertex)
	 * @param key
	 * @return
	 */
	public Boolean containsKey(int key) {
		if(key < 0 || key >= maxCapacity)
			return false;
			//throw new IndexOutOfBoundsException();
		return qp[key] != -1;
	}
	
	/**
	 * Checks if the node position is initialized.
	 */
	public Boolean nodeValueExists(int i) {
		if(i < 0 || i >= maxCapacity)
		{
			//throw new IndexOutOfBoundsException();
			return false;
		}
		return pq[i] != -1;
	}
	
	public void increaseKey(int key, Key newObject) {
		if (containsKey(key)) {
			if (keys[key].compareTo(newObject) <= 0) {
				keys[key] = newObject;
				int pIndex = qp[key];
				minHeapify(pIndex);
			}
		}
	}
	
	/**
	 * O(lgn)
	 * @param key
	 * @param newObject
	 */
	public void decreaseKey(int key, Key newObject) {
		if (containsKey(key)) {
			if (keys[key].compareTo(newObject) > 0) {
				keys[key] = newObject;				
				int pIndex = qp[key];
				minHeapify(pIndex);
			}
		}
	}
	
	/**
	 * If current node < parent(current) : swap(parent,current)
	 * This function holds the min-heap property
	 * @param heap
	 * @param i index of key changed
	 */
	private <E extends Comparable<E>> void minHeapify(int i) {
		int leftChild = leftChild(i);
		int rightChild = rightChild(i);
		int smallestChild = i;
		
		if(nodeValueExists(leftChild) && keys[pq[leftChild]].compareTo(keys[pq[i]]) <= 0)
			smallestChild = leftChild;
		else if(nodeValueExists(rightChild) && keys[pq[rightChild]].compareTo(keys[pq[i]]) <= 0)
			smallestChild = rightChild;
		
		if(smallestChild != i) {
			swap(i, smallestChild);
			minHeapify(smallestChild);
		}		
	}
	
	
	private void swap(int i, int j) {	
		int targetObject = pq[i];
		pq[i] = pq[j];
		pq[j] = targetObject;
		
		qp[pq[i]] = j;
		qp[pq[j]] = i;
	}
	
	private int leftChild(int i) {
		return 2*i + 1;
	}
	
	private int rightChild(int i) {
		return 2*i + 2;
	}
	
	private int parent(int i) {
		if((i + 1) == 1)
			throw new IllegalArgumentException("root has no parent");
		return ((i + 1) / 2) - 1;
	}
}
