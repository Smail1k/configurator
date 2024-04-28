package ru.oleg.configurator.domain.environment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecentFiles {
    private String howLongKeepHistory;
    private String clearLog;
    private String memorizeOpenDocuments;
}
