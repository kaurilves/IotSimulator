package com.internship.iotsimulator.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeviceConfigs {

    @Bean
    @ConfigurationProperties(prefix = "device")
    public DeviceProperties deviceProperties(){
        return new DeviceProperties();
    }
}
