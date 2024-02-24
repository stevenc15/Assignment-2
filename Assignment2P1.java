import java.util.Arrays; 
import java.util.List; 
import java.util.concurrent.locks.Lock;
import java.util.Random;
import org.w3c.dom.css.Counter;
import java.io.PrintWriter; 
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Random;

//Cupcake plate (shared with all threads)
class cupcakeplate {
    
    //cupcake plate variables
    private AtomicInteger cupcakestat; //plate status either empty or with cupcake
    private final Object lock = new Object(); //lock

    //plate constructor 
    public cupcakeplate() {
        this.cupcakestat = new AtomicInteger(1);
    }

    //method to check plate status
    public int checkPlate(int v) {
        //lock
        synchronized (lock) {
            
            System.out.println("Guest: " + Thread.currentThread().getName());
            System.out.println("is there a cupcake on the plate? (1=yes, 0=no): " + cupcakestat);
            
            //counter guest checks plate
            if (v == 0) {
                if (cupcakestat.get() == 1) {
                    cupcakestat.set(0);
                    return 0;
                }
                else{
                    return 1;
                }
            } 

            //normal guest checks plate
            else {
                if (cupcakestat.get() == 0) {
                    return 0;
                }
                else{
                    return 1;
                }
            }
        }
    }

    //method to replace cupcake if cupcake is not on plate 
    public void requestReplacement() {
        synchronized (lock) {           
                cupcakestat.set(1);
                System.out.println("Cupcake Replaced");
        }
    }
}

//normal guest
class normalG extends Thread {
    
    //normal guest variables
    private int verify; //to identify whether thread is counter or normal guest
    private cupcakeplate plate;
    private counterG counter; //to be able to know when the counter guest notifies the minotaur
    private boolean UsedReplacement; //each normal guest can only ask for a replacement once

    //constructor for normal guest thread
    public normalG(cupcakeplate plate, counterG counter) {
        this.verify = 1;
        this.plate=plate;
        this.counter=counter;
        this.UsedReplacement = false;
    }

    //what each normal guest does
    public void run() {
        int stat;
        Random random = new Random();

         while (!counter.isCountingFinished()) {
            
            try {
                Thread.sleep(random.nextInt(100));
            } 
            
            catch (InterruptedException e) {
            }

            stat = plate.checkPlate(verify);
            
            if (stat == 0 && !UsedReplacement) {
                plate.requestReplacement();
                UsedReplacement = true;
            }
        }
    }

}

//counter guest
class counterG extends Thread {

    //counter guest variables
    private int count; //count of how many guests have been through the maze
    private int verify; //to identify whether thread is counter or normal guest
    private int total; //total number of guests
    private cupcakeplate plate;
    private boolean countingFinished; //to communicate at the end of counting so that no more guests go through maze

    //constructor for counter guest thread
    public counterG(cupcakeplate plate, int total) {
        this.count = 0;
        this.verify = 0;
        this.total = total; 
        this.plate=plate;
        this.countingFinished = false;
    }

    //what the counter guest does
    public void run() {
        
        int stat;
        Random random = new Random();

        while (count < total) {
            
            try {
                Thread.sleep(random.nextInt(100));
            } 
            catch (InterruptedException e) {
            }

            stat = plate.checkPlate(verify);

            if (stat==0){
                count+=1;
                stat=10;
            }
    }

    countingFinished = true;
    System.out.println("MINOTAUR NOTIFIED"); //game finished
}

    //helper methods
    public int getCount() {
        return count;
    }

    public boolean isCountingFinished() {
        return countingFinished;
    }

}

//main class
public class Assignment2P1 {
	public static void main(String[] args) throws IOException
	{

        //number of guests
		int N = 8;

        //shared plate
        cupcakeplate plate = new cupcakeplate();

        //only one guest as the counter
        counterG chosen1= new counterG(plate, N);
        chosen1.start();
        
        //rest of guests are normal guests
        normalG[] threads = new normalG[N];

        for (int i=0;i<(N-1);i++){
            threads[i] = new normalG(plate, chosen1);
            threads[i].start();
        }
    }    

}
