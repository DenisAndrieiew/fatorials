package org.andrieiew.plex.logging;

import org.andrieiew.plex.params.AppParams;

public class SimpleLogger implements Logger {
    private final boolean isDebug;

    private static SimpleLogger instance;

    private SimpleLogger(AppParams appParams) {
        this.isDebug = appParams.isDebug();
    }

    public static SimpleLogger getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Logger is not initialized, please use createLogger method to create logger instance");
        }
        return instance;
    }


    public static SimpleLogger createLogger(AppParams appParams) {
        if (instance != null) {
            throw new IllegalStateException("Logger is already initialized, please use getInstance method to get logger instance");
        }
        instance = new SimpleLogger(appParams);
        return instance;
    }

    @Override
    public void log(Object message) {
        if (isDebug) {
            System.out.println(message);
        }
    }
}
