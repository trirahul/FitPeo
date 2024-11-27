package com.fitpeo.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    public static List<Object[]> readTestData(String csvFile) throws IOException {
        List<Object[]> records = new ArrayList<>();
        Reader reader = new FileReader(csvFile);
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

        for (CSVRecord csvRecord : csvParser) {
            String username = csvRecord.get("username");
            String password = csvRecord.get("password");
            String expectedTitle = csvRecord.get("expectedTitle");
            records.add(new Object[]{username, password, expectedTitle});
        }

        csvParser.close();
        reader.close();

        return records;
    }
}
