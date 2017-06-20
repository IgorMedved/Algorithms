package Tournament;
import java.util.ArrayList;
import java.util.List;

import utility.Utility;
import utility.sort.Sort;

public class App
{

	public static void main(String[] args)
	{
		List<Integer> test = new ArrayList<>();
		
		Utility.populateArray(test, 5000000);
		Tournament tournament = new Tournament (test);
		long start = System.currentTimeMillis();
		//Utility.printArray(test);
		//System.out.println("The minimum value is " + tournament.runTournament());
		for (int i = 0; i<test.size(); i++)
		{
			int root = tournament.getRoot();
			//System.out.print(root + " ");
			int leaf = tournament.getMinLeafPosition();
			tournament.insertNew(leaf, Integer.MAX_VALUE);
		}
		long endTournament = System.currentTimeMillis();;
		//System.out.println();
		Sort.mergeSortOptimized(test, 0, test.size()-1, 25);
		long endMergeSort = System.currentTimeMillis();
		//Utility.printArray(test);
		System.out.println("Tournament sort takes "+ (endTournament-start) + " merge sort " + (endMergeSort-endTournament));

	}

}
