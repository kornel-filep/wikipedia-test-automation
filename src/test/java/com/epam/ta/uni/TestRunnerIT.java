package com.epam.ta.uni;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "com.epam.ta.uni",
    plugin = {"pretty"}
)
public class TestRunnerIT {

}
