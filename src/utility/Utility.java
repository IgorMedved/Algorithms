package utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utility
{

	public static void printArray (List<?> sortArray, int lowerBound, int upperBound)
	{
		lowerBound = lowerBound<0? 0: lowerBound;
		upperBound = upperBound>=sortArray.size()? sortArray.size()-1: upperBound;
		
		for (int i = lowerBound; i <=upperBound; i++)
		{
			System.out.print(sortArray.get(i) + " ");
		}
		System.out.println();
	}
	
	public static void printArray(List<?> sortArray)
	{
		printArray(sortArray, 0, sortArray.size());
	}

	public static void reverse(List<Short> c)
	{
		for (int i = 0; i < c.size() / 2; i++)
		{
			// swap i and c.size()-i-1 elements
			short temp = c.get(c.size() - i - 1);
			c.set(c.size() - i - 1, c.get(i));
			c.set(i, temp);
		}
	}

	public static List<Integer> copyArray(List<Integer> original)
	{
		List<Integer> copy = new ArrayList<>(original.size());
		for (int i = 0; i < original.size(); i++)
			copy.add(original.get(i));

		return copy;

	}

	public static void populateArray(List<Integer> sortArray)
	{
		Random random = new Random();

		for (int i = 0; i < 10000000; i++)
			sortArray.add(random.nextInt(100000));
	}

	public static void populateArray(List<Integer> sortArray, int size)
	{
		Random random = new Random();

		for (int i = 0; i < size; i++)
		{
			sortArray.add(random.nextInt(size ));
		}
	}

	public static List<List<Integer>> copyArrayOfArrays(
			List<List<Integer>> myArrays)
	{
		List<List<Integer>> copyArrays = new ArrayList<>();
		List<Integer> myArray;

		for (int i = 0; i < myArrays.size(); i++)
		{
			myArray = Utility.copyArray(myArrays.get(i));
			copyArrays.add(myArray);
		}

		return copyArrays;
	}

	public static void printArrayOfArrays(List<List<Integer>> myArrays)
	{
		for (int i = 0; i < myArrays.size(); i++)
		{
			System.out.println("Array #" + (i + 1) + ":");
			Utility.printArray(myArrays.get(i));
		}
	}
	
	 public static List<List<Integer>> substractMatrices (List<List<Integer>> a, List<List<Integer>> b)
	    {
	      List<List<Integer>> result = new ArrayList<>(a.size());
	      List<Integer> rowResult;
	      
	      
	      for (int i = 0; i< a.size(); i++)
	      {
	        rowResult = new ArrayList<>(a.get(i).size());
	          for (int j = 0; j< a.get(0).size(); j++)
	          {
	          rowResult.add(a.get(i).get(j) - b.get(i).get(j));
	        }
	        result.add(rowResult);
	      }
	      
	      return result;
	    }
	  
	  public static List<List<Integer>> addMatrices (List<List<Integer>> a, List<List<Integer>> b)
	    {
	      List<List<Integer>> result = new ArrayList<>(a.size());
	      List<Integer> rowResult;
	      
	      
	      for (int i = 0; i< a.size(); i++)
	      {
	        rowResult = new ArrayList<>(a.get(i).size());
	          for (int j = 0; j< a.get(0).size(); j++)
	          {
	          rowResult.add(a.get(i).get(j) + b.get(i).get(j));
	        }
	        result.add(rowResult);
	      }
	      
	      return result;
	    }
	  
	  public static void populateSqrMatrix (List<List<Integer>> matrix, int size)
	    {
	      populateMatrix (matrix, size, size);
	    }
	  
	      public static void populateMatrix (List<List<Integer>> matrix, int numRows, int numCols)
	    {
	      List<Integer> row;
	      for (int i = 0; i < numRows; i++)
	      {
	        row = new ArrayList<>();
	        populateArray(row, numCols);
	        matrix.add(row);
	      }
	    }
	      
	      public static void printMatrix(List<List<Integer>> myArrays)
	      {
	        System.out.println("Matrix is: ");
	        for (int i = 0; i < myArrays.size(); i++)
	          {
	              
	              Utility.printArray(myArrays.get(i));
	          }
	      }

}
