package advent_2023._3;

import advent_2023.Reader;

import java.util.*;

public class Solution {
    private static void getRes() {
        int res = 0;
        String testData = Reader.getTestData();

        String[] lines = testData.split("\n");
        List<char[]> chars = new ArrayList<>();

        for (String line : lines) {
            chars.add(line.toCharArray());
        }
        StringBuilder sb = new StringBuilder();
        int currentNum = 0;
        boolean use = false;
        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < chars.get(i).length; j++) {
                char currentChar = chars.get(i)[j];
                if (Character.isDigit(currentChar)) {
                    sb.append(currentChar);
                    for (int di = -1; di <= 1; di++) {
                        for (int dj = -1; dj <= 1; dj++) {
                            if (di == 0 && dj == 0) {
                                continue;
                            }
                            int adjacentI = i + di;
                            int adjacentJ = j + dj;
                            if (adjacentI >= 0 && adjacentI < lines.length && adjacentJ >= 0 && adjacentJ < chars.get(adjacentI).length) {
                                char adjacentChar = chars.get(adjacentI)[adjacentJ];
                                if (!Character.isDigit(adjacentChar) && adjacentChar != '.') {
                                    use = true;
                                }
                            }
                        }
                    }
                } else {
                    if (use) {
                        use = false;
                        if (sb.length() != 0) {
                            currentNum = Integer.parseInt(sb.toString());
                            res += currentNum;
                        }
                    }
                    sb = new StringBuilder();
                }
            }
        }
        System.out.println(res);
    }


    private static void getRes2() {
        long res = 0;
        String testData = Reader.getTestData();
        String[] lines = testData.split("\n");
        List<char[]> chars = new ArrayList<>();
        for (String line : lines) {
            chars.add(line.toCharArray());
        }
        StringBuilder sb = new StringBuilder();
        int currentNum = 0;
        boolean use = false;
        List<String> astIds = new ArrayList<>();

        Map<String, Set<Integer>> asterix = new HashMap<>();
        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < chars.get(i).length; j++) {
                if (chars.get(i)[j] == '*') {
                    asterix.put(i + "," + j, new HashSet<>());
                }
            }
        }
        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < chars.get(i).length; j++) {
                char currentChar = chars.get(i)[j];
                if (Character.isDigit(currentChar)) {
                    sb.append(currentChar);
                    for (int di = -1; di <= 1; di++) {
                        for (int dj = -1; dj <= 1; dj++) {
                            if (di == 0 && dj == 0) {
                                continue;
                            }
                            int adjacentI = i + di;
                            int adjacentJ = j + dj;
                            if (adjacentI >= 0 && adjacentI < lines.length && adjacentJ >= 0 && adjacentJ < chars.get(adjacentI).length) {
                                char adjacentChar = chars.get(adjacentI)[adjacentJ];
                                if (adjacentChar == '*') {
                                    astIds.add(adjacentI + "," + adjacentJ);
                                    use = true;
                                }
                            }
                        }
                    }
                } else {
                    if (use) {
                        use = false;
                        if (sb.length() != 0) {
                            currentNum = Integer.parseInt(sb.toString());
                        }
                        for (String id : astIds) {
                            asterix.get(id).add(currentNum);
                        }
                    }
                    astIds = new ArrayList<>();
                    sb = new StringBuilder();
                }
            }
        }
        for (Set<Integer> vals : asterix.values()) {
            if (vals.size() == 2) {
                long product = 1;
                for (int val : vals) {
                    product *= val;
                }
                res += product;
            }
        }
        System.out.println(res);
    }

    public static void main(String[] args) {
        getRes();
        getRes2();
    }

}
