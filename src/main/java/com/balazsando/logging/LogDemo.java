package com.balazsando.logging;

import java.io.IOException;
import java.util.logging.*;

public class LogDemo {
    static Logger pkgLogger = Logger.getLogger("com.balazsando.logging");
    static Logger logger = Logger.getLogger("com.balazsando.logging.LogDemo");

    public static void main(String[] args) throws IOException {
        System.out.println("Config file: " + System.getProperty("java.util.logging.config.file"));

//        basicLogging();
        alternativeLogging();
//        doWork();
//        doParametized("Jim", "Wilson");
//        doHandlerFormatter();
//        doFileHandler();
    }

    private static void doFileHandler() throws IOException {
        FileHandler fh = new FileHandler("%h/myapp_%g.log", 1000, 4);
        fh.setFormatter(new SimpleFormatter());
        logger.addHandler(fh);
        logger.log(Level.INFO, "My first log message");
        logger.log(Level.INFO, "Another message");
    }

    private static void doHandlerFormatter() {
        Handler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        Formatter formatter = new SimpleFormatter();
        handler.setFormatter(formatter);
        logger.addHandler(handler);
        logger.setLevel(Level.ALL);
        logger.entering(LogDemo.class.getSimpleName(), "doHandlerFormatter");
    }

    private static void doParametized(String left, String right) {
        logger.log(Level.INFO, "{0} is my favourite", "Java");
        logger.log(Level.INFO, "{0} is {1} day from {2}", new Object[]{"Wed", 2, "Fri"});

        logger.setLevel(Level.ALL);
        logger.entering(LogDemo.class.getSimpleName(), "doParametized", new Object[]{left, right});
    }

    private static void basicLogging() {
        LogManager lm = LogManager.getLogManager();
        Logger logger = lm.getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.log(Level.INFO, "My first log message");
        logger.log(Level.INFO, "Another message");
    }

    private static void alternativeLogging() {
        logger.log(Level.INFO, "My first log message");
        logger.log(Level.INFO, "Another message");
        logger.log(Level.FINE, "Fine message");
        logger.log(Level.FINER, "Finer message");
        logger.severe("Ug oh!");
    }

    private static void doWork() {
        logger.setLevel(Level.ALL);
        logger.entering(LogDemo.class.getSimpleName(), "doWork");
        logger.logp(Level.WARNING, LogDemo.class.getSimpleName(), "doWork", "Empty Function");
        logger.exiting(LogDemo.class.getSimpleName(), "doWork");
    }
}
