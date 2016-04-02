package utility.data_structures;

import java.util.List;

import utility.Utility;
import utility.sort.HeapSort;

public class MaxPriorityQueue
{
	List<Integer> mHeapQueue;
	
	public MaxPriorityQueue (List<Integer > heapQueue)
	{
		this (heapQueue, false);
	}
	
	public MaxPriorityQueue (List<Integer> heapQueue, boolean isMaxHeap)
	{
		mHeapQueue = heapQueue;
		
		if (!isMaxHeap)
			HeapSort.buildMaxHeap(mHeapQueue);
	}
	
	public int heapMax()
	{
		return mHeapQueue.size() != 0? mHeapQueue.get(0): Integer.MIN_VALUE;
	}
	
	public int extractMax ()
	{
		int value = heapMax();
		if (mHeapQueue.size()!=0)
		{
			mHeapQueue.set(0, mHeapQueue.get(mHeapQueue.size()-1));
			mHeapQueue.remove(mHeapQueue.size()-1);
			HeapSort.maxHeapify_(mHeapQueue, 0);
		}
		
		return value;
	}
	
	public void heapIncreaseKey(int position, int key)
	{
		if (position < mHeapQueue.size() && key > mHeapQueue.get(position))
		{
			//mHeapQueue.set(position, key);
			
			int p = HeapSort.parent(position);
			while (position>0 && key> mHeapQueue.get(p))
			{
				//HeapSort.swap(mHeapQueue, position, p); // dont need to swap values just 1 assignment is enough
				mHeapQueue.set(position, mHeapQueue.get(p));
				position = p;
				p = HeapSort.parent(position);
			}
			mHeapQueue.set(position, key); // final assignment that updates the position 
		}
	}
	
	public void heapInsertKey(int key)
	{
		mHeapQueue.add(Integer.MIN_VALUE);
		heapIncreaseKey (mHeapQueue.size()-1, key);
	}
	
	public void printQueueHeap ()
	{
		Utility.printArray(mHeapQueue);
	}
}
