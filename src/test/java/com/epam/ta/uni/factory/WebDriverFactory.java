package com.epam.ta.uni.factory;

import com.epam.ta.uni.browserstack.Utility;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.epam.ta.uni.config.TestConfig.PAGE_OR_ELEMENT_LOAD_WAIT_SECONDS;

@Component
public class WebDriverFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverFactory.class);
    private static final String CHROME = "chrome";
    private static final String FIREFOX = "firefox";

    private static final String REMOTE = "browserstack";

    private final JSONObject TEST_CONFIG = this.parseWebDriverConfig();

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
                case REMOTE:
                    webDriver = createWebDriverForPlatform(this.getPlatforms().get(0));
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

    private JSONObject parseWebDriverConfig() {
        JSONParser parser = new JSONParser();
        String capabilitiesConfigFile = System.getProperty("caps", "src/test/resources/conf/single.conf.json");
        try {
            JSONObject testConfig = (JSONObject) parser.parse(new FileReader(capabilitiesConfigFile));
            return testConfig;
        } catch (IOException | ParseException var6) {
            throw new Error("Unable to parse capabilities file " + capabilitiesConfigFile, var6);
        }
    }

    public List<JSONObject> getPlatforms() {
        JSONArray environments = (JSONArray) TEST_CONFIG.get("environments");
        List<JSONObject> platforms = new ArrayList<>();
        for (Object obj : environments) {
            JSONObject singleConfig = Utility.getCombinedCapability((Map<String, String>) obj, TEST_CONFIG);
            platforms.add(singleConfig);
        }
        return platforms;
    }

    public WebDriver createWebDriverForPlatform(JSONObject platform) {
        try {
            String url = String.format("https://%s/wd/hub", TEST_CONFIG.get("server"));
            MutableCapabilities caps = new MutableCapabilities(platform);
            return new RemoteWebDriver(new URL(url), caps);
        } catch (MalformedURLException var4) {
            throw new Error("Unable to create WebDriver", var4);
        }
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
