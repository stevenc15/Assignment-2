import java.util.Arrays; 
import java.util.List; 
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Random;
import org.w3c.dom.css.Counter;
import java.io.PrintWriter; 
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Random;
import java.util.LinkedList;
import java.util.Queue;

//classes for errors concerning full or empty queue
class FullException extends Exception {
    public FullException() {
        super("line full");
    }
}

class EmptyException extends Exception {

    public EmptyException() {
        super("line empty");
    }
}

//queue/line class
class queue {
    
    //variables
    private int head, tail, count;
    private Thread[] guests;
    private Lock lock;

    //constructor
    public queue(int capacity) {
        head = 0;
        tail = 0;
        lock = new ReentrantLock();
        guests = new Thread[capacity];
        count = 0;
    }

    //enqueue function
    public void enq(Thread thread) throws FullException {
        lock.lock();
        try {
            System.out.println("Guest: " + Thread.currentThread().getName());
            
            if (tail - head == guests.length){
                throw new FullException();                
            }
            guests[tail % guests.length] = thread;
            tail++;
            count++;
            System.out.println("guest joins line");
        } finally {
            lock.unlock();
        }
    }

    //dequeue function
    public Thread deq() throws EmptyException {
        lock.lock();
        try {
            System.out.println("Guest: " + Thread.currentThread().getName());
            
            if (tail == head)
                throw new EmptyException();
            Thread thread = guests[head % guests.length];
            head++;
            System.out.println("guest sees vase and leaves line");
            return thread;
        } finally {
            lock.unlock();
        }
    }

    //to check whether the guest is in the line
    public boolean isGuestinLine(Thread thread) {
        for (Thread guest : guests) {
            if (guest==thread){
                return true;
            }
        }
        return false;
    }

    //helper methods
    public int getCount() {
        return count;
    }

    //method to check whether guest is at the front of the line
    public Thread checkFrontofLine() {
        if (head == tail) {
            return null;
        }
        return guests[head % guests.length];
    }
}

//guest
class Guest extends Thread {
    
    //guest variables
    private queue line;

    //constructor for guest
    public Guest (queue line) {       
        this.line=line;
    }

    //what each guest does
    public void run() {
        
        Random random = new Random();

        while (line.getCount()<5) {
            
            try {
                Thread.sleep(random.nextInt(100));
            } 
            
            catch (InterruptedException e) {
            }

            if (!line.isGuestinLine(this)) { 
                try {
                    line.enq(this); 
                    
                } catch (FullException e) {
                }
            } else if (line.isGuestinLine(this) && line.checkFrontofLine() == this) { 
                try {
                    line.deq(); 
                    
                } catch (EmptyException e) {
                }
            }
        }
    }

}


//main class
public class Assignment2P2 {
	public static void main(String[] args) throws IOException
	{

        //number of guests
		int N = 5;

        //line initialization
        queue line = new queue(N);

        //guest threads
        Guest[] threads = new Guest[N];
        for (int i=0;i<(N);i++){
            threads[i] = new Guest(line);
            threads[i].start();
        }

	}    

}
