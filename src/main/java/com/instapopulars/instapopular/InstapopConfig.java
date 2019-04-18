package com.instapopulars.instapopular;

import com.instapopulars.instapopular.model.ViewMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InstapopConfig {

    @Bean
    public ViewMap viewMap() {
        return new ViewMap();
    }
}