package utility.extra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* Strassen algorithm for multiplying matrices in time O (n^lg7) = O (n^2.81)
 * Divide matrices A into a11 a12
 *                        a21 a22
 *                 B into b11 b12
 *                        b21 b22    
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
 * C11 = P5 + P4 - P2 + P6 = A11*B11 + A12*B21
 * C12 = P1 + P2 = A11*B12 + A12*B22
 * C21 = P3 + P4 = A21*B11 + A22*B21
 * C22 = P5 + P1 - P3 + P7 = A21*B12 + A22*B22
 */

/* Strassen algorithm requires square matrices nxn as an input. Furthermore n should be of the form n = 2^k where k is an integer
 * However this program extends application of the multiplication to the general case of
 * Cmxn = Amxp * B pxn by treating mxn matrix as qxq matrix, where q = 2^k >=m,n
 */

public class MatrixMultiplicationStrassen
{
	
	private static final int CUT_OFF = 64 ;
	
	// main recursive function that accepts matrices A, B, of arbitrary dimensions and returns matrix Csize x size
	// size should be of the form 2^k, if size < dim(A), or dim(B) only part of the values from A and B are used
	// if size > dimA or dim(B) then A and B are treated as square matrices of Asize x size and B size x size with non
	// initialized fields treated as 0
	public static List<List<Integer>> strassenMatrix(List<List<Integer>> a,
			List<List<Integer>> b, int size)
	{
		List<List<Integer>> result = new ArrayList<>(size);

//		System.out.println("Strassen call size: " + size);
//		System.out.print("rowsA " + a.size());
//		if (a.size() > 0)
//			System.out.println(" cols A top " + a.get(0).size() + " cols A bottom " + a.get(a.size()-1).size());
//		
//		System.out.print("rowsB " + b.size());
//		if (b.size() > 0)
//			System.out.println(" cols b top " + b.get(0).size() + " cols B bottom " + b.get(b.size()-1).size());
		// base case matrix a or b are empty -  or size == 2
		// create square matrix of size x size and fill it with 0
		if (a.size() == 0 || b.size() == 0 || size <= CUT_OFF)
		{
			/*for (int i = 0; i < size; i++)
				result.add(new ArrayList<Integer>(Collections.nCopies(size, 0)));

			if (size != 2)
				return result;*/
			
			return MatrixMultiplication.multiply(a, b, size);
		}
		
		// base case multiply matrices of size 2x2 in a normal way
		/*if (size == 2)
		{

			if (b.size() != 0)
			{
				for (int i = 0; i < a.size(); i++)
					for (int j = 0; j < b.get(0).size(); j++)
					{
						int kBoundary = a.get(i).size() < b.size() ? a.get(i)
								.size() : b.size();
						int sum = 0;
						for (int k = 0; k < kBoundary; k++)
						{
							sum += a.get(i).get(k) * b.get(k).get(j);
						}
						result.get(i).set(j, sum);
					}

			}

		}*/
		// recursive case
		//
		else
		{
//			System.out.println("Recursive Case size: " + size);
			// initialize 10 S matrices of size/2 x size/2
			//S1 = B12 - B22 ;
			//S2 = A11 + A12 ;
			//S3 = A21 + A22 ;
			//S4 = B21 - B11 ;
			//S5 = A11 + A22 ;
			//S6 = B11 + B22 ;
			//S7 = A12 - A22 ;
			//S8 = B21 + B22 ;
			//S9 = A11 - A21 ;
			//S10 = B11 + B12 
			List<List<List<Integer>>> s = new ArrayList<>();
			initializeFromB(b, s, size);
			initializeFromA(a, s, size);
			
//			System.out.println("Initialized s");
			
			/*Divide matrices A into a11 a12
			 *                        a21 a22
			 *                 B into b11 b12
			 *                        b21 b22 */
			// get a11, a22, b11, b22;
			List<List<Integer>> a11 = get11(a, size);
			List<List<Integer>> a22 = get22(a, size);
			List<List<Integer>> b11 = get11(b, size);
			List<List<Integer>> b22 = get22(b, size);
			
//			System.out.println("Initialized a11 - b22");
			

			/*
			 * for debugging 
			 * 
			 * System.out.println("Matrix A11: ");
			 * Utility.printMatrix(a11); System.out.println("Matrix A22: ");
			 * Utility.printMatrix(a22); System.out.println("Matrix B11: ");
			 * Utility.printMatrix(b11); System.out.println("Matrix B22: ");
			 * Utility.printMatrix(b22);
			 * 
			 * for (int i = 0; i < s.size(); i++) {
			 * System.out.println("Matrix S" +(i+1)+ " index in sArray" +
			 * getIndexS(i+1)); Utility.printMatrix(s.get(getIndexS(i+1))); }
			 */
			
			/**************** end of debugging **************************/
			
			
			
			//P1 = A11 * S1 = A11 * B12 - A11 * B22 ;
			//P2 = S2 * B22 = A11 * B22 + A12 * B22 ;
			//P3 = S3 * B11 = A21 * B11 + A22 * B11 ;
			//P4 = A22 * S4 = A22 * B21 - A22 * B11 ;
			//P5 = S5 * S6 = A11 * B11 + A11 * B22 + A22 * B11 + A22 * B22 ;
			//P6 = S7 * S8 = A12 * B21 + A12 * B22 - A22 * B21 - A22 * B22 ;
			//P7 = S9 * S10 = A11 * B11 + A11 *  B12 - A21 * B11 - A21 * B12 

			List<List<Integer>> p1 = strassenMatrix(a11, s.get(getIndexS(1)),
					size / 2);
			/*
			 * System.out.println("Matrix p1 is "); Utility.printMatrix(p1);
			 */

			List<List<Integer>> p2 = strassenMatrix(s.get(getIndexS(2)), b22,
					size / 2);
			/*
			 * System.out.println("Matrix p2 is "); Utility.printMatrix(p2);
			 */

			List<List<Integer>> p3 = strassenMatrix(s.get(getIndexS(3)), b11,
					size / 2);
			/*
			 * System.out.println("Matrix p3 is "); Utility.printMatrix(p3);
			 */

			List<List<Integer>> p4 = strassenMatrix(a22, s.get(getIndexS(4)),
					size / 2);
			/*
			 * System.out.println("Matrix p4 is "); Utility.printMatrix(p4);
			 */
			List<List<Integer>> p5 = strassenMatrix(s.get(getIndexS(5)),
					s.get(getIndexS(6)), size / 2);
			/*
			 * System.out.println("Matrix p5 is "); Utility.printMatrix(p5);
			 */

			List<List<Integer>> p6 = strassenMatrix(s.get(getIndexS(7)),
					s.get(getIndexS(8)), size / 2);
			/*
			 * System.out.println("Matrix p6 is "); Utility.printMatrix(p6);
			 */

			List<List<Integer>> p7 = strassenMatrix(s.get(getIndexS(9)),
					s.get(getIndexS(10)), size / 2);

			/*
			 * System.out.println("Matrix p7 is "); Utility.printMatrix(p7);
			 */
			
//			System.out.println("Initialized ps");
			
			/* get results
			 * C11 = P5 + P4 - P2 + P6 = A11*B11 + A12*B21
			 * C12 = P1 + P2 = A11*B12 + A12*B22
			 * C21 = P3 + P4 = A21*B11 + A22*B21
			 * C22 = P5 + P1 - P3 + P7 = A21*B12 + A22*B22
			 */

			assembleResult(result, p1, p2, p3, p4, p5, p6, p7);

		}

		return result;
	}

	

