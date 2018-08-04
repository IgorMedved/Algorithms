# Algorithms
A collection of algorithms based on exercises from a book Introduction to Algorithms
http://www.amazon.com/Introduction-Algorithms-3rd-Edition-Press/dp/0262033844

Progress so far:

Chapter 2:

2-1-1 Insertion Sort (ascending)

2-1-2 Insertion Sort  (descending)

2-1-3 LinearSearch

2-1-4 adding 2 n-bit binary integers (stored in n-element array)


2-2-2 Selection Sort (find smallest, exchange with A[0] , find smallest, exchange with with A[1], etc)


2-3-1 Merge Sort (with sentineles (Max_Values))

2-3-2 Merge Sort (without sentinels)

2-3-3 Recursive insertion sort

2-3-5 binary search

2-3-6 Improving selection sort by using binary search 

2-3-7 * (finding if a given integer can be represented by a sum of two integers from the list)
        !!! Great results once the list is sorted the algorithm works n (ln n) worst case, but O(1) on average
		
2-P-1 Improving Merge sort by reducing overhead for small Merge Array sizes by switching to insertion sort 

2-P-2 Bubble Sort

2-Extra Improving MergeSort for running on multicore processors with multithreading
		results: 2.5-3 times increase in speed on 4 core processor compared to base case
		
		
Chapter 4 - Divide and Conquer

4-1 Maximum Subarray (subarray with the maximum sum of elements inside it)


4-2-1 Strassen matrix multiplication for matrices of size nxn where n = 2^m

4-2-2 Strassen matrix multiplication for arbitrary matrix sizes

4-2-3 Impproving strassen by substituting regular matrix multiplication at certain cutoff:
		result: the cutoff size for square matrices is between n = 32 and n = 64;
		
		
Chapter 6 - HeapSort


6-1 Heaps 

6-2 maxHeapify on arraybuildMaxHeap

6-3 buildMaxHeapheapSort

6-4 heapSort

6-5-1 priority queues - maxHeapInsert, heapExtractMax, heapIncreaseKey, heapMaximum

6-5-6 improving efficiency substitute swap by only doing one assignment insided the loop and 1 at the beginning and end of the loop

6-5-7 implementing regular queue with priority queue

6-5-8 heap delete


Chapter 7 - quicksort


7-1 quicksort - regular partition

7-3-1 randomized quicksort - avoiding worst cast

7-4-1 improving quicksort with cut off for "almost sorted array"

7-4-2 improving quicksort by running partition about median of 3 random elements


7-P-1 Hoare partition

7-P-2 Improving quicksort with middle partiotion of equal values


Chapter 8 - sorting in Linear times


8-1 Count sort

8-2 Radix sort

8-3 Bucket sort


Chapter 9 - Median and Order Statistics


9-1 Minimum and Maximum (exercise find 2nd largest element in unsorted array in O(N) time)

9-2 Selection in Expected linear time (exercise find kth largest element in unsorted array in O(N) time)

9-3 Selection in Worst case linear time (exercise find p elements around kth largest element in O(N) time)


Chapter 10 - Elementary Data Structures


10-1 Stacks and Queues

10-2 Linked Lists

10-4 Trees (Breadth and depth first traversals, reducing memory use and recursion during traversal)



Chapter 11 - Hash Tables


11-2 Hash Tables


Chapter 12 - Binary Search Trees


12-2 Quering Binary Search Trees
