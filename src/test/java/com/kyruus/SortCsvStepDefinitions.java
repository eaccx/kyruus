package com.kyruus;

import com.kyruus.model.Csv;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static com.kyruus.Constants.CSV_SEPARATOR;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SortCsvStepDefinitions {

    private String column;
    private List<String> fileContent;
    private Csv csv;

    @Given("^user passes the content of file (.*) and column name (.*)$")
    public void user_passes_arguments(String path, String column) {
        this.column = column;
        this.fileContent = TestUtils.getFileContent(path);
    }

    @When("^user executes the command$")
    public void user_executes_command() {
        this.csv = Main.sortCsv(fileContent, column);
    }

    @Then("^the output csv last row must be (.*)$")
    public void output_csv_last_row(String lastRow) {
        final List<List<String>> dataRows = this.csv.getDataRows();
        final String lastRowAsString = String.join(CSV_SEPARATOR, dataRows.get(dataRows.size() - 1));

        assertEquals(lastRowAsString, lastRow); // last row as string
    }

}