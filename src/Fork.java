import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Fork {
    private Lock lock = new ReentrantLock();
    private int forkId;

    public Fork (int forkId) {
        this.forkId = forkId;
    }
    public void pickUpFork (int philoshopherNum) {
        lock.lock();
        System.out.println("Philosopher " + philoshopherNum + " picked up fork " + forkId);
    }

    public void putDownFork (int philoshopherNum) {
        System.out.println("Philosopher " + philoshopherNum + " put down fork " + forkId);
        lock.unlock();
    }
}
