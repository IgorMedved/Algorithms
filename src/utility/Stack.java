package utility;

import java.util.ArrayList;

public class Stack
{
	public static int INVALID_KEY = Integer.MIN_VALUE;
	private static int MIN = Integer.MIN_VALUE+1;
	private int mCurrentKey = MIN;
	private MaxPriorityQueue mPrQueue;
	
	public Stack()
	{
		mPrQueue = new MaxPriorityQueue(new ArrayList<Integer>());
	}
	
	public void push()
	{
		mPrQueue.heapInsertKey(mCurrentKey);
		mCurrentKey++;
	}
	
	public int pop()
	{
		if (mCurrentKey > MIN)
		{
			mCurrentKey--;
			return mPrQueue.extractMax();
		}
			
			
		return INVALID_KEY;
	}
	
	public void printStackHeap()
	{
		mPrQueue.printQueueHeap();
	}
}
