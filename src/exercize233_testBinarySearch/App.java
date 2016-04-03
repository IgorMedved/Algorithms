package exercize233_testBinarySearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import utility.Utility;
import utility.search.Search;
import utility.sort.Sort;

public class App
{

	public static void main(String[] args)
	{
		List<Integer> sortedArray = new ArrayList<>();
		Utility.populateArray(sortedArray, 5);
		Sort.mergeSort(sortedArray, 0, sortedArray.size() - 1);
		Utility.printArray(sortedArray);
		Scanner sc = new Scanner(System.in);
		String exitString = " ";
		int value = Integer.MIN_VALUE;
		System.out
		.println("Enter number to find in a list or exit to exit ");
		do
		{
			
			if (sc.hasNextInt())
			{
				value = sc.nextInt();
				int index = Search.binaryInsertionSearch(sortedArray, value, 0, sortedArray.size()-1);
				if (index != Search.NOT_FOUND)
				{
					System.out.println("The integer " + value + " is located at "
							+ index + " position in the list");
				} 
				else
					System.out.println("The integer " + value
							+ " is not found in the list");
				
				System.out
				.println("Enter number to find in a list or exit to exit ");
			} 
			else
			{
				exitString = sc.nextLine();
			}
			

		} while (!exitString.equalsIgnoreCase("exit"));
		
		sc.close();
	}

}
