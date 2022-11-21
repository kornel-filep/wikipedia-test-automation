package com.epam.ta.uni.hooks;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

import com.epam.ta.uni.factory.WebDriverFactory;
import io.cucumber.java.After;

public class Hooks {

    @Autowired
    private WebDriverFactory webDriverFactory;

    @After
    public void afterScenario() {
        final WebDriver webDriver = webDriverFactory.getWebDriver();

        webDriver.manage().deleteAllCookies();
    }
}
