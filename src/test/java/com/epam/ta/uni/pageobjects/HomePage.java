package com.epam.ta.uni.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.stereotype.Component;

import com.epam.ta.uni.factory.WebDriverFactory;


@Component
public class HomePage extends CommonPageObject {
    private static final String HOME_PAGE_URL = "https://www.wikipedia.org";

    @FindBy(css = "#search-form button")
    private WebElement searchButton;

    @FindBy(id = "searchInput")
    private WebElement searchInput;

    public HomePage(final WebDriverFactory factory) {
        super(factory);
    }

    public void navigateToHomePage() {
        navigateToUrl(HOME_PAGE_URL);
    }

    public void clickOnSearchButton() {
        waitForElementToBeClickable(searchButton);
        searchButton.click();
        waitForPageReadiness();
    }

    public WebElement getSearchInput() {
        waitForPageReadiness();
        return searchInput;
    }

}
