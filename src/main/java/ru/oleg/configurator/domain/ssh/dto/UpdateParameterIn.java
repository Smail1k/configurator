package ru.oleg.configurator.domain.ssh.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateParameterIn {
    private String parameterName;
    private String parameterValue;
}
