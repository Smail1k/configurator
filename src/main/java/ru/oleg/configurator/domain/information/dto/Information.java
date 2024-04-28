package ru.oleg.configurator.domain.information.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Information {
    private String versionKdePlasma;
    private String versionKdeFrameworks;
    private String versionQt;
    private String versionCore;
    private String graphicPlatform;
    private String processors;
    private String ram;
    private String graphicProcessor;
    private String manufacturer;
    private String nameProduct;
    private String versionSystem;
    private String serialNumber;
}
