package table;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Sage extends Thread{

    private String name;
    private String state;
    private int mealCount;
    int leftFork;
    int rightFork;
    private Random random;
    private CountDownLatch count;
    private Table table;

    public Sage(String name,Table table, int leftFork, int rightFork, CountDownLatch count){
        this.name = name;
        this.state = "thinking";
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.table = table;
        this.count = count;
        mealCount = 0;
        random = new Random();
    }

    private void think() throws InterruptedException {
        this.state = "thinking";
        System.out.println(this.name + " размышляет");
        sleep(random.nextInt(1000, 1500));
    }

    private void eat() throws InterruptedException {
        if (table.getForks(leftFork, rightFork)){
            this.state = "eating";
            System.out.println(this.name + " ест");
            sleep(random.nextInt(1000, 2000));
            table.putForks(leftFork, rightFork);
            System.out.println(this.name + " отложил вилки");
            mealCount++;
        }
    }

    @Override
    public void run() {
        while (mealCount < 3){
            try {
                think();
                eat();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        count.countDown();
    }
}
