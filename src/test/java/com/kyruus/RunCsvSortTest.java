package com.kyruus;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/com/kyruus/CsvSort.feature",
        glue = "com.kyruus"
)
public class RunCsvSortTest {

}