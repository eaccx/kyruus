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

public class Utils {

    static private final String CSV_SEPARATOR = ",";
    static private final int FILE_PATH_ARGUMENT_INDEX = 0;
    static private final int COLUMN_ARGUMENT_INDEX = 1;


    /**
     * Validates the input arguments and parses them
     * @param args
     * @return a new Arguments object containing a Csv and the column-index for the selected column
     */
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
            throw new RuntimeException("Error while reading the file " + csvPath);
        }

        // let's validate that the CSV is not empty
        if (allRows.isEmpty()) {
            throw new IllegalArgumentException("The csv passed does not contain any row");
        }
        final List<String> header = allRows.get(0);

        // let's validate that CSV does not have duplicated columns (not valid in our context)
        if(header.stream().distinct().count() != header.size()) {
            throw new IllegalArgumentException("The csv passed is invalid: contains duplicated columns");
        }

        // let's validate that the column passed by parameter exists in the header and obtain its index
        // in the list
        final OptionalInt selectedColumnIndex = IntStream.range(0, header.size())
                .filter(i -> selectedColumn.equals(header.get(i)))
                .findFirst();
        if (!selectedColumnIndex.isPresent()) {
            throw new IllegalArgumentException("The column passed does not exists in the csv");
        }

        // CSV is well-formed: let's validate that all data rows are the same length as the header
        int numberOfColumnsInCsv = header.size();
        if (allRows.stream().anyMatch(r -> r.size() != numberOfColumnsInCsv)) {
            throw new IllegalArgumentException("The csv is malformed: all rows must be the same length as the header");
        }

        // Now that we parsed the parameters and verified the validity of them, return to caller

        return new Arguments(selectedColumnIndex.getAsInt(), new Csv(allRows.get(0), allRows.subList(1, allRows.size())));
    }

    public static void printCsv(Csv csv) {
        System.out.println(String.join(CSV_SEPARATOR, csv.getHeader()));

        for (List<String> row : csv.getDataRows()) {
            System.out.println(String.join(CSV_SEPARATOR, row));
        }
    }



}
