package spring.gemfire.showcase.account.csv.functions;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Supplier;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

/**
 * Watched for files in a given directory.
 *
 * @Author Gregory Green
 */
@Component
@Slf4j
public class FileSupplier implements Supplier<String> {
    private final WatchService watcher;
    private final Path dir;
    private final WatchKey registrationKey;
    private final Queue<String> queue = new ConcurrentLinkedQueue<>();
    private long sleepDelay = 1000;

    public FileSupplier(@Value("${file.directory}")String directory,@Value("${file.modification.delay.ms:1000}") long sleepDelay) throws IOException {

        this.dir = Paths.get(directory);
        log.info("file.directory: {} ",dir.toFile().getAbsolutePath());
        this.watcher = FileSystems.getDefault().newWatchService();
        this.registrationKey = dir.register(watcher,
                ENTRY_CREATE,
                ENTRY_MODIFY);
    }

    @SneakyThrows
    @Override
    public String get() {

        if (queue.isEmpty())
            fillQueue();

        return queue.poll();
    }

    private void fillQueue() throws InterruptedException, IOException {
        try
        {
            var watchKey = watcher.take();

            for (WatchEvent<?> event : watchKey.pollEvents()) {
                var kind = event.kind();
                var watchEvent = (WatchEvent<Path>) event;
                var filePath = watchEvent.context();
                var file = Paths.get(dir.toFile().getAbsolutePath(),filePath.toFile().getName());

                log.info("Reading file: {}",file.toFile().getAbsolutePath());

                //watch for changes
                long previousFileSize;
                long currentFileSize;
                do
                {
                    previousFileSize = file.toFile().length();
                    Thread.sleep(sleepDelay);
                    currentFileSize = file.toFile().length();
                } while(currentFileSize  != previousFileSize);

                try (Scanner reader = new Scanner(file)) {
                    while (reader.hasNextLine()) {
                        String line = reader.nextLine();
                        log.info(line);
                        queue.add(line);
                    }
                }
            }
        }
        finally {
            registrationKey.reset();
        }

    }
}
