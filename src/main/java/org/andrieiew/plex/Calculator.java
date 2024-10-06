package org.andrieiew.plex;

import org.andrieiew.plex.logging.Logger;
import org.andrieiew.plex.logging.SimpleLogger;
import org.andrieiew.plex.params.AppParams;

import java.math.BigInteger;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Calculator implements AutoCloseable {
    private final ExecutorService executor;
    private Futures futures;

    public Calculator(AppParams appParams, Futures futures) {
        this.executor = Executors.newFixedThreadPool(appParams.getThreadsCount());
        this.futures = futures;
    }

    private static final Logger logger = SimpleLogger.getInstance();

    public void factorial(String value) {
        if (executor.isShutdown() || executor.isTerminated()) {
            throw new IllegalStateException("Calculator is closed");
        }
            Callable<String> callable = () -> {
            try {
                int number = 0;
                String result;
                try {
                    number = Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    return value;
                }
                if (number <= 0) {
                    result = number + " = " + 0;
                } else if (number <= 20) {
                    result = calculateSmallNumberFactorial(number);
                } else {
                    result = calculateBigNumberFactorial(number);
                }
                return result;
            } finally {
                futures.notifyThatReady();
            }
        };
        Future<String> future = executor.submit(callable);
        futures.add(future);
    }

    private static String calculateBigNumberFactorial(int number) {
        BigInteger factorial = BigInteger.ONE;
        for (int i = 1; i <= number; i++) {
            factorial = factorial.multiply(BigInteger.valueOf(i));
        }
        return number + " = " + factorial;
    }

    private static String calculateSmallNumberFactorial(int number) {
        long factorial = 1;
        for (int i = 1; i <= number; i++) {
            factorial *= i;
        }
        return number + " = " + factorial;
    }

    @Override
    public void close() {
        executor.shutdown();
        futures.setReadingFinished();
    }
}