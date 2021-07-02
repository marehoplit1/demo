package com.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Data
@Validated
@Component
@ConfigurationProperties(value = "dcc.pms", ignoreUnknownFields = false)
public class DccPmsProperties {

    @Valid
    private Security security;

    @Data
    public static class Security {

        @NotEmpty
        private String usersInitFilePath;
    }
}
