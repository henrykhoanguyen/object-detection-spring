package com.take.home.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class ImaggaConfiguration {
    @Autowired
    private Environment env;

    public String getCredentials(){
        return env.getProperty("imagga.credentials");
    }

    public String getEndpointUrl(){
        return env.getProperty("imagga.api");
    }
}
