
public class markedTest{
    public static void main(String[] args) {
        FibonacciHeap fibHeap = new FibonacciHeap();
        for (int i = 1 ; i < 7 ; i++){
            fibHeap.insert(i);
        }
        FibonacciHeap.HeapNode seven = fibHeap.insert(7);
        FibonacciHeap.HeapNode eight = fibHeap.insert(8);
        for (int i = 9 ; i < 12 ; i++){
            fibHeap.insert(i);
        }
        FibonacciHeap.HeapNode twelve = fibHeap.insert(12);
        FibonacciHeap.HeapNode thirteen = fibHeap.insert(13);
        FibonacciHeap.HeapNode fourteen = fibHeap.insert(14);
        FibonacciHeap.HeapNode fifteen = fibHeap.insert(15);
        FibonacciHeap.HeapNode sixteen = fibHeap.insert(16);
        printStats(fibHeap, "Starting stats");

        fibHeap.deleteMin();
        printStats(fibHeap, "After first deleteMin");

        fibHeap.decreaseKey(sixteen, 32);
        fibHeap.decreaseKey(fourteen, 28);
        fibHeap.decreaseKey(twelve, 24);
        fibHeap.decreaseKey(seven, 14);
        printStats(fibHeap, "After several decreaseKey");

        fibHeap.deleteMin();
        printStats(fibHeap, "After second deleteMin");

        fibHeap.decreaseKey(thirteen, 30);
        printStats(fibHeap, "After decreaseKey(fifteen, 15)");
    }

    public static void printStats(FibonacciHeap heap, String message){
        System.out.println(" ===== "+message+" =====");
        System.out.println("size: "+heap.size());
        System.out.println("numOfTrees: "+heap.numOfTrees);
        System.out.println("numOfMarked: "+heap.numOfMarked);
        System.out.println("min key: "+heap.findMin().getKey());
        System.out.println("total links: "+FibonacciHeap.totalLinks);
        System.out.println("total cuts: "+FibonacciHeap.totalCuts);
        System.out.println();
    }
}
