package exercize102_linked_list;

import java.util.LinkedList;

/*
 * 10.2-1 Can you implement the dynamic-set operation INSERT on a singly linked list in O(1) time? How about DELETE?
 * Solution: Insert can be O(1) operation unless the list is sorted then it is O(N) operation. Delete is always O(N) operation in the worst case
 * 
 * 
 * 10.2-2 Implement a stack using a singly linked list L. The operations PUSH and POP should still take O.1/ time.
 * Solution: push adds another value at the head, pop returns the value from the head and re-attaches head to second element
 * 
 * 
 * 10.2-3 Implement a queue by a singly linked list L. The operations ENQUEUE and DEQUEUE should still take O.1/time.
 * Solution: dequeue points at the head, queue points at the last element
 * 
 * 
 * 10.2-4 As written, each loop iteration in the LIST-SEARCH0 procedure requires two tests: one for x != L:nil and one
 *  for x:key != k. Show how to eliminate the test for x ¤ L:nil in each iteration. 
 *  
 *  LIST-SEARCH' (L,k)
 *   1 x = L.nil.next 
 *   2 while x != L.nil and x.key != k 
 *   3 		x = x.next 
 *   4 return x

 * Solution: 1) L.nil.key = k;
 * 			 2) after loop check if x == L.nil (only 1 check)
 *  
 *  
 *  
 *  
 *  10.2-5 Implement the dictionary operations INSERT,DELETE, and SEARCH using singly linked, circular lists.
 *   What are the running times of your procedures?
 *   
 *   Solution: Singly a) Insert - O(1), delete O(N), search O(N)
 *   		   circular a) Insert O(1), delete O(N), search O(N)
 *
 *
 *  10.2-6 The dynamic-set operation UNION takes two disjoint sets S1 and S2 as input, and it returns a set S D S1
 *   [ S2 consisting of all the elements of S1 and S2. The sets S1 and S2 areusually destroyed bytheoperation. 
 *   Showhowtosupport UNION in O.1/ time using a suitable list data structure.
 *   
 *   Solution: Singly linked list with Head and Nil nodes should do the job as well as circular lists
 *   
 *
 *  
 *  10.2-7 Give a O(N)-time nonrecursive procedure that reverses a singly linked list of n elements.
 *   The procedure should use no more than constant storage beyond that needed for the list itself.
 *   
 *   public void reverse()
 *   {
 *   	LinkedList <T> current = HEAD.next;
 *   	LinkedList <T> next = current.next;
 *   	current.next = null;
 *   // optional  NIL.next = HEAD.next;
 *   	while (next!= null)
 *   	{
 *   		current = next;
 *   		next = next.next;
 *   		current.next = head.next;
 *   		head.next = current;
 *   	}
 *   
 *   
 * 10.2-8 ? Explain how to implement doubly linked lists using only one pointer value x:np per item instead of the usual
 * two(next and pre). Assume that all pointer values can be interpreted as k-bit integers, and deﬁne x:np to be 
 *  x.np = x.next XOR x.pre, the k-bit “exclusive-or” of x:next and x:pre. (The valueNIL is represented by 0.)
 *   Be sure to describe what information you need to access the head of the list. 
 *   Show how to implement the SEARCH,INSERT, and DELETE operations on such a list. Also show how to reverse such 
 *   a list in O.1/ time.
 *   
 *   ________
 *   |Tail1 |
 *   |______|
 *    ___|___     ___________     _________     _________     __________
 *   |		|     |         |     |        |<---|        |<---|tail2   |
 *   |___|__|     |____|____|     |___|____|np4 |________|    |________|
 *       |			   |              |             
 *      np1           np2			 np3		   
 *
 *
 *   reverse: Tail1.np.np (np1) = Tail1.np.np(np1) Xor (&Tail1) (node1.prev) = &node2
 *   		  tail2.np.np (np4) = tail2.np.np (np4) Xor (@tail2) (nodelast.prev) = np4 pointing to nonexisting address
 *   
 *   search:
 *   prev = &Tail1;
 *   cur = Tail1.np
 *   next = prev Xor cur.np
 *   
 *   
 *   while (cur.val!= value && cur.np!= prev)
 *   {
 *      prev = &cur;
 *      cur = next;
 *      next = prev Xor cur.np
 *    }
 *    if (cur.val!= value)
 *    return null;
 *    
 *    delete:
 *    		search for delete element
 *    		del = element to be deleted
 *          prev = element before del
 *          next = element after del
 *          
 *          prev.np =  (prev.np XOR &del) XOR &next // works even if prev = Tail1 as the expression in parenthethis is 0
 *          next.np = (&prev XOR next.np) XOR &del // works even if next = tail2 as the expression in parenthethis is 0
 *          del.np = null;
 *          delete del;
 * 
 *     insert
 *          ins = element to be inserted
 *          prev = element before insertion point
 *          next = element after insertion point
 *          
 *          prev.np = (prev.prev = prev.np XOR &next) XOR &ins
 *          next.np = (next.np XOR &prev) XOR &ins
 *          ins.np = &prev XOR next.np XOR &ins
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */

public class App {

}
