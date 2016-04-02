package exercize651_max_min_heap_queues;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import utility.Utility;
import utility.data_structures.MaxPriorityQueue;
import utility.data_structures.MinPriorityQueue;

public class App
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		List<Integer> array = new ArrayList<>();
		Utility.populateArray(array, 12);
		Utility.printArray(array);
		MaxPriorityQueue myMaxQ = new MaxPriorityQueue(array);
		myMaxQ.printQueueHeap();
		
		System.out.println("Extracted element is " + myMaxQ.extractMax());
		myMaxQ.printQueueHeap();
		myMaxQ.heapInsertKey(450);
		myMaxQ.printQueueHeap();
		
		MinPriorityQueue myMinQ = new MinPriorityQueue(array);
		myMinQ.printMinHeap();
		System.out.println("Extracted element is " + myMinQ.extractHeapMin());
		myMinQ.printMinHeap();
		myMinQ.heapInsertKey(335);
		myMinQ.printMinHeap();
		

	}

}
