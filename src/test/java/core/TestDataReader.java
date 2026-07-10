package core;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class TestDataReader {
    private static final String PATH = System.getProperty("user.dir") + "/src/test/resources/testdata/test.csv";

    public static String getValue(String key) {
        ensureFileExists();

        try (BufferedReader reader = new BufferedReader(new FileReader(PATH))) {
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] cols = line.split(",", -1);

                if (cols.length >= 1 && cols[0].equals(key)) return cols[1];
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read test data", e);
        }

        return null;
    }

    public static void setValue(String key, String value) {
        ensureFileExists();

        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(PATH));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read test data", e);
        }

        boolean found = false;
        for (int i = 1; i < lines.size(); i++) {
            String[] cols = lines.get(i).split(",", -1);

            if (cols.length >= 1 && cols[0].equals(key)) {
                lines.set(i, String.join(",", key, value));
                found = true;
                break;
            }
        }

        if (!found) lines.add(String.join(",", key, value));

        try {
            Files.write(Paths.get(PATH), lines);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save test data", e);
        }
    }

    private static void ensureFileExists() {
        File file = new File(PATH);
        if (file.exists()) return;

        file.getParentFile().mkdirs();
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println("key,value");
        } catch (IOException e) {
            throw new RuntimeException("Failed to create test data file", e);
        }
    }

}