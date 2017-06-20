package exercize0141_add_binary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utility.Utility;

/*
*
*
* 2.1-4
* Consider the problem of adding two n-bit binary integers, stored in two n-element
* arrays A and B. The sum of the two integers should be stored in binary form in
* an (n + 1)-element array C. State the problem formally and write pseudocode for
* adding the two integers
*
*
*
*

*/

public class App
{

	
	

	     public static void main(String []args){
	        List<Short> a = new ArrayList<>();
	        a = Arrays.asList(new Short[]{1,1,1,1,1});
	        List<Short> b = new ArrayList<>();
	        b = Arrays.asList(new Short[]{1,0,1,1,0});
	        List<Short> c = addBinary(a,b); 
	        Utility.printArray(c);
	     }
	     
	     
	     // assumes a and b are proper binary integers no leading 0s
	     public static ArrayList<Short> addBinary(List<Short> a , List<Short> b)
	     {
	         int lengthC = Math.max(a.size(), b.size());
	         
	         ArrayList<Short> c = new ArrayList<>();
	         short carryOver = 0;
	         
	         // obtain c written in reverse order
	         for (int i = 0; i < lengthC; i ++)
	         {
	            short valueA = 0; 
	            if (a.size() - i -1 >=0)
	                valueA = a.get(a.size() - i -1);
	             
	            short valueB = 0;
	            if (b.size() - i -1 >=0)
	                valueB = b.get(b.size() - i -1);
	                
	            short valueC = (short)(valueA + valueB + carryOver); 
	            
	            // valueC can be 0,1,2,3
	            /* 0 means next number is 0
	            *  1 means next number is 1
	            *  2 means number is 0, but carryOver is 1
	            */
	            if (valueC == 0)
	            {
	                c.add((short)0);
	                carryOver = 0;
	            }
	            else if (valueC==1)
	            {
	                c.add((short)1);
	                carryOver = 0;
	            }
	            else if (valueC ==2)
	            {
	                c.add((short)0);
	                carryOver = 1;
	            }
	            
	            else if (valueC == 3)
	            {
	                c.add((short)1);
	                carryOver = 1;
	            }
	         }
	         // if there was a carryOver we should add it to c
	         if (carryOver == 1)
	         {
	             c.add((short)1);
	         }
	         
	         // the value obtained by above algorithm obtains c in reverse order, here we reverse it to correct order
	         Utility.reverse (c);
	         
	         return c;
	     }

}
