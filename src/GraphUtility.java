import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GraphUtility {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try	{
			//readFromFile();
		} catch(Exception ex0)
		{
			ex0.toString();
		}

	}
	
	public static void swap(int[] array, int i, int j) {	
		int tempO = array[i];
		array[i] = array[j];
		array[j] = tempO;
		
	}
	
	//Util Function
	public static int[] listToArray(List<Integer> list)
	{
		int[] tempArray = new int[list.size()];
		for(int i = 0; i < list.size(); i++)
		{
			tempArray[i] = list.get(i);
		}
		return tempArray;
	}
	
	public static int[] readFuelInfo() throws FileNotFoundException
	{
		Scanner scan = new Scanner(new File("fuels.txt"));
		String strInput = "";
		List<Integer> arrayValues = new ArrayList<Integer>();
		if(scan.hasNextLine()) {
			strInput = scan.nextLine();
			String[] tempValues = strInput.split(",");
			for(int i = 0; i < tempValues.length; i++)
			{
				try
				{
					arrayValues.add(Integer.parseInt(tempValues[i].trim()));
				}
				catch(Exception ex0){}
			}
		}
		int[] fuelData = listToArray(arrayValues);
		System.out.println(fuelData.length > 0 ? ("fuel stations: " + Arrays.toString(fuelData)) : "no fuel stations found.");
		return fuelData;
	}
	
	public static GraphData readGraph() throws FileNotFoundException
	{
		Scanner scan = new Scanner(new File("adjacencymatrix.txt"));
		int V = scan.nextInt();	
		scan.nextLine();
		double[][] adjacencyMatrix = new double[V][V];
		for (int v = 0; v < V; v++) {
			for (int e = 0; e < V; e++) {
				adjacencyMatrix[v][e] = scan.nextDouble();
			}
		}
		return new GraphData(V, adjacencyMatrix);
	}

}
