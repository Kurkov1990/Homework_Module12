package task2;

public class App {

    public static void main(String[] args) throws InterruptedException {
        int n = 30;
        FizzBuzzOperation task = new FizzBuzzOperation(n);

        Thread fizzThread = new FizzBuzzProcessor(task, FizzBuzzType.FIZZ);
        Thread buzzThread = new FizzBuzzProcessor(task, FizzBuzzType.BUZZ);
        Thread fizzbuzzThread = new FizzBuzzProcessor(task, FizzBuzzType.FIZZBUZZ);
        Thread numberThread = new FizzBuzzProcessor(task, FizzBuzzType.NUMBER);

        fizzThread.start();
        buzzThread.start();
        fizzbuzzThread.start();
        numberThread.start();

        task.printOutput();

        try {
            fizzThread.join();
            buzzThread.join();
            fizzbuzzThread.join();
            numberThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\nCompleted.");
    }
}