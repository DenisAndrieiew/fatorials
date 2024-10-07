package org.andrieiew.plex;

import lombok.SneakyThrows;
import org.andrieiew.plex.logging.Logger;
import org.andrieiew.plex.logging.SimpleLogger;
import org.andrieiew.plex.params.Dialog;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Writer {
    private static final String FILE_NAME = "output.txt";
    private static final Logger logger = SimpleLogger.getInstance();
    private final Futures futures;

    public Writer(Futures futures) {
        this.futures = futures;
    }

    @SneakyThrows
    public void write() {
        new Thread(() -> {
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(FILE_NAME))) {

                while (true) {
                    Future<String> future;

                    synchronized (futures) {
                        if (!futures.isFutureReady()) {
                            if (futures.isEmpty() && futures.isReadingFinished()) {
                                Dialog.getInstance().getString("All tasks are done. Press Enter to exit.");
                                break;
                            }
                            futures.wait();
                            continue;
                        } else {
                            future = futures.poll();
                        }
                    }

                    if (future != null) {
                        String result = future.get();
                        if (result != null) {
                            writer.write(result);
                            writer.newLine();
                        }
                    }
                }
            } catch (IOException | InterruptedException | ExecutionException e) {
                logger.log(e.getMessage());
        }}).start();

    }
}
