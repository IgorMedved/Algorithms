package utility.extra;

import java.util.List;
import java.util.Random;

import utility.sort.QuickSort;

public class QuickSortJugs {
	
	private static Random random;
	
	public static void quickSortJugs(List<Integer> blue, List<Integer> red, int leftBound, int rightBound)
	{
		int randomBlueIndex = random(leftBound, rightBound);
		int blueValue =blue.get(randomBlueIndex);
		
		int redIndex = partition (red, leftBound, rightBound, blueValue);
		int redValue = red.get(redIndex);
		
		
		int blueIndex = partition (blue, leftBound, rightBound, redValue);
		
		quickSortJugs(blue, red, leftBound, blueIndex-1);
		quickSortJugs(blue, red, blueIndex+1, rightBound);
	}
	
	public static int random(int lower, int upper)
	{
		return random.nextInt(upper-lower) + lower;
	}
	
	public static int partition (List<Integer> array, int leftBound, int rightBound, int otherValue)
	{
		int anchorIndex = leftBound;
		for (int i = leftBound; i <rightBound; i++)
		{
			if (otherValue == array.get(i))
				QuickSort.swap(array, i, rightBound);
		    if (otherValue > array.get(i))
		    {
		    	QuickSort.swap(array, i, anchorIndex);
		    	anchorIndex ++;
		    }
		}
		
		QuickSort.swap (array, anchorIndex, rightBound);
			
		
		return anchorIndex;
	}

}
