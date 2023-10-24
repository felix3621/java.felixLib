package io.github.felix3621.felixLib;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Logger {
    //basics
    private final boolean host;
    public enum logLevel {DEBUG,INFO,WARN,ERROR}
    private record logElement(String name, logLevel level, String date, String time, String message) {}
    private final String name;

    //host
    public final Path logFile;
    private final Queue<logElement> logQueue;
    private final Thread logThread;
    private boolean logLoop;
    private boolean logStop;

    //slave
    private final Logger parent;


    //init
    /**
     * Create a Logger, and automatically start it
     * Remember to end the Logger before the program stops, as it runs on a threaded loop
     * @param logPath The path in which the log should be created in
     * @param name The name of this Logger
     */
    public Logger(Path logPath, String name) {
        this.host = true;
        this.name = name;

        //ensure directory exists
        if (!Files.isDirectory(logPath)) {
            try {
                Files.createDirectory(logPath);
            } catch (IOException e) {
                System.out.println("An error occurred trying to create directory");
                e.printStackTrace();
            }
        }

        //filename
        String fileName = logPath + "\\" + getDate();
        int id = 0;
        while (true) {
            if (!Files.exists(Path.of(fileName + ((id == 0) ? "" : "-"+id) + ".log"))) {
                break;
            }
            id++;
        }

        //initialize file
        this.logFile = Path.of(fileName + ((id == 0) ? "" : "-"+id) + ".log");
        getOrCreateFile();

        //initialize logging system
        this.logQueue = new ConcurrentLinkedQueue<>();
        this.logThread = new Thread(this::loggingScript);

        this.parent = null;
        this.start();
    }

    /**
     * Create a Logger, and automatically start it
     * Remember to end the Logger before the program stops, as it runs on a threaded loop
     * @param logFile The File to log to
     * @param name The name of this Logger
     */
    public Logger(File logFile, String name) {
        this.host = true;
        this.name = name;

        //initialize file
        this.logFile = logFile.toPath();
        getOrCreateFile();

        //initialize logging system
        this.logQueue = new ConcurrentLinkedQueue<>();
        this.logThread = new Thread(this::loggingScript);

        this.parent = null;
        this.start();
    }

    /**
     * Create a subcategory in the parent Logger
     * stopping/starting the thread will run on the topmost parent, if run on this
     * @param parent  the Logger that this will be a subcategory of
     * @param name    the name of this subcategory
     */
    public Logger(Logger parent, String name) {
        this.host = false;
        this.parent = parent;
        this.name = name;

        this.logFile = null;
        this.logQueue = null;
        this.logThread = null;
    }


    //log loop
    private void loggingScript() {
        this.logLoop = true;
        while (this.logLoop) {
            if (!this.logQueue.isEmpty()) {
                logElement entry = this.logQueue.poll();
                String line = MessageFormat.format(
                        "[{0} {1}] [{2}/{3}]: {4}\n",
                        entry.date, entry.time, entry.name, entry.level, entry.message
                );
                System.out.print(line);
                try {
                    Writer writer = new FileWriter(getOrCreateFile(), true);
                    writer.append(line);
                    writer.close();
                } catch (IOException e) {
                    System.out.println("An error occurred trying to write log");
                    e.printStackTrace();
                }
            } else if (this.logStop) {
                this.logLoop = false;
            }
        }
    }


    //log thread control
    public void start () {
        if (this.host) {
            this.logStop = false;
            if (!this.logThread.isAlive()) this.logThread.start();
        } else {
            this.parent.start();
        }
    }
    public void end() {
        if (this.host) {
            this.logStop = true;
        } else {
            this.parent.end();
        }
    }
    public void forcEnd() {
        if (this.host) {
            this.logLoop = true;
        } else {
            this.parent.forcEnd();
        }
    }


    //create logElement
    public void addLogEntry(logLevel level, String message) {
        logElement entry = new logElement(this.name, level, getDate(), getTime(), message);
        if (this.host) {
            this.logQueue.offer(entry);
        } else {
            this.parent.addLogEntry(entry);
        }
    }

    /**
     * Pass a {@code logElement} to this Logger, to be a subElement of this Logger
     * @param log The {@code logElement} to pass down
     */
    public void addLogEntry(logElement log) {
        logElement newLog = new logElement(this.name+"/"+log.name, log.level, log.date, log.time, log.message);
        if (this.host) {
            this.logQueue.offer(newLog);
        } else {
            this.parent.addLogEntry(newLog);
        }
    }


    //logging levels
    public void debug(String message) {
        this.addLogEntry(logLevel.DEBUG, message);
    }
    public void info(String message) {
        this.addLogEntry(logLevel.INFO, message);
    }
    public void warn(String message) {
        this.addLogEntry(logLevel.WARN, message);
    }
    public void error(String message) {
        this.addLogEntry(logLevel.ERROR, message);
    }

    private static String getDate() {
        Calendar c = Calendar.getInstance();
        String year = String.valueOf(c.get(Calendar.YEAR));
        String month = String.format("%02d", c.get(Calendar.MONTH)+1);
        String day = String.format("%02d", c.get(Calendar.DATE));

        return year+"-"+month+"-"+day;
    }

    private static String getTime() {
        Calendar c = Calendar.getInstance();
        String hour = String.format("%02d", c.get(Calendar.HOUR));
        String minute = String.format("%02d", c.get(Calendar.MINUTE));
        String second = String.format("%02d", c.get(Calendar.SECOND));

        return hour+":"+minute+":"+second;
    }

    public File getOrCreateFile () {
        if (this.host) {
            try {
                if (!this.logFile.toFile().exists()) this.logFile.toFile().createNewFile();
            } catch (IOException e) {
                System.out.println("An error occurred trying to create file");
                e.printStackTrace();
            }
            return this.logFile.toFile();
        } else {
            return this.parent.getOrCreateFile();
        }
    }
}
