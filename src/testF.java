import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;



public class testF {

	 public static void main(String [] args){
	        sequence1();
	        sequence2();
//	        System.out.println(Integer.toBinaryString(500));

	    }

	    private static void sequence1(){
	        long startTime,stopTime;
	        int m = 1000;
	        Random r = new Random();
	        FibonacciHeap heap;

	        for (int j = 1; j <= 3 ; j++) {
	            heap =new FibonacciHeap();

	            startTime = System.currentTimeMillis();
	            for (int i = m*j; i >0  ; i--)
	                heap.insert(i);
	            stopTime = System.currentTimeMillis();

	            System.out.println("total Links = " + FibonacciHeap.totalLinks());
	            System.out.println("total Cuts = " + FibonacciHeap.totalCuts());
	            System.out.println("potential = " + heap.potential());
	            System.out.println("Elapsed time was " + (stopTime - startTime) + " miliseconds.");
	            FibonacciHeap.totalLinks = 0;
	            FibonacciHeap.totalCuts = 0;
	        }
	    }

	    private static void sequence2(){
	        long startTime,stopTime;
	        int m = 1000;
	        Random r = new Random();
	        FibonacciHeap heap;

	        for (int j = 1; j <= 3 ; j++) {
	            heap =new FibonacciHeap();

	            startTime = System.currentTimeMillis();
	            for (int i = m*j; i >0  ; i--)
//					for (int i = 1; i <m*j+1  ; i++)
	                heap.insert(i);
	            for (int i = 0; i < (m*j)/2  ; i++)
	                heap.deleteMin();
	            stopTime = System.currentTimeMillis();

	            System.out.println("total Links = " + FibonacciHeap.totalLinks());
	            System.out.println("total Cuts = " + FibonacciHeap.totalCuts());
	            System.out.println("potential = " + heap.potential());
	            System.out.println("Elapsed time was " + (stopTime - startTime) + " miliseconds.");
	            FibonacciHeap.totalLinks = 0;
	            FibonacciHeap.totalCuts = 0;
				System.out.println("countersRep: "+ Arrays.toString(heap.countersRep()));
	        }
	    }}
