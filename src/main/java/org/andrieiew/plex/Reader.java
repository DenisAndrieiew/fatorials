package org.andrieiew.plex;

import org.andrieiew.plex.logging.Logger;
import org.andrieiew.plex.logging.SimpleLogger;
import org.andrieiew.plex.params.AppParams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Reader {
    private final AppParams params;
    private final Logger logger;

    public Reader(AppParams params) {
        this.params = params;
        this.logger = SimpleLogger.getInstance();
    }

    public void read(Futures futures) {
        new Thread(() -> {
            logger.log("Reading file: " + params.getFileName());

            Path path = Paths.get(params.getFileName());
            long currentTimeMillis = System.currentTimeMillis();
            try (Stream<String> linesStream = Files.lines(path);
                 Calculator calculator = new Calculator(params, futures)) {

                linesStream.forEach(calculator::factorial);
                logger.log("Reading finished in " + (System.currentTimeMillis() - currentTimeMillis) + " ms");
            } catch (IOException ignored) {
                logger.log("File not found");
            }
        }).start();
    }
}
