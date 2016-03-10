package utility;

import java.util.List;

public class MinPriorityQueue
{
	private List<Integer> mMinQueue;
	
	public MinPriorityQueue(List<Integer> array)
	{
		this (array, false);
	}
	
	public MinPriorityQueue (List<Integer> array, boolean isMinHeap)
	{
		mMinQueue = array;
		if (!isMinHeap);
			HeapSort.buildMinHeap(mMinQueue);
	}
	
	public int heapMin()
	{
		return mMinQueue.size()==0? Integer.MAX_VALUE: mMinQueue.get(0);
	}
	
	public int  extractHeapMin()
	{
		int value = heapMin();
		
		if (mMinQueue.size()!= 0)
		{
			mMinQueue.set(0, mMinQueue.get(mMinQueue.size()-1));
			mMinQueue.remove(mMinQueue.size()-1);
			HeapSort.minHeapify_(mMinQueue, 0);
			
		}
		return value;
	}
	
	public void heapDecreaseKey (int index, int key)
	{
		if (mMinQueue.size()> index && key < mMinQueue.get(index))
		{
			mMinQueue.set(index, key);
			
			int p = HeapSort.parent(index);
			while (index>0 && key<mMinQueue.get(p))
			{
				//HeapSort.swap(mMinQueue, index, p);
				mMinQueue.set(index, mMinQueue.get(p)); // only one assignment instead of swap above!
				index = p;
				p = HeapSort.parent(index);
			}
			mMinQueue.set(index, key);
		}
	}
	
	public void heapInsertKey(int key)
	{
		mMinQueue.add(Integer.MAX_VALUE);
		heapDecreaseKey(mMinQueue.size() -1 , key);
	}
	
	public void printMinHeap()
	{
		Utility.printArray(mMinQueue);
	}
}
