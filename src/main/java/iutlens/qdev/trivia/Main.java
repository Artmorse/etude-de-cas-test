package iutlens.qdev.trivia;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class.getSimpleName());

    public static void main(String[] args) {
        System.out.println(Main.class.getSimpleName());

        System.out.println(LOGGER.getName());
        LOGGER.trace("Trace log message");
        LOGGER.debug("Debug log message");
        LOGGER.info("Info log message");
        LOGGER.error("Error log message");
        System.out.println("Hello world!");
    }
}
