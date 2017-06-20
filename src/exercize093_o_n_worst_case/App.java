package exercize093_o_n_worst_case;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import utility.Utility;
import utility.search.Search;
import utility.sort.Sort;

public class App {
	
	private static int findCounter = 0;
	private static int assignCounter = 0;
	private static int operationsCounter = 0;
	private static int enterCounter = 0;
	private static int largestNonAssigned;
	private static List<Integer> nonAssignedInts;

	public static void main(String[] args) {
		
		// test finding nth statistics 9.3.1
		System.out.println("Finding nth statistics problem 9.3.1");
		List<Integer> testArray = new ArrayList<>();
		Utility.populateArray(testArray,100000, 1000000);
		Utility.printArray(testArray);
		
		int relativeSize = 2;
		//int nthStatistic = findNthStatistic(testArray, relativeSize);
		int nthStatistic = findNthStatisticO_n_(testArray, relativeSize, 0, testArray.size()-1);
		System.out.println("The " + relativeSize + " number in array is " + nthStatistic);
		Sort.mergeSortOptimized(testArray, 0, testArray.size()-1, 32);
		Utility.printArray(testArray);
		
		
		// test kth quantile 9.3.6
		System.out.println("\n\n\n\nTest nth quantile");
		List<Integer> testArray1 = new ArrayList<>();
		Utility.populateArray(testArray1,100000, 1000000);
		Utility.printArray(testArray1);
		int numberOfStatistics = 150;
		Integer[] stats = new Integer[numberOfStatistics];
		Arrays.fill(stats, -1);
		List <Integer> statistics = (Arrays.asList(stats));
		Random rand = new Random();
		List <Integer> indexes = assignStatisticsIndexes(testArray.size(), statistics.size());
		nonAssignedInts = new LinkedList<>(indexes);
		Utility.printArray(indexes);
		findStats(testArray, statistics, indexes, 0, testArray.size()-1, rand);
		Utility.printArray(statistics);
		
		Sort.mergeSortOptimized(testArray1, 0, testArray1.size()-1, 32);
		Utility.printArray(testArray1);
		
		System.out.println("Find stats function ran " + findCounter + " times ");
		System.out.println("Stats assignment function ran " + assignCounter + " times");
		System.out.println("enter find stats ran " + enterCounter + " times");
		System.out.println("total operations " + operationsCounter + " times");
		
		
		// testing finding numbers around median
		System.out.println("\n\n\n\nFinding numbers around median");
		List<Integer> testArray2 = new ArrayList<>();
		int size2 = 10;
		Utility.populateArray(testArray2,size2, 2);
		Utility.printArray(testArray2);
		
		int numberAroundMean = 5;
	    List <Integer> result = findNumbersAroundMedian(testArray2, numberAroundMean);
	    Sort.mergeSortOptimized(testArray2, 0, size2-1, 16);
	    Utility.printArray(testArray2);
	    Utility.printArray(result);
	    
	    // testing find median of two sorted arrays of same size
	    System.out.println("\n\n\n\nFinding median of two equal sized sorted arrays in LogN time");
	    int sizeArrays = 3;
	    List <Integer> arrayX = new ArrayList<>(sizeArrays);
	    List <Integer> arrayY = new ArrayList<>(sizeArrays);
	    Utility.populateArray(arrayX, sizeArrays);
	    Utility.populateArray(arrayY, sizeArrays);
	    Sort.mergeSort(arrayX, 0, sizeArrays-1);
	    Sort.mergeSort(arrayY, 0, sizeArrays-1);
	    int median = findMedianTwoArrays(arrayX, arrayY);
	    Utility.printArray(arrayX);
	    Utility.printArray(arrayY);
	    System.out.println("Median of two arrays is " + median);
	}
	
