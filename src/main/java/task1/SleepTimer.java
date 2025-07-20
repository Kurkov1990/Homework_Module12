package task1;

public class SleepTimer {

    private final long startTime = System.currentTimeMillis();
    private final Object lock = new Object();

    private volatile boolean isRunning = true;
    private volatile boolean fiveSecondFlag = false;

    public static void main(String[] args) {
        new SleepTimer().startTimer();
    }

    public void startTimer() {
        Thread secondThread = new Thread(() -> {
            while (isRunning) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }

                long currentTime = System.currentTimeMillis();
                long elapsedSeconds = (currentTime - startTime) / 1000;
                System.out.println("Time from starting: " + elapsedSeconds + " seconds");

                if (elapsedSeconds % 5 == 0) {
                    synchronized (lock) {
                        fiveSecondFlag = true;
                        lock.notify();
                    }
                }

                if (elapsedSeconds >= 60) {
                    isRunning = false;
                    synchronized (lock) {
                        lock.notify();
                    }
                }
            }

            synchronized (lock) {
                lock.notifyAll();
            }
        });

        Thread fiveSecondThread = new Thread(() -> {
            while (isRunning) {
                synchronized (lock) {
                    while (!fiveSecondFlag && isRunning) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            break;
                        }
                    }

                    if (fiveSecondFlag) {
                        System.out.println("5 seconds have passed");
                        fiveSecondFlag = false;
                    }
                }
            }
        });

        secondThread.start();
        fiveSecondThread.start();

        try {
            secondThread.join();
            fiveSecondThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Timer finished.");
    }
}
