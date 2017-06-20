package exercize0241_improving_merge;

import java.util.ArrayList;
import java.util.List;

import utility.Utility;
import utility.sort.Sort;

public class App
{

	public static void main(String[] args) throws java.lang.Exception
	{
		List<List<Integer>> myArrayOfArraysMerge = new ArrayList<>();
		final int NUM_ARRAYS = 1;
		final int NUM_ELEM =  7000000;
		final int LEAFSIZE = 1;
		
		List<Integer> myArray = new ArrayList<>();
		Utility.populateArray(myArray, NUM_ELEM);
		
		List<Integer> myArrayCopy1 = (Utility.copyArray(myArray));

		List<Integer> myArrayCopy2 = (Utility.copyArray(myArray));
		/*for (int i = 0; i < NUM_ARRAYS; i++)
		{
			
			myArrayOfArraysMerge.add(Utility.copyArray(myArray));
		}
		*/
		//Utility.printArrayOfArrays(myArrayOfArraysMerge);
		
		
		List<Long> startTimes = new ArrayList<Long>(10);
		List<Long> endTimes = new ArrayList<Long>(10);
		/*for (int i = 0; i < NUM_ARRAYS; i++)
		{
			startTimes.add(System.currentTimeMillis());
			Sort.mergeSortOptimized(myArrayOfArraysMerge.get(i), 0, NUM_ELEM-1, i);
			endTimes.add(System.currentTimeMillis());
			System.out.println("The merge with " + NUM_ELEM + " elements and insertion sort starting at " 
								+  i + " elements took "+ (endTimes.get(i)-startTimes.get(i)));
		}*/
		
		
		/*
		
		startTimes.add(System.currentTimeMillis());
		Sort.mergeSortOptimized(myArrayOfArraysMerge.get(0), 0, NUM_ELEM-1, 0);
		endTimes.add(System.currentTimeMillis());
		
		startTimes.add(System.currentTimeMillis());
		Sort.mergeSortOptimized(myArrayOfArraysMerge.get(1), 0, NUM_ELEM-1, 1);
		endTimes.add(System.currentTimeMillis());
		
		startTimes.add(System.currentTimeMillis());
		Sort.mergeSortOptimized(myArrayOfArraysMerge.get(3), 0, NUM_ELEM-1, 3);
		endTimes.add(System.currentTimeMillis());
		
		startTimes.add(System.currentTimeMillis());
		Sort.mergeSortOptimized(myArrayOfArraysMerge.get(2), 0, NUM_ELEM-1, 2);
		endTimes.add(System.currentTimeMillis());
		
		startTimes.add(System.currentTimeMillis());
		Sort.mergeSortOptimized(myArrayOfArraysMerge.get(4), 0, NUM_ELEM-1, 4);
		endTimes.add(System.currentTimeMillis());
		
		startTimes.add(System.currentTimeMillis());
		Sort.mergeSortOptimized(myArrayOfArraysMerge.get(5), 0, NUM_ELEM-1, 5);
		endTimes.add(System.currentTimeMillis());*/
		
		startTimes.add(System.currentTimeMillis());
		Sort.mergeSortOptimized(myArrayCopy1, 0, NUM_ELEM-1,  40);
		endTimes.add(System.currentTimeMillis());
		
		startTimes.add(System.currentTimeMillis());
		Sort.mergeSort(myArray, 0, NUM_ELEM-1);
		endTimes.add(System.currentTimeMillis());
		
		
		
		
		
		System.out.println("The merge with " + NUM_ELEM + " elements and insertion sort starting at " 
				+  1 + " elements took "+ (endTimes.get(0)-startTimes.get(0)));
		
		System.out.println("The merge with " + NUM_ELEM + " elements and insertion sort starting at " 
				+  4 + " elements took "+ (endTimes.get(1)-startTimes.get(1)));
		
		/*System.out.println("The merge with " + NUM_ELEM + " elements and insertion sort starting at " 
				+  2 + " elements took "+ (endTimes.get(2)-startTimes.get(2)));

		System.out.println("The merge with " + NUM_ELEM + " elements and insertion sort starting at " 
				+  3 + " elements took "+ (endTimes.get(3)-startTimes.get(3)));
		
		System.out.println("The merge with " + NUM_ELEM + " elements and insertion sort starting at " 
				+  4 + " elements took "+ (endTimes.get(4)-startTimes.get(4)));
		
		System.out.println("The merge with " + NUM_ELEM + " elements and insertion sort starting at " 
				+  5 + " elements took "+ (endTimes.get(5)-startTimes.get(5)));

		System.out.println("The merge with " + NUM_ELEM + " elements and insertion sort starting at " 
				+  6 + " elements took "+ (endTimes.get(6)-startTimes.get(6)));*/
		//Utility.printArrayOfArrays(myArrayOfArraysMerge);
		
	}
	
	public static void compareMergeInsertionSort ()
	{
		List<List<Integer>> myArrayOfArraysMerge = new ArrayList<>();
		final int NUM_ARRAYS = 500000;
		final int NUM_ELEM = 10;
		List<Integer> myArray;

		for (int i = 0; i < NUM_ARRAYS; i++)
		{
			myArray = new ArrayList<>();
			Utility.populateArray(myArray, NUM_ELEM);
			myArrayOfArraysMerge.add(myArray);
		}

		// Utility.printArrayOfArrays (myArrayOfArraysMerge);

		List<List<Integer>> myArrayOfArraysInsert = Utility.copyArrayOfArrays(myArrayOfArraysMerge);

		// Utility.printArrayOfArrays (myArrayOfArraysInsert);

		Long startTimeMerge = System.currentTimeMillis();

		for (int i = 0; i < NUM_ARRAYS; i++)
		{
			Sort.mergeSort(myArrayOfArraysMerge.get(i), 0, NUM_ELEM - 1);
		}

		Long endTimeMerge = System.currentTimeMillis();

		for (int i = 0; i < NUM_ARRAYS; i++)
		{
			Sort.insertSortNondescending(myArrayOfArraysInsert.get(i));
		}

		Long endTimeInsert = System.currentTimeMillis();

		System.out.println("The time for merge is "
				+ (endTimeMerge - startTimeMerge));
		System.out.println("The time for insert is "
				+ (endTimeInsert - endTimeMerge));

		// Utility.printArrayOfArrays (myArrayOfArraysMerge);
		// Utility.printArrayOfArrays (myArrayOfArraysInsert);
	}
}
