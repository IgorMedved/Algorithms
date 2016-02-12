package utility;

import java.util.List;

public class Search
{
	public static final int NOT_FOUND = -1; //  
	
	public static int linearSearch (List<Integer> searchArray, int value)
     {
         
         
         
         for (int i = 0; i < searchArray.size(); i++)
         {
             if (value == searchArray.get(i))
                return i;
         }
         
         return NOT_FOUND;
     }
	 
	 // ascending list
	 public static int binarySearch (List<Integer> searchArray, int value)
	 {
		 int lower = 0;
		 int upper = searchArray.size()-1;
		 
		 while(lower <= upper)
		 {
			 int middle = (lower+upper)/2;
			 
			 if (value == searchArray.get(middle))
			 {
				 return middle;
			 }
			 else if (value > searchArray.get(middle))
			 {
				 lower = middle+1;
			 }
			 else
			 {
				 upper = middle-1;
			 }
		 }
		 
		 return NOT_FOUND;
	 }
	 
	 // recursive search returns insertion position of for @value into sorted subArray @searchArray (limited by lower and upper bounds) 
	 // if two values are the same the insertion position is to the right of the value found
	 
	 public static int binaryInsertionSearch (List<Integer> searchArray, int value, int lower, int upper)
	 {
		 if (lower>upper)
		 {
			 if (upper >= 0)
				 return lower;
			 else
				 return 0;
		 }
		 
		 int middle = (lower+upper)/2;
		 System.out.println("Lower " + lower +" upper "+ upper + " middle "+middle);
		 if(searchArray.get(middle) == value)
			 return middle+1;
		 else if (value < searchArray.get(middle))
			 return binaryInsertionSearch(searchArray, value, lower, middle-1);
		 else
			 return binaryInsertionSearch(searchArray, value, middle+1, upper);
		 
	 }
	 
	 
	 // find index of the smallest element in unsorted
	 // @searchArray to the right of @currentPosition
	 public static int findSmallest (List<Integer> searchArray, int currentPosition)
	 {
	        int index = currentPosition;
	        int minValue = searchArray.get(index);
	        for (int i = index+1; i < searchArray.size(); i++)
	        {
	            if (minValue> searchArray.get(i))
	            {
	                minValue = searchArray.get(i);
	                index = i;
	            }
	        }
	        return index;
	  }
		 
		 
	
}
