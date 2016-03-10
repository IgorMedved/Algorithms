package utility;

import java.util.List;

public class HeapSort
{
	public static void maxHeapify (List<Integer> heap, int position)
	{
		int l = left(position);
		int r = right(position);
		
		int largest = (l < heap.size() && heap.get(l) > heap.get(position) )? l: position;
		
		largest = (r<heap.size() && heap.get(r)> heap.get(largest))? r:largest;
		
		if (largest!= position)
		{
			swap (heap, position, largest);
			maxHeapify (heap, largest);
		}
	}
	
	public static void maxHeapDelete( List<Integer> heap, int position)
	{
		if (position < heap.size())
		{
			heap.set(position, heap.get(heap.size()-1));
			heap.remove(heap.size()-1);
			maxHeapify_(heap, position);
		}
	}
	
	public static void minHeapDelete( List<Integer> heap, int position)
	{
		if (position < heap.size())
		{
			heap.set(position, heap.get(heap.size()-1));
			heap.remove(heap.size()-1);
			minHeapify_(heap,position); 
		}
	}
	
	public static int left (int position)
	{
		return (position+1)*2 -1; 
	}
	
	public static int right (int position)
	{
		return (position+1)*2;
	}
	
	public static int parent (int position)
	{
		return position == 0? -1: (position-1)/2;
	}
	
	public static void swap (List<Integer> heap, int pos1, int pos2)
	{
		int temp = heap.get(pos1);
		heap.set(pos1, heap.get(pos2));
		heap.set(pos2, temp);
	}
	
	public static void minHeapify (List<Integer> heap, int position)
	{
		int l = left(position);
		int r = right(position);
		
		int smallest = (l< heap.size() && heap.get(l)< heap.get(position))? l: position;
		smallest = (r<heap.size() && heap.get(r)< heap.get(smallest))? r: smallest;
		
		if (smallest!= position)
		{
			swap (heap, smallest, position);
			minHeapify (heap, smallest);
		}
	}
	
	public static void minHeapify_ (List<Integer> heap, int position)
	{
		minHeapify_ (heap, position, heap.size());
	}
	
	public static void maxHeapify_ (List<Integer> heap, int position)
	{
		maxHeapify_ (heap, position, heap.size());
	}
	
	public static void maxHeapify_ (List<Integer> heap, int position, int heapSize)
	{
		if (heapSize > heap.size()) // avoid array out of bounds errors
			return;
		
		
		int l;
		int r;
		int largest;
		boolean isSwapped;
		
		do
		{
			l = left(position);
			r = right (position);
			isSwapped = false;
			
			largest = (l< heapSize && heap.get(l)> heap.get(position))? l: position;
 			largest = (r< heapSize && heap.get(r)> heap.get(largest))? r: largest;
			
			if (largest!= position)
			{
				swap (heap, position, largest);
				position = largest;
				isSwapped = true;
			}
		}
		while (isSwapped);
	}
	
	public static void minHeapify_ (List<Integer> heap, int position, int heapSize)
	{
		if (heapSize > heap.size()) // avoid array out of bounds errors
			return;
		
		int l;
		int r;
		int smallest;
		boolean isSwapped;
		
		do
		{
			l = left(position);
			r = right (position);
			isSwapped = false;
			
			smallest = (l< heapSize && heap.get(l)< heap.get(position))? l: position;
			smallest = (r< heapSize && heap.get(r)< heap.get(smallest))? r: smallest;
			
			if (smallest!= position)
			{
				swap (heap, position, smallest);
				position = smallest;
				isSwapped = true;
			}
		}
		while (isSwapped);
	}
	
	public static void buildMaxHeap (List<Integer> heap)
	{
		for (int i = heap.size()/2 -1 ; i >=0;i-- )
			maxHeapify_(heap, i);
	}
	
	public static void buildMinHeap (List<Integer> heap)
	{
		for (int i = heap.size()/2 -1; i >=0; i--)
			minHeapify_(heap, i);
			
	}
	
	public static void heapSortAscend (List<Integer> array)
	{
		buildMaxHeap(array);
		
		for (int i = array.size()-1; i >=1; i--)
		{
			swap (array, 0, i);
			maxHeapify_ (array, 0, i);
		}
	}
	
	public static void heapSortDescend( List<Integer> array)
	{
		buildMinHeap(array);
		
		for (int i = array.size()-1; i >=1; i--)
		{
			swap (array, 0, i);
			minHeapify_ (array, 0, i);
		}
	}
}
