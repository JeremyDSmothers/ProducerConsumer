package producer_consumer;

import java.util.Vector;

// what does it mean that Producer implements Runnable?
class Producer implements Runnable {

    private final Vector<Integer> sharedQueue;
    private final int SIZE;
    private final int NUM_PROCESSED;
    private boolean finished;

    public Producer(Vector<Integer> sharedQueue, int size, int numProcessed) {
    	//sharedQueue is passed as a parameter from ThreadExample.  
    	// So are there two copies of the Vector or One?
    	//2. the one that is passed into that is then copied into the local sharedQueue Vector object.
        this.sharedQueue = sharedQueue;
        // how many copies of size are there among the three classes?
        // 3
        this.SIZE = size;
        this.NUM_PROCESSED = numProcessed;
    }


    public void run() {
        for (int i = 0; i < NUM_PROCESSED; i++) {
            System.out.println("Produced: " + i);
            try {
                produce(i);
                
                // this puts the thread to sleep (paused for a bit) change it here and in 
                // Consumer to combinations of 0 and 200 .
                // what changes about the program?  Why?         
                // This changes how long it takes the producer to produce something for the consumer to consume.
                // If the sleep times are not the same then the synchronization of the two threads is thrown off
                // and the producer and consumer may or may not be more subject to waiting on the other to produce/consume something
                Thread.sleep(0);
            } catch (InterruptedException ex) {
                System.out.println(ex);
                ex.printStackTrace();
            }
        }
        System.out.println("finished with producer");
        //sharedQueue.notifyAll();
    }

    private void produce(int i) throws InterruptedException {

        //wait if queue is full
        while (sharedQueue.size() == SIZE) {
            synchronized (sharedQueue) {
                System.out.println("Queue is full " + Thread.currentThread().getName()
                                    + " is waiting , size: " + sharedQueue.size());

                // what does wait do?  Why are you doing it in this part of the program?
                // this will halt operation of run method until the consume method of the consumer
                // notifyAll's the producer to let it know it can continue running again. 
                sharedQueue.wait();
            }
        }

        //producing element and notify consumers
        synchronized (sharedQueue) {
            sharedQueue.add(i);
            
            // what does notifyAll() do?
            // if the sharedQueue is empty then the consumer object will have called wait to wait for the 
            // produce something for the consumer to consume. The producer lets the consumer know it has
            // produced by calling notifyAll which breaks the wait method of the consumer.
            sharedQueue.notifyAll();
        }
    }
}
