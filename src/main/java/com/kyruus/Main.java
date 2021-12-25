package com.kyruus;

import com.kyruus.model.Csv;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.kyruus.Constants.COLUMN_ARGUMENT_INDEX;
import static com.kyruus.Constants.CSV_SEPARATOR;

public class Main {

    public static void main(final String[] args) {
        try {
            final List<String> inputLines = readContentFromInputStream();
            final String columnName = validateArguments(args);

            final Csv csv = sortCsv(inputLines, columnName);

            Utils.printCsv(csv);
        } catch (IllegalArgumentException e) {
            System.out.println("There was an error while executing the command: " + e.getMessage());
        }
    }

    protected static Csv sortCsv(final List<String> inputLines, final String selectedColumn) {
        final Csv csv = parseAndValidateCsv(inputLines);

        int selectedColumnIndex = getColumnIndex(csv, selectedColumn);

        return sortRowsInDescendingOrder(csv, selectedColumnIndex);
    }

    private static Csv parseAndValidateCsv(final List<String> inputList) {
        // Preparing input to represent a Csv as a list of lists
        final List<List<String>> allRows = inputList.stream()
                .map(l -> l.split(CSV_SEPARATOR))
                .map(Arrays::asList)
                .collect(Collectors.toList());

        // let's validate that the CSV is not empty
        if (allRows.isEmpty()) {
            throw new IllegalArgumentException("The csv passed is invalid: the file does not contain any row");
        }
        final List<String> header = allRows.get(0);

        // let's validate that CSV does not have duplicated columns (not valid in our context)
        if (header.stream().distinct().count() != header.size()) {
            throw new IllegalArgumentException("The csv passed is invalid: header contains duplicated columns");
        }

        // CSV is well-formed: let's validate that all data rows are the same length as the header
        int numberOfColumnsInCsv = header.size();
        if (allRows.stream().anyMatch(r -> r.size() != numberOfColumnsInCsv)) {
            throw new IllegalArgumentException("The csv is malformed: all rows must be the same length as the header");
        }

        final List<List<String>> dataRows = allRows.subList(1, allRows.size());

        return new Csv(header, dataRows);
    }

    private static String validateArguments(final String[] args) {
        // Check that we received exactly 1 argument (column name)
        if (args.length != 1) {
            throw new IllegalArgumentException("The command takes exactly 1 parameter: Usage 'java -jar kyruus <column name>'");
        }

        return args[COLUMN_ARGUMENT_INDEX];
    }

    private static int getColumnIndex(final Csv csv, final String selectedColumn) {
        final List<String> header = csv.getHeader();

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

        return selectedColumnIndex.getAsInt();
    }

    private static List<String> readContentFromInputStream() {
        // Read content from input stream

        final List<String> inputLines = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            inputLines.add(in.nextLine());
        }

        return inputLines;
    }

    private static Csv sortRowsInDescendingOrder(final Csv csv, int columnIndex) {
        final List<List<String>> dataRows = csv.getDataRows();

        dataRows.sort(Comparator.comparing(l -> l.get(columnIndex)));
        Collections.reverse(dataRows);

        return new Csv(csv.getHeader(), dataRows);
    }


}
