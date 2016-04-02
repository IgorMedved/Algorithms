package utility.extra;

import java.util.List;

import utility.search.Search;


// this class contains some interesting algorithms

public class Extra
{
	/* this method finds if the 
	* @value that can be represented as a sum of two integers from sorted
	* @myList
	* @lower - index for lower bound in the list
	* @upper - index for upper bound in the list
	* The algorithm works in the following manner
	* First it finds the halfIndex for which value ~= 2* myList.get(halfIndex) using binaryInsertionPostion for value/2
	* after we check value == myList.get(halfIndex) + my.List.get(halfIndex -1) one can be sure that 1 of the indexes must be
	* smaller than half index as the array is nonDecreasing
	* before we start however we can further decrease the range that needs to be checked
	* by reevaluating upper bound to ignore the right side of the list for which Xr+Xmin > value
	* upper = binaryInsertionPosition(value-myList.get(0) - 1;
	* 
	* we then evaluate difference = value - myList.get(leftIndex)
	* and look for the element == difference in the right part of the list.
	* The right part of the list is bounded by rightIndex and upper
	* Initially right index = leftIndex + 1, but is reevaluated each turn as an insertion value of difference -1
	* Each iteration we decrement leftFront to the left by 1 and proceed until leftFront < 0 or rightFront > upper
	* While the theoretical efficiency of the algorithm is nlog n for randomly distributed large list of numbers the average
	* as well as 99 percentile is far better and for all practical purposes the limitation is sorting of the list that is required
	* to run this method
	* 
	*/
	public static boolean sumInList (List<Integer> myList, int value, int lower, int upper)
	{
		
		// find position in the sorted list for which the following relationship always holds
		// value <= myList.get(leftFront) + myList.get(rightFront)
		// initially rightFront = leftFront +1;
		int leftFront = Search.binaryInsertionSearch (myList, value/2 , lower, upper)-1;
		
		// the value < 2*myList.get(0) -value too small
		if (leftFront<0)
		{
			return false;
		}
		// check special case where value == 2 * myList.get(leftFront) & myList.get(leftFront) == myList.get(leftFront-1)
		else if (leftFront >0 && value == myList.get(leftFront) + myList.get(leftFront-1))
		{
			return true;
		}
		
		int rightFront = leftFront +1;
		// reevaluate upper bound to ignore the right side of the list for which Xr+Xmin > value
		upper = Search.binaryInsertionSearch (myList, value - myList.get(0) , lower, upper)-1;
		int counter =0;
		while (leftFront >= lower && rightFront <=upper)
		{
			counter++;
			int difference = value - myList.get(leftFront);
			rightFront = Search.binaryInsertionSearch(myList, difference, rightFront, upper)-1;
			if (myList.get(rightFront)== difference)
			{
				System.out.println("It took " + counter +" iterations to complete the search");
				return true;
			}
			else
				leftFront --;
			
			
		}
		
		return false;
		
	}
}
