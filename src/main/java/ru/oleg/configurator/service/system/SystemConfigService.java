package ru.oleg.configurator.service.system;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.oleg.configurator.service.system.dto.SystemConfig;
import ru.oleg.configurator.service.utils.InteractionSystem;
import ru.oleg.configurator.service.utils.Parser;

import java.util.Objects;


@Service
@AllArgsConstructor
public class SystemConfigService {
    private final InteractionSystem interactionSystem;
    private final Parser parsers;

    public SystemConfig getInformation() {
        SystemConfig systemConfig = new SystemConfig();

        systemConfig.setDeviceName(makeDeviceName());
        systemConfig.setRamMemory(makeRamMemoryInfo());
        systemConfig.setCpu(makeCpuInfo());
        systemConfig.setGraphics(makeGraphicsInfo());
        systemConfig.setCapacityDisk(makeRomMemoryInfo());
        systemConfig.setOsName(makeOsName());
        systemConfig.setOsType(makeOsType());
        systemConfig.setEnvironmentVersion(makeEnvironmentVersion());
        systemConfig.setInterfaceWindow(makeWindowInterface());
        systemConfig.setVirtualization(makeVirtualization());

        return systemConfig;
    }

    private String makeDeviceName() {
        return interactionSystem.executeCommand("hostname");
    }

    private String makeRamMemoryInfo() {
        String memoryInfo;
        memoryInfo = interactionSystem.executeCommand("free -h");
        if (memoryInfo != null) {
            return parsers.extractData(memoryInfo, "Память:\\s+([\\d,]+)\\s*Gi");
        } else {
            return null;
        }
    }

    private String makeCpuInfo() {
        String cpuInfo = interactionSystem.executeCommand("lscpu");
        if (cpuInfo != null) {
            String countCore = parsers.extractData(cpuInfo, "CPU\\(s\\):\\s+(\\d+)");
            String cpuModel = parsers.extractData(cpuInfo, "Имя модели:\\s+(.+)");
            return String.format("%s × %s", countCore, cpuModel);
        } else {
            return null;
        }
    }

    private String makeGraphicsInfo() {
        String graphicsInfo = interactionSystem.executeCommand("lshw -c video");
        if (graphicsInfo != null) {
            return parsers.extractData(graphicsInfo, "описание:\\s+(.+)");
        } else {
            return null;
        }
    }

    private String makeRomMemoryInfo() {
        String romMemoryInfo = interactionSystem.executeCommand("df -h --total");
        if (romMemoryInfo != null) {
            String result = parsers.extractData(romMemoryInfo, "total\\s+([\\d,]*G)");
            return  result.substring(0, result.length() - 1);
        } else {
            return null;
        }
    }

    private String makeOsName() {
        String osName = interactionSystem.executeCommand("lsb_release -a");
        if (osName != null) {
            return parsers.extractData(osName, "Description:\\s+(.+)");
        } else {
            return null;
        }
    }

    private String makeOsType() {
        return interactionSystem.executeCommand("getconf LONG_BIT");
    }

    private String makeEnvironmentVersion() {
        String environmentVersion;
        if ((environmentVersion = interactionSystem.executeCommand("gnome-shell --version")) != null) {
            return environmentVersion;
        } else if ((environmentVersion = interactionSystem.executeCommand("budgie-desktop --version")) != null) {
            return environmentVersion;
        } else if ((environmentVersion = interactionSystem.executeCommand("kf5-config --version")) != null) {
            return parsers.extractData(environmentVersion, "KDE Frameworks:\\s+(.+)");
        } else if ((environmentVersion = interactionSystem.executeCommand("dde-desktop --version")) != null) {
            return environmentVersion;
        }

        return environmentVersion;
    }

    private String makeWindowInterface() {
        String windowInterface = interactionSystem.executeCommand("cat /etc/gdm3/custom.conf");
        if (windowInterface != null) {
            String waylandEnable = parsers.extractData(windowInterface, "#WaylandEnable=(.*)");
            if (Objects.equals(waylandEnable, "true")) {
                return "Wayland";
            } else {
                return "X11";
            }
        } else {
            return null;
        }
    }

    private String makeVirtualization() {
        return interactionSystem.executeCommand("systemd-detect-virt");
    }
}
