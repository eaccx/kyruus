package com.kyruus;

import com.kyruus.model.Csv;

import java.util.List;

import static com.kyruus.Constants.CSV_SEPARATOR;

public class Utils {

    public static void printCsv(final Csv csv) {
        System.out.println(String.join(CSV_SEPARATOR, csv.getHeader()));

        for (List<String> row : csv.getDataRows()) {
            System.out.println(String.join(CSV_SEPARATOR, row));
        }
    }

}
