package utility;

import java.util.ArrayList;

public class Queue
{
	public static int INVALID_KEY = Integer.MAX_VALUE;
	private static int MAX = Integer.MAX_VALUE- 1;
	private int mEndQueue = MAX;
	private int mStartQueue = MAX;
	private MaxPriorityQueue mPrQueue;
	
	public Queue()
	{
		mPrQueue= new MaxPriorityQueue(new ArrayList<Integer> ());
	}
	
	public void queue()
	{
		mPrQueue.heapInsertKey(mEndQueue);
		mEndQueue --;
	}
	
	public int extract()
	{
		if (mEndQueue < mStartQueue)
		{
			mStartQueue--;
			return mPrQueue.extractMax();
		}
		else if (mEndQueue != MAX)
		{
			mStartQueue = MAX;
			mEndQueue = MAX;
		}
		return INVALID_KEY;
	}
	
	public void printQueueHeap()
	{
		mPrQueue.printQueueHeap();
	}
}
