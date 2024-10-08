package ru.oleg.configurator.service.utils;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class InteractionSystem {
    private final String sudoPassword = "vikaivino";  // Замените на ваш пароль
    private static final String CONFIG_FILE_PATH = "/etc/gdm3/custom.conf";

    public String executeCommand(String command) {
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

    public void changeAutoLogin(String username, boolean value) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(CONFIG_FILE_PATH));
            StringBuilder newContent = new StringBuilder();
            boolean daemonSectionFound = false;
            boolean autoLoginEnabled = false;

            for (String line : lines) {
                if (line.trim().equals("[daemon]")) {
                    daemonSectionFound = true;
                    newContent.append(line).append("\n");
                    continue;
                }
                if (daemonSectionFound && line.trim().startsWith("AutomaticLoginEnable")) {
                    newContent.append("AutomaticLoginEnable = ").append(value).append("\n");
                    autoLoginEnabled = true;
                } else if (daemonSectionFound && line.trim().startsWith("AutomaticLogin")) {
                    newContent.append("AutomaticLogin = ").append(username).append("\n");
                } else {
                    newContent.append(line).append("\n");
                }
            }
            if (!daemonSectionFound) {
                newContent.append("[daemon]").append("\n");
                newContent.append("AutomaticLoginEnable = ").append(value).append("\n");
                newContent.append("AutomaticLogin = ").append(username).append("\n");
            } else if (!autoLoginEnabled) {
                newContent.append("AutomaticLoginEnable = ").append(value).append("\n");
                newContent.append("AutomaticLogin = ").append(username).append("\n");
            }

            // Запись нового содержимого в файл с правами суперпользователя
            ProcessBuilder pb = new ProcessBuilder("sudo", "bash", "-c", "echo \"" + newContent.toString().replace("\"", "\\\"") + "\" > " + CONFIG_FILE_PATH);
            Process p = pb.start();
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        // Команда для выполнения
//        String command = "sudo apt-get update"; // Измените эту команду на нужную вам
//        String sudoPassword = "your_password"; // Замените 'your_password' на ваш пароль sudo
//
//        try {
//            // Создание процесса
//            ProcessBuilder processBuilder = new ProcessBuilder();
//            processBuilder.command("bash", "-c", command);
//
//            // Запуск процесса
//            Process process = processBuilder.start();
//
//            // Поток для ввода данных в процесс
//            OutputStream os = process.getOutputStream();
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
//
//            // Чтение вывода и ошибок процесса
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//
//            String line;
//            boolean passwordPrompted = false;
//
//            System.out.println("Output:");
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
//            }
//
//            System.out.println("\nErrors:");
//            while ((line = errorReader.readLine()) != null) {
//                System.out.println(line);
//
//                // Проверка на запрос пароля
//                if (line.toLowerCase().contains("[sudo] password for")) {
//                    passwordPrompted = true;
//                    writer.write(sudoPassword + "\n");
//                    writer.flush();
//                }
//            }
//
//            // Закрытие потока записи, если вводили пароль
//            if (passwordPrompted) {
//                writer.close();
//            }
//
//            // Ожидание завершения процесса
//            int exitCode = process.waitFor();
//            System.out.println("\nКоманда завершилась с кодом: " + exitCode);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
}
