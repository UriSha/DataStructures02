import java.util.InputMismatchException;

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

        if (this.empty()){
            this.min = toBeInserted;
        }
        else {
            // insert the new HeapNode 'behind' the minNode
            insertNodeToRootsList(toBeInserted);  //TODO what about if the Heap is empty?
//        HeapNode minPrev = this.min.prev;
//        minPrev.next = toBeInserted;
//        toBeInserted.prev = minPrev;
//        this.min.prev = toBeInserted;
//        toBeInserted.next = this.min;
        }

        // update the relevant fields
        this.size++;
        this.numOfTrees++;
        this.counterRep[0]++;
        if (toBeInserted.key < this.min.key){ //TODO what about duplicates?
            this.min = toBeInserted;
        }
        return toBeInserted;
    }

    private void insertNodeToRootsList(HeapNode node){
        HeapNode minPrev = this.min.prev;
        minPrev.next = node;
        node.prev = minPrev;
        this.min.prev = node;
        node.next = this.min;
    }
    /**
     * public void deleteMin()
     *
     * Delete the node containing the minimum key.
     *
     */
//    public void deleteMin()
//    {

//        int tempNumOfTrees = this.min.rank;
//        this.counterRep[this.min.rank]--;
//        int[] tempCounterRep = new int[42];
//
//
//        HeapNode childOfMin = this.min.child;
//        HeapNode brother = childOfMin;
//        HeapNode currentMin = childOfMin;
//
//
//        do {
//            if (brother.key < currentMin.key){
//                currentMin = brother;
//            }
//            brother.parent = null;
//            tempCounterRep[brother.rank]++;
//            if (brother.mark){
//                brother.mark = false;
//                this.numOfMarked--;
//            }
//            brother = brother.next;
//        }while (brother != childOfMin);
//
//        FibonacciHeap tempHeap = new FibonacciHeap();
//        tempHeap.min = currentMin;
////        tempHeap.size = 0;
////        tempHeap.numOfMarked = 0;
//        tempHeap.numOfTrees = tempNumOfTrees;
//        tempHeap.counterRep = tempCounterRep;
//
//        this.meld((tempHeap)); //TODO we still didn't delete the min!!
//
////        this.successiveLinking();
//    }
    public void deleteMin()
    {
        //Heap is empty
        if (this.empty()){return;}
        if (this.size == 1){
            this.min.clear();
            this.min = null;
            this.size--;
            this.numOfTrees--;
            this.counterRep[0]--;
            return;
        }

        HeapNode minChild = this.min.child;

        //min has kids
        if (minChild!=null){
            //link the two lists together
            HeapNode minPrev = this.min.prev;
            minPrev.next = minChild.next;
            minChild.next.prev = minPrev;
            this.min.prev = minChild;
            minChild.next = this.min;
        }

        //remove min from root list
        this.min.prev.next = this.min.next;
        this.min.next.prev = this.min.prev;
        HeapNode tempMin = this.min.next;
        this.min.clear();
//        this.min = tempMin;
        this.size--;

        successiveLinking(tempMin);
    }

    public void successiveLinking(HeapNode node)
    //TODO check if marked, unmark all and update numOfMarked.
    // TODO parent = null for all roots

     //tobuckets:
    // TODO find new min
    {
        HeapNode[] buckets = toBuckets(node);
        fromBuckets(buckets);
    }



    /**
     *
     * @pre big and small are both unmarked
     *
     */
    public HeapNode link(HeapNode big, HeapNode small)
    {
        if (big.key < small.key){ //TODO what about duplicates?
            link(small, big);
        }
        small.next = big.child;
        big.child = small;
        big.rank++;
        totalLinks++;
        return big;
    }
    /**
     *
     * @pre Node is in roots list
     *
     */
    public HeapNode[] toBuckets(HeapNode node)
    {
        HeapNode[] buckets = new HeapNode[(int)(Math.log(this.size)/Math.log(2))+1];
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
        this.numOfTrees = 0;
        this.counterRep = new int[42];
        return buckets;
    }

    public void fromBuckets(HeapNode[] buckets)
    {
        this.min = null;
        for (HeapNode heap : buckets){
            if (heap != null){
                this.counterRep[heap.rank]++;
                this.numOfTrees++;
                if (this.min ==null){ //the first heap we find in buckets
                    this.min = heap;
                    this.min.next = this.min;
                    this.min.prev = this.min;
                }
                else {
                    insertNodeToRootsList(heap);
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
        int delta = x.getKey() - this.min.getKey();
        decreaseKey(x, delta+1);
        deleteMin();
    }

    /**
     * public void decreaseKey(HeapNode x, int delta)
     *
     * The function decreases the key of the node x by delta. The structure of the heap should be updated
     * to reflect this chage (for example, the cascading cuts procedure should be applied if needed).
     */
    public void decreaseKey(HeapNode x, int delta)
    {
        x.key -= delta;
        if (x.parent == null || x.key < x.parent.key){ //TODO what should we do if they are equal?
            return;
        }
        cascadingCut(x, x.parent);

        return; // should be replaced by student code
    }

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
        else { // the case where parent is the root of the current tree
            this.counterRep[parent.rank]--;
            this.counterRep[parent.rank-1]++;
        }
    }

    private void cut(HeapNode child, HeapNode parent){
        child.parent = null;
        if (child.mark) {this.numOfMarked--;}
        child.mark = false;
        parent.rank--;
        if (child.next == child){
            parent.child = null;
        }
        else {
            parent.child = child.next;
            child.prev.next = child.next;
            child.next.prev = child.prev;
        }
        insertNodeToRootsList(child);
        this.numOfTrees++;
        this.counterRep[child.rank]++;
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

        public void clear(){
            this.next = null;
            this.prev = null;
            this.child = null;
            this.parent = null;
        }

        public String toString(){
            return "(" + this.key + ", " + this.info + ")";
        }

        public int getKey(){
            return this.key;
        }
    }
}