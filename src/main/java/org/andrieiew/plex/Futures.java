package org.andrieiew.plex;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Future;

public class Futures {
    private final List<Future<String>> futures = new LinkedList<>();
    @Getter
    private boolean readingFinished = false;

    public synchronized void add(Future<String> future) {
        futures.add(future);
    }

    public synchronized void setReadingFinished() {
        readingFinished = true;
        notifyAll();
    }

    public synchronized Future<String> poll() {
        if (futures.isEmpty()) {
            return null;
        }
        return futures.remove(0);
    }

    public synchronized boolean isFutureReady() {
        if (futures.isEmpty()) {
            return false;
        }
        return futures.get(0).isDone();
    }

    public synchronized void notifyThatReady() {
        notifyAll();
    }

    public boolean isEmpty() {
        return futures.isEmpty();
    }
}
