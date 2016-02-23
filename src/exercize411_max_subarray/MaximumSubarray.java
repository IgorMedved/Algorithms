package exercize411_max_subarray;

import java.util.ArrayList;
import java.util.List;

import utility.Utility;
/*
 * Maximum subarray is such a subarray where the sum of the entries is maximum. For example:
 * 1) The maximum subarray of [124, -15, 8] is [124]
 * 2) The maximum subarray of [17, -12, 6 93, -7] is [17, -12, 6 93}
 */
public class MaximumSubarray
{
	// this method finds maximum subArray recursively by splitting the original array into two parts [0... middle] [middle +1 ... array.size -1]
	// and looking for maximum subarray in 3 places leftPart, rightPart and cross (subarray that must contatin middle element of the original subarray
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
	
	// method for finding the middle subarray. It must containt at least one element middle and goes up to <code>left<code> on the left and <right> on the right
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
	
	// this is part of the exercise. It is a less efficient version of the above version that finds the maxSubarray in n2 time, however its best running time is n
 	public static MaximumSubarrayBean maximumSubarrayN2 (List <Integer> myList)
 	{
 		
 		MaximumSubarrayBean tempSubarray = new MaximumSubarrayBean(-1, -1, Integer.MIN_VALUE);;
 		MaximumSubarrayBean bestSubarray = copyBean(tempSubarray);
      	int sum = Integer.MIN_VALUE;
 		for (int i = 0; i < myList.size(); i++)
 		{
 			if(tempSubarray.getHigh()< i)
 			{
 				tempSubarray = findBestSubarrayFromIndex(myList, i); // find best subarray from element i to end of list
              	sum = tempSubarray.getSum(); // save sum
				              
 			}
          	else
              	sum -= myList.get(i-1); //
 			
 			
 			if (sum> tempSubarray.getSum())
 			{
 				tempSubarray.setLow(i);
 				tempSubarray.setSum(sum);
 			}
 			
          	//  save tempSubarray if it is better than current best
 			if (bestSubarray.getSum() < tempSubarray.getSum())
 				bestSubarray = copyBean (tempSubarray);
          	
      		
 		}
 		
 		return bestSubarray;
 	}
 	// find the best subarray that starts from starting indext and can go up to the end of the list
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