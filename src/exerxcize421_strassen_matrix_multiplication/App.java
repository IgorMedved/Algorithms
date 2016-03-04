package exerxcize421_strassen_matrix_multiplication;

import java.util.ArrayList;
import java.util.List;

import utility.Utility;

/* Strassen algorithm for multiplying matrices in time O (n^ln7) = O (n^2.81)
 * Divide matrices A into a11 a12
 * 						  a21 a22
 * 				   B into b11 b12
 * 						  b21 b22	
 * 
 * C = A*B
 */
// compute s matrices
//S1 = B12 - B22 ;
//S2 = A11 + A12 ;
//S3 = A21 + A22 ;
//S4 = B21 - B11 ;
//S5 = A11 + A22 ;
//S6 = B11 + B22 ;
//S7 = A12 - A22 ;
//S8 = B21 + B22 ;
//S9 = A11 - A21 ;
//S10 = B11 + B12 :

// compute 7 matrix multiplications of size n/2 recursively
//P1 = A11 * S1 = A11 * B12 - A11 * B22 ;
//P2 = S2 * B22 = A11 * B22 + A12 * B22 ;
//P3 = S3 * B11 = A21 * B11 + A22 * B11 ;
//P4 = A22 * S4 = A22 * B21 - A22 * B11 ;
//P5 = S5 * S6 = A11 * B11 + A11 * B22 + A22 * B11 + A22 * B22 ;
//P6 = S7 * S8 = A12 * B21 + A12 * B22 - A22 * B21 - A22 * B22 ;
//P7 = S9 * S10 = A11 * B11 + A11 *  B12 - A21 * B11 - A21 * B12 :

/* get results
 * C11 = P5 + P4 - P2 + P6 
 * C12 = P1 + P2
 * C21 = P3 + P4
 * C22 = P5 + P1 - P3 + P7
 */



public class App
{
	public static void main(String[] args)
	{

		System.out.print("test");
		List<List<Integer>> a = new ArrayList<>();
		List<List<Integer>> b = new ArrayList<>();
		List<List<Integer>> c = new ArrayList<>();

		Utility.populateSqrMatrix(a, 3);
		Utility.populateSqrMatrix(b, 3);
		Utility.printArrayOfArrays(a);
		Utility.printArrayOfArrays(b);

		c = Utility.substractMatrices(a, b);

		Utility.printArrayOfArrays(c);
	}

	public static List<List<Integer>> strassenMatrix(List<List<Integer>> a, List<List<Integer>> b,  int size)
	{
		List<List<Integer>> result = new ArrayList<>();

		// base case
		if (size == 2)
		{
		} else
		{
			List<List<List<Integer>>> s = new ArrayList<>();
			initializeFromB(b, s, size);
			initializeFromA(a, s, size);
			
			List<List<Integer>> a11 = getA11(a, size);
			List<List<Integer>> a22 = getA22(a, size);
			List<List<Integer>> b11 = getB11(b, size);
			List<List<Integer>> b22 = getB22(b, size);
			
			List<List<Integer>> p1 = strassenMatrix(a11, s.get(getIndexS(1)), size/2);
			List<List<Integer>> p2 = strassenMatrix(s.get(getIndexS(2)), b22, size/2);
			List<List<Integer>> p3 = strassenMatrix(s.get(getIndexS(3)), b11, size/2);
			List<List<Integer>> p4 = strassenMatrix(a22, s.get(getIndexS(4)), size/2);
			List<List<Integer>> p5 = strassenMatrix(s.get(getIndexS(5)), s.get(getIndexS(6)),size/2);		
			List<List<Integer>> p6 = strassenMatrix(s.get(getIndexS(7)), s.get(getIndexS(8)),size/2);
			List<List<Integer>> p7 = strassenMatrix(s.get(getIndexS(9)), s.get(getIndexS(10)),size/2);

		}
		
		return result;
	}
	
	public static List<List<Integer>> getA11 (List<List<Integer>> a, int size)
	{
		return getMatrix (a, 0, size/2, 0, size/2, size);
	}
	
