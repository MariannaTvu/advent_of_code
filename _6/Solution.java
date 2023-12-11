package advent_2023._6;

import advent_2023.Reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {
    private static void getRes() {
        String testData = Reader.getTestData();
        Map<String, List<Integer>> resultMap = parseData(testData);
        long res = 1;
        List<Integer> times = resultMap.get("Time");
        List<Integer> distances = resultMap.get("Distance");
        for (int i = 0; i < times.size(); i++) {
            int time = times.get(i);
            int distance = distances.get(i);
            int ways = 0;
            for (int j = 0; j < time; j++) {
                int newDist = j * (time - j);
                if (newDist > distance) ways++;
            }
            if (ways > 0) res *= ways;
        }
        System.out.println(res);
    }

    private static void getRes2() {
        long res = 1;
        String testData = Reader.getTestData();
        Map<String, List<Long>> resultMap = parseData2(testData);
        List<Long> times = resultMap.get("Time");
        List<Long> distances = resultMap.get("Distance");
        for (int i = 0; i < times.size(); i++) {
            long time = times.get(i);
            long distance = distances.get(i);
            long ways = 0;
            for (int j = 0; j < time; j++) {
                long newDist = j * (time - j);
                if (newDist > distance) ways++;
            }
            if (ways > 0) res *= ways;
        }
        System.out.println(res);
    }

    public static void main(String[] args) {
        getRes();
        getRes2();
    }

    private static Map<String, List<Long>> parseData2(String data) {
        Map<String, List<Long>> resultMap = new HashMap<>();
        String[] lines = data.split("\n");
        for (String line : lines) {
            String[] parts = line.split(":");
            String key = parts[0].trim();
            String[] values = parts[1].trim().split("\\s+");

            List<Long> numbers = new ArrayList<>();
            for (String value : values) {
                numbers.add(Long.parseLong(value));
            }
            resultMap.put(key, numbers);
        }
        return resultMap;
    }

    private static Map<String, List<Integer>> parseData(String data) {
        Map<String, List<Integer>> resultMap = new HashMap<>();
        String[] lines = data.split("\n");
        for (String line : lines) {
            String[] parts = line.split(":");
            String key = parts[0].trim();
            String[] values = parts[1].trim().split("\\s+");
            List<Integer> numbers = new ArrayList<>();
            for (String value : values) {
                numbers.add(Integer.parseInt(value));
            }
            resultMap.put(key, numbers);
        }
        return resultMap;
    }
}
