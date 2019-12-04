package ru.instapopular.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.instapopular.view.ViewMap;

@Configuration
public class InstapopularConfig {

    @Bean
    public ViewMap viewMap() {
        return new ViewMap();
    }
}
