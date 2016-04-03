package utility.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CountingSort {
	
	public static List<Integer> countSort (List<Integer> array, int order, int sweep)
	{
		int module = pow(10, sweep);
		int dividerPower = (order-1)* sweep ;
		//System.out.println("module " + module+ " divider power " + dividerPower);
		if (dividerPower <10)
		{
			int divider = pow (10 , dividerPower);
			
			int cSize = dividerPower + sweep >9? pow(10, 10-dividerPower): module;
			//int reductor = reductor(order, sweep);
			
		
			// array c initialized to 0;
			List<Integer> c = new ArrayList<Integer>(Collections.nCopies(cSize, 0));
		
			// c stores how many values of size @value does original array hold
			for (int i = 0; i < array.size(); i++)
			{
				int index = (array.get(i)/divider)%module;
				int value = c.get(index);
				c.set(index, value+1);
			}
			
			// c stores how many values are smaller or equal to value orrigina array hold
			for (int i = 1; i < cSize; i++)
			{
				c.set(i, c.get(i-1) +  c.get(i));
			}
			
			
			List<Integer> b = new ArrayList<Integer>(Collections.nCopies(array.size(), 0));
			for (int i = array.size()-1; i >=0; i--)
			{
				int index = (array.get(i)/divider)%module;
				int value = c.get(index);
				b.set(value -1 , array.get(i));
				c.set(index, value-1);
			}
			
			return b;
			
		}
		return array;
			
	}
	
	public static List<Integer> countSort (List<Integer> array, int  order)
	{
		return countSort (array, order, 3);
	}
	
	private static int module (int order, int sweep)
	{
		return pow(10, order*sweep);
	}
	
	private static int pow (int base, int exp)
	{
		if (exp == 0)
			return 1;
		else if (exp%2 == 0)
			return exp ==2? base* base:pow (pow (base, exp/2),2);
		
		else
			return base*pow(base,exp-1);
	}
	
	public static List<Integer> radixSort (List<Integer> array, int radix)
	{
		for (int i = 1; i <= 10/radix +1; i++)
			array = countSort (array, i, radix);
		
		return array;
	}

}
