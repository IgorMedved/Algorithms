package utility.sort;

import java.util.List;

public class SortHelper {
	
	    // @lower - lower index of the range over which rotation is done
		// @upper - upper index of the range over which rotation is done
		// @shift - amount by which elements should be shifted
		// array rotation is best illustrated by example: if this is original order of elements 1,2,3,5,10,3,4 and this is
		// new order after rotating by 2 positions 3,5,10,3,4,1,2. Elements go to the left by a specified number of positions, and
	// elements at the front go at the back. If the shift is negative the elements go to the right instead and from the back to beginning
		public static void  rotate (List<Integer> array, int lower, int shift, int upper) { 
			// check that rotation range is valid
			if (upper <= lower)
				return;
			// rotating an array of 7 elements by 10 positions is same as rotating array of 7 elements by 3 positions
			while (shift > upper - lower)
			{
				shift -= (upper-lower+1);
			}
			
			// rotating an array of 6 elements by -7 positions is same as rotating by -1 (or 5)
			while (shift < lower-upper)
				shift +=(upper-lower +1);
			
			if (shift == 0)
				return;
			
			// if number of elements in the rotating range has a common denominator with number of positions by which the elements are shifted
			// then closed loops are formed e.g 
			// (for array of 6 elements that is shifted by 2 spots elements at positions 3 go to position 1, elements at position 1 go to position 5 and elements from position 5 go to position 3)
			// (on the other hand elements from position 2 go to position 0, elements from 0 go to 4 and from 4 to 2)
			// two independent loops are formed
			// calculated number of independent loops through the array
			int numSteps = gcd(upper-lower+1, shift);
			//System.out.println(numSteps);
			
			// rotate elements for each loop
			for (int i = 0; i < numSteps; i++)
			{
				int writeIndex = lower + i;
				// read index leads write index by shift value
				int readIndex = writeIndex+shift;
				
				// adjust read index when reaching array edge
				if (readIndex > upper)
					readIndex = readIndex - upper + lower-1;
				else if (readIndex < lower)
					readIndex = readIndex -lower + upper+1;
				
				// before going in circle over the array and overwriting values save first value 
				int savedValue = array.get(writeIndex);
				// stop after going full circle through the array
				while (readIndex != lower+i)
				{
					//System.out.println("Read index " + readIndex + " write index " + writeIndex);
					array.set(writeIndex, array.get(readIndex));
					writeIndex = readIndex;
					readIndex = readIndex+ shift;
					if (readIndex > upper)
						readIndex = readIndex - upper + lower-1;
					else if (readIndex < lower)
						readIndex = readIndex -lower + upper+1;
						
				}
				// write saved value into position that is -shift away from the first overwritten spot
				array.set(writeIndex, savedValue);
			}
		}
		
		// finding greatest common denominator 
		private static int gcd (int m, int n)
		{
			m = Math.abs(m);
			n = Math.abs(n);
			while (n!=0)
			{
				int t= m%n; m=n; n = t;
			}
			return m;
		}
		
		// if value in array between lower and upper return leftmost element index
		// if value not in array return index of the smallest element for which value < array.get(index)
		// if value > array.get(anyIndex) return upper+1
		public static int firstBinary(List<Integer> array, int lower, int upper, int value)
		{
			if (lower > upper)
				return -1;
			int mid;
			while (lower < upper)
			{
				mid = (lower + upper)/2;
				if (value <= array.get(mid))
					upper = mid;
				else
					lower = mid+1;
			}
			
			
			if (value <= array.get(lower))
				return lower;
			else
				return lower+1;
		}
		// if value is present in array return rightmost element index
		// if value is not present in array, but would be between two elements return largest element index 
		// for which value > array.get(index)
		// if value < array.get(anyindex) return lower-1
		public static int lastBinary(List<Integer>array, int lower, int upper, int value)
		{
			int mid;
			while (lower < upper)
			{
				mid = (lower + upper+1)/2;
				if (value >= array.get(mid))
					lower = mid;
				else
					upper = mid-1;
			}
			
			
			if (value >= array.get(lower))
				return lower;
			else
				return lower-1;
		}
		
		
		public static void  swap (List <Integer> array, int range1start, int range2start, int length)
		{
			for (int i = 0; i < length; i++)
			{
				int temp = array.get(range1start+i);
				array.set(range1start+i, array.get(range2start+ i));
				array.set(range2start +i, temp);
			}
		}
		
		public static int floorPow2(int number)
		{
			int floor2 = 1;
			number/=2;
			while (number > 0)
			{
				number/=2;
				floor2*=2;
			}
			return floor2;
		}

}
