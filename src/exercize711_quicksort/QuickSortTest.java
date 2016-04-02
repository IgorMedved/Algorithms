package exercize711_quicksort;

import java.util.ArrayList;
import java.util.List;

import utility.QuickSort;
import utility.Utility;
import utility.StatisticsUtility;

public class QuickSortTest
{
  public static void main (String[] args)
  {
    System.out.println("It's good to be back to work again!");
    
    List <Integer> testArray = new ArrayList<>(); // testing standard Quick sort
    Utility.populateArray(testArray, 8);
    List <Integer> testHoare = Utility.copyArray(testArray); // testing Hoare variation
    List <Integer> testEqElem = Utility.copyArray(testArray); // testing modification that deals better with arrays where elements can be same size
    Utility.printArray(testArray);
    Utility.printArray(testHoare);
    Utility.printArray(testEqElem);
    
    QuickSort.quickSort(testArray, 0, testArray.size()-1);
    QuickSort.quickSortHoare(testHoare, 0, testArray.size()-1);
    QuickSort.quickSortEq(testEqElem, 0, testArray.size()-1);
    
    
    Utility.printArray(testArray);
    Utility.printArray(testHoare);
    Utility.printArray(testEqElem);
    
    
    
    
    
    
    
    //printStats(testArray);
    //printRandomMeans(testArray);
  }
  
  
  public static void printStats(List<Integer> testArray)
  {
    double mean = StatisticsUtility.mean (testArray);
    double sDev = StatisticsUtility.standardDev(testArray);
    
    System.out.println("The set mean is: " + mean + " the standard deviation " +  sDev);
  }
  
  public static void printRandomMeans (List<Integer> testArray)
  {
    // print 20 means of sampleSize 3 - 10
    
    for (int i = 1; i <=10; i++)
    {
      
      
      System.out.println ("***************** means of sample size " + i + " *********************");
      for (int j = 0; j < 20; j++)
      {
         System.out.println (StatisticsUtility.getSampleMean(testArray, i) + " ");
      }
      System.out.println ("***************** end of sample size " + i + " ***********************");
                             
    }
         
    
  }
  
  
  
  
}

