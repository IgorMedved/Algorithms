package utility.sort;

import java.util.ArrayList;
import java.util.List;

import utility.search.Search;

// This class contains different sorting algorithms
/*
 * insertion sort - start with 1 element in sub array add 1 element at a time making sub array grow and sorted
 * bubble sort - go left to right swapping element not in order from position 0 to n-2. Do it again from 0 to n-3.
 * 		stop when no swaps are made
 * mergesort - using recursion subdivide array into equal parts. Repeat until the array is divided in elements of size1
 * 		each sub array is thus trivially sorted. Next step is to merge sorted arrays one by 1 using the fact that they are sorted
 * 
 * selection sort - find the smallest element swap with element at position 0; find next smallest element in the remaing array
 * replace with element at position 1, continue until array is sorted
 * 
 * 
 * 
 * 
 * 
 */
public class Sort
{
	private static int mCores = Runtime.getRuntime().availableProcessors();

	// simple insertion sort

	public int getCores()
	{
		return mCores;
	}

	public static void insertSortNondescending(List<Integer> sortArray)
	{
		for (int i = 1; i < sortArray.size(); i++)
		{
			int key = sortArray.get(i);

			int j = i - 1;

			while (j >= 0 && key < sortArray.get(j))
			{
				sortArray.set(j + 1, sortArray.get(j));
				j--;
			}
			sortArray.set(j + 1, key);
		}
	}

	// exercize 2-1-2
	public static void insertSortNonascending(List<Integer> sortArray)
	{
		for (int i = 1; i < sortArray.size(); i++)
		{
			int key = sortArray.get(i);

			int j = i - 1;
			while (j >= 0 && key > sortArray.get(j))
			{
				sortArray.set(j + 1, sortArray.get(j));
				j--;
			}

			sortArray.set(j + 1, key);
		}
	}

	public static void mergeSort(List<Integer> sortArray, int p, int r)
	{

		if (p < r)
		{
			int q = (p + r) / 2;
			mergeSort(sortArray, p, q);
			mergeSort(sortArray, q + 1, r);
			merge(sortArray, p, q, r);
		}
	}
	
	public static void mergeSortOptimized(List<Integer> sortArray, int p, int r, int minDivisionSize)
	{

		if (p+minDivisionSize < r)
		{
			int q = (p + r) / 2;
			mergeSortOptimized(sortArray, p, q, minDivisionSize);
			mergeSortOptimized(sortArray, q + 1, r, minDivisionSize);
			merge(sortArray, p, q, r);
		}
		else
		{
			if (minDivisionSize != 0)
			insertSortNondescending (sortArray, p, r);
		}
	}
	
	public static void insertSortNondescending(List<Integer> sortArray, int p, int r)
	{
		for (int i = p+1; i <= r; i++)
		{
			int key = sortArray.get(i);

			int j = i - 1;

			while (j >= p && key < sortArray.get(j))
			{
				sortArray.set(j + 1, sortArray.get(j));
				j--;
			}
			sortArray.set(j + 1, key);
		}
	}

	public static void mergeSortWOSentinel(List<Integer> sortArray, int p, int r)
	{

		if (p < r)
		{
			int q = (p + r) / 2;
			mergeSortWOSentinel(sortArray, p, q);
			mergeSortWOSentinel(sortArray, q + 1, r);
			mergeWithoutSentinel(sortArray, p, q, r);
		}
	}

	static void merge(List<Integer> sortArray, int p, int q, int r)
	{
		int n1 = q - p + 1;
		int n2 = r - q;
		List<Integer> left = new ArrayList<>(n1 + 1);
		List<Integer> right = new ArrayList<>(n2 + 1);

		for (int i = 0; i < n1; i++)
			left.add(sortArray.get(p + i));

		for (int i = 0; i < n2; i++)
			right.add(sortArray.get(q + i + 1));

		left.add(Integer.MAX_VALUE);
		right.add(Integer.MAX_VALUE);

		int leftIndex = 0;
		int rightIndex = 0;
		for (int k = p; k <= r; k++)
			if (left.get(leftIndex) < right.get(rightIndex))
			{
				sortArray.set(k, left.get(leftIndex));
				leftIndex++;
			} 
			else
			{
				sortArray.set(k, right.get(rightIndex));
				rightIndex++;
			}

	}

	static void mergeWithoutSentinel(List<Integer> sortArray, int p,
			int q, int r)
	{
		int n1 = q - p + 1;
		int n2 = r - q;
		List<Integer> left = new ArrayList<>(n1);
		List<Integer> right = new ArrayList<>(n2);

		for (int i = 0; i < n1; i++)
			left.add(sortArray.get(p + i));

		for (int i = 0; i < n2; i++)
			right.add(sortArray.get(q + i + 1));

		int leftIndex = 0;
		int rightIndex = 0;
		for (int k = p; k <= r; k++)
		{
			if (leftIndex >= left.size())
			{
				sortArray.set(k, right.get(rightIndex));
				rightIndex++;
			} 
			else if (rightIndex >= right.size() 
					|| left.get(leftIndex) < right.get(rightIndex))
			{
				sortArray.set(k, left.get(leftIndex));
				leftIndex++;
			} 
			else
			{
				sortArray.set(k, right.get(rightIndex));
				rightIndex++;
			}
		}
	}
	
	
	/*
	2.2-2
	Consider sorting n numbers stored in array A by first finding the smallest element
	of A and exchanging it with the element in position 1. Then find the second smallest
	element of A, and exchange it with position 2. Continue in this manner for the first n 1
	elements of A. Write pseudocode for this algorithm, which is known as selection
	sort. What loop invariant does this algorithm maintain? Why does it need to run
	for only the first n 1 elements, rather than for all n elements? Give the best-case
	and worst-case running times of selection sort in �-notation.
	*/
	public static void selectionSort (List<Integer> sortArray)
    {
        for (int i = 0; i < sortArray.size() -1; i++)
        {
            int smallestIndex = Search.findSmallest(sortArray, i);
            if (smallestIndex!=i)
            {
                int temp = sortArray.get(i);
                sortArray.set(i, sortArray.get(smallestIndex));
                sortArray.set(smallestIndex, temp);
            }
        }
    }
	
	
	/*2.3-4
	* We can express insertion sort as a recursive procedure as follows. In order to sort
	* A[1 .. n], we recursively sort A[1 .. n- 1] and then insert A[n] into the sorted array
	* A[1 .. n - 1]. Write a recurrence for the running time of this recursive version of
	* insertion sort.
	*/
	
	public static void recursiveInsertionSort (List<Integer> sortedArray, int lastIndex)
	{
		
		if (lastIndex > 0)
		{
			recursiveInsertionSort(sortedArray, lastIndex-1);
			insertValue (sortedArray, lastIndex);
		}
		
		
	}
	
	public static void insertValue (List<Integer> sortedArray, int currentIndex)
	{
		int value = sortedArray.get(currentIndex);
		int insertIndex = Search.binaryInsertionSearch(sortedArray, value, 0, currentIndex-1);
		
		if (currentIndex!=insertIndex)
		{
			for (int i = currentIndex; i > insertIndex; i--)
			{
				sortedArray.set(i, sortedArray.get(i-1));
			}
			
			sortedArray.set(insertIndex, value);
		}
	}
	
    
    
}
