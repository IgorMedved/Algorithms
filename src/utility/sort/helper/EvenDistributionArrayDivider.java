package utility.sort.helper;

public class EvenDistributionArrayDivider extends ArrayDivider {

	
	int wholeStep;
	int fractionalStep;
	int currentStep;
	int fraction;
	int base;
	
	int startStep;
	int endStep;
	
	public EvenDistributionArrayDivider(int size, int minBlockSize) {
		super (size, minBlockSize);
		base = floor2()/minBlockSize;
		wholeStep = getSize()/base;
		fractionalStep = getSize()%base;
	}

	@Override
	public void nextStep() {
		startStep = currentStep;
		currentStep += wholeStep;
		fraction += fractionalStep;
		if (fraction >= base)
		{
			fraction -=base;
			currentStep++;
		}
		
	}
	
	public int getStart()
	{
		return startStep;
	}
	
	public int getEnd()
	{
		return currentStep-1;
	}

	public boolean finished ()
	{
		return currentStep >= getSize();
	}
	
	@Override
	public int length() {
		
		return currentStep - startStep;
	}
	
	

	@Override
	public void begin() {
		currentStep = fraction = 0;
	}

	@Override
	public boolean nextLevel() {
		wholeStep += wholeStep;
		fractionalStep+=fractionalStep;
		if (fractionalStep >= base)
		{
			fractionalStep-=base;
			wholeStep ++;
		}
		
		return wholeStep < getSize();
		
	}

}