	/* get result (C = c11 c12
	 * 				   c21 c22)
	 * C11 = P5 + P4 - P2 + P6 = A11*B11 + A12*B21
	 * C12 = P1 + P2 = A11*B12 + A12*B22
	 * C21 = P3 + P4 = A21*B11 + A22*B21
	 * C22 = P5 + P1 - P3 + P7 = A21*B12 + A22*B22
	 */
	private static void assembleResult(List<List<Integer>> result,
			List<List<Integer>> p1, List<List<Integer>> p2,
			List<List<Integer>> p3, List<List<Integer>> p4,
			List<List<Integer>> p5, List<List<Integer>> p6,
			List<List<Integer>> p7)
	{
		int size = p1.size();
		List<Integer> tempRow;
		for (int i = 0; i < size * 2; i++)
		{
			tempRow = new ArrayList<>();
			for (int j = 0; j < size * 2; j++)
				if (i < size && j < size) // initialize c11
				{
					tempRow.add(p5.get(i).get(j) + p4.get(i).get(j)
							- p2.get(i).get(j) + p6.get(i).get(j));
				} else if (i >= size && j >= size) // initialize c22
				{
					tempRow.add(p5.get(i - size).get(j - size)
							+ p1.get(i - size).get(j - size)
							- p3.get(i - size).get(j - size)
							- p7.get(i - size).get(j - size));
				} else if (i < size) // initialize c12
				{
					tempRow.add(p1.get(i).get(j - size)
							+ p2.get(i).get(j - size));
				} else
				// initialize c21
				{
					tempRow.add(p3.get(i - size).get(j)
							+ p4.get(i - size).get(j));
				}
			result.add(tempRow);
		}

	}

