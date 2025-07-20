package task1;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import static java.util.concurrent.Executors.newScheduledThreadPool;

public class SheduledTimer {

    private final long startTime = System.currentTimeMillis();
    private final ScheduledExecutorService scheduler = newScheduledThreadPool(2);


    public static void main(String[] args) {
        new SheduledTimer().startTimer();
    }

    public void startTimer() {
        Runnable everySecondTask = () -> {
            long elapsed = (System.currentTimeMillis() - startTime) / 1000;
            System.out.println("Time from starting: " + elapsed + " seconds");
        };

        Runnable everyFiveSecondsTask = () -> System.out.println("5 seconds have passed");

        scheduler.scheduleAtFixedRate(everySecondTask, 1, 1, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(everyFiveSecondsTask, 5, 5, TimeUnit.SECONDS);

        scheduler.schedule(() -> {
            System.out.println("Timer finished.");
            scheduler.shutdown();
        }, 60, TimeUnit.SECONDS);

    }
}
