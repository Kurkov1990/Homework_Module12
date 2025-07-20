package task2;

public class FizzBuzzProcessor extends Thread {

    private final FizzBuzzOperation operation;
    private final FizzBuzzType type;

    public FizzBuzzProcessor(FizzBuzzOperation operation, FizzBuzzType type) {
        this.operation = operation;
        this.type = type;
    }

    @Override
    public void run() {
            switch (type) {
                case FIZZ -> operation.fizz();
                case BUZZ -> operation.buzz();
                case FIZZBUZZ -> operation.fizzbuzz();
                case NUMBER -> operation.number();
            }
        }
    }
