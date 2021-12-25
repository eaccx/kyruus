package com.kyruus;

import com.kyruus.model.Arguments;
import com.kyruus.model.Csv;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.kyruus.Constants.*;

public class Main {

    public static void main(final String[] args) {
        try {
            final Csv csv = sortCsv(args);
            Utils.printCsv(csv);
        } catch (IllegalArgumentException e) {
            System.out.println("There was an error while executing the command: " + e.getMessage());
        }
    }

    protected static Csv sortCsv(final String[] args) {
        final Arguments parsedArguments = parseAndValidateArguments(args);

        final Csv csv = parsedArguments.getCsv();
        int selectedColumnIndex = parsedArguments.getSelectedColumnIndex();

        return sortRowsInDescendingOrder(csv, selectedColumnIndex);
    }

    private static Csv sortRowsInDescendingOrder(final Csv csv, int columnIndex) {
        final List<List<String>> dataRows = csv.getDataRows();

        dataRows.sort(Comparator.comparing(l -> l.get(columnIndex)));
        Collections.reverse(dataRows);

        return new Csv(csv.getHeader(), dataRows);
    }

    public static Arguments parseAndValidateArguments(String[] args) {

        // Check that we received exactly 2 arguments
        if (args.length != 2) {
            throw new IllegalArgumentException("The cli takes exactly 2 parameters. Usage 'java -jar kyruus <csv path> <column name>'");
        }

        final File csvPath = new File(args[FILE_PATH_ARGUMENT_INDEX]);
        final String selectedColumn = args[COLUMN_ARGUMENT_INDEX];

        // Let's check that the file exists, or fail
        if (!csvPath.exists()) {
            throw new IllegalArgumentException(String.format("The file '%s' passed by parameter does not exists", csvPath));
        }

        final List<List<String>> allRows;
        try {
            allRows = Files.readAllLines(csvPath.toPath(), StandardCharsets.UTF_8).stream()
                    .map(l -> l.split(CSV_SEPARATOR))
                    .map(Arrays::asList)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(String.format("Error while reading the file '%s'", csvPath));
        }

        // let's validate that the CSV is not empty
        if (allRows.isEmpty()) {
            throw new IllegalArgumentException("The csv passed is invalid: the file does not contain any row");
        }
        final List<String> header = allRows.get(0);

        // let's validate that CSV does not have duplicated columns (not valid in our context)
        if (header.stream().distinct().count() != header.size()) {
            throw new IllegalArgumentException("The csv passed is invalid: header contains duplicated columns");
        }

        // let's validate that the column passed by parameter exists in the header and obtain its index
        // in the list
        final OptionalInt selectedColumnIndex = IntStream.range(0, header.size())
                .filter(i -> selectedColumn.equals(header.get(i)))
                .findFirst();
        if (!selectedColumnIndex.isPresent()) {
            throw new IllegalArgumentException(
                    String.format("The column '%s' passed does not exists in the csv", selectedColumn)
            );
        }

        // CSV is well-formed: let's validate that all data rows are the same length as the header
        int numberOfColumnsInCsv = header.size();
        if (allRows.stream().anyMatch(r -> r.size() != numberOfColumnsInCsv)) {
            throw new IllegalArgumentException("The csv is malformed: all rows must be the same length as the header");
        }

        final List<List<String>> dataRows = allRows.subList(1, allRows.size());
        return new Arguments(
                selectedColumnIndex.getAsInt(),
                new Csv(header, dataRows)
        );
    }

}
