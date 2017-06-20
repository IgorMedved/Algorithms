package exercize101_stack_queue;
/*
 * 10.1-2 Explain how to implement two stacks in one array AŒ1::n in such a way that neither stack overﬂows
 *  unless the total number of elements in both stacks together is n. The PUSH and POP operations should run in O.1/ time.
 *  
 *  Solution: have one pointer head point at -1, another at n. Increment 1st head during pushes, decrement 2nd head during pushes
 *
 *
 * 10.1-4 Rewrite ENQUEUE and DEQUEUE to detect underﬂow and overﬂow of a queue.
 * public static enqueue(Object obj)
 * {
 * 		q[tail] = object;
 * 		tail ++;
 * 		if (tail == q.size())
 * 			tail = 0;
 * 		if (tail== head)
 * 			throw RuntimeError("The queue has overflown");
 * }
 * 
 * public static Object dequeue()
 * {
 * 		if (!empty())
 * 		{
 * 			head++;
 * 			if (head == q.size())
 * 				head++;
 *   		return q[head];
 *   	}
 *   	else
 *   		throw new RuntimeError("the queue has underflown");
 *  }
 *      
 *
 *10.1-6 Show how to implement aqueue using two stacks. Analyze the running time of the queue operations.
 *
 * QueueWithStacks
 * {
 * 		Stack queue_;
 * 		Stack dequeue_;
 * 
 * 		public void queue(Object obj)
 * 		{
 * 			while(!dequeue.empty())
 * 			{
 * 				queue_.push(dequeue_.pop());
 * 			}
 * 			queue_.push(obj);
 * 		}
 * 		
 * 
 * 		public Object dequeue()
 * 		{
 * 			while(!queue.empty())
 * 				dequeue_.push(queue_.pop());
 * 			return dequeue_.pop();
 * 		}
 * }
 * 
 * StackWithQueues
 * {
 * 		Queue pop_;
 * 		Queue push_;
 * 	
 * 
 * 		public void push (Object obj)
 * 		{
 * 			while(!pop_.empty())
 * 				push_.queue(pop_.pop());
 * 			push_.queue(obj);
 * 		}
 * 
 * 		public Object pop ()
 * 		{
 * 			while (!push_.empty()
 * 				pop_.queue(push_.dequeue());
 * 			pop_.dequeue();
 * 		}
 * 
10.1-7 Show how to implement astack using twoqueues. Analyze the running time of the stack operations.

 */



public class App {

}
