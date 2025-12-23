package databases;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BaseDatabase {

    protected final Logger logger = Logger.getLogger(getClass().getName());

    protected void ensureFileExists(String filename) {
        try {
            File file = new File(filename);
            if (!file.exists()) {
                file.createNewFile();
                logger.info("Created file: " + filename);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error creating file: " + filename, e);
        }
    }

    protected List<String> readLines(String filename) {
        try {
            return Files.readAllLines(Paths.get(filename));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading file: " + filename, e);
            return new ArrayList<>();
        }
    }

    protected void writeLines(String filename, List<String> lines) {
        try {
            Files.write(Paths.get(filename), lines);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error writing file: " + filename, e);
        }
    }
}
