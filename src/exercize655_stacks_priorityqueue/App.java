package exercize655_stacks_priorityqueue;

import utility.data_structures.Queue;
import utility.data_structures.Stack;



public class App
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Stack myStack = new Stack();
		
		for (int i = 0; i < 10; i++)
			myStack.push();
		
		myStack.printStackHeap();
		
		for (int i = 0; i < 11; i++)
		{
			int value = myStack.pop();
			if (value !=Stack.INVALID_KEY)
				System.out.println("The extracted value is " + value);
			else
				System.out.println("The stack is empty");
			
		}
		
		Queue queue = new Queue();
		
		for (int i = 0; i < 10; i++)
			queue.queue();
		
		for (int i = 0; i < 12; i++)
		{
			int value = queue.extract();
			if(value!=Queue.INVALID_KEY)
				System.out.println("The extracted value is " + value);
			else
				System.out.println("The Queue is empty");
		}
			
			queue.printQueueHeap();

	}

}
