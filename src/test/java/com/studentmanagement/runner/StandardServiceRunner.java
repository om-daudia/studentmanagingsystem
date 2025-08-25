package com.studentmanagement.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.junit.runner.RunWith;

import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;
//@Suite
//@SelectClasspathResource("features")
//@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.studentmanagement.stepdefs")
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.studentmanagement.stepdefs"
)
public class StandardServiceRunner {
}
