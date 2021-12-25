package com.kyruus;

import com.kyruus.model.Csv;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SortCsvStepDefinitions {

    private final String[] args = new String[2];
    private Csv outCsv;

    @Given("^user passes csv path (.*) and column (.*)$")
    public void user_passes_arguments(String path, String column) {
        this.args[0] = path;
        this.args[1] = column;
    }

    @When("^user executes the command$")
    public void user_executes_command() {
        this.outCsv = Main.sortCsv(this.args);
    }

    @Then("^the output csv last row must be (.*)$")
    public void output_csv_last_row(String lastRow) {
        final List<List<String>> dataRows = this.outCsv.getDataRows();
        final String lastRowAsString = String.join(",", dataRows.get(dataRows.size() - 1));

        assertEquals(lastRowAsString, lastRow); // last row as string
    }

}