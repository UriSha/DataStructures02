/**
 * FibonacciHeap
 *
 * An implementation of fibonacci heap over non-negative integers.
 */
public class FibonacciHeap
{
    private HeapNode min;
    private int size;
    private int numOfMarked;
    private int numOfTrees;
    private int[] counterRep = new int[42];
    private static int totalLinks;
    private static int totalCuts;
    /**
     * public boolean empty()
     *
     * precondition: none
     *
     * The method returns true if and only if the heap
     * is empty.
     *
     */
    public boolean empty()
    {
        return this.min == null ;
    }

    /**
     * public HeapNode insert(int key)
     *
     * Creates a node (of type HeapNode) which contains the given key, and inserts it into the heap.
     */
    public HeapNode insert(int key)
    {
        // create new HeapNode //TODO is there suppose to be an info input?
        String temp = "tempString";
        HeapNode toBeInserted = new HeapNode(temp, key);

        // insert the new HeapNode 'behind' the minNode
        HeapNode minPrev = this.min.prev;
        minPrev.next = toBeInserted;
        toBeInserted.prev = minPrev;
        this.min.prev = toBeInserted;
        toBeInserted.next = this.min;


        // update the relevant fields
        this.size++;
        this.numOfTrees++;
        this.counterRep[0]++;
        if (toBeInserted.key < this.min.key){ //TODO what about duplicates?
            this.min = toBeInserted;
        }
        return toBeInserted;
    }

    /**
     * public void deleteMin()
     *
     * Delete the node containing the minimum key.
     *
     */
    public void deleteMin()
    {
        return; // should be replaced by student code

    }

    /**
     * public HeapNode findMin()
     *
     * Return the node of the heap whose key is minimal.
     *
     */
    public HeapNode findMin()
    {
        return this.min; // if the Heap is empty will return null
    }

    /**
     * public void meld (FibonacciHeap heap2)
     *
     * Meld the heap with heap2
     *
     */
    public void meld (FibonacciHeap heap2)
    {
        // if one of the heaps is empty
        if (heap2.empty()){
            return;
        }
        else if (this.empty()){
            this.min = heap2.min;
            this.size = heap2.size;
            this.numOfMarked = heap2.numOfMarked;
            this.numOfTrees = heap2.numOfTrees;
            this.counterRep = heap2.counterRep;
            return;
        }


        //link the two lists together
        HeapNode minPrev = this.min.prev;
        minPrev.next = heap2.min.next;
        heap2.min.next.prev = minPrev;
        this.min.prev = heap2.min;
        heap2.min.next = this.min;

        // update the fields
        if (heap2.min.key < this.min.key){
            this.min = heap2.min;
        }

        this.size += heap2.size;
        this.numOfTrees += heap2.numOfTrees;
        this.numOfMarked += heap2.numOfMarked;
        for (int i=0 ; i< this.counterRep.length ; i++){ //TODO is this counts as O(1)?
            this.counterRep[i] += heap2.counterRep[i];
        }
    }

    /**
     * public int size()
     *
     * Return the number of elements in the heap
     *
     */
    public int size()
    {
        return this.size; // should be replaced by student code
    }

    /**
     * public int[] countersRep()
     *
     * Return a counters array, where the value of the i-th entry is the number of trees of order i in the heap.
     *
     */
    public int[] countersRep()
    {
//        int[] arr = new int[42];
//        return arr; //	 to be replaced by student code
        return this.counterRep;
    }

    /**
     * public void arrayToHeap()
     *
     * Insert the array to the heap. Delete previous elemnts in the heap.
     *
     */
    public void arrayToHeap(int[] array)
    {
        // restart all the Heap fields
        this.min = null;
        this.size = 0;
        this.numOfTrees = 0;
        this.numOfMarked = 0;
        this.counterRep = new int[42]; //TODO is this counts as O(1)?

        // for every int in the array, make it a new HeapNode and insert it to the heap
        for (int key : array){
            this.insert(key);
        }

        return; //	 to be replaced by student code
    }

    /**
     * public void delete(HeapNode x)
     *
     * Deletes the node x from the heap.
     *
     */
    public void delete(HeapNode x)
    {
        return; // should be replaced by student code
    }

    /**
     * public void decreaseKey(HeapNode x, int delta)
     *
     * The function decreases the key of the node x by delta. The structure of the heap should be updated
     * to reflect this chage (for example, the cascading cuts procedure should be applied if needed).
     */
    public void decreaseKey(HeapNode x, int delta)
    {
        return; // should be replaced by student code
    }

    /**
     * public int potential()
     *
     * This function returns the current potential of the heap, which is:
     * Potential = #trees + 2*#marked
     * The potential equals to the number of trees in the heap plus twice the number of marked nodes in the heap.
     */
    public int potential()
    {
        return this.numOfTrees + 2*this.numOfMarked;
    }

    /**
     * public static int totalLinks()
     *
     * This static function returns the total number of link operations made during the run-time of the program.
     * A link operation is the operation which gets as input two trees of the same rank, and generates a tree of
     * rank bigger by one, by hanging the tree which has larger value in its root on the tree which has smaller value
     * in its root.
     */
    public static int totalLinks()
    {
        return totalLinks;
    }

    /**
     * public static int totalCuts()
     *
     * This static function returns the total number of cut operations made during the run-time of the program.
     * A cut operation is the operation which diconnects a subtree from its parent (during decreaseKey/delete methods).
     */
    public static int totalCuts()
    {
        return totalCuts;
    }

    /**
     * public class HeapNode
     *
     * If you wish to implement classes other than FibonacciHeap
     * (for example HeapNode), do it in this file, not in
     * another file
     *
     */
    public class HeapNode{

        private String info;
        private int key;
        private int rank;
        private boolean mark;
        private HeapNode child;
        private HeapNode next;
        private HeapNode prev;
        private HeapNode parent;


        public HeapNode(String newInfo, int newKey){
            this.info = newInfo;
            this.key = newKey;
            this.next = this;
            this.prev = this;
        }



    }
}