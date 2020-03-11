
package producer_consumer;
import java.util.Vector;
 
public class ThreadExample {
    public static void main(String[] args) throws InterruptedException {
    	
    	// what does Vector<Integer> mean / do?
        Vector<Integer> sharedQueue = new Vector<Integer>();
        //It creates a vector object of type Integer
        
        // how does size change the program?  try 7.  try 2.
        /* this changes the number of spaces available to store extra things to consume. 
         if the producer outruns the consumer then it can place the extras into extra spots
         in the vector. Then the consumer can pick them up as it pleases. But the producer only
         has enough space to fit "vectorSize" of them for the consumer to come and get. 
         Producer will then have to wait to produce until consumer opens a spot for producer
         to place another "thing"*/
        int vectorSize = 4;
        int itemCount = 10;
        
        // what in the world is happening here??  What is a thread?
        // A thread is like a certain procedure. The producer has a thread and consumer has a thread
        // The procedure itself has an object that is stored in the prodThread and consThread objects.
        // What is size?   What is 10;
        // size is the number of available spaces to put extra "things"
        // 10 is the itemCount. Otherwise the number of items that the producer must produce to be finished
        // and the number of items the consumer must consume to be finished
        Thread prodThread = new Thread(new Producer(sharedQueue, vectorSize,itemCount), "Producer");
        Thread consThread = new Thread(new Consumer(sharedQueue, vectorSize,itemCount), "Consumer");
        
        // what does the start method of the Thread object do?
        // calls the run method of the Thread object, aka starting the object
        
        // What runs when the start method is called?
        // procedure stored in the run method of the Thread object
        
        // what happens if you comment out one of the following lines?  Why?
        // producer commented out: producer never starts producing, therefore queue is never filled
        // and consumer starts trying to consume but never get anything to consume
        // consumer commented out: consumer never starts consuming, therefore producer fills queue
        // until queue is full and it can't produce anymore.
        prodThread.start();
        consThread.start();
        
        
        // what does the join method of the thread object do?
        prodThread.join();
        consThread.join();
        // this methods makes the producer and consumer threads to run together. Helps with synchonization
        // of procedures
        
        
        // erase "throws InterruptedException".  You get an error with the two joins.
        // Fix it with a try catch.  What is happening?  What exactly is a InterruptedException?
        // An InterruptedException is thrown when the wait, sleeping, or occupied. Or when the thread is interrupted.
        // What happens if either the producer thread or consumer thread stops executing unexpectedly?
        // You need to know a thread is stopped and this allows for error checking of when a thread stops unexpectedly. 
        // If it stops running and you don't know about it then it would be difficult to debug as to what the problem was.
    }
}    