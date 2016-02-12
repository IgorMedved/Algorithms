package exercize237;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import utility.Sort;
import utility.Utility;

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
		Utility.populateArray(myList, 20);
		Sort.mergeSort(myList, 0, 19);
		
		do
		{
			System.out.println("Press any key to continue to generate new value, type exit to exit ");
			int value = random.nextInt(80);
			
			Utility.printArray(myList);
			
			System.out.print("The value " + value + " is " );
			if (!sumInList (myList, value, 0, myList.size()-1))
			{
				System.out.print("not ");
			}
			System.out.println("in the list");
		}
		while (!sc.nextLine().equalsIgnoreCase("exit"));
	}
	
	public static boolean sumInList (List<Integer> myList, int value, int lower, int upper)
	{
		int middle = (lower+upper)/2;
		
		while(lower < upper )
		{
			int sum = myList.get(middle) + myList.get(middle+1);
			System.out.println(sum);
			
			if (value == sum )
				return true;
			else if (value > sum)
				lower = middle +1;
			else
				upper = middle;
			
			middle = (lower + upper)/2;
			
		
		}
		
		
		return false;
				
	}

}
