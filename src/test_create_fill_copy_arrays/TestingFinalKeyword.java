package test_create_fill_copy_arrays;

import java.util.ArrayList;
import java.util.List;

import utility.Utility;

public class TestingFinalKeyword {

	public static void main(String[] args)
	{
		TestingFinalKeyword test = new TestingFinalKeyword();
		List<Integer> array = new ArrayList<>();
		Utility.populateArray(array, 10);
		Utility.printArray(array);
		test.testFinal(array,10);
		Utility.printArray(array);
		
	}
	
	public void testFinal (final List<Integer> array, final Integer testInt)
	{
		array.set(0, 0);
	}
}
