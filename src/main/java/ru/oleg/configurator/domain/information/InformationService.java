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
        numberWords.put("Single", 1);
        numberWords.put("Double", 2);
        numberWords.put("Triple", 3);
        numberWords.put("Quad", 4);
        numberWords.put("Quintuple", 5);
        numberWords.put("Sextuplet", 6);
        numberWords.put("Septuple", 7);
        numberWords.put("Octuple", 8);
        numberWords.put("Nonuple", 9);
        numberWords.put("Decuple", 10);
    }

    public Information getInformation() {
        Information information = new Information();
//        String inxiOutput = getCommandOutput("inxi -F");

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("OKEY");
        System.out.println("OKEY");
        System.out.println("OKEY");
        System.out.println();
        System.out.println();
        System.out.println();


        String inxiOutput = "System:\n" +
                "Host: host-69 Kernel: 6.1.57-un-def-alt1 x86_64 bits: 64 Desktop: KDE Plasma 5.27.8 Distro: ALT 10.2\n" +
                "Machine:\n" +
                "Type: Kvm System: QEMU product: Standard PC (i440FX + \n" +
                "PIIX, 1996) v: pc-1440fx-8.1 serial: ‹superuser required>\n" +
                "Mobo: N/A model: N/A serial: N/A BIOS: SeaBIOS v: rel-1.16.2-0-gea1b7a073390-prebuilt.qemu.org date: 04/01/2014\n" +
                "CPU:\n" +
                "Info: Triple Core model: QEMU Virtual version 2.5+ bits: 64 type: MCP cache: L2: 16 MiB\n" +
                "Speed: 3310 MHz min/max: N/A Core speeds (MHz): 1: 3310 2: 3310 3: 3310\n" +
                "Graphics: Device-1: driver: bochs-drm v: N/A\n" +
                "Display: x11 server: X.Org 1.20.14 driver: loaded: modesetting unloaded: fbdev, vesa resolution: 1440x900~50HZ\n" +
                "OpenGL: renderer: llvmpipe (LLVM 11.0.1 128 bits) v: 4.5 Mesa 23.1.8\n" +
                "Audio:\n" +
                "Message: No device data found.\n" +
                "Sound Server-1: PulseAudio V: 16.1 running: yes\n" +
                "Network:\n" +
                "Device-1: Intel 82371AB/EB/MB PIIX4 ACPI type: network bridge driver: piix4_smbus\n" +
                "Device-2: Red Hat Virtio network driver: virtio-pci\n" +
                "IF: ens18 state: up speed:\n" +
                "duplex: unknown mac: bc: 24:11:d9:e9:86\n" +
                "IF-ID-1: docker® state: down mac: 02:42:05:d8:b8:9e\n" +
                "Drives:\n" +
                "Local Storage: total: 32 GiB used: 16.36 GiB (51.1%)\n" +
                "ID-1: /dev/sda vendor: QEMU model: HARDDISK size: 32 GiB\n" +
                "Partition: ID-1:\n" +
                "size: 31.87 GiB used: 16.31 GiB (51.2%) fs: btrfs dev: /dev/sda2\n" +
                "ID-2: /home size: 31.87 GiB used: 16.31 GiB (51.2%) fs: btrfs dev: /dev/sda2\n" +
                "Swap:\n" +
                "ID-1: swap-1 type: partition size: 127 MiB used: 48.4 MiB (38.1%) dev: /dev/sda1\n" +
                "Sensors:\n" +
                "Message: No sensor data found. Is 1m-sensors configured?\n" +
                "Info:\n" +
                "Processes: 174 Uptime: 1d 16h 34m Memory: 3.82 GiB used: 1.11 GiB (29.1%) Shell: Bash inxi: 3.3.04";

        // Заполнение полей класса Information
        information.setVersionKdePlasma(parseInxiLine(inxiOutput, "KDE Plasma\\s+([^\\s]+)"));

//        information.setVersionKdeFrameworks(parseInxiLine(getCommandOutput("kf5-config --version"),
//                "KDE Frameworks:\\s+([^\\s]+)"));

        information.setVersionCore(parseCore(parseInxiLine(inxiOutput, "Kernel:\\s+([^\\s]+)"),
                parseInxiLine(inxiOutput, "bits:\\s+([^\\s]+)")));

        information.setGraphicPlatform(parseInxiLine(inxiOutput, "Display:\\s+([^\\s]+)"));

        information.setProcessors(parseProcessors(parseInxiLine(inxiOutput, "Info:\\s+([^\\s]+)"),
                parseInxiLine(inxiOutput, "model:\\s+([^\\s]+)")));

        information.setRam(parseInxiLine(inxiOutput, "Memory:\\s+([^\\s]+)"));

        information.setGraphicProcessor(parseInxiLine(inxiOutput, "renderer:\\s+([^\\s]+)"));

        information.setManufacturer(parseInxiLine(inxiOutput, "System:\\s+([^\\s]+)+ product"));

        information.setNameProduct(parseInxiLine(inxiOutput, "product:(.*?)v:"));

        information.setVersionSystem(parseInxiLine(inxiOutput, "v:\\s+([^\\s]+)"));

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
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
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

}

