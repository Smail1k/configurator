package ru.oleg.configurator.service.ssh.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateParameterIn {
    private String parameterName;
    private String parameterValue;
}
