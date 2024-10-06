package org.andrieiew.plex;

import lombok.SneakyThrows;
import org.andrieiew.plex.logging.Logger;
import org.andrieiew.plex.logging.SimpleLogger;
import org.andrieiew.plex.params.AppParams;
import org.andrieiew.plex.params.RunParamsResolver;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        AppParams appParams = RunParamsResolver.resolve(args);
        Logger logger = SimpleLogger.createLogger(appParams);
        logger.log(appParams.getCacheType());
        logger.log(appParams.getFileName());
        logger.log(appParams.getThreadsCount());
        logger.log(appParams.isSkipEmptyLines());
        logger.log(appParams.isSkipStrings());
        Futures futures = new Futures();
        Reader reader = new Reader(appParams);
        reader.read(futures);
        Writer writer = new Writer(futures);
        writer.write();
    }
}