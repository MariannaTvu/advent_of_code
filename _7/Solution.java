package advent_2023._7;

import advent_2023.Reader;

import java.util.*;

public class Solution {
    public static void main(String[] args) {
        getRes();
    }

    private static void getRes() {
        String testData = Reader.getTestData();
        String[] hands = testData.split("\\n");
        Map<String, Integer> handMap = new HashMap<>();
        List<String> list = new ArrayList<>();
        for (String hand : hands) {
            String[] h = hand.trim().split(" ");
            list.add(h[0]);
            handMap.put(h[0], Integer.parseInt(h[1]));
        }
        list.sort(new PokerHandComparator());
        System.out.println(list);
        long res = 0;
        int count = 1;
        for (String hand : list) {
            res += (count * handMap.get(hand));
            count++;
        }
        System.out.println(res);
    }

    public static class PokerHandComparator implements Comparator<String> {
        private static final String RANKS = "AKQJT98765432";

        @Override
        public int compare(String hand1, String hand2) {
            int[] hand1Values = handValue(hand1);
            int[] hand2Values = handValue(hand2);
            if (hand1Values[0] != hand2Values[0]) {
                return Integer.compare(hand1Values[0], hand2Values[0]);
            }
            for (int i = 0; i < hand1.length(); i++) {
                int rank1 = RANKS.indexOf(hand1.charAt(i));
                int rank2 = RANKS.indexOf(hand2.charAt(i));
                if (rank1 != rank2) {
                    return Integer.compare(rank2, rank1);
                }
            }
            return 0;
        }

        private int[] handValue(String hand) {
            Map<Character, Integer> frequencyMap = new HashMap<>();
            for (char card : hand.toCharArray()) {
                frequencyMap.put(card, frequencyMap.getOrDefault(card, 0) + 1);
            }
            int handType = determineHandType(frequencyMap);
            int highestRank = getHighestRank(frequencyMap, hand);
            return new int[]{handType, highestRank};
        }

        private int determineHandType(Map<Character, Integer> freqMap) {
            if (isFiveOfAKind(freqMap)) return 9;
            if (isFourOfAKind(freqMap)) return 8;
            if (isFullHouse(freqMap)) return 7;
            if (isThreeOfAKind(freqMap)) return 6;
            if (isTwoPair(freqMap)) return 5;
            if (isOnePair(freqMap)) return 4;
            return 3;
        }

        private int getHighestRank(Map<Character, Integer> freqMap, String hand) {
            int highestRank = -1;
            for (char card : hand.toCharArray()) {
                if (freqMap.get(card) > 1) {
                    highestRank = Math.max(highestRank, RANKS.indexOf(card));
                }
            }
            return highestRank;
        }

        private boolean isFiveOfAKind(Map<Character, Integer> freq) {
            return freq.containsValue(5);
        }

        private boolean isFourOfAKind(Map<Character, Integer> freq) {
            return freq.containsValue(4);
        }

        private boolean isFullHouse(Map<Character, Integer> freq) {
            return freq.containsValue(3) && freq.containsValue(2);
        }

        private boolean isThreeOfAKind(Map<Character, Integer> freq) {
            return freq.containsValue(3);
        }

        private boolean isTwoPair(Map<Character, Integer> freq) {
            return freq.values().stream().filter(v -> v == 2).count() == 2;
        }

        private boolean isOnePair(Map<Character, Integer> freq) {
            return freq.containsValue(2);
        }
    }
}