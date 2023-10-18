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
    private enum logLevel {DEBUG,INFO,WARN,ERROR}
    private record logElement(logLevel level, String date, String time, String message) {};
    private final File logFile;
    private final Queue<logElement> logQueue;
    private static final String eol = System.getProperty("line.separator");
    private final String name;

    private final Thread logThread;
    private boolean logLoop;
    private boolean logStop;

    /**
     * Create a logger, and automatically start it
     * Remember to end the logger before the program stops, as it runs on a threaded loop
     * @param logPath The path in which the log should be created in
     * @param name The name of this logger
     */
    public Logger(Path logPath, String name) {
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
        this.logFile = new File(fileName + ((id == 0) ? "" : "-"+id) + ".log");
        this.createFile();

        this.name = name;

        //initialize the logging system
        this.logQueue = new ConcurrentLinkedQueue<>();
        this.logThread = new Thread(this::loggingScript);
        this.start();
    }

    private void loggingScript() {
        this.logLoop = true;
        while (this.logLoop) {
            if (!this.logQueue.isEmpty()) {
                logElement entry = this.logQueue.poll();
                String line = MessageFormat.format("[{0} {1}] [{2}/{3}]: {4}{5}", entry.date, entry.time, this.name, entry.level, entry.message, eol);
                System.out.print(line);
                try {
                    Writer writer = new FileWriter(this.logFile, true);
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

    public void end() {
        this.logStop = true;
    }
    public void start() {
        this.logStop = false;
        if (!this.logThread.isAlive()) this.logThread.start();
    }

    public void forceEnd() {
        this.logLoop = false;
    }

    private void createNewEntry(logLevel level, String message) {
        logElement entry = new logElement(level, getDate(), getTime(), message);
        this.logQueue.offer(entry);
    }

    public void debug(String message) {
        this.createNewEntry(logLevel.DEBUG, message);
    }
    public void info(String message) {
        this.createNewEntry(logLevel.INFO, message);
    }
    public void warn(String message) {
        this.createNewEntry(logLevel.WARN, message);
    }
    public void error(String message) {
        this.createNewEntry(logLevel.ERROR, message);
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

    private void createFile() {
        try {
            this.logFile.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred trying to create file");
            e.printStackTrace();
        }
    }
}
