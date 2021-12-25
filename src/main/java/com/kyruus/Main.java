package com.kyruus;

import com.kyruus.model.Arguments;
import com.kyruus.model.Csv;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            final Csv csv = sortCsv(args);
            Utils.printCsv(csv);
        } catch (IllegalArgumentException e) {
            System.out.println("There was an error while executing the command: " + e.getMessage());
        }
    }

    protected static Csv sortCsv(String[] args) {
            final Arguments parsedArguments = Utils.parseAndValidateArguments(args);

            Csv csv = parsedArguments.getCsv();
            int selectedColumnIndex = parsedArguments.getSelectedColumnIndex();

            return sortRowsInDescendingOrder(csv, selectedColumnIndex);
    }

    /**
     * @param csv
     * @param columnIndex
     * @return Returns a new instance of CSV with the rows ordered according to the column at index @columnIndex
     */
    private static Csv sortRowsInDescendingOrder(Csv csv, int columnIndex) {
        List<List<String>> dataRows = csv.getDataRows();

        dataRows.sort(Comparator.comparing(l -> l.get(columnIndex)));
        Collections.reverse(dataRows);

        return new Csv(csv.getHeader(), dataRows);
    }

}
