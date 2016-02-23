package exercize411_max_subarray;

// bean for remembering low, high indexes of subArray and the sum of elements from array.get(low) to array.get(high
class MaximumSubarrayBean
{
	
	
	private int mLow;
	private int mHigh;
	private int mSum;
	
	public MaximumSubarrayBean (int low,int high, int sum)
	{
		mLow = low;
		mHigh = high;
		mSum = sum;
	}
	
	public void setAll (int low,int high, int sum)
	{
		mLow = low;
		mHigh = high;
		mSum = sum;
	}
	
	public void setLow (int low)
	{
		mLow = low;
	}
	
	public void setHigh(int high)
	{
		mHigh = high;
	}
	
	public void setSum (int sum)
	{
		mSum = sum;
	}
	
	public int getLow ()
	{
		return mLow;
	}
	
	public int getHigh ()
	{
		return mHigh;
	}
	
	public int getSum()
	{
		return mSum;
	}
	
}