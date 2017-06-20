package exercize092_randomized_select;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import utility.Utility;
import utility.sort.Sort;

public class App {

	public static void main(String[] args) {
		List<Integer> testArray = new ArrayList<>();
		Utility.populateArray(testArray,50, 100);
		Utility.printArray(testArray);
		
		int relativeSize = 49;
		//int nthStatistic = findNthStatistic(testArray, relativeSize);
		int nthStatistic = findNthStatisticNonRecursive(testArray, relativeSize);
		System.out.println("The " + relativeSize + " number in array is " + nthStatistic);
		Sort.mergeSortOptimized(testArray, 0, testArray.size()-1, 32);
		Utility.printArray(testArray);
		
	}

	// finds kth smallest element in an array of integers and returns its value
	// k should be within array bounds
	// Warning: the order of elements is switched in the original array
	public static int findNthStatistic(List<Integer> array, int k)
	{
		int value = -1;
		Random rand = new Random();
		
		if (k > 0 && k <= array.size()) // check that k is within bounds
			value = randomizedSelect(array, 0, array.size()-1, k-1, rand);
		else
			System.out.println("The given statistics is out of bounds " + k);
		return value;
	}
	
	// recursive function that finds kth smallest element in unsorted array of integers
	// @array - array to look in
	// @lower - current lower bound index
	// @upper - current upper bound index
	// @k - originally an index between 0 and array.size() -1 of an element to look for
	// @rand - random number generator;
	// The order of elements is switched in the original array
	private static int randomizedSelect(List<Integer> array, int lower, int upper, int k, Random rand)
	{
		if (upper == lower) // if bounds converged
			return array.get(lower);
		//Utility.printArray(array, lower, upper);
		int anchor = rand.nextInt(upper - lower+1) + lower; // generate random anchor value between lower and upper
		int value = array.get(anchor); // save the value of element at anchor position
		
		int partition = lower;
		// switch anchor and upper
		int temp = array.get(upper);
		array.set(upper, value);
		array.set(anchor, temp);
		
		// go through array and divide it in two parts one with elements smaller than anchor value and another larger or equal;
		
		for (int i = lower; i < upper; i++)
		{
			if (array.get(i) < value) // if element at current position smaller than value at anchor
			{
				if (partition != i) // switching the elements only needed when partition is not at the same position as currently processed element
				{
					temp = array.get(i);
					array.set(i, array.get(partition));
					array.set(partition, temp);
				}
				partition ++;
			}
		}
		
		if (k< partition)
		{
			return randomizedSelect(array, lower, partition-1, k, rand);// the searched for element must be in the left part of the partition
		}
		else if (k == partition)
			return array.get(upper); // if partition is the same as the element we are looking for return it from anchor position
		else
		{
			return randomizedSelect(array, partition, upper -1, k-1, rand ); // look for the element in the right part (minus the anchor, adjust k)
		}
	}
	
	public static int findNthStatisticNonRecursive (List<Integer> array, int k)
	{
		if (k > 0 && k <= array.size()) // check that k is within bounds
			k--; //adjust value of k to 0 indexed array
		else
		{
			System.out.println("The given statistics is out of bounds " + k);
			return -1;
		}
		Random rand = new Random();
		
		
		
		int lower = 0;
		int upper = array.size()-1;
		int anchor, value, temp, partition;
		
		while (lower<upper)
		{
			anchor = rand.nextInt(upper-lower+1) + lower;
			value = array.get(anchor);
			partition = lower;
			
			// switch anchor and upper
			if (upper != anchor)
			{
				temp = array.get(upper);
				array.set(anchor, temp);
				array.set(upper, value);
			}
			
			for (int i = lower; i < upper; i++)
			{
				if (array.get(i) < value) // if element at current position smaller than value at anchor
				{
					if (partition != i) // switching the elements only needed when partition is not at the same position as currently processed element
					{
						temp = array.get(i);
						array.set(i, array.get(partition));
						array.set(partition, temp);
					}
					partition ++;
				}
			}
			
			if (k< partition)
			{
				upper = partition -1;return randomizedSelect(array, lower, partition-1, k, rand);// the searched for element must be in the left part of the partition
			}
			else if (k == partition)
				return array.get(upper); // if partition is the same as the element we are looking for return it from anchor position
			else
			{
				lower = partition;
				upper--;
				k--; // look for the element in the right part (minus the anchor, adjust k)
			}
			
		}
		// lower == upper
		return array.get(lower);
	}
}
