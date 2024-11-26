package table;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class Table extends Thread{

    private int number;
    ArrayList<Sage> people = new ArrayList<>();
    ArrayList<Fork> forks = new ArrayList<>();
    private CountDownLatch cdl;

    public Table(int count) {
        cdl = new CountDownLatch(count);
        number = count;
        setTheTable();
    }


    private void setTheTable(){
        System.out.println("Стол накрыт на " + number + " человек");
        for (int i = 0; i < number; i++) {
            people.add(new Sage("Мудрец " + (i + 1), this, i, (i + 1) % number, cdl));
            forks.add(new Fork());
        }
    }

    public synchronized boolean getForks(int leftFork, int rightFork){
        if (forks.get(leftFork).isFree && forks.get(rightFork % number).isFree){
            forks.get(leftFork).isFree = false;
            forks.get(rightFork).isFree = false;
            return true;
        }
        return false;
    }

    public void putForks(int leftFork, int rightFork){
        forks.get(leftFork).isFree = true;
        forks.get(rightFork).isFree = true;
    }

    private void thinking(){
        for (Sage sage : people)
            sage.start();
    }

    @Override
    public void run() {
        thinking();
        try {
            cdl.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