	public static List<List<Integer>> getMatrix(List<List<Integer>> matrix, int topRow, int bottomRow, int leftCol, int RightCol, int size)
	{
		List<List<Integer>> subMatrix = new ArrayList<>();
		 
		List<Integer> tempRow;
		
		for (int i = topRow; )
	}

	//S1 = B12 - B22 ;
	//S2 = A11 + A12 ;
	//S3 = A21 + A22 ;
	//S4 = B21 - B11 ;
	//S5 = A11 + A22 ;
	//S6 = B11 + B22 ;
	//S7 = A12 - A22 ;
	//S8 = B21 + B22 ;
	//S9 = A11 - A21 ;
	//S10 = B11 + B12 :
	public static int getIndexS (int matrixNumber)
	{
		switch (matrixNumber)
		{
		case 1:
			return 0;
		case 2:
			return 5;
		case 3:
			return 6;
		default:
			return matrixNumber/2 + (matrixNumber%2)*5;
			
		}
	}


	public static void initializeFromA(List<List<Integer>> a, List<List<List<Integer>>> s, int size)
	{
		int lastNon0RowA =  a.size()-1;
		int lastNon0ColA = a.size()== 0? -1: a.get(0).size()-1; 
		if ((lastNon0RowA >= 0 && lastNon0ColA >= 0)) // matrix a not all 0
		{
			
			
			List<List<Integer>> tempRowsArray = new ArrayList<>();
			List<Integer> row;

			
			boolean hasFewRows = lastNon0RowA < size / 2 - 1;
			boolean hasFewCols = lastNon0ColA < size / 2 - 1;

			// increment i to a smaller of size/2 and lastNon0RowA, increment j
			// to a smaller of size/2 and lastNon0ColA
			for (int i = 0; i < (hasFewRows ? lastNon0RowA : size / 2); i++)
			{
				for (int k = 0; k < 5; k++)
				{
					row = new ArrayList<>();
					tempRowsArray.add(row);
				}
				for (int j = 0; j < (hasFewCols ? lastNon0ColA : size / 2); j++)
				{

					if (i + size / 2 <= lastNon0RowA
							&& j + size / 2 <= lastNon0ColA) // intialize
																// everything
					{

						tempRowsArray.get(0).add(a.get(i).get(j) + a.get(i).get(j+size/2)); //S2 = A11 + A12 ;
						tempRowsArray.get(1).add(a.get(i+size/2).get(j)+ a.get(i+size/2).get(j+size/2));//S3 = A21 + A22 ;
						tempRowsArray.get(2).add(a.get(i).get(j) + a.get(i+size/2).get(j+size/2));//S5 = A11 + A22 ;
						tempRowsArray.get(3).add(a.get(i).get(j+size/2)- a.get(i+size/2).get(j+size/2)); //	S7 = A12 - A22 ;
						tempRowsArray.get(4).add(a.get(i).get(j)-a.get(i+size/2).get(j)); //	S9 = A11 - A21 ;
						

					} 
					else if (i + size / 2 > lastNon0RowA
							&& j + size / 2 > lastNon0ColA) // initialize only a11
					{
						tempRowsArray.get(0).add(a.get(i).get(j)); //S2 = A11 + A12 ;
						tempRowsArray.get(2).add(a.get(i).get(j));//S5 = A11 + A22 ;
						tempRowsArray.get(4).add(a.get(i).get(j)); //	S9 = A11 - A21 ;
					} 
					else if (j + size / 2 > lastNon0ColA) // initialize a11 && a21
					{
						tempRowsArray.get(0).add(a.get(i).get(j)); //S2 = A11 + A12 ;
						tempRowsArray.get(1).add(a.get(i+size/2).get(j));//S3 = A21 + A22 ;
						tempRowsArray.get(2).add(a.get(i).get(j));//S5 = A11 + A22 ;
						tempRowsArray.get(4).add(a.get(i).get(j)-a.get(i+size/2).get(j)); //	S9 = A11 - A21 ;

					}
					else if (i + size / 2 > lastNon0RowA) // initialize a11 && a12
					{
						tempRowsArray.get(0).add(a.get(i).get(j) + a.get(i).get(j+size/2)); //S2 = A11 + A12 ;
						tempRowsArray.get(2).add(a.get(i).get(j));//S5 = A11 + A22 ;
						tempRowsArray.get(3).add(a.get(i).get(j+size/2)); //	S7 = A12 - A22 ;
						tempRowsArray.get(4).add(a.get(i).get(j)); //	S9 = A11 - A21 ;
					}

				}
				s.add(tempRowsArray);
			}

		}

	}
	
