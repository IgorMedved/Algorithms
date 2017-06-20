package exercize0411_max_subarray;

import java.util.ArrayList;
import java.util.List;

import utility.Utility;

public class MaxSubarrayApp
{
	public static void main(String[] args)
	{
		List<Integer> testList = new ArrayList<>();
		Utility.populateArray(testList, 20);
		List<Integer> preparedList = MaximumSubarray.prepareArray(testList);
		// Sort.mergeSort(testList, 0, 19);
		Utility.printArray(testList);
		Utility.printArray(preparedList);

		MaximumSubarrayBean myBean = MaximumSubarray.produceMaximumSubarray(
				preparedList, 0, 18);
		MaximumSubarrayBean n2 = MaximumSubarray
				.maximumSubarrayN2(preparedList);
		System.out.print("The best subarray is ");
		Utility.printArray(preparedList, myBean.getLow(), myBean.getHigh());

		System.out.print("The best subarrayN2 is ");
		Utility.printArray(preparedList, n2.getLow(), n2.getHigh());

	}
}
