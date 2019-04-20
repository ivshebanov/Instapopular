package com.instapopulars.instapopular;

import com.instapopulars.instapopular.view.ViewMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InstapopularConfig {

    @Bean
    public ViewMap viewMap() {
        return new ViewMap();
    }
}