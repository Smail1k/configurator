package ru.oleg.configurator.domain.system;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.oleg.configurator.domain.system.dto.SystemConfig;
import static ru.oleg.configurator.domain.utils.ExecuteCommand.executeCommand;
import static ru.oleg.configurator.domain.utils.ExecuteCommand.extractData;

import java.util.Objects;


@Service
@AllArgsConstructor
public class SystemConfigService {

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
        String deviceName;
        deviceName = executeCommand("hostname");
        return deviceName;
    }

    private String makeRamMemoryInfo() {
        String memoryInfo;
        memoryInfo = executeCommand("free -h");
        if (memoryInfo != null) {
            return extractData(memoryInfo, "Память:\\s+([\\d,]+)\\s*Gi");
        } else {
            return null;
        }
    }

    private String makeCpuInfo() {
        String cpuInfo;
        cpuInfo = executeCommand("lscpu");
        if (cpuInfo != null) {
            String countCore = extractData(cpuInfo, "CPU\\(s\\):\\s+(\\d+)");
            String cpuModel = extractData(cpuInfo, "Имя модели:\\s+(.+)");
            return String.format("%s × %s", countCore, cpuModel);
        } else {
            return null;
        }
    }

    private String makeGraphicsInfo() {
        String graphicsInfo;
        graphicsInfo = executeCommand("lshw -c video");
        if (graphicsInfo != null) {
            return extractData(graphicsInfo, "описание:\\s+(.+)");
        } else {
            return null;
        }
    }

    private String makeRomMemoryInfo() {
        String romMemoryInfo;
        romMemoryInfo = executeCommand("df -h --total");
        if (romMemoryInfo != null) {
            String result = extractData(romMemoryInfo, "total\\s+([\\d,]*G)");
            return  result.substring(0, result.length() - 1);
        } else {
            return null;
        }
    }

    private String makeOsName() {
        String osName;
        osName = executeCommand("lsb_release -a");
        if (osName != null) {
            return extractData(osName, "Description:\\s+(.+)");
        } else {
            return null;
        }
    }

    private String makeOsType() {
        String osType;
        osType = executeCommand("getconf LONG_BIT");
        return osType;
    }

    private String makeEnvironmentVersion() {
        String environmentVersion;
        if ((environmentVersion = executeCommand("gnome-shell --version")) != null) {
            return environmentVersion;
        } else if ((environmentVersion = executeCommand("budgie-desktop --version")) != null) {
            return environmentVersion;
        } else if ((environmentVersion = executeCommand("kf5-config --version")) != null) {
            return extractData(environmentVersion, "KDE Frameworks:\\s+(.+)");
        } else if ((environmentVersion = executeCommand("dde-desktop --version")) != null) {
            return environmentVersion;
        }

        return environmentVersion;
    }

    private String makeWindowInterface() {
        String windowInterface;

        windowInterface = executeCommand("cat /etc/gdm3/custom.conf");
        if (windowInterface != null) {
            String waylandEnable = extractData(windowInterface, "#WaylandEnable=(.*)");
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
        String virtualization;
        virtualization = executeCommand("systemd-detect-virt");
        return virtualization;
    }
}
