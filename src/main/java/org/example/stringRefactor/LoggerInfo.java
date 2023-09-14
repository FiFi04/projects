package org.example.stringRefactor;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class LoggerInfo {
    private final static String LOGGER_FILE_PATH = "src/main/resources/stringRefactorApp/loggerFile.txt";
    private static LoggerInfo loggerInfo;
    private static File loggerFile = new File(LOGGER_FILE_PATH).getAbsoluteFile();
    private static BufferedWriter writer;
    private final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";


    private LoggerInfo() {
    }

    static {
        try {
            writer = new BufferedWriter(new FileWriter(loggerFile, true));
        } catch (IOException e) {
            String exceptionMessage = "Błąd inicjalizacji loggera";
            System.err.println(exceptionMessage);
            LoggerInfo.logAnException(e, exceptionMessage);
        }
    }

    public static LoggerInfo getInstance() {
        if (loggerInfo == null) {
            loggerInfo = new LoggerInfo();
        }
        return loggerInfo;
    }

    public void log(StringRefactorController stringRefactorController, int chosenOption, String source,
                    String target) {
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
        chosenOption = chosenOption != 0 ? chosenOption - 1 : StringRefactorController.EXIT_OPTION_INDEX;
        String className = stringRefactorController.getStringRefactors().get(chosenOption).getClass().getSimpleName();
        String optionDescription = stringRefactorController.getStringRefactors().get(chosenOption).getInfo();
        StringBuilder sb = createLogMessage(source, target, currentTime, className, optionDescription);
        saveLogToFile(sb);
    }

    public static void logAnException(Exception exception, String message) {
        StackTraceElement[] stackTrace = exception.getStackTrace();
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
        StringBuilder sb = new StringBuilder();
        sb.append(currentTime);
        sb.append(" - exception message: ");
        sb.append(message);
        sb.append(" - ");
        sb.append(Arrays.toString(stackTrace));
        saveLogToFile(sb);
    }

    private static void saveLogToFile(StringBuilder log) {
        try {
            writer.write(log.toString());
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            String exceptionMessage = "Błąd zapisu do pliku";
            System.err.println(exceptionMessage);
            logAnException(e, exceptionMessage);
        }
    }

    private StringBuilder createLogMessage(String source, String target, String currentTime, String className, String optionDescription) {
        StringBuilder sb = new StringBuilder();
        sb.append(currentTime);
        sb.append(" - ");
        sb.append(className);
        sb.append(" - ");
        sb.append(optionDescription);
        sb.append(" - ");
        sb.append("source: ");
        sb.append(source);
        sb.append(" -> target: ");
        sb.append(target);
        return sb;
    }

}
