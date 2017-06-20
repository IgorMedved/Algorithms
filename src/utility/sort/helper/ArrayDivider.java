package utility.sort.helper;

public abstract class ArrayDivider {
	
	private int size;
	protected int minBlockSize;
	
	
	
	public ArrayDivider (int size, int minBlockSize)
	{
		if (size <1)
			throw new RuntimeException("The array must have at least one element to be divded into parts");
		if (minBlockSize > size)
		    throw new RuntimeException( "Block length should be less or equal to size" );
		this.size = size;
		this.minBlockSize = minBlockSize;
		begin();
		
	}
	
	public abstract void nextStep();
	public abstract int length();
	public abstract void begin();
	public abstract boolean nextLevel();
	
	
	public int getSize()
	{
		return size;
	}
	
	public static final int floor2(int size)
	{
		int floor = 1;
		while ((size/=2) > 0)
		{
			floor*=2;
		}
		return floor;
	}
	
	public final int  floor2 ()
	{
		return floor2(getSize());
	}
	

}
