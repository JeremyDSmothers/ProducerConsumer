package producer_consumer;

import java.util.Vector;

//what does it mean that Consumer implements Runnable?
class Consumer implements Runnable {

    private final Vector<Integer> sharedQueue;
    private final int SIZE;
    private final int NUM_PROCESSED;
    private int processed;

    public Consumer(Vector<Integer> sharedQueue, int size, int numProcessed) {
    	// sharedQueue is passed as a parameter from ThreadExample.  
    	// So are there two copies of the Vector or One? 
    	//2. the one that is passed into that is then copied into the local sharedQueue Vector object.
        this.sharedQueue = sharedQueue;
        // how many copies of size are there among the three classes?
        // 3
        this.SIZE = size;
        this.NUM_PROCESSED = numProcessed;
        processed = 0;
    }


    public void run() {
        while (true) {
            try {
                System.out.println("Consumed: " + consume());
                // this puts the thread to sleep (paused for a bit) change it here and in 
                // Producer to combinations of 0 and 200 .
                // what changes about the program?  Why?
                
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                System.out.println(ex);
                ex.printStackTrace();
            }
            if (processed == NUM_PROCESSED) {
            	System.out.println("finished with consumer");
            	return;
            }
        }
    }

    private int consume() throws InterruptedException {
        //wait if queue is empty
        while (sharedQueue.isEmpty()) {
            synchronized (sharedQueue) {
                System.out.println("Queue is empty " + Thread.currentThread().getName()
                                    + " is waiting , size: " + sharedQueue.size());

                // what does wait() do?  why are you doing it in this part of the program?
                // if the sharedQueue is emptied due to the consumer consuming the last object in the vector, 
                // then the consumer will wait() until it is notified by the producer that normal operation
                // can continue.
                sharedQueue.wait();
            }
        }

        //Otherwise consume element and notify waiting producer
        synchronized (sharedQueue) {
            // what does notifyAll() do?    
        	// If the queue is full then Producer will have called wait until it gets a notification from
        	// consumer that it has opened a spot in the queue. Once this notifyAll method is called then
        	// the producer will resume normal function. 
            sharedQueue.notifyAll();
            
            // what does remove(0) do?
            // removes the first object in the sharedQueue vector. Effectively "eats" the first object in 
            // line that the producer produced. The object that has been there the longest. 
            processed++;
            return (Integer) sharedQueue.remove(0);
        }
    }
}
