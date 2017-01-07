
public class FibonacciHeapTester {
    public static void main(String[] args){
        FibonacciHeap fibHeap = new FibonacciHeap();
        fibHeap.insert(5);
        fibHeap.insert(9);
        FibonacciHeap.HeapNode ten =  fibHeap.insert(10);
        fibHeap.deleteMin();
        fibHeap.insert(19);
        fibHeap.insert(20);
        fibHeap.insert(0);
        fibHeap.deleteMin();
        fibHeap.insert(122);
        FibonacciHeap.HeapNode seventy = fibHeap.insert(70);
        fibHeap.insert(80);
//        System.out.println(fibHeap.findMin());
        fibHeap.insert(30);
        fibHeap.deleteMin();
//        fibHeap.decreaseKey(ten, 2);
        fibHeap.insert(60);
        fibHeap.insert(50);
        fibHeap.insert(66);
        fibHeap.deleteMin();
        fibHeap.deleteMin();

        System.out.println("numOfTrees: "+fibHeap.numOfTrees);
        System.out.println("numOfMarked: "+fibHeap.numOfMarked);
        System.out.println(fibHeap.findMin());

//        fibHeap.decreaseKey(seventy, 48);
//        System.out.println("==============");
//        System.out.println("numOfTrees: "+fibHeap.numOfTrees);
//        System.out.println("numOfMarked: "+fibHeap.numOfMarked);
//        System.out.println(fibHeap.findMin());

        for (int i = 1 ; i<3; i++){
            fibHeap.insert(i*100);
        }
        FibonacciHeap.HeapNode threeHundred = fibHeap.insert(300);
        FibonacciHeap.HeapNode fourHundred = fibHeap.insert(400);
        FibonacciHeap.HeapNode fiveHundred = fibHeap.insert(500);
        for (int i = 6 ; i<9; i++){
            fibHeap.insert(i*100);
        }
        fibHeap.deleteMin();
        fibHeap.insert(900);
        fibHeap.insert(1000);
        fibHeap.deleteMin();
        //PIC HERE

        fibHeap.decreaseKey(fiveHundred, 445);
        fibHeap.decreaseKey(threeHundred, 255);

        System.out.println();
        System.out.println("numOfTrees: "+ fibHeap.numOfTrees);
        System.out.println("size: "+fibHeap.size());
        System.out.println("numOfMarked: "+fibHeap.numOfMarked);
        System.out.println("min: " + fibHeap.findMin());






//        if (fibHeap.findMin().getKey() != 5){
//            System.out.println("wrong min key. suppose to be 5, but it is " + fibHeap.findMin().getKey());
//        }
//
//        fibHeap.insert(2);
//        fibHeap.insert(10);
//        fibHeap.insert(8);
//        if (fibHeap.findMin().getKey() != 2){
//            System.out.println("wrong min key. suppose to be 2, but it is " + fibHeap.findMin().getKey());
//        }
//        System.out.println("size supposed to be: 8, it is: " +fibHeap.size());
        System.out.println("done!");
    }
}
