package advent_2023._11;

import advent_2023.Reader;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    private static void getRes() {
        String testData = Reader.getTestData();
        String[] lines = testData.split("\\n");
        int len = lines[0].length();

        boolean[] columnHasGalaxy = new boolean[len];
        boolean[] rowHasGalaxy = new boolean[lines.length];

        List<int[]> coord = new ArrayList<>();

        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < len; j++) {
                if (lines[i].charAt(j) == '#') {
                    coord.add(new int[]{i, j});
                    rowHasGalaxy[i] = true;
                    columnHasGalaxy[j] = true;
                }
            }
        }

        for (int[] c : coord) {
            c[0] += countFalseBefore(rowHasGalaxy, c[0]);
            c[1] += countFalseBefore(columnHasGalaxy, c[1]);
        }

        int res = 0;
        for (int i = 0; i < coord.size(); i++) {
            for (int j = i + 1; j < coord.size(); j++) { // Avoid duplicate pairs
                res += findDist(coord.get(i), coord.get(j));
            }
        }

        System.out.println(res);
    }

    private static int findDist(int[] c1, int[] c2) {
        return Math.abs(c1[0] - c2[0]) + Math.abs(c1[1] - c2[1]);
    }

    private static int countFalseBefore(boolean[] array, int index) {
        int count = 0;
        for (int i = 0; i < index; i++) {
            if (!array[i]) {
                count++;
            }
        }
        return count;
    }

    private static void getRes2() {
        String testData = Reader.getTestData();
        String[] lines = testData.split("\\n");
        int len = lines[0].length();

        boolean[] columnHasGalaxy = new boolean[len];
        boolean[] rowHasGalaxy = new boolean[lines.length];

        List<int[]> coord = new ArrayList<>();
        List<int[]> adjustedCoord = new ArrayList<>();

        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < len; j++) {
                if (lines[i].charAt(j) == '#') {
                    coord.add(new int[]{i, j});
                    rowHasGalaxy[i] = true;
                    columnHasGalaxy[j] = true;
                }
            }
        }

        for (int[] c : coord) {
            adjustedCoord.add(new int[]{
                    c[0] + countFalseBefore(rowHasGalaxy, c[0]) * (1000000 - 1),
                    c[1] + countFalseBefore(columnHasGalaxy, c[1]) * (1000000 - 1)
            });
        }

        long res = 0;
        for (int i = 0; i < adjustedCoord.size(); i++) {
            for (int j = i + 1; j < adjustedCoord.size(); j++) {
                res += findDist(adjustedCoord.get(i), adjustedCoord.get(j));
            }
        }

        System.out.println(res);
    }

    public static void main(String[] args) {
        getRes();
        getRes2();
    }
}
