package utility.sort.helper;

public class Node {
	
	private double mValue;
	private Node mNext;
	
	
	public Node(double value, Node next) {
		
		this.mValue = value;
		this.mNext = next;
	}
	
	public Node ()
	{
		this (-1);
	}
	
	public Node(double value)
	{
		this (value, null);
	}
	
	public double getValue() {
		return mValue;
	}
	public void setValue(double value) {
		mValue = value;
	}
	public Node getNext() {
		return mNext;
	}
	public void setNext(Node next) {
		mNext = next;
	}
	
	

}
