package exercize121_search_trees;
/*
 * 12.1-1 For the set of {1;4;5;10;16;17;21} of keys, draw binary search trees of heights 2, 3, 4, 5, and 6.
 * a) height 2:
 *                 10
 *               4     17
 *              1 5  16  21
 *  b) height 3
 *  			17
 *  		   5  21
 *           4  10
 *          1     16
 *   e) height 6:
 *           1
 *            4
 *              5
 *               10
 *                 16
 *                   17
 *                     21
 *           
 * 12.1-2 What is the difference between the binary-search-tree property and the min-heap property
 * (see page 153)? Can the min-heap property be used to print out the keys of an n-node tree in sorted
 *  order in O(n) time? Show how, or explain why not.
 *  
 * Solution:
 * a) The difference between search tree and min-heap is that at each parent min heap is guaranteed to have the value that is
 * less than or equal to all the children. The search tree is guaranteed to have smaller or equal children on the left branch
 * 
 * b) It takes nlnn time to bring the minHeap in order thus it is impossible tor print the sorted tree in O(n) time
 * 
 * 
 * 12.1-3 Give a nonrecursive algorithm that performs an inorder tree walk. (Hint: An easy solution uses a stack as
 * an auxiliary data structure. A more complicated, but elegant, solution uses no stack but assumes that we can test
 * two pointers for equality.)
 * 
 * See exercises 104_trees
 * 
12.1-4 Give recursive algorithms that perform preorder and postorder tree walks in ‚.n/ time on a tree of n nodes.
12.1-5 Argue that since sorting n elements takes .nlgn/ time in the worst case in the comparison model, any comparison-based algorithm for constructing a binary search tree from an arbitrary list of n elements takes .nlgn/ time in the worst case.

 * 
 */

public class SearchTrees {

}
