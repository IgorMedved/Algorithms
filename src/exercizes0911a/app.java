package exercizes0911a;

import java.util.Random;

public class app
{
	public static void main(String[] s)
	{
		int size = 5;

		int[] array = generateArray(size, 100);
		printArray(array, size);
		System.out
				.println("The second minimum is: " + findSecondSmallest(array));
		mergeSort(array, 0, size - 1);
		printArray(array, size);

	}
	
	// regular merge sorting algorithm that works by splitting array recursively untlil the split parts are of size 1
	// Each array of size 1 is naturally sorted
	// Using the fact that each of the unmerged arrays is sorted efficient merging can be done recursively
	public static void mergeSort(int[] array, int low, int high)
	{
		if (low < high) // check if further splitting is required
		{
			int q = (low + high) / 2; // find middle of the array
			// run merge sort recursively on left half
			mergeSort(array, low, q);
			// run merge sort recursively on right half
			mergeSort(array, q + 1, high);
			// both left and right part are recursively sorted and merging of left and right half can be done
			merge(array, low, q, high);
		}

	}
// at the time merge is run parts of the array from low to q, and from q+1 to high are sorted
// merging is done by temporary copying the content of main array in two temporary arrays
	// then the content of main array is overwritten by the correct sorted sequence of elements
	public static void merge(int[] array, int low, int q, int high)
	{
		int n1 = q - low + 1; // size of left temporary array
		int n2 = high - q; // size of right temporary array
		int[] tempLeft = new int[n1 + 1]; // temporary array containing elements of left half of main array (plus 1 extra element "Sentinel")
		int[] tempRight = new int[n2 + 1]; // temporary array containing elements of right half of main array (plus 1 extra element "Sentinel")
		for (int i = 0; i < n2; i++) // copy elements from main array to temporary
		{
			tempLeft[i] = array[low + i];
			tempRight[i] = array[q + 1 + i];
		}
		if (n1 > n2) // left half can be larger by one element than the right. extra copying is required
			tempLeft[n2] = array[low + n2];

		// sentinel values are assigned to reduce the number of checks during the processing 
		tempLeft[n1] = Integer.MAX_VALUE;
		tempRight[n2] = Integer.MAX_VALUE;

		int counterLeft = 0;
		int counterRight = 0;
		// main part of merge: at each turn the smaller of the two elements from each array is coppied back into main array until all elements are processed
		do
		{
			if (tempLeft[counterLeft] < tempRight[counterRight])
			{
				array[low + counterLeft + counterRight] = tempLeft[counterLeft];
				counterLeft++;
			} else
			{
				array[low + counterLeft
						+ counterRight] = tempRight[counterRight];
				counterRight++;
			}
		} while (counterLeft + counterRight < n1 + n2);

	}

	// generate array of given @arraySize, and maximum possible value of elements @maxElement
	public static int[] generateArray(int arraySize, int maxElement)
	{
		int[] array = new int[arraySize];
		Random r = new Random();
		for (int i = 0; i < arraySize; i++)
			array[i] = r.nextInt(maxElement);
		return array;
	}

	// print elements of array on screen
	public static void printArray(int[] array, int size)
	{
		for (int i = 0; i < size; i++)
		{
			System.out.print(array[i] + " ");
		}
		System.out.println();
	}

	// finding second smallest element in unsorted array using method of tournaments 
	public static int findSecondSmallest(int[] array)
	{
		boolean isLengthOdd = array.length % 2 == 1;
		int[] tournament = isLengthOdd ? new int[array.length]
				: new int[array.length - 1]; // create array containing tournament bracket for finding the smallest element

		// elements 0 and 1, 2and 3,  and so on of the original are compared pairwise
		// smaller of the two is assigned as a leaf to the tournament tree
		for (int i = 0; i < array.length - 1; i += 2)
		{
			tournament[(tournament.length + i) / 2] = array[i] > array[i + 1]
					? array[i + 1] : array[i];
		}

		// if the total number of elements is odd the last element does not have a pair and is
		// assigned as a winner to the last leaf of the tournament
		if (isLengthOdd)
		{
			tournament[tournament.length - 1] = array[array.length - 1];
		}
		System.out.println(tournament.length);
		
		printArray(tournament, tournament.length);
		// run tournament: for each inner node the winner is the smaller of two children
		// runs recursively until the root contains overall smallest element 
		runTourney(tournament);
		System.out.println("Minumum value is: " + tournament[0]);
		// second smallest element is one of the loosers to the smallest element and should be on the side of the winning path
		
		int position = 0;
		int min = tournament[0];
		int secondMin = Integer.MAX_VALUE;
		int left = left(position);
		int right = right(position);
		while (left < tournament.length)
		{
			
			// if smallest element is to the left seek second smallest on the right
			if (min == tournament[left])
			{
				if (right < tournament.length)
				{
					if (tournament[right] == min) // if min elements are on both sides of the node than two smallest elements have the same value
						return min;
					else if (tournament[right] < secondMin)
						secondMin = tournament[right];
				}
				position = left; // follow the smallest element path

			}
			// the winner path is to the right, check left for second smallest
			else
			{
				if (tournament[left] < secondMin)
					secondMin = tournament[left];
				position = right;
			}
			
			System.out.println("Second min at position " + secondMin + " " + position);
			left = left(position);
			right = right(position);

		}

		// after the tournament tree is processed do last check in the original array
		left = left(position, tournament.length);
		if (array[left] == min)
			return (left + 1 >= array.length ? secondMin
					: secondMin < array[left + 1] ? secondMin
							: array[left + 1]);
		else
			return secondMin < array[left] ? secondMin : array[left];

	}

	public static int parent(int child)
	{
		return child/2 - (child +1)%2;
	}

	public static int left(int parent)
	{
		return parent * 2 + 1;

	}

	public static int right(int parent)
	{
		return parent * 2 + 2;
	}

	public static int left(int parent, int tourneyLength)
	{
		return (parent - tourneyLength / 2) * 2;
	}

	public static void runTourney(int[] tournament)
	{
		int position = tournament.length / 2 - 1;
		int winner;
		while (position >= 0)
		{
			winner = right(position) < tournament.length
					? tournament[right(position)] : Integer.MAX_VALUE;
			winner = winner < tournament[left(position)] ? winner
					: tournament[left(position)];
			tournament[position] = winner;
			position--;
		}
	}
}
