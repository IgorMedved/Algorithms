package utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utility
{

	public static void printArray(List<?> sortArray)
	{
		for (int i = 0; i < sortArray.size(); i++)
		{
			System.out.print(sortArray.get(i) + " ");
		}
		System.out.println();
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
			sortArray.add(random.nextInt(size *100));
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

}
