package com.epam.ta.uni.factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.epam.ta.uni.config.TestConfig.PAGE_OR_ELEMENT_LOAD_WAIT_SECONDS;

@Component
public class WebDriverFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverFactory.class);
    private static final String CHROME = "chrome";
    private static final String FIREFOX = "firefox";

    @Value("${browserName:chrome}")
    private String browserName;

    @Value("${headless:false}")
    private Boolean headless;

    private WebDriver webDriver;

    public WebDriver getWebDriver() {
        if (Objects.isNull(webDriver)) {
            switch (browserName) {
                case CHROME:
                    webDriver = setUpChromeDriver();
                    break;
                case FIREFOX:
                    webDriver = setUpFirefoxDriver();
                    break;
                default:
                    throw new RuntimeException("Unsupported driver for browser=" + browserName);
            }
        }

        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().pageLoadTimeout(PAGE_OR_ELEMENT_LOAD_WAIT_SECONDS, TimeUnit.SECONDS);
        webDriver.manage().timeouts().implicitlyWait(PAGE_OR_ELEMENT_LOAD_WAIT_SECONDS, TimeUnit.SECONDS);

        return webDriver;
    }

    private WebDriver setUpChromeDriver() {
        WebDriverManager.chromedriver().setup();

        LOGGER.info("ChromeDriver was created");
        return new ChromeDriver(new ChromeOptions().setHeadless(headless));
    }

    private WebDriver setUpFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();

        LOGGER.info("FirefoxDriver was created");
        return new FirefoxDriver(new FirefoxOptions().setHeadless(headless));
    }

    public void closeWebDriver() {
        if (Objects.nonNull(webDriver)) {
            webDriver.quit();
            webDriver = null;
            LOGGER.info("WebDriver was closed");
        }
    }

}
