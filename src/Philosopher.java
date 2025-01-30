import java.util.concurrent.Semaphore;

public class Philosopher implements Runnable{
    private int philosopherNumber;
    private Fork leftFork;
    private Fork rightFork;
    private Semaphore diningTable;

    public Philosopher(int philosopherNumber, Fork leftFork, Fork rightFork, Semaphore diningTable) {
        this.philosopherNumber = philosopherNumber;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.diningTable = diningTable;
    }

    private void think() throws InterruptedException {
        System.out.println("Philosopher " + philosopherNumber + " is thinking");
       // System.out.flush();
        Thread.sleep((int) (Math.random() * 2000));
    }
    private void eat() throws InterruptedException {
        System.out.println("Philosopher " + philosopherNumber + " is eating");
       // System.out.flush();
        Thread.sleep((int) (Math.random() * 2000));
    }
    @Override
    public void run() {
        try {
            long endTime = System.currentTimeMillis() + 10000;
            while (System.currentTimeMillis() < endTime) {
                System.out.println("Philosopher " + philosopherNumber + " is waiting to enter the dining table");
                diningTable.acquire();

                think();

                if (philosopherNumber < 4) {
                    leftFork.pickUpFork(philosopherNumber);
                    rightFork.pickUpFork(philosopherNumber);
                }
                else {
                    rightFork.pickUpFork(philosopherNumber);
                    leftFork.pickUpFork(philosopherNumber);
                }

                eat();

                leftFork.putDownFork(philosopherNumber);
                rightFork.putDownFork(philosopherNumber);

                diningTable.release();
                //System.out.println("Philosopher " + philosopherNumber + " left the dining table");


            }
        }
        catch (InterruptedException e) {
            System.out.println("Philosopher " + philosopherNumber + " was interrupted");
            Thread.currentThread().interrupt();
        }

    }
}
