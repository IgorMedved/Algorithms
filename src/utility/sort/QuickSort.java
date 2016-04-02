package utility.sort;

import java.util.List;

import utility.sort.helper.IndexPair;

public class QuickSort {

	public static void quickSort(List<Integer> sortArray, int leftBound, int rightBound) {

		if (leftBound < rightBound) {
			int anchorIndex = partition(sortArray, leftBound, rightBound);
			quickSort(sortArray, leftBound, anchorIndex - 1);
			quickSort(sortArray, anchorIndex + 1, rightBound);

		}

	}

	public static void quickSortHoare(List<Integer> sortArray, int leftBound, int rightBound) {

		if (leftBound < rightBound) {
			int anchorIndex = hoarePartition(sortArray, leftBound, rightBound);
			//System.out.println("Left bound " + leftBound +" right bound " + rightBound + " anchor index " + anchorIndex);
			//Utility.printArray(sortArray, leftBound, rightBound);
			// swap (sortArray, leftBound, anchorIndex);
			quickSortHoare(sortArray, leftBound, anchorIndex);
			quickSortHoare(sortArray, anchorIndex + 1, rightBound);
			
			
			
			//Utility.printArray(sortArray, anchorIndex+1, rightBound);
			
		}
	}

	public static void quickSortEq(List<Integer> sortArray, int leftBound, int rightBound) {
		if (leftBound < rightBound) {
			IndexPair pair = partitionEq(sortArray, leftBound, rightBound);
			// swap (sortArray, leftBound, anchorIndex);
			quickSortEq(sortArray, leftBound, pair.getQ() -1);
			quickSortEq(sortArray, pair.getT() + 1, rightBound);
		}
	}

	public static IndexPair partitionEq(List<Integer> sortArray, int leftBound, int rightBound) 
	{

		int anchor = sortArray.get(rightBound);
		int nAnchors = 1; // number of elements equal in size to the anchor
							// element. Starts with one
		int separatorIndex = leftBound;

		// partion array:
		// if elements < anchor - move them to the left
		// if elements == anchor - move them to the right
		for (int i = leftBound; i <= rightBound - nAnchors; i++) {
			if (sortArray.get(i) < anchor) {
				swap(sortArray, separatorIndex, i);
				separatorIndex++;
			}

			else if (sortArray.get(i) == anchor) {

				swap(sortArray, i, rightBound - nAnchors);
				nAnchors++;
			}

		}

		// swap elements of anchor size to the middle
		// minimize swapping depending on whehter number of elements equal to
		// anchor or bigger than anchor is larger
		int numBigElements = rightBound - leftBound - separatorIndex + 1;

		for (int i = separatorIndex; i <= (nAnchors > numBigElements ? numBigElements : nAnchors); i++)
			swap(sortArray, i, rightBound - (i - separatorIndex));

		return new IndexPair(separatorIndex, separatorIndex + nAnchors - 1);

	}
	
	public static int partition(List<Integer> sortArray, int leftBound, int rightBound)
	{
		int anchor = sortArray.get(rightBound);
		int separatorIndex = leftBound;
		for (int i = leftBound; i < rightBound; i++) {
			if (sortArray.get(i) < anchor) {
				swap(sortArray, separatorIndex, i);
				separatorIndex++;
			}
		}

		swap(sortArray, separatorIndex, rightBound);

		return separatorIndex;

	}

	public static int hoarePartition(List<Integer> sortArray, int leftBound, int rightBound) {
		int anchor = sortArray.get(leftBound);
		int left = leftBound - 1;
		int right = rightBound + 1;
		

		while (true) {
			do {
				right--;
			} while (right > left && sortArray.get(right) >= anchor);

			do {
				left++;
			} while (left < right && sortArray.get(left) < anchor);


			if (left < right)
			{
				swap(sortArray, left, right);
			}
			else
			{
				return right > leftBound? right:leftBound;
			}
				

		}
	}

	

	public static void swap(List<Integer> sortArray, int index1, int index2) {
		int temp = sortArray.get(index1);
		sortArray.set(index1, sortArray.get(index2));
		sortArray.set(index2, temp);
	}

}
