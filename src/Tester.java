
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;


public class Tester {
	
	public static void main(String[] args){
		FibonacciHeap h1 = new FibonacciHeap();
		h1.insert(13);
		h1.insert(2);	
		h1.insert(4);
		h1.insert(3);	
		h1.insert(8);
		h1.insert(9);
		FibonacciHeap.HeapNode n2= h1.insert(7);
		FibonacciHeap.HeapNode n3= h1.insert(12);
		FibonacciHeap.HeapNode n4= h1.insert(6);
		FibonacciHeap.HeapNode n1= h1.insert(5);
		h1.insert(10);
		h1.insert(11);
    	h1.insert(1);
    	h1.deleteMin();
    	h1.delete(n4);
    	h1.delete(n2);
    	h1.delete(n3);
    	stressTest(50,100);
    	meldTest(100);
      	insertDeleteTest(50);
		

	}
	
	public static void meldTest(int startHeapSize){
		System.out.println("");
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("Init MeldTest");
		System.out.println("");
		FibonacciHeap heap1 = new FibonacciHeap();
		FibonacciHeap heap2 = new FibonacciHeap();
		initHeap(heap1,startHeapSize);
		initHeap(heap2,startHeapSize/2);
		FibonacciHeap.HeapNode m1 = heap1.findMin();
		FibonacciHeap.HeapNode m2 = heap2.findMin();
		heap1.decreaseKey(m1, 1000);
		heap1.meld(heap2);
		if (m1 != heap1.findMin())
			System.out.println("EROR#1");
		heap1.decreaseKey(m2, 100000);
		if (m2 != heap1.findMin())
			System.out.println("EROR#2");
		
		System.out.println("MeldTest Done!");
		
	}
	
	
	public static void offTestPart1(){
		System.out.println("");
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("Init offTestPart1");
		System.out.println("");
		for (int i = 7; i>0; i--){
			FibonacciHeap heap = new FibonacciHeap();
			double startTime = System.currentTimeMillis();
			for (int j = i*1000; j>0; j--){
				heap.insert(j);
			}
			double stopTime = System.currentTimeMillis();
			System.out.println("");
			System.out.println("for " + i*1000);
			System.out.println("RunTime = " + (stopTime-startTime)/1e0);
			System.out.println("Total Links = " + FibonacciHeap.totalLinks());
			System.out.println("Total Cuts = " + FibonacciHeap.totalCuts());
			System.out.println("potential = " + heap.potential());
			
		}
	}
	
	public static void offTestPart2(){
		System.out.println("");
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("Init offTestPart2");
		System.out.println("");
		for (int i = 150; i>0; i--){
			int tsize = i*1000;
			int tsize2 = tsize/2;
			FibonacciHeap heap = new FibonacciHeap();
			for (int j = tsize; j>0; j--){
				heap.insert(j);
			}
			double startTime = System.nanoTime();

			for (int j = tsize2; j>0; j--){
				heap.deleteMin();
			}
			
			double stopTime = System.nanoTime();
			System.out.println("");
			System.out.println("for " + i*1000);
			System.out.println("RunTime = " + (double)(stopTime-startTime)/1e6);
			System.out.println("Total Links = " + FibonacciHeap.totalLinks());
			System.out.println("Total Cuts = " + FibonacciHeap.totalCuts());
			System.out.println("potential = " + heap.potential());
			FibonacciHeap.totalLinks = 0;
			 FibonacciHeap.totalCuts = 0;
			
		}
	}

		
	private static final double PHI = (1+Math.sqrt(5))/2;
	
