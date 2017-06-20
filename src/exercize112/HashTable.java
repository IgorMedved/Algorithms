package exercize112;


/*
 * 11.2-1 Suppose we use a hash function h to hash n distinct keys into an array T of length m. Assuming simple uniform hashing,
 *  what is the expected number of collisions? More precisely, what is the expected cardinality of k,l k != l and h(k) = h(l)?
 *  
 *  Solution: Expected h(k) = h(l) when k!=l is 1/m. Number of collisions (n-1)/m
 * 11.2-2 Demonstrate what happens when we insert the keys 5;28;19;15;20;33;12;17;10 into a hash table with collisions resolved
 *  by chaining. Let the table have 9 slots, and let the hash function be h.k/ D k mod 9
 *    
 *  Solution    kmod9 
 *  			0
 *  			1 -10-19- 28
 *  			2 -20
 *  			3 -12
 *  			4
 *  			5 -33-5
 *  			6 -15
 *  			7
 *  			8 -17
 *  			
 *  
 *  			 
 *  11.2-3 Professor Marley hypothesizes that he can obtain substantial performance gains by modifying the chaining scheme
 *   to keep each list in sorted order. How does the professor’s modiﬁcation affect the running time for successful searches,
 *    unsuccessful searches, insertions, and deletions?
 *    
 *    Solution: does not matter for successful searches,
 *    			decrease running time of unsuccessful searches by half
 *    			increase insertion time from O(1) to O(N)
 *    			reduce deletion time by half on average
 *    
 * 11.2-4 Suggest how to allocate and deallocate storage for elements within the hash table itself by linking all unused slots 
 * into a free list. Assume that one slot can store a ﬂag and either one element plus a pointer or two pointers. All dictionary
 * and free-list operations should run in O.1/expected time. Does the free list need to be doubly linked, or does a singly
 * linked free list sufﬁce?
11.2-5 Suppose that we are storing a set of n keys into a hash table of size m. Show that if thekeysaredrawnfromauniverse U withjUj > nm,thenU hasasubset ofsize n consisting of keys that all hash to the same slot, so that the worst-case searching time for hashing with chaining is ‚.n/.
11.2-6 Supposewehavestored nkeysinahashtableofsize m,withcollisions resolved by chaining, and that we know the length of each chain, including the length L of the longest chain. Describe a procedure that selects a key uniformly at random from among the keys in the hash table and returns it in expected time O.L.1C1=˛//.
 */
public class HashTable {

}