	// number must be < array.size()
	public static List<Integer> findNumbersAroundMedian(List<Integer> array, int number)
	{
		if (number > array.size())
			throw new RuntimeException("Array is too small");
		int size = array.size();
		int	number1 = size/2 - (number-(size+1)%2)/2 -(size+1)%2;
		int	number2 = size/2 + (number-size%2)/2 - (size+1)%2;
		
		
		System.out.println("number is " + number + " number1 is " + number1 + " number2 is " + number2 + " array size "+  array.size());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*Integer[] num = new Integer[number2-number1+1];
		Arrays.fill(num, -1);
		List<Integer> numbers = new ArrayList<>(Arrays.asList(num));*/
		Random rand = new Random();
		findNumbers (array, 0, array.size()-1, number1, number2, false, false, rand);
		List<Integer> numbers = new ArrayList<>(number2-number1+1);
		for (int i = number1; i <= number2; i++)
			numbers.add(array.get(i));
		
		return numbers;
	}
	
	private static void findNumbers(List<Integer> array, final int lower, final int upper, final int num1, final int num2, boolean num1Assigned, boolean num2Assigned, final Random rand)
	{
		if (lower > num2 || upper < num1)
			return;
		if (num1Assigned && num2Assigned)
			return;
		if (num1Assigned && upper < num2)
			return;
		if (num2Assigned && lower > num1)
			return;
		if (upper <= lower)
			return;
		
		int anchor = rand.nextInt(upper-lower+1)+ lower;
		int value = array.get(anchor);
		int partition = lower-1;
		int sameValues = 0;
		
		//System.out.println("lower " +  lower + " upper " + upper + " value " + value +  " partition " + partition );
		
		for (int i = lower; i <= upper; i++)
		{
			if (array.get(i) < value)
			{
				partition++;
				if (i!= partition)
				{
					int temp = array.get(i);
					array.set(i, array.get(partition) );
					if (sameValues!=0)
						array.set(partition + sameValues, array.get(partition));
					array.set(partition, temp);
				}
			}
			else if (array.get(i) == value)
			{
				sameValues++;
				int temp = array.get(i);
				array.set(i, array.get(partition + sameValues));
				array.set(partition + sameValues, temp);
				
			}
		}
		
		//System.out.println("num1Assigned " + num1Assigned + " num2Assigned " + num2Assigned);
		
		if (partition+1 <= num1 && partition +sameValues >= num1)
			num1Assigned = true;
		if (partition+1 <=num2 && partition +sameValues >=num2)
			num2Assigned = true;
		
		findNumbers(array, lower, partition, num1, num2, num1Assigned, num2Assigned, rand);
		findNumbers(array, partition + sameValues, upper, num1, num2, num1Assigned, num2Assigned, rand);
 	}
	
	public static boolean shouldPartition (int lower, int upper)
	{
		if (lower > upper)
			return false;
		for (Integer index: nonAssignedInts){
			if (upper < index)
				return false;
			else if (lower <= index)
				return true;
		}
		return false;
	}
	
	private static List<Integer> assignStatisticsIndexes(int arraySize, int numberStats)
	{
		if (numberStats >= arraySize)
			throw new RuntimeException("Array size should be larger than number of statistics");
		
		List<Integer> indexes = new ArrayList<>(numberStats);
		indexes.add(arraySize/(numberStats+1)-1);
		for (int i = 1; i < numberStats;i ++  ){
			indexes.add(indexes.get(i-1)+(arraySize+i)/(numberStats+1));
		}
		return indexes;
	}
	