	// extract a11 as a separate matrix A = a11 a12
//											a21 a22
	private static List<List<Integer>> get11(List<List<Integer>> a, int size)
	{
		return getMatrix(a, 0, size / 2 - 1, 0, size / 2 - 1);
	}

	// extract a11 as a separate matrix A = a11 a12
//											a21 a22
	private static List<List<Integer>> get22(List<List<Integer>> a, int size)
	{
		return getMatrix(a, size / 2, size - 1, size / 2, size - 1);
	}

	// extract part of the matrix from original matrix limited vertically by topRow, bottomRow
	// and horizontally by leftCol and rightCol
	// if any of the topRow, bottomRow, leftCol, rightCol are outside the boundaries of the matrix
	// a smaller (up to null matrix) is returned
	private static List<List<Integer>> getMatrix(List<List<Integer>> matrix,
			int topRow, int bottomRow, int leftCol, int rightCol)
	{
		List<List<Integer>> subMatrix = new ArrayList<>();

		List<Integer> tempRow;

		for (int i = topRow; i <= (bottomRow < matrix.size() ? bottomRow
				: matrix.size() - 1); i++) // increment i to the lower of
											// matrix.size() - 1 and bottomRow
		{
			tempRow = new ArrayList<>();
			for (int j = leftCol; j <= (rightCol < matrix.get(i).size() ? rightCol
					: matrix.get(i).size() - 1); j++)
			{

				tempRow.add(matrix.get(i).get(j));
			}
			if (tempRow.size()!=0)
				subMatrix.add(tempRow);

		}
		return subMatrix;

	}

	
	// matrices s in strassen method are stored in an array
	// this method returns a correct index inside that array for matrices s1 to s10
	// e.g s.get(getIndex(1)); // returns the index of s1 which is 0
	private static int getIndexS(int matrixNumber)
	{
		switch (matrixNumber)
		{
		case 1:
			return 0;
		case 2:
			return 5;

		default:
			return matrixNumber / 2 - 1 + (matrixNumber % 2) * 6;

		}
	}
	
	
	// intialize 
	//S2 = A11 + A12 ;
	//S3 = A21 + A22 ;
	//S5 = A11 + A22 ;
	//S7 = A12 - A22 ;
	//S9 = A11 - A21 
	public static void initializeFromA(List<List<Integer>> a,
			List<List<List<Integer>>> s, int size)
	{
		int lastNon0RowA = a.size() - 1;
		int lastNon0ColA = a.size() == 0 ? -1 : a.get(0).size() - 1;

		if ((lastNon0RowA >= 0 && lastNon0ColA >= 0)) // matrix a not all 0
		{

			List<List<Integer>> tempRowsArray;
			List<Integer> row;
			prepareS(s, size);

			boolean hasFewRows = lastNon0RowA < size / 2 - 1;
			boolean hasFewCols = lastNon0ColA < size / 2 - 1;

			// increment i to a smaller of size/2 and lastNon0RowA, increment j
			// to a smaller of size/2 and lastNon0ColA
			for (int i = 0; i <= (hasFewRows ? lastNon0RowA : size / 2 - 1); i++)
			{
				tempRowsArray = new ArrayList<>(5);
				for (int k = 0; k < 5; k++)
				{
					row = new ArrayList<>(size / 2);
					tempRowsArray.add(row);
				}
				for (int j = 0; j <= (hasFewCols ? lastNon0ColA : size / 2 - 1); j++)
				{

					if (i + size / 2 <= lastNon0RowA
							&& j + size / 2 <= lastNon0ColA) // intialize
																// everything
					{

						tempRowsArray.get(0).add(
								a.get(i).get(j) + a.get(i).get(j + size / 2)); // S2
																				// =
																				// A11
																				// +
																				// A12
																				// ;
						tempRowsArray
								.get(1)
								.add(a.get(i + size / 2).get(j)
										+ a.get(i + size / 2).get(j + size / 2));// S3
																					// =
																					// A21
																					// +
																					// A22
																					// ;
						tempRowsArray
								.get(2)
								.add(a.get(i).get(j)
										+ a.get(i + size / 2).get(j + size / 2));// S5
																					// =
																					// A11
																					// +
																					// A22
																					// ;
						tempRowsArray
								.get(3)
								.add(a.get(i).get(j + size / 2)
										- a.get(i + size / 2).get(j + size / 2)); // S7
																					// =
																					// A12
																					// -
																					// A22
																					// ;
						tempRowsArray.get(4).add(
								a.get(i).get(j) - a.get(i + size / 2).get(j)); // S9
																				// =
																				// A11
																				// -
																				// A21
																				// ;

					} else if (i + size / 2 > lastNon0RowA
							&& j + size / 2 > lastNon0ColA) // initialize only
															// a11
					{
						tempRowsArray.get(0).add(a.get(i).get(j)); // S2 = A11 +
																	// A12 ;
						tempRowsArray.get(2).add(a.get(i).get(j));// S5 = A11 +
																	// A22 ;
						tempRowsArray.get(4).add(a.get(i).get(j)); // S9 = A11 -
																	// A21 ;
					} else if (j + size / 2 > lastNon0ColA) // initialize a11 &&
															// a21
					{
						tempRowsArray.get(0).add(a.get(i).get(j)); // S2 = A11 +
																	// A12 ;
						tempRowsArray.get(1).add(a.get(i + size / 2).get(j));// S3
																				// =
																				// A21
																				// +
																				// A22
																				// ;
						tempRowsArray.get(2).add(a.get(i).get(j));// S5 = A11 +
																	// A22 ;
						tempRowsArray.get(4).add(
								a.get(i).get(j) - a.get(i + size / 2).get(j)); // S9
																				// =
																				// A11
																				// -
																				// A21
																				// ;

					} else if (i + size / 2 > lastNon0RowA) // initialize a11 &&
															// a12
					{
						tempRowsArray.get(0).add(
								a.get(i).get(j) + a.get(i).get(j + size / 2)); // S2
																				// =
																				// A11
																				// +
																				// A12
																				// ;
						tempRowsArray.get(2).add(a.get(i).get(j));// S5 = A11 +
																	// A22 ;
						tempRowsArray.get(3).add(a.get(i).get(j + size / 2)); // S7
																				// =
																				// A12
																				// -
																				// A22
																				// ;
						tempRowsArray.get(4).add(a.get(i).get(j)); // S9 = A11 -
																	// A21 ;
					}

				}
				for (int k = 5; k < 10; k++)
					if (tempRowsArray.get(k - 5).size() != 0)
						s.get(k).add(tempRowsArray.get(k - 5));
			}

		}

	}

	
	// prepare List<List<List<Integer>>> s for instertion of matrices s1-s10
	public static void prepareS(List<List<List<Integer>>> s, int size)
	{
		List<List<Integer>> matrix;
		for (int i = 0; i < 5; i++)
		{
			matrix = new ArrayList<>(size / 2);
			s.add(matrix);
		}
	}
	
	
	// initialize
	//S1 = B12 - B22
	//S4 = B21 - B11 ;
	//S6 = B11 + B22 ;
	//S8 = B21 + B22 ;
	//S10 = B11 + B12

