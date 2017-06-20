package exercize0911_2nd_smallest_element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.management.RuntimeErrorException;

import utility.Utility;

public class App {

	public static void main(String[] args) {
		List <Integer> array = new ArrayList<>();
		Utility.populateArray(array, 3, 10000);
		//find2ndSmallest(array);
		Utility.printArray(array);
		//System.out.println("Second smallest value is " + find2ndSmallest(array));
		List<Integer> minMax = findMinMax(array);
		System.out.println("Minimum value is " + minMax.get(0) + " maximum value is " + minMax.get(1));

	}
	
	// finds 2nd smallest element in unsorted array in n+ln(n) time
	// assumes the elements are non-negative
	// creates a tournament through a heap structure
	// the minimum element is at the top of the heap and the second smallest should be next to the tournament winner path
	public static int find2ndSmallest(List<Integer >array)
	{
		int size = array.size();
		int length = size  - (size + 1) %2; // size of copy array that contains the tournament n for odd and n-1 for even sized original array
		
		
		Integer [] copyA = new Integer[length];
		Arrays.fill(copyA, 0);
		List<Integer> copy = Arrays.asList(copyA); // copy array that will contain the tournament
		// fill the leaves of the copy array
		for (int i = 0; i < size-(size)%2; i+=2)
		{
			copy.set(length-i/2-1, Math.min(array.get(size-i-1), array.get(size-i-2)));
		}
		// if the number of elements is odd in original array one more assignment is necessary
		if (size==length)
			copy.set(length/2, array.get(0));
		// conduct tournament
		for (int i = length/2-1; i>=0; i--)
			copy.set(i, Math.min(copy.get(2*i+1), copy.get(2*i+2)));
		
		//Utility.printHeap(copy);
		
		
		// Go along the winner path to find second smallest value
		int min = copy.get(0); // top of the heap now contains smallest value
		int min2 = Integer.MAX_VALUE;
		
		int i = 0;
		
		while (i*2+2 < length)
		{
			
			int left = copy.get(2*i+1);
			int right = copy.get(2*i+2);
			
			if (min == left)
			{
				if(min == right) // if both children contain minimum then second minimum is the same as minimum
					return min; 
				else // if the value on the right is different from the left
				{
					min2 = Math.min(min2, right); // compare value on the right to min2
					i = 2*i+1; // go left
				}
			}
			else // the min value must be on the right
			{
				min2 = Math.min(min2, left); // compare min2 to the left value
				i = 2*i+2; // and go right
			}
		}
		// check for smallest second value in original array
		// System.out.println(i);
		int index;
		if (size == length ) // if the size of original array is odd
			if( i == length/2) // and we landed in the leftmost leafless node on full level 
				return min2; // then current min2 value is correct
			else // not the leftmost leafless node on lass full level
			{
				index = (i-length/2)*2-1; // calculate the index in the original array that corresponds to the end leaf
			}
		else // the size of original array is even
			index = (i-length/2)*2;
		min2 = Math.min(min2, Math.max(array.get(index), array.get(index+1))); //compare current second minimum to the largest of children
		return min2;
	}

	//find minimum and maximum on a same pass through unsorted array in O(3n/2) -2
	public static List<Integer> findMinMax(List<Integer> array)
	{
		List<Integer> minMax = new ArrayList<>(2);// position 0 contains min position 1 - max
		
		if (array.isEmpty())
		{
			throw new RuntimeErrorException(null, "Array is too small");
		}
		
		else if (array.size()%2 == 1) // if array size is odd
		{
			minMax.add(array.get(0)); // initialize min to first element
			minMax.add(array.get(0)); // initialize max to first element
			for (int i = 1; i < array.size(); i+=2) // compare successive elements in array to each other pairwise
			{
				if (array.get(i)< array.get(i+1)) // if first of compared elements is smaller
				{
					if( minMax.get(0) > array.get(i)) // compare smaller with minimum
						minMax.set(0, array.get(i));
					if(minMax.get(1) <array.get(i+1)) // compare larger with maximum
						minMax.set(1, array.get(i+1));
				}
				else // if second of compared elements is smaller
				{
					if( minMax.get(0) > array.get(i+1))
						minMax.set(0, array.get(i+1));
					if(minMax.get(1) <array.get(i))
						minMax.set(1, array.get(i));
					
				}
			}
		}
		else // if array size is even
		{
			if(array.get(0) < array.get(1)) // initialize min and maximum to the smaller and larger of first two elements respecitvely
			{
				minMax.add(array.get(0));
				minMax.add(array.get(1));
			}
			else
			{
				minMax.add(array.get(1));
				minMax.add(array.get(0));
			}
			
			for (int i = 2; i < array.size(); i+=2) // do same pairwise comparison with each other and then max and min as above
			{
				if (array.get(i)< array.get(i+1))
				{
					if( minMax.get(0) > array.get(i))
						minMax.set(0, array.get(i));
					if(minMax.get(1) <array.get(i+1))
						minMax.set(1, array.get(i+1));
				}
				else
				{
					if( minMax.get(0) > array.get(i+1))
						minMax.set(0, array.get(i+1));
					if(minMax.get(1) <array.get(i))
						minMax.set(1, array.get(i));
					
				}
			}
		}
		return minMax;
	}
	
}