	public static void stressTest(int numOfLoops, int startHeapSize){
		System.out.println("");
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("Init stressTest");
		System.out.println("");
		for (int k  = 0; k <numOfLoops; k++ ){
			
		
		FibonacciHeap heap = new FibonacciHeap();
		FibonacciHeap.HeapNode[] nodes = new FibonacciHeap.HeapNode[startHeapSize];
 		nodes = initHeap(heap,startHeapSize);
		int testSize = 0;
		int max = 0;
		int[] arr = heap.countersRep();
		for (int i = 0; i< arr.length; i++){
			if ((arr[i] != 0) && (arr[i] != 1))
				System.out.println("EROR #1 " + arr[i]);
			if (arr[i] == 1)
				max = i;
			testSize += arr[i]*( java.lang.Math.pow(2, i));
		}
		
		if (testSize != heap.size())
			System.out.println("EROR #2  " + heap.size() + " " + testSize);
		
		if (max > (int) (Math.log(heap.size())/ Math.log(PHI))+1)
			System.out.println("EROR #3 " +  heap.size());
		
		int randomNum;
		FibonacciHeap.HeapNode n1 = heap.findMin();
		heap.decreaseKey(n1, 10000);
		randomNum = ThreadLocalRandom.current().nextInt(0, startHeapSize);
		if (nodes[randomNum] != null)
			heap.decreaseKey(nodes[randomNum], 158531);
		for (int i = 0; i < startHeapSize*5; i++){
			randomNum = ThreadLocalRandom.current().nextInt(0, startHeapSize);
			if (nodes[randomNum] != null)
				heap.decreaseKey(nodes[randomNum], 1);
		}
		for (int i = 0; i < startHeapSize; i++){
			heap.insert(i + startHeapSize);
		}
		for (int i = 0; i < startHeapSize*5; i++){
			randomNum = ThreadLocalRandom.current().nextInt(0, startHeapSize);
			if (nodes[randomNum] != null)
				heap.decreaseKey(nodes[randomNum], 1);
		}
		if (heap.findMin() != n1)
			heap.deleteMin();

		for (int i = 0; i< arr.length; i++){
			if ((arr[i] != 0) && (arr[i] != 1))
				System.out.println("EROR #4 " + arr[i]);
		}
			
		if (max > (int) (Math.log(heap.size())/ Math.log(PHI))+1)
			System.out.println("EROR #5 " +  heap.size());
		
		if (heap.findMin() != n1){
			System.out.println("EROR #6 ");
			System.out.println(heap.findMin().key);
			System.out.println(n1.key);
			printHeap(heap);
		}
		if (!testNumTrees(heap))
			System.out.println("EROR IN Trees num");
		System.out.println("Potential,Total Links,Total Cuts after loop "+ k + ": [" + heap.potential() + ", " + FibonacciHeap.totalLinks()+ ", " +FibonacciHeap.totalCuts()+ "]");

		}
		System.out.println("Stress Test Done!");
	
	}
	
	
	public static boolean testNumTrees(FibonacciHeap heap){
		
		FibonacciHeap.HeapNode cur = heap.findMin();
		int cnt = 1;
		do{
			cnt++;
			cur = cur.next;
		} while (cur.next != heap.findMin());
		int secCnt = 0;

		for (int i : heap.countersRep())
			secCnt+= i;


		if (cnt != secCnt)
			return false;
		return true;
	}
	
	
	

	public static FibonacciHeap.HeapNode[] initHeap(FibonacciHeap heap, int startHeapSize){
		
		int randomNum = ThreadLocalRandom.current().nextInt(0, startHeapSize);
		FibonacciHeap.HeapNode[] arr = new FibonacciHeap.HeapNode[startHeapSize+1];
		FibonacciHeap.HeapNode n1 = null;
		for (int i = 0; i <= startHeapSize; i++){
			if (i==randomNum){
				n1 = heap.insert(i);
			}
			else{
				arr[i] = heap.insert(i);
			}
		}
		heap.delete(n1);
		return arr;
		
	}
	
	public static void insertDeleteTest(int numOfLoops){
		System.out.println("");
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("Init insertDeleteTest");
		System.out.println("");
		for (int k = 0; k < numOfLoops; k++){
			FibonacciHeap heap = new FibonacciHeap();
			FibonacciHeap.HeapNode[] nodes = initHeap(heap, 1000);
			int randomNum;
			randomNum = ThreadLocalRandom.current().nextInt(0, 1000);
			if (nodes[randomNum] == null)
				continue;
			FibonacciHeap.HeapNode pivot = nodes[randomNum];
			for (int i = 0; i< 50; i++){
				randomNum = ThreadLocalRandom.current().nextInt(0, 1000);
				if ((nodes[randomNum] == null) || (nodes[randomNum] == pivot))
					continue;
				heap.delete(nodes[randomNum]);
				nodes[randomNum] = null;
			}
			heap.decreaseKey(pivot, 1500);
			for (int i = 0; i< 50; i++){
				randomNum = ThreadLocalRandom.current().nextInt(0, 1000);
				if ((nodes[randomNum] == null) || (nodes[randomNum] == pivot))
					continue;
				heap.delete(nodes[randomNum]);
				nodes[randomNum] = null;
			}
			if (heap.findMin() != pivot)
				System.out.println("EROR in insert-delete test");
			System.out.println("loop " + k + " successful");
		}
		System.out.println("insert-delete test Done!");
	}

	
	static void printHeap(FibonacciHeap heap){
		if(heap==null || heap.empty()){return;}
		FibonacciHeap.HeapNode tempNode = heap.findMin();
		do{
			printNode(tempNode,0,0);
			System.out.format("%n");
			tempNode = tempNode.next;
		}while(tempNode != heap.findMin());
		
	}
	static void printNode(FibonacciHeap.HeapNode heapNode, int lastLevel, int level){
		for(int i = lastLevel; i<level; i++){
			System.out.print("	");
		}
		if(heapNode.mark){
			System.out.print(".");
		}
		System.out.print(heapNode.key);
		if(heapNode.child!=null){
			printNode(heapNode.child.prev,level,level+1);
			FibonacciHeap.HeapNode tempNode = heapNode.child.prev.prev, firstNode = heapNode.child.prev;
			while(firstNode!=tempNode){
				System.out.format("%n");
				printNode(tempNode,0,level+1);
				tempNode = tempNode.prev;
			}
		}
	}
	

}