	public static void initializeFromB (List<List<Integer>> b, List<List<List<Integer>>> s, int size)
	{
		int lastNon0RowB = b.size()== 0? -1: b.size();
		int lastNon0ColB = b.size()== 0? -1: b.get(0).size()==0? -1: b.get(0).size();
		
		if ((lastNon0RowB >= 0 && lastNon0ColB >= 0)) // matrix a not all 0
		{
			List<List<Integer>> tempRowsArray = new ArrayList<>();
			List<Integer> row;

			
			boolean hasFewRows = lastNon0RowB < size / 2 - 1;
			boolean hasFewCols = lastNon0ColB < size / 2 - 1;

			// increment i to a smaller of size/2 and lastNon0RowA, increment j
			// to a smaller of size/2 and lastNon0ColA
			for (int i = 0; i < (hasFewRows ? lastNon0RowB : size / 2); i++)
			{
				for (int k = 0; k < 5; k++)
				{
					row = new ArrayList<>();
					tempRowsArray.add(row);
				}
				for (int j = 0; j < (hasFewCols ? lastNon0ColB : size / 2); j++)
				{

					if (i + size / 2 <= lastNon0RowB
							&& j + size / 2 <= lastNon0ColB) // intialize
																// everything
					{

						tempRowsArray.get(0).add(b.get(i).get(j+size/2) - b.get(i+size/2).get(j+size/2));//S1 = B12 - B22 ;
						tempRowsArray.get(1).add(b.get(i+size/2).get(j) -  b.get(i).get(j));//S4 = B21 - B11 ;
						tempRowsArray.get(2).add(b.get(i).get(j) + b.get(i+size/2).get(j+size/2));//S6 = B11 + B22 ;
						tempRowsArray.get(3).add(b.get(i+size/2).get(j) + b.get(i+size/2).get(j+size/2)); //S8 = B21 + B22 ;
						tempRowsArray.get(4).add(b.get(i).get(j) + b.get(i).get(j+size/2)); //S10 = B11 + B12 :
						

					} 
					else if (i + size / 2 > lastNon0RowB
							&& j + size / 2 > lastNon0ColB) // initialize only b11
					{
						tempRowsArray.get(1).add(- b.get(i).get(j));//S4 = B21 - B11 ;
						tempRowsArray.get(2).add(b.get(i).get(j));//S6 = B11 + B22 ;
						tempRowsArray.get(4).add(b.get(i).get(j)); //S10 = B11 + B12 :
					} 
					else if (j + size / 2 > lastNon0ColB) // initialize b11 && b21
					{
						tempRowsArray.get(1).add(b.get(i+size/2).get(j) -  b.get(i).get(j));//S4 = B21 - B11 ;
						tempRowsArray.get(2).add(b.get(i).get(j));//S6 = B11 + B22 ;
						tempRowsArray.get(3).add(b.get(i+size/2).get(j)); //S8 = B21 + B22 ;
						tempRowsArray.get(4).add(b.get(i).get(j)); //S10 = B11 + B12 :

					}
					else if (i + size / 2 > lastNon0RowB) // initialize b11 && b12
					{
						tempRowsArray.get(0).add(b.get(i).get(j+size/2));//S1 = B12 - B22 ;
						tempRowsArray.get(1).add(- b.get(i).get(j));//S4 = B21 - B11 ;
						tempRowsArray.get(2).add(b.get(i).get(j));//S6 = B11 + B22 ;
						tempRowsArray.get(4).add(b.get(i).get(j) + b.get(i).get(j+size/2)); //S10 = B11 + B12 :
					}

				}
				s.add(tempRowsArray);
			}

		}

	}

}
