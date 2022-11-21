package com.epam.ta.uni.stepdefinitions;

import static com.epam.ta.uni.config.TestConfig.PAGE_OR_ELEMENT_LOAD_WAIT_SECONDS;

import java.util.concurrent.TimeUnit;

import com.epam.ta.uni.pageobjects.HungaryPage;
import io.cucumber.java.en.Then;
import org.awaitility.Awaitility;
import org.awaitility.Duration;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.epam.ta.uni.config.TestConfig;
import com.epam.ta.uni.pageobjects.HomePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

@ContextConfiguration(classes = TestConfig.class)
public class WikipediaStepDefs {

    @Autowired
    private HomePage homePage;

    @Autowired
    private HungaryPage hungaryPage;

    @Given("the home page is opened")
    public void theHomePageIsOpened() {
        homePage.navigateToHomePage();
    }

    @When("the search field is filled with {string}")
    public void searchFill(String text) {
        homePage.getSearchInput().sendKeys(text);
    }

    @When("I click the search button")
    public void clickSearch() {
        homePage.clickOnSearchButton();
    }

    @Then("I see {string} in the heading")
    public void verifyHeading(String expected) {
        String actual = hungaryPage.getMainHeading().getText();
        Assert.assertEquals(expected, actual);
    }
}
