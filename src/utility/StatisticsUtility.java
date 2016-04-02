package utility;

import java.util.List;
import java.util.Random;

public class StatisticsUtility {
	public static double mean (List<Integer> set)
	  {
	    long sum = 0;
	    
	    for (int i = 0; i < set.size(); i ++)
	    {
	      sum += (long)set.get(i);
	    }
	    
	    return (double)sum/set.size();
	      
	  }
	  
	  public static double standardDev (List<Integer> set)
	  {
	    double mean = mean (set);
	    double variance = 0;
	    double square;
	    for (int i = 0; i < set.size(); i++)
	    {
	      square = (mean-set.get(i)); 
	      variance += square*square;
		}
	    
	    return Math.sqrt(variance/set.size());
	                
	  }
	  
	  public static double getSampleMean( List<Integer> set, int nSamples)
	  {
	    Random random = new Random();
	    
	    long sum = 0;
	    for (int i = 0; i < nSamples; i++)
	    {
	      sum += (long)set.get(random.nextInt(set.size()));
	    }
	    
	    return (double)sum/nSamples;
	  }

}
