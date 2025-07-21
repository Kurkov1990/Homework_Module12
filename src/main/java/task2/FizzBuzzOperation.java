package task2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.IntPredicate;

public class FizzBuzzOperation {
    private final int n;
    private volatile int current = 1;
    private final BlockingQueue<String> outputQueue = new LinkedBlockingQueue<>();
    private final Object lock = new Object();

    public FizzBuzzOperation(int n) {
        this.n = n;
    }

    private void processTask(IntPredicate condition, String output) {
        while (true) {
            synchronized (lock) {
                while (current <= n && !condition.test(current)) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                if (current > n) {
                    lock.notifyAll();
                    return;
                }
                outputQueue.offer(output.equals("number") ? String.valueOf(current) : output);
                current++;
                lock.notifyAll();
            }
        }
    }

    public void fizz() {
        processTask(i -> i % 3 == 0 && i % 5 != 0, "fizz");
    }

    public void buzz() {
        processTask(i -> i % 5 == 0 && i % 3 != 0, "buzz");
    }

    public void fizzbuzz() {
        processTask(i -> i % 3 == 0 && i % 5 == 0, "fizzbuzz");
    }

    public void number() {
        processTask(i -> i % 3 != 0 && i % 5 != 0, "number");
    }

    public void printOutput() {
        int number = 0;
        while (number < n) {
            try {
                String val = outputQueue.take();
                System.out.print(val);
                number++;
                if (number < n){
                    System.out.print(", ");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
