Feature: Csv Sort

    Scenario Outline: CLI command execution e2e (happy path)
        Given user passes csv path <csv-path> and column <column-name>
        When user executes the command
        Then the output csv last row must be <last-row>

        Examples:
            | csv-path | column-name | last-row |
            | src/main/resources/sample1.csv | City | Boston,Massachusetts,It’s All Here,Michelle Wu |
            | src/main/resources/sample1.csv | Motto | New Orleans,Louisiana,City of Yes,LaToya Cantrell |
            | src/main/resources/sample1.csv | Mayor | New Orleans,Louisiana,City of Yes,LaToya Cantrell |
            | src/main/resources/one-line.csv | City | Portland,Oregon,The City That Works,Ted Wheeler |