	private static boolean assignStats (List<Integer> stats, final List<Integer> indexes, final int lower, final int value)
	{
		int index = Search.binarySearch(indexes, lower);
		
		assignCounter ++;
		if (index!=Search.NOT_FOUND)
		{
			stats.set(index, value);
			nonAssignedInts.remove((Integer)indexes.get(index));
		}
		return index!=Search.NOT_FOUND;
	}
	
	
	public static void findStats(List<Integer>testArray, List<Integer> stats, final List<Integer> indexes, final int lower, final int upper, final Random rand)
	{
		enterCounter ++;
		if(testArray.size() <= stats.size())
		{ 
			System.out.println("Too many stats to find");
			return;
		}
			
		if (lower == upper)
		{
			assignStats(stats, indexes, lower,testArray.get(lower));
			return;
		}
		
//		else if (lower > upper)
//			return;
		else if (!shouldPartition(lower,upper))
			return;
		
		findCounter++;
		
		int partition = lower-1;
		int anchor = rand.nextInt(upper-lower+1)+ lower;
		int value = testArray.get(anchor);
		int temp;
		
		operationsCounter = operationsCounter + upper - lower+1;
		for (int i = lower; i <= upper; i++)
		{
			if (testArray.get(i) <= value)
			{
				partition ++;
				if (i != partition)
				{
					temp = testArray.get(i);
					testArray.set(i, testArray.get(partition));
					testArray.set(partition, temp);
				}
			}
		}
		
		// account for same values
		int split = partition;
		while (split >0 && testArray.get(split)== value)
		{
			assignStats(stats, indexes, split, value);
			split --;
		}
		
		// check if all the stats are already assigned before doing recursive call
		boolean statsAssigned = true;
		for (int i = 0; i < stats.size(); i++)
		{
			if (stats.get(i) == -1)
			{
				statsAssigned = false;
				break;
			}
		}
		if (statsAssigned)
			return;
		
		findStats(testArray, stats, indexes, lower,split, rand);
		findStats(testArray, stats, indexes, partition+1, upper, rand);
	}
	
	
	
	private int findIndex(int arraySize, int statsSize, int position)
	{
		return position /(arraySize/(statsSize+1));
	}
	// if k is not inside the array the value of -1 is returned
	public static int findNthStatisticO_n_ (List<Integer> testArray, int k, int lower, int upper)
	{
		if(k<1 || k > testArray.size() )
			return -1; //
		
		if (lower == upper)
			return testArray.get(lower);
		 
		
		List <Integer> medianMedians = findMedian5(testArray, lower, upper);
		//Utility.printArray(medianMedians, lower, upper);
		
		while (medianMedians.size()!=1)
		{
			medianMedians = findMedian5(medianMedians, 0, medianMedians.size()-1);
			//Utility.printArray(medianMedians, lower, upper);
		}
		
		
		int value = medianMedians.get(0);
		
		int partition = lower;
		int temp;
		
		for (int i = lower; i <= upper; i ++)
		{
			if (testArray.get(i)<= value)
			{
				if (i != partition)
				{
					temp = testArray.get(i);
					testArray.set(i, testArray.get(partition));
					testArray.set(partition, temp);
				}
			partition ++;
			}
		}
		
		
		if (k-1 < partition)
			return findNthStatisticO_n_(testArray, k, lower, partition-1);
		else if (k-1 == partition)
			return testArray.get(partition);
		
		else
			return findNthStatisticO_n_(testArray, k, partition+1, upper);
		
		
	}
	
	/*9.3-8 Let X(1::n) and Y(1::n) be two arrays, each containing n numbers already in sorted order. 
	 * Give an O.lgn/-time algorithm to find the median of all 2n elements in arrays X and Y .
*/
	public static int findMedianTwoArrays(List<Integer> array1,List<Integer> array2)
	{
		int range = array1.size();
		
		int upperX = range-1;
		int lowerX = 0;
		int midX = (lowerX+upperX)/2;
		int valueX;
		
		// if value that is @midX percentile of X is between the sizes of values @range - midX - 1 and @range-midX-2 of Y or equal to one of those values than this value is a median of two arrays
		// if it is not than we can reduce the range of possible X values by half
		while (lowerX < upperX)
		{
			valueX = array1.get(midX); 
			if ((valueX > array2.get(range-midX-2) && valueX < array2.get(range-midX-1)) || // value in x @midX is between values @range-midX-2 and @range-midX-1 
					valueX == array2.get(range-midX-2) || valueX == array2.get(range-midX-1)) // value in x @midX equal to one or both of the values  @range-midX-2 and @range-midX-1 
				return valueX;
			if (valueX > array2.get(range-midX-1))
				upperX = midX; // previousUpperX > midX when upperX > lowerX
			else
				lowerX = midX+1; // previous lowerY < midX+1
			midX= (lowerX + upperX)/2;
		}
		
		// when the range of x was reduced to one the median is the smaller of the value @midX in X and value @range-midX-1 in Y
		return Math.min(array1.get(midX), array2.get(range-midX-1));
		
	}
	public static List <Integer> findMedian5 (List<Integer> testArray, int lower, int upper)
	{
		List<Integer> medians = new ArrayList<>((upper - lower)/5 +1);
		for (int i = lower; i+4 <= upper; i+=5)
		{
			Sort.insertSortNondescending(testArray, i, i+4);
			medians.add(testArray.get(i+2));
		}
		
		int remainder = (upper - lower +1)%5; // size of last array with medians
		
		if (remainder != 0)
		{
			Sort.insertSortNondescending(testArray, upper - remainder+1, upper);
			medians.add(testArray.get(upper-remainder/2));
		}
			
		
		return medians;
	}

}

