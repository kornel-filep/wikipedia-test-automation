package com.epam.ta.uni.pageobjects;

import com.epam.ta.uni.factory.WebDriverFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.stereotype.Component;

@Component
public class HungaryPage extends CommonPageObject {

    @FindBy(id = "firstHeading")
    private WebElement mainHeading;

    public HungaryPage(final WebDriverFactory factory) {
        super(factory);
    }

    public WebElement getMainHeading() {
        return mainHeading;
    }
}
