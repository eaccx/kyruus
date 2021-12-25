package com.kyruus.model;

/**
 * Representation of the arguments passed by cli
 * - The CSV passed to the command
 * - The index of the column passed as parameter in the CSV
 */
public class Arguments {

    private Integer selectedColumnIndex;
    private Csv csv;

    public Arguments(Integer selectedColumnIndex, Csv csv) {
        this.csv = csv;
        this.selectedColumnIndex = selectedColumnIndex;
    }

    public Integer getSelectedColumnIndex() {
        return selectedColumnIndex;
    }

    public Csv getCsv() {
        return csv;
    }

}
