package com.remote_vitals.backend.services;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Service
public class CsvService {

    /**
     * Parses a CSV file from the given absolute path.
     * Each row is converted into a Map with lowercase, trimmed column headers as keys.
     * Key-value pairs with empty or blank values are skipped.
     * If headers are invalid or a row contains a different number of comma-separated values
     * than the header, an IllegalArgumentException is thrown.
     *
     * @param absolutePath Absolute path to the CSV file.
     * @return List of Maps, each representing a non-empty row of the CSV.
     * @throws IOException if the file cannot be read.
     * @throws IllegalArgumentException if headers are invalid or a row has inconsistent column count.
     */
    public List<Map<String, String>> parseCsv(String absolutePath) throws IOException {
        List<Map<String, String>> resultList = new ArrayList<>();
        System.out.println(absolutePath);
        try (BufferedReader br = new BufferedReader(new FileReader(absolutePath))) {
            String headerLine = br.readLine();
            if (headerLine == null) {
                return resultList; // Empty file
            }
            // Trim and lowercase headers
            String[] rawHeaders = headerLine.split(",", -1);
            String[] headers = Arrays.stream(rawHeaders)
                    .map(h -> h.trim().toLowerCase())
                    .toArray(String[]::new);

            if (!validateHeaders(headers)) {
                throw new IllegalArgumentException("CSV header validation failed: unexpected or missing columns.");
            }

            int expectedColumns = headers.length;
            String line;
            int lineNumber = 1; // Header is line 1

            while ((line = br.readLine()) != null) {
                lineNumber++;
                String[] rawValues = line.split(",", -1);

                if (rawValues.length != expectedColumns) {
                    throw new IllegalArgumentException(
                            "CSV format error at line " + lineNumber +
                                    ": expected " + expectedColumns + " values, but found " + rawValues.length + "."
                    );
                }

                Map<String, String> rowMap = new HashMap<>();
                for (int i = 0; i < expectedColumns; i++) {
                    String key = headers[i].trim();             // Redundant trim but safe
                    String value = rawValues[i].trim();         // Ensure value is trimmed

                    if (!value.isEmpty()) {
                        rowMap.put(key, value);
                    }
                }

                if (!rowMap.isEmpty()) {
                    resultList.add(rowMap);
                }
            }
        }

        return resultList;
    }

    /**
     * Validates that all headers in the CSV are expected.
     *
     * @param headers Array of headers to validate.
     * @return true if all headers are valid, false otherwise.
     */
    private boolean validateHeaders(String[] headers) {
        List<String> allowedHeaders = Arrays.asList(
                "date-time",
                "body-temperature",
                "heart-rate",
                "rbc",
                "wbc",
                "platelet-count",
                "respiratory-rate",
                "blood-pressure-systolic",
                "blood-pressure-diastolic",
                "blood-volume",
                "haemoglobin",
                "height",
                "weight"
        );

        for (String header : headers) {
            if (!allowedHeaders.contains(header.trim().toLowerCase())) {
                return false;
            }
        }
        return true;
    }
}
