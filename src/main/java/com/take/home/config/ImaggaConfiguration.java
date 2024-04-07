package com.take.home.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@Getter
@PropertySource("classpath:application.properties")
public class ImaggaConfiguration {

    @Value("${imagga.credentials}")
    private String credentials;

    @Value("${imagga.api}")
    private String endpointUrl;

    private String basicAuth;

    public String getBasicAuth() {
        return Base64.getEncoder().encodeToString(this.credentials.getBytes(StandardCharsets.UTF_8));
    }
}
