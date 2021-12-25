package com.kyruus.model;

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
