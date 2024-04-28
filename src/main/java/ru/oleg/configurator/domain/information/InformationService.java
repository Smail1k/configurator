package ru.oleg.configurator.domain.information;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.oleg.configurator.domain.information.dto.Information;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class InformationService {
    private static final Map<String, Integer> numberWords = new HashMap<>();

    static {
        numberWords.put("single", 1);
        numberWords.put("double", 2);
        numberWords.put("triple", 3);
        numberWords.put("quad", 4);
        numberWords.put("quintuple", 5);
        numberWords.put("sextuple", 6);
        numberWords.put("septuple", 7);
        numberWords.put("octuple", 8);
        numberWords.put("nonuple", 9);
        numberWords.put("decuple", 10);
    }

    public Information getInformation() {
        Information information = new Information();
//        String inxiOutput = executeCommand("inxi -F");

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("OKEY");
        System.out.println("OKEY");
        System.out.println("OKEY");
        System.out.println();
        System.out.println();
        System.out.println();

//        System.out.println();
//        System.out.println();
//        System.out.println();
//        System.out.println(inxiOutput);
//        System.out.println();
//        System.out.println();
//        System.out.println();
//
//        // Заполнение полей класса Information
//        information.setVersionKdePlasma(parseInxiLine(inxiOutput, "12 KDE Plasma\\s+([^\\s]+)"));
//
////        information.setVersionKdeFrameworks(parseInxiLine(getCommandOutput("kf5-config --version"),
////                "KDE Frameworks:\\s+([^\\s]+)"));
//
//        information.setVersionCore(parseCore(parseInxiLine(inxiOutput, "12Kernel\\s+(.*?)\\s+12bits"),
//                parseInxiLine(inxiOutput, "12bits\\s+(.*?)\\s+12Console")));
//
//        information.setGraphicPlatform(parseInxiLine(inxiOutput, "12Display\\s+([^\\s]+)"));
//
//        information.setProcessors(parseProcessors(parseInxiLine(inxiOutput, "12Info\\s+(.*?)\\s+Core"),
//                parseInxiLine(inxiOutput, "12Model\\s+(.*?)\\s+12bits")));
//
//        information.setRam(parseInxiLine(inxiOutput, "12Memory\\s+(.*?)\\s+Gib"));
//
//        information.setGraphicProcessor(parseInxiLine(inxiOutput, "12renderer\\s+([^\\s]+)"));
//
//        information.setManufacturer(parseInxiLine(inxiOutput, "12System\\s+(.*?)\\s+12product"));
//
//        information.setNameProduct(parseInxiLine(inxiOutput, "12Product\\s+(.*?)\\s+12v"
//        ));
//
//        information.setVersionSystem(parseInxiLine(inxiOutput, "12v\\s+(.*?)\\s+12Serial"));

        return information;
    }

    private String getCommandOutput(String command) {
        ProcessBuilder processBuilder = new ProcessBuilder();

        processBuilder.command("bash", "-c", command);

        processBuilder.redirectErrorStream(true); // Перенаправляем стандартный поток ошибок в стандартный поток вывода

        Process process;
        try {
            process = processBuilder.start(); // Запуск процесса
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String output = reader.lines().collect(Collectors.joining("\n")); // Сбор вывода в одну строку
            process.waitFor(); // Ожидаем завершения процесса
            return output;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error executing command: " + command;
        }
    }


    // Общий метод для парсинга строки из вывода inxi
    private String parseInxiLine(String inxiOutput, String regex) {
        // Предположим, что каждое поле начинается с "12", добавим это в регулярное выражение.
        Pattern pattern = Pattern.compile("12" + regex);
        Matcher matcher = pattern.matcher(inxiOutput);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return "Неизвестно";
    }


    public String parseProcessors(String count, String model) {
        Integer number = numberWords.get(count.toLowerCase());
        if (number != null) {
            return number + " ✕ " + model;
        }
        return "";
    }

    public String parseCore(String model, String depth) {
        return String.format("%s (%s бита)", model, depth);
    }

    public static String executeCommandOnHost(String containerName, String command) throws IOException, InterruptedException {
        // Команда Docker для выполнения команды в контейнере на хосте
        String dockerCommand = "docker exec " + containerName + " " + command;

        ProcessBuilder builder = new ProcessBuilder();
        builder.command("bash", "-c", dockerCommand);

        Process process = builder.start();

        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Failed to execute host command with exit code " + exitCode);
        }

        return output.toString();
    }
}

