import java.util.HashMap;
import java.util.Map;

/**
 * FibonacciHeap
 *
 * An implementation of fibonacci heap over non-negative integers.
 *
 *  Names: Guy Klien, Uri Shaham
 *  User Names: guyklien1, urishaham
 */
public class FibonacciHeap
{
    private HeapNode min;
    private int size;
    public int numOfMarked;
    public int numOfTrees;
    public static int totalLinks;
    public static int totalCuts;

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
        // create new HeapNode
        HeapNode toBeInserted = new HeapNode(key);

        if (this.empty()){
            this.min = toBeInserted;
        }
        else {
            // insert the new HeapNode next to the minNode
            insertNodeToOthersList(toBeInserted, this.min);
        }

        // update the relevant fields
        this.size++;
        this.numOfTrees++;
        if (toBeInserted.key <= this.min.key){
            this.min = toBeInserted;
        }
        return toBeInserted;
    }

    /**
     * public void insertNodeToOthersList(HeapNode node, HeapNode other)
     *
     * @pre node and other are both not null
     *
     */
    private void insertNodeToOthersList(HeapNode node, HeapNode other){
        HeapNode otherNext = other.next;
        otherNext.prev = node;
        node.next = otherNext;
        other.next = node;
        node.prev = other;
    }

    /**
     * public void combineTwoLists(HeapNode node, HeapNode other)
     *
     * @pre node and other are both not null
     *
     */
    private void combineTwoLists(HeapNode node, HeapNode other) {
        HeapNode nodePrev = node.prev;
        nodePrev.next = other.next;
        other.next.prev = nodePrev;
        node.prev = other;
        other.next = node;
    }

    /**
     * public void deleteMin()
     *
     * Delete the node containing the minimum key.
     *
     */
    public void deleteMin()
    {
        //Heap is empty
        if (this.empty()){return;}
        if (this.size == 1){
            this.min.clear();
            this.min = null;
            this.size--;
            this.numOfTrees--;
            return;
        }

        HeapNode minChild = this.min.child;

        //min has kids
        if (minChild!=null){
            //link the two lists together
            combineTwoLists(this.min, minChild);
        }

        //remove min from root list
        this.min.prev.next = this.min.next;
        this.min.next.prev = this.min.prev;
        HeapNode tempMin = this.min.next;
        this.min.clear();
        this.size--;

        successiveLinking(tempMin);
    }

    /**
     * public void successiveLinking(HeapNode node)
     *
     * @pre node is in this's roots list, and this.min is no longer in the list
     * @post the heap has maximum of one tree per rank between 0 and log(n)
     *
     */
    public void successiveLinking(HeapNode node)
    {
        HeapNode[] buckets = toBuckets(node);
        fromBuckets(buckets);
    }

    /**
     * public void link(HeapNode big, HeapNode small)
     *
     * @pre big and small are has the same rank
     * @return the HeapNode with the smaller key, with the other HeapNode as it's child
     *
     */
    public HeapNode link(HeapNode big, HeapNode small)
    {
        if (big.key < small.key){
            HeapNode temp = big;
            big = small;
            small = temp;
        }
        if (small.child==null) {
            big.next = big;
            big.prev = big;
        }
        else{
            insertNodeToOthersList(big, small.child);
        }
        big.parent = small;
        small.child = big;
        small.rank++;
        totalLinks++;
        return small;
    }

    /**
     * public void toBuckets(HeapNode node)
     *
     * @pre node is in roots list
     * @return array containing maximum of one tree per rank between 0 and log(n)
     *
     */
    public HeapNode[] toBuckets(HeapNode node)
    {
        HeapNode[] buckets = new HeapNode[(int)(Math.log(this.size)/Math.log(1.6)+1)];
        node.prev.next = null;
        HeapNode brother;
        while (node != null){
            brother = node;
            node = node.next;

            // if brother is marked, unmark it and update counter
            if (brother.mark){
                brother.mark = false;
                this.numOfMarked--;
            }

            //a node from root list has no parent
            brother.parent = null;

            while(buckets[brother.rank]!= null){
                brother = link(brother, buckets[brother.rank]);
                buckets[brother.rank-1] = null;
            }

            buckets[brother.rank] = brother;

        }
        // all of the trees are now inside the buckets
        this.numOfTrees = 0;
        this.min = null;
        return buckets;
    }

    /**
     * public void fromBuckets(HeapNode[] buckets)
     *
     * @pre  buckets contains maximum of one tree per rank between 0 and log(n)
     * @pre this.numOfTrees == 0
     * @pre this.min == null
     * @post FibonacciHeap with maximum of one tree per rank between 0 and log(n)
     *
     */
    public void fromBuckets(HeapNode[] buckets)
    {
        for (HeapNode heap : buckets){
            if (heap != null){
                this.numOfTrees++;
                if (this.min ==null){ //the first heap we find in buckets
                    this.min = heap;
                    this.min.next = this.min;
                    this.min.prev = this.min;
                }
                else {
                    insertNodeToOthersList(heap, this.min);
                    if (heap.key < this.min.key){
                        this.min = heap;
                    }
                }
            }
        }
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
            return;
        }

        //link the two lists together
        combineTwoLists(this.min, heap2.min);

        // update the fields
        if (heap2.min.key < this.min.key){
            this.min = heap2.min;
        }
        this.size += heap2.size;
        this.numOfTrees += heap2.numOfTrees;
        this.numOfMarked += heap2.numOfMarked;
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
        if (this.empty()){
            return new int[0];
        }

        Map<Integer, Integer> ranksMap = new HashMap<>();
        HeapNode currentNode = this.min.prev;
        int maxRank = 0;
        for (int i=0 ; i < numOfTrees ; i++){
            currentNode = currentNode.next;
            if (ranksMap.get(currentNode.rank)==null){
                ranksMap.put(currentNode.rank, 1);
            }
            else {
                ranksMap.put(currentNode.rank, ranksMap.get(currentNode.rank)+1);
            }
            if (maxRank < currentNode.rank){
                maxRank = currentNode.rank;
            }
        }
        int[] counterRep = new int[maxRank+1];
        for (Integer rank : ranksMap.keySet()){
            counterRep[rank] = ranksMap.get(rank);
        }
        return counterRep;
    }

    /**
     * public void arrayToHeap(int[] array)
     *
     * Insert the array to the heap. Delete previous elements in the heap.
     *
     */
    public void arrayToHeap(int[] array)
    {
        // restart all the Heap fields
        this.min = null;
        this.size = 0;
        this.numOfTrees = 0;
        this.numOfMarked = 0;
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
        int delta = x.getKey() - this.min.getKey();
        decreaseKey(x, delta+1);
        deleteMin();
    }

    /**
     * public void decreaseKey(HeapNode x, int delta)
     *
     * The function decreases the key of the node x by delta. The structure of the heap should be updated
     * to reflect this change (for example, the cascading cuts procedure should be applied if needed).
     */
    public void decreaseKey(HeapNode x, int delta)
    {
        x.key -= delta;
        if (x.parent == null || x.key >= x.parent.key){
            if (x.key < this.min.key){
                this.min = x;
            }
            return;
        }
        cascadingCut(x, x.parent);

        return; // should be replaced by student code
    }

    /**
     * public void cascadingCut(HeapNode child, HeapNode parent)
     *
     * @pre child and parent are not null
     * @pre child.key < parent.key
     */
    private void cascadingCut(HeapNode child, HeapNode parent){
        cut(child, parent);
        if (parent.parent != null){
            if (!parent.mark){
                parent.mark = true;
                this.numOfMarked++;
            }
            else {
                cascadingCut(parent, parent.parent);
            }
        }
    }

    /**
     * public void cascadingCut(HeapNode child, HeapNode parent)
     *
     * @pre child and parent are not null
     */
    private void cut(HeapNode child, HeapNode parent){

        //cut child from its parent
        child.parent = null;
        if (child.mark) {this.numOfMarked--;}
        child.mark = false;
        parent.rank--;

        // child is an only child
        if (child.next == child){
            parent.child = null;
        }
        else { // child has siblings
            parent.child = child.next;
            child.prev.next = child.next;
            child.next.prev = child.prev;
        }

        insertNodeToOthersList(child, this.min);

        // update the fields
        if (child.key < this.min.key){
            this.min = child;
        }
        this.numOfTrees++;
        totalCuts++;
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

        public int key;
        public int rank;
        public boolean mark;
        public HeapNode child;
        public HeapNode next;
        public HeapNode prev;
        public HeapNode parent;


        /**
         * public HeapNode(int newKey)
         *
         * constructor
         */
        public HeapNode(int newKey){
            this.key = newKey;
            this.next = this;
            this.prev = this;
        }

        /**
         * public void clear()
         *
         * clears all of the node's pointers
         */
        public void clear(){
            this.next = null;
            this.prev = null;
            this.child = null;
            this.parent = null;
        }

        /**
         * public void getKey()
         *
         * @return key of the node
         */
        public int getKey(){
            return this.key;
        }
    }
}