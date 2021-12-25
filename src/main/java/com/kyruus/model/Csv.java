package com.kyruus.model;

import java.util.List;

public class Csv {

    private List<List<String>> dataRows;
    private List<String> header;

    public Csv(List<String> header, List<List<String>> dataRows) {
        this.dataRows = dataRows;
        this.header = header;
    }

    public List<List<String>> getDataRows() {
        return dataRows;
    }

    public List<String> getHeader() {
        return header;
    }

}
