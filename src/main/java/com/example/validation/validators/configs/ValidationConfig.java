package com.example.validation.validators.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationConfig
{
    @Bean("longString")
    public String getLongString()
    {
        return "123456789";
    }
}
