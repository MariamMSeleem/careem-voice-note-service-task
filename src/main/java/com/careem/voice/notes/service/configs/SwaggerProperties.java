package com.careem.voice.notes.service.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "voice.notes.service.swagger")
@Getter
@Setter
public class SwaggerProperties {
    private String apiVersion;
    private String title;
    private String description;
    private String termsOfServiceUrl;
    private String contactName;
    private String contactUrl;
    private String contactEmail;
    private String apiLicense;
    private String apiLicenseUrl;
}