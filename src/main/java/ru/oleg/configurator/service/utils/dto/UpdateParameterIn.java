package ru.oleg.configurator.service.utils.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateParameterIn {
    private String parameterName;
    private String parameterValue;
}