	public static void initializeFromB(List<List<Integer>> b,
			List<List<List<Integer>>> s, int size)
	{
		int lastNon0RowB = b.size() - 1;
		int lastNon0ColB = b.size() == 0 ? -1 : b.get(0).size() - 1;

		if ((lastNon0RowB >= 0 && lastNon0ColB >= 0)) // matrix a not all 0
		{
			List<List<Integer>> tempRowsArray;
			List<Integer> row;

			prepareS(s, size);

			boolean hasFewRows = lastNon0RowB < size / 2 - 1;
			boolean hasFewCols = lastNon0ColB < size / 2 - 1;

			// increment i to a smaller of size/2 and lastNon0RowA, increment j
			// to a smaller of size/2 and lastNon0ColA
			for (int i = 0; i <= (hasFewRows ? lastNon0RowB : size / 2 - 1); i++)
			{
				tempRowsArray = new ArrayList<>(5);
				for (int k = 0; k < 5; k++)
				{
					row = new ArrayList<>(size / 2);
					tempRowsArray.add(row);
				}
				for (int j = 0; j <= (hasFewCols ? lastNon0ColB : size / 2 - 1); j++)
				{

					if (i + size / 2 <= lastNon0RowB
							&& j + size / 2 <= lastNon0ColB) // intialize
																// everything
					{

						tempRowsArray
								.get(0)
								.add(b.get(i).get(j + size / 2)
										- b.get(i + size / 2).get(j + size / 2));// S1
																					// =
																					// B12
																					// -
																					// B22
																					// ;
						tempRowsArray.get(1).add(
								b.get(i + size / 2).get(j) - b.get(i).get(j));// S4
																				// =
																				// B21
																				// -
																				// B11
																				// ;
						tempRowsArray
								.get(2)
								.add(b.get(i).get(j)
										+ b.get(i + size / 2).get(j + size / 2));// S6
																					// =
																					// B11
																					// +
																					// B22
																					// ;
						tempRowsArray
								.get(3)
								.add(b.get(i + size / 2).get(j)
										+ b.get(i + size / 2).get(j + size / 2)); // S8
																					// =
																					// B21
																					// +
																					// B22
																					// ;
						tempRowsArray.get(4).add(
								b.get(i).get(j) + b.get(i).get(j + size / 2)); // S10
																				// =
																				// B11
																				// +
																				// B12
																				// :

					} else if (i + size / 2 > lastNon0RowB
							&& j + size / 2 > lastNon0ColB) // initialize only
															// b11
					{

						tempRowsArray.get(1).add(-b.get(i).get(j));// S4 = B21 -
																	// B11 ;
						tempRowsArray.get(2).add(b.get(i).get(j));// S6 = B11 +
																	// B22 ;
						tempRowsArray.get(4).add(b.get(i).get(j)); // S10 = B11
																	// + B12 :
					} else if (j + size / 2 > lastNon0ColB) // initialize b11 &&
															// b21
					{

						tempRowsArray.get(1).add(
								b.get(i + size / 2).get(j) - b.get(i).get(j));// S4
																				// =
																				// B21
																				// -
																				// B11
																				// ;
						tempRowsArray.get(2).add(b.get(i).get(j));// S6 = B11 +
																	// B22 ;
						tempRowsArray.get(3).add(b.get(i + size / 2).get(j)); // S8
																				// =
																				// B21
																				// +
																				// B22
																				// ;
						tempRowsArray.get(4).add(b.get(i).get(j)); // S10 = B11
																	// + B12 :

					} else if (i + size / 2 > lastNon0RowB) // initialize b11 &&
															// b12
					{

						tempRowsArray.get(0).add(b.get(i).get(j + size / 2));// S1
																				// =
																				// B12
																				// -
																				// B22
																				// ;
						tempRowsArray.get(1).add(-b.get(i).get(j));// S4 = B21 -
																	// B11 ;
						tempRowsArray.get(2).add(b.get(i).get(j));// S6 = B11 +
																	// B22 ;
						tempRowsArray.get(4).add(
								b.get(i).get(j) + b.get(i).get(j + size / 2)); // S10
																				// =
																				// B11
																				// +
																				// B12
																				// :
					}

				}
				for (int k = 0; k < 5; k++)
				{
					if (tempRowsArray.get(k).size() != 0)
						s.get(k).add(tempRowsArray.get(k));

				}
			}

		}

	}

}
