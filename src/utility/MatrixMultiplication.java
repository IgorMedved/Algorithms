package utility;

import java.util.ArrayList;
import java.util.List;

public class MatrixMultiplication
{
	public static List<List<Integer>> matrixMultiplication(List<List<Integer>> a, List<List<Integer>> b, List<Long> executionTimes)
    {
      List<List<Integer>> result = new ArrayList<>(a.size());
      List<Integer> tempRow;
      int sum;
      
      
      for (int i = 0; i < a.size(); i++)
      {
        executionTimes.add(System.nanoTime());
        tempRow = new ArrayList<>(a.size());
        for (int j = 0; j < a.size(); j++)
        {
              sum = 0;
            for (int k = 0; k < a.size(); k++)
              sum += a.get(i).get(k) * b.get(k).get(j);
              tempRow.add(sum);
        }
        result.add(tempRow);
        executionTimes.add(System.nanoTime());
                           
      }
      return result;
    }
	
	public static List<List<Integer>> multiply (List<List<Integer>> a, List<List<Integer>> b, int size)
	{
		List<List<Integer>> result = new ArrayList<>(a.size());
        List<Integer> tempRow;
	    int sum;
	    
	    int maxK = b.size();
	      
	      for (int i = 0; i < size; i++)
	      {
	        tempRow = new ArrayList<>(a.size());
	        for (int j = 0; j < size; j++)
	        {
	              sum = 0;
	            if (i < a.size())
	            {
	            	maxK = maxK < a.get(i).size()? maxK:a.get(i).size();
	            	for (int k = 0; k < maxK; k++)
		            {
		            	if ( j < b.get(k).size())
		            	sum += a.get(i).get(k) * b.get(k).get(j);
		            }
	            }
	            	
	            
	              
	              tempRow.add(sum);
	        }
	        result.add(tempRow);
	                           
	      }
	      return result;
	}
	
	
}
