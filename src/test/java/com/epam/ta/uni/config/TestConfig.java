package com.epam.ta.uni.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.epam.ta.uni.factory.WebDriverFactory;

@Configuration
@ComponentScan("com.epam.ta.uni")
public class TestConfig {
    public static final long PAGE_OR_ELEMENT_LOAD_WAIT_SECONDS = 15;

    @Bean(destroyMethod = "closeWebDriver")
    public WebDriverFactory webDriverFactory() {
        return new WebDriverFactory();
    }
}