/*
 * 9.3-9 Professor Olay is consulting for an oil company, which is planning a large pipeline running east to west 
 * through an oil ﬁeld of n wells. The company wants to connect a spur pipeline from each well directly to the main
 *  pipeline along a shortest route (either north or south), as shown in Figure 9.2. Given the x- andy-coordinates
 *   of the wells, how should the professor pick the optimal location of the main pipeline, which would be the one
 *    that minimizes the total length of the spurs? Show how to determine the optimal location in linear time
 *    
 *    
 *    Solution that minimizes the total length of the spurs runs through the well that is median in y direction if 
 *    number of wells is odd and anywhere between the two middle wells for even number of wells
 *    
 *    It is easy to see as moving the pipeline either south or north of the median would increase total length
 *    by dy*(n/2 + 1 - n/2) for odd number of wells and would not change until one of the middle wells is crossed dy*(n/2-n/2)
 *    Thus median value needs to be found. It is possible by finding n/2 percentile value by LogN algorithm
 */

/*
 * 9-1 Largest i numbers in sorted order Given a set of n numbers, we wish to ﬁnd the i largest in sorted order using a comparison-based algorithm. Find the algorithm that implements each of the following methods with the best asymptotic worst-case running time, and analyze the running times of the algorithms in terms of n and i.
a. Sort the numbers, and list the i largest.
b. Buildamax-priority queuefromthenumbers,andcall EXTRACT-MAX i times.
c. Use an order-statistic algorithm to ﬁnd the ith largest number, partition around that number, and sort the i largest numbers.

a. Sorting n numbers with comparisons is nLogN + i for listing the numbers
b. building maxheap nLogN + iLogN extracting the numbers
c. finding partition for i-th statistics O(N) + sorting i numbers - iLogi 
 */

/*9-2 Weighted median For n distinct elements x1;x2;:::;xn with positive weights w1;w2;:::;wn such that Pn 
 * iD1 wi D 1, theweighted (lower) median is the element xk satisfying Xx i<xk wi < 1 2 and Xx i>xk wi  1 2 : 
 * 
 * c. Show how to compute the weighted median in n worst-case time using a linear-time median algorithm such as
 *  SELECT from Section 9.3. 

Solution: find Xk which satisfies median by summing weights of elements x1.. xk until sum(wi) >=1/2 O(N)
find Xk in unordered list by partitioning O(N)
 
 post-ofﬁce location problem is deﬁned as follows. We are given n points p1;p2;:::;pn with associated weights w1;w2;:::;wn.
  We wish to ﬁnd a point p (not necessarily one of the input points) that minimizes the sumPn iD1 wi d.p;pi/, where d.a;b/is
   the distance between points a and b. 
   
  d. Argue that the weighted median is a best solution for the 1-dimensional postofﬁce
  location problem, in which points are simply real numbers and the distance between points a and b is d.a;b/Djabj.
  
   e. Find the best solution for the 2-dimensional post-ofﬁce location problem, in which the points are .x;y/ coordinate pairs 
 and the distance between points a D .x1;y1/ and b D .x2;y2/ is the Manhattan distance given by d.a;b/ D jx1 x2jCjy1 y2j. 
 
Solution Find weighted means independently for two directions
 */
