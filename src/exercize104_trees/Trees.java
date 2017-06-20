package exercize104_trees;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Trees {
	
	public static void main(String[] args)
	{
		
	}
	
	// 10.4.2 print binary tree recursively depth first
	// Algorithm works like this:
	// It launches when current = root of the tree
	// prints its value
	// a)Try going left all the way printing each new node value until current.left() == null
	// b)If can't go left try going right by one 
	//		c) if can go right print new node value. and retry a)
	//		d) if can't go right return to the last level where attempt wasnt made to go right
	public static void printRecursiveTreeRightChildDepth(NodeRightChild current)
	{
		System.out.println(current.getValue());
		if (current.left()!= null)
			printRecursiveTreeRightChildDepth(current.left());
	    if (current.right()!=null)
	    	printRecursiveTreeRightChildDepth(current.right());
	}
	
	// 10.4.3a print binary tree non-recursively depth first
	// non recursive procedure is very similar to a recursive algorithm (10.4.2). The only
	// difference is that it uses explicit stack that contains Node values on left and right of
	// the current node instead of relying on call stack
	public static void printNonRecursiveTreeRightChildDepth(NodeRightChild root)
	{
		NodeRightChild current;
		Stack<NodeRightChild> stack = new Stack<>();
		stack.add(root);
		while (!stack.isEmpty())
		{
			current = stack.pop();
			System.out.println(current.getValue());
			if (current.right()!= null)
				stack.add(current.right());
			if (current.left()!=null)
				stack.add(current.left());
		}
	}
	
	// 10.4.3b print binary tree non-recursively breadth first
	public static void printNonRecursiveTreeRightChildBreadth(NodeRightChild root)
	{
		NodeRightChild current;
		Queue<NodeRightChild> queue = new LinkedList<>();
		queue.add(root);
		while(!queue.isEmpty())
		{
			current = queue.remove();
			System.out.println(current.getValue());
			if (current.left() != null)
				queue.add(current.left());
			if (current.right()!= null)
				queue.add(current.right());
		}
	}
	
	// 10.4.4a print RightSibling tree depth first
	public static void printNonRecuriveTreeRightSiblingDepth(NodeRightSibling root)
	{
		NodeRightSibling current;
		Stack<Queue<NodeRightSibling>> stack = new Stack<>(); 
		Queue<NodeRightSibling> currentQueue = new LinkedList<>();
		currentQueue.add(root);
		stack.add(currentQueue);
		while(!stack.isEmpty())
		{
			currentQueue = stack.pop();
			while (!currentQueue.isEmpty())
			{
				
				current = currentQueue.remove();
				System.out.println(current.getValue());
				
				Queue<NodeRightSibling> tempQueue = new LinkedList<>();
				if (current.parent() != null && current.parent().left() == current)
				{
					NodeRightSibling temp = current;
					while (temp.right()!= null)
					{
						temp = temp.right();
						tempQueue.add(temp);
					}
					if (!tempQueue.isEmpty())
					{
						stack.add(tempQueue);
						tempQueue = new LinkedList<>();
					}
				}
				if (current.left()!= null)
				{
					tempQueue.add(current.left());
					stack.add(tempQueue);
				}
				
			}
			
		}
	}
	
	// 10.4.4a print RightSibling tree breadth first
	public static void printNonRecursiveRightSiblingTreeBreadth(NodeRightSibling root)
	{
		Queue<NodeRightSibling> queue = new LinkedList<>();
		queue.add(root);
		NodeRightSibling current;
		while (!queue.isEmpty())
		{
			current = queue.remove();
			System.out.println(current.getValue());
			if (current.parent()!=null && current.parent().left() == current)
			{
				NodeRightSibling temp = current;
				while (temp.right()!=null)
				{
					temp =temp.right();
					queue.add(temp);
				}
			}
			if (current.left()!= null)
			{
				queue.add(current.left());
			}
			
		}
	}
	
	/*
	 * 10.4-5 ? Write an O(n)-time nonrecursive procedure that, given an n-node
	 *  binary tree, prints out the key of each node. Use no more than constant
	 *  extra space outside of the tree itself and do not modify the tree, even 
	 *  temporarily, during the procedure.

	 */
		
	public static void printNonRecursiveBinaryO1Memory (NodeRightChild root)
	{
		NodeRightChild current = root;
		NodeRightChild prev = null;
		while (current!= null) // while did not get to root.parent();
		{
			if (current.parent() == prev) // if started or came to the current node from parent
			{
				System.out.println(current.getValue());
				if (current.left()!=null)// if can go left
				{
					prev = current;
					current = current.left();
					continue; // go left and skip the rest of the loop
				}
				
			}
			if (prev != current.right() && current.right()!= null) // if did not come from the right 
																	//(in other words if came from parent or came from left)
																	// && can go right
			{
				prev = current;
				current = current.right(); // go right
			}
			else // if came from right or can't go right
			{
				prev = current;
				current = current.parent();// go to parent
			}
		}
	}
	
	/*
	 * 10.4-6 ? The left-child, right-sibling representation of an arbitrary rooted tree uses three pointers in each node:
	 *  left-child, right-sibling, and parent. From any node, its parent can be reached and identiﬁed in constant time and all
	 *  its children can be reached and identiﬁed in time linear in the number of children. Show how to use only two pointers
	 *  and one boolean value in each node so that the parent of a node or all of its children can be reached and identiﬁed in 
	 *  time linear in the number of children.
	 *  
	 *  Solution: One pointer points to the left. Second pointer pointer points to the right if right sibling is available and
	 *  to parent if no more right values exist
	 *  boolean hasRightValue controls whether the pointer is to the right or to parent example
	 *      O
	 *      |     \
	 *      O - O - O
	 *      	||
	 *      	O

	 */
	
	
	public static void createExampleTree()
	{
		
	}
}


class NodeRightChild<T>
{
	private NodeRightChild parent;
	private NodeRightChild left;
	private NodeRightChild right;
	private T value;
	
	public NodeRightChild(T value)
	{
		this.value = value;
	}
	
	public NodeRightChild parent()
	{
		return parent;
	}
	
	public final NodeRightChild left()
	{
		return left;
	}
	
	public NodeRightChild right()
	{
		return right;
	}
	
	public T getValue()
	{
		return value;
	}
	
	
}

class NodeRightSibling<T>
{
	private NodeRightSibling parent;
	private NodeRightSibling left;
	private NodeRightSibling right;
	private T value;
	
	public NodeRightSibling(T value)
	{
		this.value = value;
	}
	
	public NodeRightSibling parent()
	{
		return parent;
	}
	
	public final NodeRightSibling left()
	{
		return left;
	}
	
	public NodeRightSibling right()
	{
		return right;
	}
	
	public T getValue ()
	{
		return value;
	}
}