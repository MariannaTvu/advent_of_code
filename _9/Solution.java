package advent_2023._9;

import advent_2023.Reader;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    private static void getRes() {
        String testData = Reader.getTestData();
        long res = 0;
        String[] seqLines = testData.split("\n");
        for (int i = 0; i < seqLines.length; i++) {
            String[] seqS = seqLines[i].split(" ");
            int[] seq = new int[seqS.length];
            for (int j = 0; j < seqS.length; j++) {
                seq[j] = Integer.parseInt(seqS[j]);
            }
            res += process(seq);
        }
        System.out.println(res);
    }

    private static long process(int[] seq) {
        List<List<Long>> matrix = new ArrayList<>();
        List<Long> currentLevel = new ArrayList<>();
        for (int num : seq) {
            currentLevel.add(Long.valueOf(num));
        }
        matrix.add(new ArrayList<>(currentLevel));
        while (currentLevel.size() > 1) {
            List<Long> nextLevel = new ArrayList<>();
            for (int i = 1; i < currentLevel.size(); i++) {
                nextLevel.add(currentLevel.get(i) - currentLevel.get(i - 1));
            }
            matrix.add(nextLevel);
            currentLevel = nextLevel;
        }
        for (int i = matrix.size() - 2; i >= 0; i--) {
            long lastValueOfNextRow = matrix.get(i + 1).get(matrix.get(i + 1).size() - 1);
            long lastValueOfCurrentRow = matrix.get(i).get(matrix.get(i).size() - 1);
            matrix.get(i).add(lastValueOfCurrentRow + lastValueOfNextRow);
        }
        return matrix.get(0).get(matrix.get(0).size() - 1);
    }

    private static void getRes2() {
        String testData = Reader.getTestData();
        long res = 0;
        String[] seqLines = testData.split("\n");
        for (int i = 0; i < seqLines.length; i++) {
            String[] seqS = seqLines[i].split(" ");
            int[] seq = new int[seqS.length];
            for (int j = 0; j < seqS.length; j++) {
                seq[j] = Integer.parseInt(seqS[j]);
            }
            res += process2(seq);
        }
        System.out.println(res);
    }

    private static long process2(int[] seq) {
        List<List<Long>> matrix = new ArrayList<>();
        List<Long> currentLevel = new ArrayList<>();
        for (int num : seq) {
            currentLevel.add(Long.valueOf(num));
        }
        matrix.add(new ArrayList<>(currentLevel));

        while (currentLevel.size() > 1) {
            List<Long> nextLevel = new ArrayList<>();
            for (int i = 1; i < currentLevel.size(); i++) {
                nextLevel.add(currentLevel.get(i) - currentLevel.get(i - 1));
            }
            matrix.add(nextLevel);
            currentLevel = nextLevel;
        }
        for (int i = matrix.size() - 2; i >= 0; i--) {
            long firstValueOfNextRow = matrix.get(i + 1).get(0);
            long firstValueOfCurrentRow = matrix.get(i).get(0);
            matrix.get(i).add(0, firstValueOfCurrentRow - firstValueOfNextRow);
        }
        return matrix.get(0).get(0);
    }

    public static void main(String[] args) {
        getRes();
        getRes2();
    }
}
