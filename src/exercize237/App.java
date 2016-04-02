package exercize237;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import utility.Utility;
import utility.extra.Extra;
import utility.sort.Sort;

public class App
{
	/*2.3-7
	*		Describe a O (n lg n)-time algorithm that, given a set S of n integers and another
	*		integer x, determines whether or not there exist two elements in S whose sum is
	*		exactly x.
	*
	*/
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		Random random = new Random();
		
		List <Integer> myList = new ArrayList<>();
		//System.out.println(Integer.MAX_VALUE);
		Utility.populateArray(myList, 1000000);
		Sort.mergeSort(myList, 0, 999999);
		
		do
		{
			System.out.println("Press any key to continue to generate new value, type exit to exit ");
			int value = random.nextInt(2000000000);
			
			//Utility.printArray(myList);
			
			System.out.print("The value " + value + " is " );
			Long startTime = System.currentTimeMillis();
			if (!Extra.sumInList (myList, value, 0, myList.size()-1))
			{
				System.out.print("not ");
			}
			System.out.println("in the list");
			
			
			
			Long endTime = System.currentTimeMillis();
			System.out.println("The time it took is " + (endTime-startTime));
		}
		while (!sc.nextLine().equalsIgnoreCase("exit"));
	}
	
	
}
