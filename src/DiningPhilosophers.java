import java.util.concurrent.Semaphore;

public class DiningPhilosophers {
    public static void main(String[] args) {
        Philosopher[] philosopherList = new Philosopher[5];
        Fork[] forks = new Fork[5];
        Semaphore diningTable = new Semaphore(4);

        for (int i = 0; i < 5; i++) {
            forks[i] = new Fork();
        }
        for (int i = 0; i < 5; i++) {
            philosopherList[i] = new Philosopher(i, forks[i], forks[(i + 1) % 5], diningTable);
             Thread diningPhilThread = new Thread(philosopherList[i]);
             diningPhilThread.start();
        }
    }


}
