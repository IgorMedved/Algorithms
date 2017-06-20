package utility.sort;

import java.util.ArrayList;
import java.util.List;

import utility.sort.helper.EvenDistributionArrayDivider;
import utility.Utility;

// this class implements the idea of merge sort without using the external buffer
// the algorithm is in place
// the merge consists of the following parts
// 0) pre sort the intervals of length up to 31 with insertion sort
// 1) outer loop:  bottom up merge without using recursion which is very similar to standard bottom up merge
			// it uses EvenDistributionArrayDivider to get proper intervals for each merge level

// 2) inner loop: given two sorted ranges A and B merge them without using external buffer
		// prepare for iteration
/*		a) divide range A and range B into blocks of length SQRT(length(A))
 * 		b) use first block of A and last block of B for buffer1 and 2 respectively
 *      c) temporary move some values out of order so that all values in buffer1 and buffer 2 are unique 
 *      (e.g original A [1,2,2][2,5,7][7,11,13] make into A [1,2,5][2,2,7][7,11,13] = [buffer1][A1][A2]
 *      original B [1,3,3][10,12,13][13,13,13] => [1,3,3][13,13,13][10,12,13] = [B1][B2][buffer2]
 *      d) since some blocks in A and B can be uneven size make first A block and last B block uneven (for example if size of a is 8 the buffer is size 3 then first uneven block is size 2 and last block is size 3)
 *      e) tag evenly sized A blocks with values from buffer by swapping buffer values with first element of each block
 *        (e.g) A [1,2,5][2,2,7][7,11,13] => [2,7,5][1,2,7][2,11,13]
 *      f) set up some initial values
 *      	Alast - unevenly sized first block of A if there are no unevenly sized blocks Alast is empty (e.g empty for this case)
 *      	Blast - empty
 *      	currentRangeA - start at the first evenly sized block of A, ends at the end of rangeA (e.g. [1,2,7][2,11,13])
 *      	currentRangeB - start at the start of the range b and goes for length min(blockSize, to the end of B range0
 *      		e.g currentRangeB = B1 =[1,3,3]
 *      
 *      // roll and drop
 *      a) get first evenly sized block in currentRangeA out of the way replace it with currentRangeB (e.g for first pass A1)
 *         (if currentRangeB is evenly sized block (like B1 on first pass) swap it with currentRangeA first block, if currentRangeB is uneven 
 *         rotate currentRangeB into left most position of currentRangeA)
 *         e.g ([2,7,5][1,2,7][2,11,13][1,3,3][13,13,13][10,12,13] => [2,7,5][1,3,3][2,11,13][1,2,7][13,13,13][10,12,13] = [buffer1][Afirst][B1][A2][A1][B2][buffer2] - Afirst is empty
 *         update ranges e.g (Alast - same (empty on first pass),
 *          Blast = currentRangeB (B1 on first pass) , currentRangeA = shift by Blast to the right, currentRangeB = start to the right of currentRangeA and go for min(blockSize, to the end of B)
 *         
 *         do a) until ran out of B blocks on the right or buffer1[0] < Blast[end] e.g 1<3 then do b (buffer1[0] element that was originally at the front of the smallest full sized A block)
 *         
 *         b) 
 *         		1. find minA block  in currentRangeA by first index of the block (e.g. firstPass minA = A1 = [1,2,7])
 *         		2. place minA beside Last B (e.g. firstPass [2,7,5][1,3,3][1,2,7][2,11,13][13,13,13][10,12,13] (swap A2 and A1))
 *         		3. split Blast into two parts (elements in part1 < buffer1[0], elements in part2 >= buffer1[0] e.g. [1][3,3]
 *              4. swap minA[0] and buffer1[0] and increase buffer1 lookup index by 1 e.g. [buffer1] = [1,7,5], minA = [2,2,7]
 *              5. rotate minA into B [1][2,2,7][3,3]
 *              6. merge lastA and first part of bSplit (skip at this step as lastA is empty)
 *              7. update ranges for next step:
 *                 Alast = [2,2,7]
 *                 Blast = [3,3]
 *                 currentA = [2,11,13]
 *               8. check if currentA is empty and break out of the cycle if it is.
 *                 If not repeat step a
 *          
 *          // do post processing for given merge
 *          a) merge aLast and range bLast
 *          b) sort buffer2 with InsertSort if exist and merge it in sorted range
 *          c) merge buffer 1 into sorted range
 *          
 *          
 *          go to next merge interval (e.g. if sort array is of size 128 and intervals of length 16 are InsertSort sorted in step 0)
 *          							Then first range for A would be 0-15, first range for B would be 16-31, next merge interval A: 32-47, B: 48-63
 *     go to next merge level or finish (e.g. 1st merge level intervals of length 16, second 32, 3rd 64 and until length < array.size())
 *                 
 *         
 *      	
 *      
 *    
 * *
 */
