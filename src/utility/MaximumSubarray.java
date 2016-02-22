 package utility;

import java.util.ArrayList;
import java.util.List;

 public class MaximumSubarray
 {
 	public static MaximumSubarrayBean produceMaximumSubarray (List <Integer> myList, int low, int high)
 	{
 		if (low == high)
 		{
 			return new MaximumSubarrayBean(low, high, myList.get(low));
 		}
 		else
 		{
 			int middle = (low+high)/2;
 			MaximumSubarrayBean leftSubarray = produceMaximumSubarray(myList, low, middle);
 			MaximumSubarrayBean rightSubarray = produceMaximumSubarray(myList, middle+1, high);
 			MaximumSubarrayBean crossSubarray = middleSubarray (myList, low, high, middle);
 			
 			if (leftSubarray.getSum() >= rightSubarray.getSum() && leftSubarray.getSum() >= crossSubarray.getSum())
 				return leftSubarray;
 			else if (rightSubarray.getSum() > leftSubarray.getSum() && rightSubarray.getSum()>= crossSubarray.getSum())
 				return rightSubarray;
 			else
 				return crossSubarray;
 		}
 	}
 	
 	public static MaximumSubarrayBean middleSubarray (List <Integer> myList, int left, int right, int middle)
 	{
 		int leftSum = Integer.MIN_VALUE;
 		int sum = 0;
 		int leftMax = middle;
 		for (int i = middle; i >= left; i--)
 		{
 			sum+= myList.get(i);
 			if (sum > leftSum)
 			{
 				leftMax = i;
 				leftSum = sum;
 			}
 		}
 		
 		int rightSum = Integer.MIN_VALUE;
 		sum = 0;
 		int rightMax = middle+1;
 		for (int i = middle + 1; i <= right; i ++)
 		{
 			sum+= myList.get(i);
 			if (sum > rightSum)
 			{
 				rightSum = sum;
 				rightMax = i;
 			}
 		}
 		
 		
 		return new MaximumSubarrayBean (leftMax, rightMax, leftSum + rightSum);
 	}
 	
 	public static MaximumSubarrayBean middleSubarrayN2 (List <Integer> myList)
 	{
 		
 		MaximumSubarrayBean tempSubarray = new MaximumSubarrayBean(-1, -1, Integer.MIN_VALUE);;
 		MaximumSubarrayBean bestSubarray = copyBean(tempSubarray);
 		for (int i = 0; i < myList.size(); i++)
 		{
 			if(tempSubarray.getHigh()< i)
 			{
 				tempSubarray = findBestSubarrayFromIndex(myList, i);
 			}
 			
 			
 			else if (myList.get(i) <0)
 			{
 				tempSubarray.setLow(i);
 				tempSubarray.setSum(tempSubarray.getSum()- myList.get(i));
 			}
 			
 			if (bestSubarray.getSum() < tempSubarray.getSum())
 				bestSubarray = copyBean (tempSubarray);
 				
 		}
 		
 		return bestSubarray;
 	}
 	private static MaximumSubarrayBean findBestSubarrayFromIndex(List <Integer>myList, int startingIndex)
 	{
 		int sum = 0;
 		int bestSum = Integer.MIN_VALUE;
 		int endIndex=startingIndex;
 		
 		for (int i = startingIndex; i < myList.size(); i++)
 		{
 			sum+=myList.get(i);
 			if (sum > bestSum)
 			{
 				endIndex = i;
 				bestSum = sum;
 			}
 		}
 		
 		return new MaximumSubarrayBean(startingIndex, endIndex, bestSum);
 		
 	}
 	
 	public static MaximumSubarrayBean copyBean (MaximumSubarrayBean original)
 	{
 		return new MaximumSubarrayBean (original.getLow(),original.getHigh(), original.getSum());
 	}
 	
 	public static List<Integer> prepareArray (List<Integer> originalArray)
 	{
 		List<Integer> maxSubarray = new ArrayList<Integer>(originalArray.size()-1);
 		for (int i = 0; i < originalArray.size()-1; i ++)
 		{
 			maxSubarray.add(originalArray.get(i+1) - originalArray.get(i));
 		}
 		
 		return maxSubarray;
 	}
 }

 class MaximumSubarrayBean
 {
 	
 	
 	private int mLow;
 	private int mHigh;
 	private int mSum;
 	
 	public MaximumSubarrayBean (int low,int high, int sum)
 	{
 		mLow = low;
 		mHigh = high;
 		mSum = sum;
 	}
 	
 	public void setAll (int low,int high, int sum)
 	{
 		mLow = low;
 		mHigh = high;
 		mSum = sum;
 	}
 	
 	public void setLow (int low)
 	{
 		mLow = low;
 	}
 	
 	public void setHigh(int high)
 	{
 		mHigh = high;
 	}
 	
 	public void setSum (int sum)
 	{
 		mSum = sum;
 	}
 	
 	public int getLow ()
 	{
 		return mLow;
 	}
 	
 	public int getHigh ()
 	{
 		return mHigh;
 	}
 	
 	public int getSum()
 	{
 		return mSum;
 	}
 	
 }



