package test_create_fill_copy_arrays;

import java.util.Arrays;

public class Create_fill_copy_arrays {

	public static void main(String[] args) {
		
		
		
		int [] array = new int[10000000];
		Integer [] arrayInt = new Integer[10000000];
		long start = System.nanoTime();
		//long end = System.nanoTime();
		//Arrays.fill(array, 1);
		
		long end = System.nanoTime();
		System.out.println(arrayInt[0]);
		System.out.println((end - start)/1000);

	}

}
