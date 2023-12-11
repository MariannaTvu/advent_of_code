package advent_2023._2;

import advent_2023.Reader;

public class Solution {
    private static void getRes() {
        int res = 0;
        String testData = Reader.getTestData();
        String[] splitArray = testData.split("Game ");

        outerLoop:
        for (String s : splitArray) {
            if (s.length() == 0) continue;
            String[] splitArrayGameId = s.split(":");
            String[] games = splitArrayGameId[1].split(";");

            for (String game : games) {
                String[] gam = game.split(",");
                for (String g : gam) {
                    String[] parts = g.trim().split(" ");
                    int count = Integer.parseInt(parts[0]);
                    if ((parts[1].endsWith("red") && count > 12) ||
                            (parts[1].endsWith("green") && count > 13) ||
                            (parts[1].endsWith("blue") && count > 14)) {
                        continue outerLoop;
                    }
                }
            }

            res += Integer.parseInt(splitArrayGameId[0].trim());
        }
        System.out.println(res);
    }


    private static void getRes2() {
        long res = 0;
        String testData = Reader.getTestData();
        String[] splitArray = testData.split("Game ");

        for (String s : splitArray) {
            if (s.length() == 0) continue;
            String[] splitArrayGameId = s.split(":");
            String[] games = splitArrayGameId[1].split(";");
            int maxRed = 0;
            int maxBlue = 0;
            int maxGreen = 0;
            for (String game : games) {
                String[] gam = game.split(",");
                for (String g : gam) {
                    String[] parts = g.trim().split(" ");
                    int count = Integer.parseInt(parts[0]);
                    if (parts[1].endsWith("red")) {
                        maxRed = Math.max(maxRed, count);
                    }
                    if (parts[1].endsWith("green")) {
                        maxGreen = Math.max(maxGreen, count);
                    }
                    if (parts[1].endsWith("blue")) {
                        maxBlue = Math.max(maxBlue, count);
                    }
                }
            }
            res += (maxBlue * maxGreen * maxRed);
        }
        System.out.println(res);
    }

    public static void main(String[] args) {
        getRes();
        getRes2();
    }

}
