package exercize641_heapsort;

import java.util.ArrayList;
import java.util.List;

import utility.Utility;
import utility.sort.HeapSort;

public class App
{
	public static void main(String[] args)
	{ 
		List<Integer> array = new ArrayList<>();
		Utility.populateArray(array, 12);
		Utility.printArray(array);
		HeapSort.heapSortAscend(array);
		Utility.printArray(array);
		HeapSort.heapSortDescend(array);
		Utility.printArray(array);
		
	}
}
