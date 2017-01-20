package com.chen.utils;


import com.chen.enums.LoggerEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Chenwl
 * @version 1.0.0
 */
public class LoggerUtil {

    private static final Logger LOGGER= LoggerFactory.getLogger(LoggerEnum.DEFAULT.toString());
    private static final Logger ERRLOGGER= LoggerFactory.getLogger(LoggerEnum.ERROR.toString());

    /**
     *
     * @param logMessage 日志信息
     */
    public static void log(String logMessage) {

        LOGGER.info(logMessage);

    }

    /**
     *
     * @param logName 对应的日志名称
     * @param logMessage 日志信息
     */
    public static void log(LoggerEnum logName, String logMessage) {
        Logger logger = getLogger(logName);
        if(!logName.equals(LoggerEnum.ERROR)) {
            logger.info(logMessage);
        } else {
            logger.error(logMessage);
        }
    }

    private static Logger getLogger(LoggerEnum logName) {
        if(LoggerEnum.DEFAULT.equals(logName)){
            return LOGGER;
        }else if(LoggerEnum.ERROR.equals(logName)){
            return ERRLOGGER;
        }else {
            return LoggerFactory.getLogger(logName.toString());
        }

    }

    /**
     *
     * @param logName 对应的日志名称
     * @param logMessage 日志信息
     */
    public static void log(String logName, String logMessage) {
        Logger logger = LoggerFactory.getLogger(logName);
        logger.info(logMessage);
    }

    /**
     *
     * @param t 对应的类
     * @param logMessage 日志信息
     */
    public static void log(Class t, String logMessage) {
        Logger logger = LoggerFactory.getLogger(t);
        logger.info(logMessage);
    }




    /**
     * 保存异常日志
     */
    public static void logException(LoggerEnum loggerName, Exception e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        e.printStackTrace(writer);
        StringBuffer buffer = stringWriter.getBuffer();
        Logger logger = getLogger(loggerName);
        logger.info(buffer.toString());
    }

    /**
     * 保存异常日志
     */
    public static void logException(Exception e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        e.printStackTrace(writer);
        StringBuffer buffer = stringWriter.getBuffer();
        ERRLOGGER.info(buffer.toString());
    }

    public static void setExceptionLog(Exception e) {
        /**
         * 日志保存
         */
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        e.printStackTrace(writer);
        StringBuffer buffer = stringWriter.getBuffer();
        LoggerUtil.log(LoggerEnum.ERROR, buffer.toString());
    }

    /**
     * 系统异常字符串日志保存
     */
    public static void saveExceptionLog(Exception e, String exceptionString) {
        String exceptionLog = getExceptionString(e);
        LoggerUtil.log(LoggerEnum.ERROR, exceptionLog + " \n " + exceptionString);
    }

    public static String getExceptionString(Exception e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        e.printStackTrace(writer);
        StringBuffer buffer = stringWriter.getBuffer();
        return buffer.toString();
    }
}