public class BufferlessMerge {
	// min insertionSortLength 31
		public static void mergeSortBottomUp(List<Integer> array) 
		{
			// use standard merge for small sizes
			if (array.size() < 128)
			{
				Sort.mergeSortOptimized(array, 0, array.size()-1, 31);
				return;
			}
			
			int len = 16;
			
			EvenDistributionArrayDivider divider = new EvenDistributionArrayDivider(array.size(), len);
			divider.begin();
			while (!divider.finished())
			{
				divider.nextStep();
				Sort.insertSortNondescending(array, divider.getStart(), divider.getEnd());
				System.out.println("Start: " + divider.getStart() + " End: "+ divider.getEnd() + " step: " + (divider.getEnd() - divider.getStart() +1));
				Utility.printArray(array, divider.getStart(), divider.getEnd());
			}
			
			Utility.printArray(array);
			
			do
			{ 
				
				divider.begin();
				do
				{
					System.out.println("\n\n\n\n\n");
					
					divider.nextStep();
					// setup division size for this step
					int blockSize = (int)Math.sqrt(divider.length());
					int bufferSize = divider.length()/blockSize;
					if (blockSize!=bufferSize &&divider.length()%bufferSize == 0)
						bufferSize --;
					// set A and b ranges
					int aStart = divider.getStart();
					int aEnd = divider.getEnd();
					divider.nextStep();
					int bStart = divider.getStart();
					int bEnd = divider.getEnd();
					
					System.out.println("aStart " + aStart + " aEnd " + aEnd);
					System.out.println(("bStart " + bStart + " bEnd " + bEnd));
					
					// extractBuffers
					// extract buffer from A (buffer will be in positions aStart - aStart+bufferSize-1)
					int indexA = aStart;
					int buffer1Start = aStart;
					int buffer1End= aStart +bufferSize-1;
									
					Utility.printArray(array, aStart, bEnd);
					
					List <Integer> indexes = new ArrayList(buffer1End - buffer1Start +1);
					int value = array.get(aStart);
					indexes.add(aStart);
					int index = aStart+1;
					while (indexes.size() < buffer1End - buffer1Start +1)
					{
						index = SortHelper.firstBinary(array, index, aEnd, value+1);
						if (index > aEnd)
							break;
						indexes.add(index);
						value = array.get(index);
					}
					
					if (indexes.size() == buffer1End - buffer1Start +1 )
					{
						for (int i = indexes.size() -1; i > 0; i--)
							SortHelper.rotate(array,indexes.get(i-1)+1, i-indexes.size(), indexes.get(i) + indexes.size() - i -1);
					}
					else
						throw new RuntimeException("Could not find enough unique values for \"A\" buffer");
					
					
					// extract buffer from b (buffer will be in positions (bEnd- bufferSize +1) - bEnd; 
					int buffer2Start = bEnd - bufferSize+1;
					int buffer2End = bEnd;
					
					indexes.clear();
					indexes.add(bEnd);
					index = bEnd-1;
					value = array.get(bEnd);
					
					while (indexes.size() < buffer2End - buffer2Start +1)
					{
						index = SortHelper.lastBinary(array, bStart, index, value-1);
						if (index < bStart)
							break;
						indexes.add(index);
						value = array.get(index);
					}
					
					if (indexes.size() == buffer2End - buffer2Start +1 )
					{
						for (int i = indexes.size() -1; i > 0; i--)
							SortHelper.rotate(array,indexes.get(i)- indexes.size() +i+1, indexes.size()-i, indexes.get(i-1) -1);
					}
					else
						throw new RuntimeException("Could not find enough unique values for b buffer");
					/*Utility.printArray(array, buffer1Start, buffer2End);
					Utility.printArray(array, buffer1Start, buffer1End);
					System.out.println("aStart " + aStart + " aEnd " + aEnd);
					System.out.println(("bStart " + bStart + " bEnd " + bEnd));*/
					
					
					// adjust A and B ranges to account for buffers
					aStart += bufferSize;
					bEnd -=bufferSize;
					
					int blockBStart = bStart;
					int blockBEnd = bStart + Math.min(bStart+blockSize, bEnd);
					int aFirstStart = aStart;
					int aFirstEnd = ((aEnd - aStart +1)%blockSize) + aStart -1;
					int blockAStart = aFirstEnd+1;
					int blockAEnd = aEnd;
					
					// tag aBlocks with values from first buffer (except for first uneven sized aBlock)
					
					int numFullBlocks = (aEnd-buffer1End) / blockSize;
					
					//swap (array,buffer1Start, buffer1End+1,1);
					for (int i = 0; i < numFullBlocks; i++)
					{
						SortHelper.swap(array, buffer1Start+i, aFirstEnd +1 + blockSize*i,1);
					}
					System.out.println("Matrix after tagging");
					Utility.printArray(array, buffer1Start, buffer2End);
					Utility.printArray(array, buffer1Start, buffer1End);
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					
					
					
					
					// initial setup for Roll and drop
					
					
					int aLastStart = aFirstStart;
					int aLastEnd = aFirstEnd;
					
					blockAStart= aLastEnd+1;
					int bLastStart = 0;
					int bLastEnd = -1;
					
					/*System.out.println("Buffer size: " + bufferSize);
					System.out.println("Block a start "+ blockAStart);
					System.out.println("block size: " + blockSize);
					System.out.println("aFirstStart: " + aFirstStart);
					System.out.println("aFirstEnd " +  aFirstEnd);*/
					
					while (true)
					{
						if ((bLastEnd - bLastStart+1) >0 && array.get(bLastEnd) >= array.get(indexA) || (blockBEnd-blockBStart +1) <=0)
						{
							System.out.println("merge!");
							// split lastB
							int split = SortHelper.firstBinary (array, bLastStart, bLastEnd, array.get(indexA));
							int remainingB = bLastEnd - split +1;
						
							// find minA 
							int minValue = array.get(blockAStart);
							int minA = blockAStart;
							for (int i = blockAStart + blockSize; i <= blockAEnd; i+=blockSize)
							{
								if(array.get(i)< array.get(minA))
									minA = i;
							}
							// swap minA into spot next with lastB 
							if (minA != blockAStart)
								SortHelper.swap(array, minA, blockAStart, blockSize);
							
													
							// swap value from tag buffer with minA
							SortHelper.swap(array, indexA, blockAStart,1 );
							indexA++;
							// rotate minA into lastB
							if (split!=-1)
							SortHelper.rotate(array, split, remainingB, blockAStart+ blockSize-1);
							// depending on whether we have a buffer or not merge in place or mergeInternal lastA into bLastStart - bLastStart + split
								
							mergeInPlace(array, aLastStart, aLastEnd, aLastEnd+1, split-1 );
							
							// update lastA, lastB
							aLastStart = blockAStart - remainingB;
							aLastEnd = aLastStart+ blockSize-1;
							bLastStart = aLastEnd+1;
							
							bLastEnd =  aLastEnd + remainingB;
						
							// update ranges A and B
							blockAStart+=blockSize;
							if (blockAEnd< blockAStart)
							{
								//Utility.printArray(array, buffer1Start, buffer2End);
								break;
							}
								
						
						
						}
					
						// when dealing with last uneven Bblock it needs to be rotated in place instead of swapping it with regular sized leftmost Ablock
						else if (blockBEnd - blockBStart < blockSize-1)
						{
//							System.out.println("Rotate last uneven bBlock into A");
							SortHelper.rotate(array, blockAStart, blockAEnd - blockAStart +1, blockBEnd);
							bLastStart = blockAStart;
							bLastEnd = blockAStart+(blockBEnd-blockBStart);
							blockAStart += (blockBEnd- blockBStart + 1);
							blockAEnd += (blockBEnd- blockBStart+ 1);
							blockBStart = blockBEnd+1;
						}
						// move leftmost blockA out of the way and swap it with regular sized b block
						else
						{
		//					System.out.println("Move leftmost blockA out of the way and swap it with regular sized b block");
							SortHelper.swap (array, blockAStart, blockBStart, blockSize);
							bLastStart = blockAStart;
							bLastEnd = bLastStart+blockSize-1;
							blockAStart += blockSize;
							blockAEnd += blockSize;
							blockBStart += blockSize;
							blockBEnd = (blockBEnd + blockSize) > bEnd? bEnd: blockBEnd +blockSize;
						}
			//			System.out.println("Matrix after iteration");
					}
					// merge last Alast with bRemaining
//					System.out.println("Matrix after block division");
					
					
					mergeInPlace(array, aLastStart, aLastEnd, aLastEnd+1, bEnd);
					// redistribute the buffers
					Sort.insertSortNondescending(array, buffer1Start, buffer1End);
					Sort.insertSortNondescending(array, buffer2Start, buffer2End);
					mergeInPlace(array, buffer1Start, buffer1End, buffer1End + 1, bEnd);
					mergeInPlace(array, buffer1Start, bEnd, buffer2Start, buffer2End);
				}
				while (!divider.finished());
				System.out.println("Divider finished");
			}
			while (divider.nextLevel());
			
		}
		
		
		// merge to sorted ranges into 1 sorted range
		// preconditions: 
		// 1) range1Start <= range1end
		// 2) range2start <= range2end
		// 3) ranges are side by side: range2start = range1end +1
		// range1Start ..... sorted range 1 .... range1end range2start ... sorted range 2 .....range2end
		public static void mergeInPlace (List<Integer> array, int range1start, int range1end, int range2start, int range2end)
		{
			if (range1end != range2start-1)
				return;
			int mid;
			while (range1start <= range1end && range2start<= range2end)
			{
				// check if ranges are already in order
				if (array.get(range2start)>=array.get(range1end))
					break;
				
				
				// in order to minimize the amount of operations always rotate smaller range into larger
				// rotate range2 into range1
				if (range1end - range1start > range2end - range2start)
				{
									
					mid = SortHelper.lastBinary(array, range1start, range1end, array.get(range2end));
					
					int amount = range2start-mid-1;
					if (amount==0)
						range2end--;
					else
					{
						SortHelper.rotate(array, mid+1, amount, range2end);
						range1end = mid;
						range2start = mid+1;
						range2end -=amount;
					}
					
					
				}
				// rotate range 1 into 2
				else
				{
					mid = SortHelper.firstBinary(array, range2start, range2end, array.get(range1start));
					int amount = range1end - mid+1;
					if (amount == 0)
						range1start++;
					else
					{
						SortHelper.rotate(array, range1start, amount, mid-1);
						range1start += (mid - range2start + 1);
						range2start = mid;
						range1end = mid-1;
					}
						
				}
				
			}
			
		}

}
