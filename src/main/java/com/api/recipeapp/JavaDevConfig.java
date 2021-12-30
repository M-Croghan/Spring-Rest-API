package com.api.recipeapp;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;
import java.util.logging.Logger;

@Profile("dev") // Tells application that this is the bean to be picked up when we set application profile to 'dev'
@Configuration
public class JavaDevConfig {
    private final Logger LOGGER = Logger.getLogger(JavaDevConfig.class.getName());

    @PostConstruct
    public void test(){
        LOGGER.info("Loading DEV Profile!");
    }
}
