package advent_2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
    public static String getTestData() {
        String input = null;
        try {
            String inputFilePath = "file.txt";
            input = readInputFromFile(inputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }

    public static String readInputFromFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }
}
