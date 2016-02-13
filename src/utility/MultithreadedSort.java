package utility;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultithreadedSort
{
	public static final int MERGE_SORT = 0;
	public static final int MERGE_SORT_WO_SENTINEL = 1;
	public static final int MERGE_SORT_OPTIMIZED = 2;
	
	public static void runParallelMerge(List<Integer> sortArray, int numCores, int sortType)
	{
		ExecutorService executor = Executors.newFixedThreadPool(numCores);
		
		MultithreadedMergeSort sort = getSortType(sortType);
		sort.setUp(sortArray, numCores);
		
		// run merge sort divided in n parts = numCores
		runParallelMergeTasks(executor, sort);
		
		
		// merge n parts together
		int numParts = numCores;
		
		int subArraySizeFactor = 1;
		
		// each loop iteration goes through 1 merge level 
		while (numParts > 1)
		{
			
			// pass current values of remaining parts, subArraySize, subArraySizeRight to sort object
			sort.setUpMerge(numParts, subArraySizeFactor);//, subArraySizeRight); 
			// pass current values of remaining parts, subArraySize, subArraySizeRight to sort object
			runParallelMergeTasks(executor, sort);
			
			
			numParts = (numParts%2) + numParts/2; // if (numParts == even) numParts /= 2; if (numParts == odd) numParts = numParts/2 +1
			 
			subArraySizeFactor *=2;
			
			
		}


	}
	
	public static MultithreadedMergeSort getSortType (int sortType)
	{
		switch (sortType)
		{
		case MERGE_SORT:
			//return new MultithreadedMergeSortConcrete();
		case MERGE_SORT_OPTIMIZED:
			return new MultithreadedMergeSortOptimized();
		case MERGE_SORT_WO_SENTINEL:
			return new MultithreadedMergeSortWOSentinel();
		default:
			return null;
		}
	}
	
	
	
	private static void runParallelMergeTasks (ExecutorService executor, MultithreadedMergeSort sort)
	{
		int numRemainingParts = sort.getNumRemainingParts() == 0? sort.getNumCores(): sort.getNumRemainingParts()/2;
		for (int i = 0; i < numRemainingParts; i++)
		{
			executor.submit(sort);
		}
		
		try
		{
			sort.getLatch().await(); // await for merge to finish
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}

abstract class MultithreadedMergeSort implements Runnable
{
	protected List<Integer> mSortArray; // the array to be sorted
	protected int mNumCores; // number of processors on which the job runs
	protected int mNumRemainingParts; // parameter that tells how many parts are left to merge after the initial MergeSort is run 
	protected int mSubArraySizeFactor; // size of all the subArray lists except the rightmost one when the merge is run
	protected int mMergeCount; // counter for number of times Merge was run on each merge level
	
	
	protected int mRunCount; // counter for number of times the run was called
	protected volatile CountDownLatch mLatch;
	
	

	public int getNumRemainingParts()
	{
		return mNumRemainingParts;
	}
	
	public int getNumCores()
	{
		return mNumCores;
	}
	
	public CountDownLatch getLatch()
	{
		return mLatch;
	}
	
	public void setUpMerge(int remainingParts, int subArraySizeFactor)
	{
		mNumRemainingParts = remainingParts;
		mSubArraySizeFactor = subArraySizeFactor;
		
		mMergeCount = 0;
		
		mLatch = new CountDownLatch(remainingParts/2);
	}
	
	public void setUp(List<Integer> sortArray, int numCores)
	{
		mSortArray = sortArray;
		mNumCores = numCores;
		mRunCount = 0;
		
		mLatch = new CountDownLatch(numCores);
		
	}
	//abstract public void resetLatch(CountDownLatch latch);
	abstract public void run();
	
	
}


class MultithreadedMergeSortWOSentinel extends MultithreadedMergeSort implements Runnable
{

	
	// this method is designed to efficiently run MergeSort on multicore processor.
	public void run()
	{
		int localCount; // local variable required to synchronize execution
		int size = mSortArray.size();
		synchronized (this) // each time the thread is run increase mCounter
		{
			mRunCount++;
			localCount = mRunCount; // mCount keeps track of how many times run() was executed. As the problem is run on different
							    // threads mCount is changed each time new thread is called. localCount
			// stores the value of mCount in the beginning of the run() to be used throughout the call

		}
		// The problem is initially split into n
		// parts corresponding to the number of
		// cores. Each call will run on a separate processor
		// The run on each core returns sorted subArray of the size: arraySize/numCores
		// for example if numCores == 4 the problem of sorting is split into 4 equal parts:
		// sortArray: {{_ _ _ _}{_ _ _ _}{_ _ _ _}{_ _ _ _}}
		// each call of Sort.mergeSort is run on a separate thread and produces a sorted SubArray {_ _ _ _} within mSortArray
		// localCount keeps track of how many times the run() was executed
		// First mNumCores runs (localCount <= mNumCores) result in mNumCores sorted subArrays
		if (localCount <= mNumCores) 
		{
			// Params:
			// param1 - pointer to entire array to be sorted
			// @param2 - index of first element in sortSubArray
			// @param3 - index of last element in sortSubArray
			Sort.mergeSortWOSentinel(mSortArray, (localCount - 1) * size / mNumCores,
					localCount * size / mNumCores - 1); 

		}
		// now localCount > mNumCores. It is time to merge the sorted subArrays into sortedArray
		// Here are a few examples of how the merge will be done depending on the mNumCores
		// mNumCores == 2 
		// sortedArray {{_ _ _ _}{_ _ _ _}} merge=> {_ _ _ _ _ _ _ _ _}
		// mNumCores ==3
		// sortedArray {{_ _ _ _}{_ _ _ _}{_ _ _ _}} merge=> {{_ _ _ _ _ _ _ _ _} {_ _ _ _}} synchronize
		// {{_ _ _ _ _ _ _ _ _}, {_ _ _ _}} merge= > {_ _ _ _ _ _ _ _ _ _ _ _ _}
		// mNumCores == 7
		// sortedArray : {{_ _ _ _}{_ _ _ _}{_ _ _ _}{_ _ _ _}{_ _ _ _}{_ _ _ _}{_ _ _ _}} merge =>      merge Level 1
		// => {{_ _ _ _ _ _ _ _ _}{_ _ _ _ _ _ _ _ _}{_ _ _ _ _ _ _ _ _}{_ _ _ _}} synchronize =>        merge Level 2
		// => {{_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _}{_ _ _ _ _ _ _ _ _ _ _ _ _}} synchronize =>          merge Level 3
		// => {_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _}
		//****************************************************************************************
		// mRemainingParts keeps track of how many parts need to be merged on current merge level
		// mRemainingParts = mNumCores at merge level 1
		// mRemainingparts = mRemaingParts/2 + mRemainingParts%2; for each new merge level
		// ie for mNumCores == 7
		// mRemainingParts = 7 on first merge Level, mRemainingParts = 4 for second merge Level,
		// mRemainingParts = 2 for 3rd merge Level
		// *********************************************************************
		// mSubArraySize keeps track of subArraySizes(except the rightmost one which can be smaller)
		// mSubArraySize = arraySize/mNumCores for merge Level 1
		// mSubArraySize = mSubArraySize * 2^(merge Level - 1)
		// ie for mNumCores == 7
		// mSubArraySize = arraySize/7 for merge level 1
		// mSubArraySize = 2*mSubArraySize = 2*arraySize/7 for merge level 2
		// mSubArraySize = 4*arraySize/7 for merge level 3
		// **********************************************************************
		// mSubArraySizeRight keeps track of the size of rightmost subArray for each merge level
		// The rightmost subArray is always of the same size as mSubArraySize or smaller. mSubArraySizeRight = mSubArraySize
		// when mNumCores is an exact power of 2: mNumCores = 2^n where n is an integer
		// mSubArraySizeRight = mSubArraySizeRight for merge level 1
		// mSubArraySizeRight += mSubArraySizeRight*(mRemainingParts+1)%2 for other merge levels
		// ie for mNumCores == 7
		// mSubArraySizeRight = arraySize/7 for merge level 1
		// mSubArraySizeRight = arraySize/7 for merge level 2
		// mSubArraySizeRight = (2 + 1)*arraySize/7 = 3*arraySize/7 for merge level 3
		else
		{
			int p, r, q; // p - left bound of subArray to merge, r - right bound of subArray to merge
			
			// setting p and r for all but rightmost part
			int localMergeCount;
			
			synchronized (this)
			{
				mMergeCount++;
				localMergeCount = mMergeCount;
			}
			
			p = (localMergeCount - 1) * 2* mSubArraySizeFactor*size/mNumCores;
			q = (2*localMergeCount - 1) * mSubArraySizeFactor*size/mNumCores-1;
			
			if (localMergeCount % (mNumRemainingParts /2 + mNumRemainingParts%2) != 0)
 				r = localMergeCount * 2*mSubArraySizeFactor*size/mNumCores - 1;
			else
				r = mSortArray.size()-1; 
			
			Sort.mergeWithoutSentinel(mSortArray, p, q, r);
		}

		mLatch.countDown();

	}
}


class MultithreadedMergeSortOptimized extends MultithreadedMergeSort implements Runnable
{

	public static final int MERGE_CUT_OFF = 40;

	// this method is designed to efficiently run MergeSort on multicore processor.
	public void run()
	{
		int localCount; // local variable required to synchronize execution
		int size = mSortArray.size();
		synchronized (this) // each time the thread is run increase mCounter
		{
			mRunCount++;
			localCount = mRunCount; // mCount keeps track of how many times run() was executed. As the problem is run on different
							    // threads mCount is changed each time new thread is called. localCount
			// stores the value of mCount in the beginning of the run() to be used throughout the call

		}
		// The problem is initially split into n
		// parts corresponding to the number of
		// cores. Each call will run on a separate processor
		// The run on each core returns sorted subArray of the size: arraySize/numCores
		// for example if numCores == 4 the problem of sorting is split into 4 equal parts:
		// sortArray: {{_ _ _ _}{_ _ _ _}{_ _ _ _}{_ _ _ _}}
		// each call of Sort.mergeSort is run on a separate thread and produces a sorted SubArray {_ _ _ _} within mSortArray
		// localCount keeps track of how many times the run() was executed
		// First mNumCores runs (localCount <= mNumCores) result in mNumCores sorted subArrays
		if (localCount <= mNumCores) 
		{
			// Params:
			// param1 - pointer to entire array to be sorted
			// @param2 - index of first element in sortSubArray
			// @param3 - index of last element in sortSubArray
			Sort.mergeSortOptimized(mSortArray, (localCount - 1) * size / mNumCores,
					localCount * size / mNumCores - 1, MERGE_CUT_OFF); 

		}
		// now localCount > mNumCores. It is time to merge the sorted subArrays into sortedArray
		// Here are a few examples of how the merge will be done depending on the mNumCores
		// mNumCores == 2 
		// sortedArray {{_ _ _ _}{_ _ _ _}} merge=> {_ _ _ _ _ _ _ _ _}
		// mNumCores ==3
		// sortedArray {{_ _ _ _}{_ _ _ _}{_ _ _ _}} merge=> {{_ _ _ _ _ _ _ _ _} {_ _ _ _}} synchronize
		// {{_ _ _ _ _ _ _ _ _}, {_ _ _ _}} merge= > {_ _ _ _ _ _ _ _ _ _ _ _ _}
		// mNumCores == 7
		// sortedArray : {{_ _ _ _}{_ _ _ _}{_ _ _ _}{_ _ _ _}{_ _ _ _}{_ _ _ _}{_ _ _ _}} merge =>      merge Level 1
		// => {{_ _ _ _ _ _ _ _ _}{_ _ _ _ _ _ _ _ _}{_ _ _ _ _ _ _ _ _}{_ _ _ _}} synchronize =>        merge Level 2
		// => {{_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _}{_ _ _ _ _ _ _ _ _ _ _ _ _}} synchronize =>          merge Level 3
		// => {_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _}
		//****************************************************************************************
		// mRemainingParts keeps track of how many parts need to be merged on current merge level
		// mRemainingParts = mNumCores at merge level 1
		// mRemainingparts = mRemaingParts/2 + mRemainingParts%2; for each new merge level
		// ie for mNumCores == 7
		// mRemainingParts = 7 on first merge Level, mRemainingParts = 4 for second merge Level,
		// mRemainingParts = 2 for 3rd merge Level
		// *********************************************************************
		// mSubArraySize keeps track of subArraySizes(except the rightmost one which can be smaller)
		// mSubArraySize = arraySize/mNumCores for merge Level 1
		// mSubArraySize = mSubArraySize * 2^(merge Level - 1)
		// ie for mNumCores == 7
		// mSubArraySize = arraySize/7 for merge level 1
		// mSubArraySize = 2*mSubArraySize = 2*arraySize/7 for merge level 2
		// mSubArraySize = 4*arraySize/7 for merge level 3
		// **********************************************************************
		// mSubArraySizeRight keeps track of the size of rightmost subArray for each merge level
		// The rightmost subArray is always of the same size as mSubArraySize or smaller. mSubArraySizeRight = mSubArraySize
		// when mNumCores is an exact power of 2: mNumCores = 2^n where n is an integer
		// mSubArraySizeRight = mSubArraySizeRight for merge level 1
		// mSubArraySizeRight += mSubArraySizeRight*(mRemainingParts+1)%2 for other merge levels
		// ie for mNumCores == 7
		// mSubArraySizeRight = arraySize/7 for merge level 1
		// mSubArraySizeRight = arraySize/7 for merge level 2
		// mSubArraySizeRight = (2 + 1)*arraySize/7 = 3*arraySize/7 for merge level 3
		else
		{
			int p, r, q; // p - left bound of subArray to merge, r - right bound of subArray to merge
			
			// setting p and r for all but rightmost part
			int localMergeCount;
			
			synchronized (this)
			{
				mMergeCount++;
				localMergeCount = mMergeCount;
			}
			
			p = (localMergeCount - 1) * 2* mSubArraySizeFactor*size/mNumCores;
			q = (2*localMergeCount - 1) * mSubArraySizeFactor*size/mNumCores-1;
			
			if (localMergeCount % (mNumRemainingParts /2 + mNumRemainingParts%2) != 0)
 				r = localMergeCount * 2*mSubArraySizeFactor*size/mNumCores - 1;
			else
				r = mSortArray.size()-1; 
			
			Sort.merge(mSortArray, p, q, r);
		}

		mLatch.countDown();

	}
}


class  MultithreadedMergeSortConcrete extends MultithreadedMergeSort implements Runnable
{

	/*private List<Integer> mSortArray; // the array to be sorted
	private int mNumCores; // number of processors on which the job runs
	private int mNumRemainingParts; // parameter that tells how many parts are left to merge after the initial MergeSort is run 
	private int mSubArraySizeFactor; // size of all the subArray lists except the rightmost one when the merge is run
	private int mMergeCount; // counter for number of times Merge was run on each merge level
	
	
	private int mRunCount; // counter for number of times the run was called
	private CountDownLatch mLatch;
	*/
	
	public MultithreadedMergeSortConcrete()
	{
		
	}

	
	
	

	// this method is designed to efficiently run MergeSort on multicore processor.
	public void run()
	{
		int localCount; // local variable required to synchronize execution
		int size = mSortArray.size();
		synchronized (this) // each time the thread is run increase mCounter
		{
			mRunCount++;
			localCount = mRunCount; // mCount keeps track of how many times run() was executed. As the problem is run on different
							    // threads mCount is changed each time new thread is called. localCount
			// stores the value of mCount in the beginning of the run() to be used throughout the call

		}
		// The problem is initially split into n
		// parts corresponding to the number of
		// cores. Each call will run on a separate processor
		// The run on each core returns sorted subArray of the size: arraySize/numCores
		// for example if numCores == 4 the problem of sorting is split into 4 equal parts:
		// sortArray: {{_ _ _ _}{_ _ _ _}{_ _ _ _}{_ _ _ _}}
		// each call of Sort.mergeSort is run on a separate thread and produces a sorted SubArray {_ _ _ _} within mSortArray
		// localCount keeps track of how many times the run() was executed
		// First mNumCores runs (localCount <= mNumCores) result in mNumCores sorted subArrays
		if (localCount <= mNumCores) 
		{
			// Params:
			// param1 - pointer to entire array to be sorted
			// @param2 - index of first element in sortSubArray
			// @param3 - index of last element in sortSubArray
			Sort.mergeSort(mSortArray, (localCount - 1) * size / mNumCores,
					localCount * size / mNumCores - 1); 

		}
		// now localCount > mNumCores. It is time to merge the sorted subArrays into sortedArray
		// Here are a few examples of how the merge will be done depending on the mNumCores
		// mNumCores == 2 
		// sortedArray {{_ _ _ _}{_ _ _ _}} merge=> {_ _ _ _ _ _ _ _ _}
		// mNumCores ==3
		// sortedArray {{_ _ _ _}{_ _ _ _}{_ _ _ _}} merge=> {{_ _ _ _ _ _ _ _ _} {_ _ _ _}} synchronize
		// {{_ _ _ _ _ _ _ _ _}, {_ _ _ _}} merge= > {_ _ _ _ _ _ _ _ _ _ _ _ _}
		// mNumCores == 7
		// sortedArray : {{_ _ _ _}{_ _ _ _}{_ _ _ _}{_ _ _ _}{_ _ _ _}{_ _ _ _}{_ _ _ _}} merge =>      merge Level 1
		// => {{_ _ _ _ _ _ _ _ _}{_ _ _ _ _ _ _ _ _}{_ _ _ _ _ _ _ _ _}{_ _ _ _}} synchronize =>        merge Level 2
		// => {{_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _}{_ _ _ _ _ _ _ _ _ _ _ _ _}} synchronize =>          merge Level 3
		// => {_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _}
		//****************************************************************************************
		// mRemainingParts keeps track of how many parts need to be merged on current merge level
		// mRemainingParts = mNumCores at merge level 1
		// mRemainingparts = mRemaingParts/2 + mRemainingParts%2; for each new merge level
		// ie for mNumCores == 7
		// mRemainingParts = 7 on first merge Level, mRemainingParts = 4 for second merge Level,
		// mRemainingParts = 2 for 3rd merge Level
		// *********************************************************************
		// mSubArraySize keeps track of subArraySizes(except the rightmost one which can be smaller)
		// mSubArraySize = arraySize/mNumCores for merge Level 1
		// mSubArraySize = mSubArraySize * 2^(merge Level - 1)
		// ie for mNumCores == 7
		// mSubArraySize = arraySize/7 for merge level 1
		// mSubArraySize = 2*mSubArraySize = 2*arraySize/7 for merge level 2
		// mSubArraySize = 4*arraySize/7 for merge level 3
		// **********************************************************************
		// mSubArraySizeRight keeps track of the size of rightmost subArray for each merge level
		// The rightmost subArray is always of the same size as mSubArraySize or smaller. mSubArraySizeRight = mSubArraySize
		// when mNumCores is an exact power of 2: mNumCores = 2^n where n is an integer
		// mSubArraySizeRight = mSubArraySizeRight for merge level 1
		// mSubArraySizeRight += mSubArraySizeRight*(mRemainingParts+1)%2 for other merge levels
		// ie for mNumCores == 7
		// mSubArraySizeRight = arraySize/7 for merge level 1
		// mSubArraySizeRight = arraySize/7 for merge level 2
		// mSubArraySizeRight = (2 + 1)*arraySize/7 = 3*arraySize/7 for merge level 3
		else
		{
			int p, r, q; // p - left bound of subArray to merge, r - right bound of subArray to merge
			
			// setting p and r for all but rightmost part
			int localMergeCount;
			
			synchronized (this)
			{
				mMergeCount++;
				localMergeCount = mMergeCount;
			}
			
			p = (localMergeCount - 1) * 2* mSubArraySizeFactor*size/mNumCores;
			q = (2*localMergeCount - 1) * mSubArraySizeFactor*size/mNumCores-1;
			
			if (localMergeCount % (mNumRemainingParts /2 + mNumRemainingParts%2) != 0)
 				r = localMergeCount * 2*mSubArraySizeFactor*size/mNumCores - 1;
			else
				r = mSortArray.size()-1; 
			
			Sort.merge(mSortArray, p, q, r);
		}

		mLatch.countDown();

	}
}
