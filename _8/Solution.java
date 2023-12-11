package advent_2023._8;

import advent_2023.Reader;

import java.util.*;

public class Solution {
    private static void getRes() {
        String testData = Reader.getTestData();
        String[] lines = testData.split("\n");
        char[] directions = lines[0].toCharArray();
        Map<String, String[]> map = new HashMap<>();
        for (int i = 2; i < lines.length; i++) {
            String line = lines[i];
            if (!line.trim().isEmpty()) {
                String[] parts = line.split(" = \\(|, |\\)");
                map.put(parts[0], new String[]{parts[1], parts[2]});
            }
        }
        int i = 0;
        String next = "AAA";
        int count = 0;
        while (i < directions.length) {
            if (next.equals("ZZZ")) {
                System.out.println(count);
                break;
            }
            next = map.get(next)[directions[i] == 'R' ? 1 : 0];
            count++;
            i++;
            if (i == directions.length) i = 0;
        }
    }

    private static void getRes2() {
        String testData = Reader.getTestData();
        String[] lines = testData.split("\n");
        char[] directions = lines[0].toCharArray();
        Map<String, String[]> map = new HashMap<>();
        for (int i = 2; i < lines.length; i++) {
            String line = lines[i];
            if (!line.trim().isEmpty()) {
                String[] parts = line.split(" = \\(|, |\\)");
                map.put(parts[0], new String[]{parts[1], parts[2]});
            }
        }
        int i = 0;
        ArrayDeque<String> nextDeq = new ArrayDeque<>();
        for (String k : map.keySet()) {
            if (k.endsWith("A")) {
                nextDeq.add(k);
            }
        }
        int count = 0;
        Map<String, List<String>> visited = new HashMap<>();
        Map<String, Integer> cm = new HashMap<>();
        Iterator<String> stringIterator = nextDeq.iterator();
        for (int j = 0; j < nextDeq.size(); j++) {
            visited.put(stringIterator.next(), new ArrayList<>());
        }
        for (int j = 0; j < 6; j++) {
            count = 0;
            String start = nextDeq.peekFirst();
            String next = nextDeq.pollFirst();
            while (i < directions.length) {
                List<String> path = visited.get(start);
                path.add(next);
                visited.put(start, path);
                assert next != null;
                if (next.endsWith("Z")) {
                    cm.put(start, count);
                    break;
                }
                count++;
                next = map.get(next)[directions[i] == 'R' ? 1 : 0];
                i++;
                if (i == directions.length) i = 0;
            }
        }
        long[] arr = new long[6];
        int k = 0;
        for (Map.Entry<String, Integer> me : cm.entrySet()) {
            arr[k] = me.getValue();
            k++;
        }
        long result = arr[0];
        for (int j = 1; j < arr.length; j++) {
            result = lcm(result, arr[j]);
        }
        System.out.println(result);
    }

    private static long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private static long lcm(long a, long b) {
        return a / gcd(a, b) * b;
    }

    public static void main(String[] args) {
        getRes();
        getRes2();
    }
}
