package advent_2023._4;

import advent_2023.Reader;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Solution {
    private static void getRes() {
        int res = 0;
        String testData = Reader.getTestData();
        String pattern = "Card\\s+\\d+:";
        String[] cards = testData.split(pattern);
        for (String cardFull : cards) {
            if (cardFull.equals("") || cardFull.trim().matches("Card.*")) continue;
            Set<Integer> right = new HashSet<>();
            int lastPoints = 1;
            boolean seen = false;
            String[] cardSplit = cardFull.split("\\|");
            String[] cardLeftSplit = cardSplit[0].trim().split("\\s+");
            String[] cardRightSplit = cardSplit[1].trim().split("\\s+");
            for (String rs : cardRightSplit) {
                right.add(Integer.parseInt(rs));
            }
            for (String ls : cardLeftSplit) {
                int num = Integer.parseInt(ls);
                if (right.contains(num)) {
                    lastPoints *= 2;
                    seen = true;
                }
            }
            if (seen) res += lastPoints / 2;
        }
        System.out.println(res);
    }


    private static void getRes2() {
        long res = 0;
        String testData = Reader.getTestData();
        String pattern = "Card\\s+\\d+:";
        Map<Integer, Integer> id2Occur = new HashMap<>();
        for (int i = 1; i < 210; i++) {
            id2Occur.put(i, 1);
        }
        String[] cards = testData.split(pattern);
        int id = 1;
        for (String cardFull : cards) {
            if (cardFull.equals("") || cardFull.trim().matches("Card.*")) continue;
            Set<Integer> right = new HashSet<>();
            int lastPoints = 1;
            boolean seen = false;
            String[] cardSplit = cardFull.split("\\|");
            String[] cardLeftSplit = cardSplit[0].trim().split("\\s+");
            String[] cardRightSplit = cardSplit[1].trim().split("\\s+");
            for (String rs : cardRightSplit) {
                right.add(Integer.parseInt(rs));
            }
            for (String ls : cardLeftSplit) {
                int num = Integer.parseInt(ls);
                if (right.contains(num)) {
                    lastPoints++;
                    seen = true;
                }
            }
            if (seen) {
                lastPoints--;
                for (int j = 0; j < id2Occur.get(id); j++) {
                    for (int i = id + 1; i <= lastPoints + id; i++) {
                        if (id2Occur.containsKey(i)) {
                            id2Occur.put(i, id2Occur.get(i) + 1);
                        }
                    }
                }
            }
            id++;
        }
        for (int occur : id2Occur.values()) {
            res += occur;
        }
        System.out.println(res);
    }

    public static void main(String[] args) {
        getRes();
        getRes2();
    }

}
