package utility.sort;

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
	
	// increase node value and adjust node position in maxHeap accordingly (the value at the node WILL NOT BE DECREASED with this method)
	// !! this function should be used with max heap only!!!!!!
	public static void heapIncreaseKey (List<Integer> maxHeap, int position, int value) 
	{
		if (!isValidPosition(maxHeap, position))
			return;
		
		if (maxHeap.get(position) >= value)
			return;
		
		int parent = parent(position);
		
		while (position > 0 && value > maxHeap.get(parent))
		{
			maxHeap.set(position, maxHeap.get(parent));
			position = parent;
			parent = parent(position);
		}
		
		maxHeap.set(position, value);
		
		
		
	}
	
	
	// !! this function should be used with min heap only!!!!!!
	// Decrease node value and adjust node position in minHeap accordingly (the value at the node WILL NOT be increased with this method)
		
	public static void heapDecreaseKey(List<Integer> minHeap, int position, int value)
	{
		if (!isValidPosition(minHeap, position))
		{
			return;
		}
		
		if (minHeap.get(position) <= value)
			return;
		
		int parent = parent(position);
		
		while (position > 0 && value < minHeap.get(parent))
		{
			minHeap.set(position, minHeap.get(parent));
			position = parent;
			parent = parent(position);
		}
		
		minHeap.set(position, value);
		
	}
	
	// delete an element from heap at given position
	public static void maxHeapDelete( List<Integer> heap, int position)
    {
        // check that given position is valid
		if (!isValidPosition(heap, position))
          return;
		
		
        // reduce the size of the heap by 1 and save the value of the last element
        int value  = heap.get(heap.size()-1);
        heap.remove(heap.size()-1);
        
        // insert the saved value of last element back into heap at the position of the element being deleted
        if (heap.get(position) > value) // maxHeapify if the value of element being deleted > last element value
        {
        	heap.set(position, value);
        	maxHeapify (heap, position);
        }
        else // increase key 
          heapIncreaseKey (heap, position, value); 
          
         
    }
	public static void minHeapDelete( List<Integer> heap, int position)
	{
		if (!isValidPosition(heap, position))
		 return;
		
		int value  = heap.get(heap.size()-1);
        heap.remove(heap.size()-1);
        
        
		if (heap.get(position) < value)
		{
			heap.set(position, value);
			minHeapify_(heap,position); 
		}
		else // decrease key
			heapDecreaseKey(heap, position, value);
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
	
	private static boolean isValidPosition(List<Integer> heapArray, int position)
	{
		return position < heapArray.size() && position >= 0;
	}
}
