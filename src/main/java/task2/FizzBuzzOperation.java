package task2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FizzBuzzOperation {
    private final int n;
    private volatile int current = 1;
    private final BlockingQueue<String> outputQueue = new LinkedBlockingQueue<>();
    private final Object lock = new Object();

    public FizzBuzzOperation(int n) {
        this.n = n;
    }

    public void fizz() {
        while (true) {
            synchronized (lock) {
                while (current <= n && !(current % 3 == 0 && current % 5 != 0)) {
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
                outputQueue.offer("fizz");
                current++;
                lock.notifyAll();
            }
        }
    }

    public void buzz() {
        while (true) {
            synchronized (lock) {
                while (current <= n && !(current % 5 == 0 && current % 3 != 0)) {
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
                outputQueue.offer("buzz");
                current++;
                lock.notifyAll();
            }
        }
    }

    public void fizzbuzz() {
        while (true) {
            synchronized (lock) {
                while (current <= n && !(current % 3 == 0 && current % 5 == 0)) {
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
                outputQueue.offer("fizzbuzz");
                current++;
                lock.notifyAll();
            }
        }
    }

    public void number() {
        while (true) {
            synchronized (lock) {
                while (current <= n && (current % 3 == 0 || current % 5 == 0)) {
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
                outputQueue.offer(String.valueOf(current));
                current++;
                lock.notifyAll();
            }
        }
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