package org.andrieiew.plex;

import org.andrieiew.plex.logging.Logger;
import org.andrieiew.plex.logging.SimpleLogger;
import org.andrieiew.plex.params.AppParams;

import java.math.BigInteger;
import java.util.concurrent.*;

public class Calculator implements AutoCloseable {
    private final ExecutorService executor;
    private Futures futures;
    private int readCount = 0;
    private int processedCount = 0;
    private final boolean withDelay;
    Semaphore semaphore = new Semaphore(100);

    public Calculator(AppParams appParams, Futures futures) {
        this.executor = Executors.newFixedThreadPool(appParams.getThreadsCount());
        this.futures = futures;
        this.withDelay = !appParams.isSkipDelays();
    }

    private static final Logger logger = SimpleLogger.getInstance();

    public void factorial(String value) {
        if (executor.isShutdown() || executor.isTerminated()) {
            throw new IllegalStateException("Calculator is closed");
        }
        logger.log(readCount++);
        Callable<String> callable = () -> {
            long currentTimeMillis = System.currentTimeMillis();
            try {
                acquire();
                return getresult(value);
            } finally {
                release(currentTimeMillis);
                futures.notifyThatReady();
            }
        };

        Future<String> future = executor.submit(callable);
        futures.add(future);
    }

    private void acquire() {
        if (withDelay) {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                logger.log("Interrupted: " + e.getMessage());
            }
        }
    }

    private void release(long startTime) {
        if (withDelay) {
            long time = System.currentTimeMillis() - startTime;
            logger.log("count: " + ++processedCount + " time:" + time);
            if (time < 1000) {
                try {
                    Thread.sleep(1000 - time);
                } catch (InterruptedException e) {
                    logger.log("Interrupted: " + e.getMessage());
                }
            }
            semaphore.release();
        }
    }

    private static String getresult(String value) {
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
            result = value + " = " + calculateSmallNumberFactorial(number);
        } else {
            result = value + " = " + calculateBigNumberFactorial(number);
        }
        return result;
    }

    private static BigInteger calculateBigNumberFactorial(int number) {
        BigInteger factorial = BigInteger.ONE;
        for (int i = 1; i <= number; i++) {
            factorial = factorial.multiply(BigInteger.valueOf(i));
        }
        return factorial;
    }

    private static long calculateSmallNumberFactorial(int number) {
        long factorial = 1;
        for (int i = 1; i <= number; i++) {
            factorial *= i;
        }
        return factorial;
    }

    @Override
    public void close() {
        executor.shutdown();
        futures.setReadingFinished();
    }
}