package exercize0421a_strassen_matrix_multiplication;

import java.util.ArrayList;
import java.util.List;

import utility.Utility;
import utility.extra.MatrixMultiplication;
import utility.extra.MatrixMultiplicationStrassen;

/* Strassen algorithm for multiplying matrices in time O (n^ln7) = O (n^2.81)
 * Divide matrices A into a11 a12
 * 						  a21 a22
 * 				   B into b11 b12
 * 						  b21 b22	
 * 
 * C = A*B
 */

public class App
{
	public static void main(String[] args)
	{

		System.out.println("test");
        List<List<Integer>> a = new ArrayList<>();
        List<List<Integer>> b = new ArrayList<>();
        List<List<Integer>> c;// = new ArrayList<>();
        List<List<Integer>> d;// = new ArrayList<>();
        int size = 12;
        List<Long> executionTimes = new ArrayList<>(2*size);

        Utility.populateSqrMatrix(a, size);
        Utility.populateSqrMatrix(b, size);
        //Utility.printMatrix(a);
        //Utility.printMatrix(b);
        
        long startStrassen = System.currentTimeMillis();
        
        c = MatrixMultiplicationStrassen.strassenMatrix(a, b, 16 );
        long endStrassen = System.currentTimeMillis();
        
        long startRegular = System.currentTimeMillis();
        d = MatrixMultiplication.matrixMultiplication (a, b, executionTimes);
        long endRegular =System.currentTimeMillis();

        System.out.println ("The regular matrix multiplication took: " + (endRegular - startRegular));
        System.out.println ("The strassen matrix multiplication took: " + (endStrassen - startStrassen));
        Utility.printMatrix(c);
        Utility.printMatrix(d);
        long largest = 0;
        long sum = 0;
        long smallest = Long.MAX_VALUE;
        long value;
        int smallestIndex = 0;
        int largestIndex = 0;
        List<Long> executionValues = new ArrayList<>();
        
        for (int i = 0; i < 2*size; i+=2)
        {
          value = executionTimes.get(i+1) - executionTimes.get(i);
          if (largest < value && i != 0)
          {
            largest = value;
            largestIndex = i/2;
          }
          
          if (smallest > value)
          {
            smallest = value;
            smallestIndex = i/2;
          }
          sum+= value;
          executionValues.add(value);
        }
        System.out.println("The largest loop execution time is " + largest + " The smallest loop execution time is " + smallest  + " the average time per loop iteration is " + sum/size);
        System.out.println("Smallest index " + smallestIndex + " largest index "+ largestIndex);
	}

}