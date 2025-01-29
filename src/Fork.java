import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Fork {
    private Lock lock = new ReentrantLock();

    public void pickUpFork (int philoshopherNum) {
        lock.lock();
        System.out.println("Philosopher " + philoshopherNum + " picked up a fork");
        lock.unlock();
    }

    public void putDownFork (int philoshopherNum) {
        lock.lock();
        System.out.println("Philosopher " + philoshopherNum + " put down a fork");
        lock.unlock();
    }
}
