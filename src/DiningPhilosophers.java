import java.util.concurrent.Semaphore;

public class DiningPhilosophers {
    public static void main(String[] args) {
        Philosopher[] philosopherList = new Philosopher[5];
        Fork[] forks = new Fork[5];
        Semaphore diningTable = new Semaphore(4);

        for (int i = 0; i < 5; i++) {
            // i + 1 because fork can't start at 0
            forks[i] = new Fork(i + 1);
        }
        for (int i = 0; i < 5; i++) {
            philosopherList[i] = new Philosopher(i, forks[i], forks[(i + 1) % 5], diningTable);
             Thread diningPhilThread = new Thread(philosopherList[i]);
             diningPhilThread.start();
        }
    }


}
