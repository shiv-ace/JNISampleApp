package com.codeshiv.jsonparser.util;

import android.content.Context;

import com.codeshiv.jsonparser.JsonApplication;

import org.slf4j.LoggerFactory;

import java.io.File;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.FixedWindowRollingPolicy;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.RollingPolicy;
import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy;
import ch.qos.logback.core.rolling.TriggeringPolicy;
import ch.qos.logback.core.util.StatusPrinter;

public class ApplicationDataDirectories {

    public static ApplicationDataDirectories applicationDataDirectories;
    private JsonApplication jsonApplication;
    static final String LOGS_DIR_NAME = "logs";
    private static final String DEFAULT_LOG_FILENAME = "app.log";
    private static final String LOG_APPENDER = "APPLOG";
    private static final String DEFAULT_LOG_ENTRY_PATTERN = "%date %-5level [%thread] - [%method] > %msg%n";
    private static final String DEFAULT_MAX_LOG_FILE_SIZE = "2MB";
    private static final int LOG_MIN_INDEX = 1;
    private static final String DEFAULT_BACKUP_LOG_FILENAME_PATTERN = "app.%i.log";
    private String backupLogFilenamePattern = DEFAULT_BACKUP_LOG_FILENAME_PATTERN;
    private static final int DEFAULT_NUM_BACKUP_FILES = 4;
    private int numBackupFiles = DEFAULT_NUM_BACKUP_FILES;
    private String logEntryPattern = DEFAULT_LOG_ENTRY_PATTERN;
    private String maxLogFileSize = DEFAULT_MAX_LOG_FILE_SIZE;
    private File logsDir;
    private File applicationDir;

    private ApplicationDataDirectories(){

    }

    public static ApplicationDataDirectories getApplicationDataDirectories() {
        if(applicationDataDirectories == null){
            applicationDataDirectories = new ApplicationDataDirectories();
        }
        return applicationDataDirectories;
    }

    public void createApplicationDirectories(){
        jsonApplication = JsonApplication.getJsonApplication();
        Context context = jsonApplication.getApplicationContext();
        applicationDir = context.getExternalFilesDir(null);
        if (applicationDir != null) {
            applicationDir.mkdirs();
        }
        logsDir = createApplicationSubDirectory(LOGS_DIR_NAME);
        configureFileLogger();
    }

    private File createApplicationSubDirectory(String dirName) {
        final File dir = new File(applicationDir, dirName);
        mkdir(dir);
        return dir;
    }

    private void mkdir(File dir) {
        final boolean rc = dir.mkdirs();
    }

    private void configureFileLogger(){
        final File logFile = new File(logsDir,DEFAULT_LOG_FILENAME);
        final RollingFileAppender<ILoggingEvent> fileAppender = new RollingFileAppender<>();
        fileAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        fileAppender.setName(LOG_APPENDER);
        fileAppender.setFile(logFile.getAbsolutePath());

        fileAppender.setEncoder(createEncoder());
        fileAppender.setRollingPolicy(createRollingPolicy(fileAppender));
        fileAppender.setTriggeringPolicy(createTriggeringPolicy());
        fileAppender.start();

        final ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("ROOT");
        logger.addAppender(fileAppender);

        StatusPrinter.print((LoggerContext) LoggerFactory.getILoggerFactory());
    }

    private PatternLayoutEncoder createEncoder() {
        final PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        encoder.setPattern(logEntryPattern);
        encoder.start();
        return encoder;
    }

    private RollingPolicy createRollingPolicy(RollingFileAppender<ILoggingEvent> fileAppender) {
        final File backupFilePattern = new File(applicationDir, backupLogFilenamePattern);

        final FixedWindowRollingPolicy rollingPolicy = new FixedWindowRollingPolicy();
        rollingPolicy.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        rollingPolicy.setParent(fileAppender);
        rollingPolicy.setFileNamePattern(backupFilePattern.getAbsolutePath());
        rollingPolicy.setMinIndex(LOG_MIN_INDEX);
        rollingPolicy.setMaxIndex(numBackupFiles);
        rollingPolicy.start();
        return rollingPolicy;
    }

    private TriggeringPolicy<ILoggingEvent> createTriggeringPolicy() {
        final SizeBasedTriggeringPolicy<ILoggingEvent> triggeringPolicy
                = new SizeBasedTriggeringPolicy<>();
        triggeringPolicy.setMaxFileSize(maxLogFileSize);
        triggeringPolicy.start();
        return triggeringPolicy;
    }
}