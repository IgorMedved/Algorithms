package exercize0811_count_radixsort;

import java.util.ArrayList;
import java.util.List;

import utility.Utility;
import utility.sort.CountingSort;
import utility.sort.MultithreadedSort;

public class App {
	
	public static void main (String[] args)
	{
		int size = 40000000;
		List<Integer> testArray = new ArrayList<>(size);
		Utility.populateArray(testArray, size);
		//Utility.printArray(testArray);
		long startTime = System.currentTimeMillis();
		//List<Integer> sortedArray = CountingSort.countSort(testArray, 1, 3);
		List<Integer> sortedArray = CountingSort.radixSort(testArray, 3);
		//Utility.printArray(sortedArray);
		long endTime = System.currentTimeMillis();
		
		System.out.println("Counting sort of size "  + size + " takes " + (endTime-startTime));
		
		/*List<Integer>testArray1 = new ArrayList<>();
		Utility.populateArray(testArray1,size);
		long startTimeM = System.currentTimeMillis();
		int cores = Runtime.getRuntime().availableProcessors();
		MultithreadedSort.runParallelMerge(testArray1, cores, MultithreadedSort.MERGE_SORT_OPTIMIZED);
		long endTimeM = System.currentTimeMillis();
		
		System.out.println("Merge sort of size "  + size + " takes " + (endTimeM-startTimeM));*/
	}
	
	

}
