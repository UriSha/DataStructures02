
public class FibonacciHeapTester {
    public static void main(String[] args){
        FibonacciHeap fibHeap = new FibonacciHeap();
        fibHeap.insert(5);
        fibHeap.insert(9);
        if (fibHeap.findMin().getKey() != 5){
            System.out.println("wrong min key. suppose to be 5, but it is " + fibHeap.findMin().getKey());
        }

        fibHeap.insert(2);
        fibHeap.insert(10);
        fibHeap.insert(8);
        if (fibHeap.findMin().getKey() != 2){
            System.out.println("wrong min key. suppose to be 2, but it is " + fibHeap.findMin().getKey());
        }

        System.out.println("done!");
    }
}
