package ru.oleg.configurator.domain.utils;

import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExecuteCommand {
    private static final String sudoPassword = "vikaivino";  // Замените на ваш пароль

    public static String executeCommand(String command) {
        StringBuilder output = new StringBuilder();
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", "echo " + sudoPassword + " | sudo -S " + command);

        try {
            Process process = processBuilder.start();

            try (OutputStreamWriter writer = new OutputStreamWriter(process.getOutputStream());
                 BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                 BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {

                String line;
                AtomicBoolean passwordRequested = new AtomicBoolean(false);

                // Чтение ошибок и проверка на запрос пароля
                new Thread(() -> {
                    String errorLine;
                    try {
                        while ((errorLine = errorReader.readLine()) != null) {
                            System.err.println("ERROR: " + errorLine);
                            if (errorLine.toLowerCase().contains("sudo")) {
                                passwordRequested.set(true);
                                writer.write(sudoPassword + "\n");
                                writer.flush();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();

                // Чтение основного вывода
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }

                process.waitFor();
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }

        return output.toString().trim();
    }

    public static String extractData(String totalDataInfo, String pattern) {
        String data = null;

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(totalDataInfo);

        if (m.find()) {
            try {
                data = m.group(1);  // Извлечение первой захватывающей группы
            } catch (Exception e) {
                data = m.group(0);
            }
        }
        return data;
    }

    public static void main(String[] args) {
        // Команда для выполнения
        String command = "sudo apt-get update"; // Измените эту команду на нужную вам
        String sudoPassword = "your_password"; // Замените 'your_password' на ваш пароль sudo

        try {
            // Создание процесса
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("bash", "-c", command);

            // Запуск процесса
            Process process = processBuilder.start();

            // Поток для ввода данных в процесс
            OutputStream os = process.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));

            // Чтение вывода и ошибок процесса
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String line;
            boolean passwordPrompted = false;

            System.out.println("Output:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            System.out.println("\nErrors:");
            while ((line = errorReader.readLine()) != null) {
                System.out.println(line);

                // Проверка на запрос пароля
                if (line.toLowerCase().contains("[sudo] password for")) {
                    passwordPrompted = true;
                    writer.write(sudoPassword + "\n");
                    writer.flush();
                }
            }

            // Закрытие потока записи, если вводили пароль
            if (passwordPrompted) {
                writer.close();
            }

            // Ожидание завершения процесса
            int exitCode = process.waitFor();
            System.out.println("\nКоманда завершилась с кодом: " + exitCode);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
