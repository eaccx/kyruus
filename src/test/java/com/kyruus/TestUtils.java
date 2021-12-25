package com.kyruus;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class TestUtils {

    public static List<String> getFileContent(final String filePath) {
        final File csvPath = new File(filePath);

        // Let's check that the file exists, or fail
        if (!csvPath.exists()) {
            throw new IllegalArgumentException(String.format("The file '%s' passed by parameter does not exists", csvPath));
        }

        try {
            return Files.readAllLines(csvPath.toPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Error while reading the file " + csvPath);
        }
    }
}
