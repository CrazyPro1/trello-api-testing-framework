package com.trello.api.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4jUtil {
    private static final Logger logger = LogManager.getLogger(Log4jUtil.class);

    public static void info(String message) {
        logger.info(message);
    }

    public static void error(String message) {
        logger.error(message);
    }
}
