package advent_2023._7;

import advent_2023.Reader;

import java.util.*;

public class Solution2 {

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
        long res = 0;
        int count = 1;
        for (String hand : list) {
            res += (count * handMap.get(hand));
            count++;
        }
        System.out.println(res);
    }

    public static class PokerHandComparator implements Comparator<String> {
        private static final String RANKS = "AKQT98765432J";

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
                if (card == 'J') continue;
                frequencyMap.put(card, frequencyMap.getOrDefault(card, 0) + 1);
            }
            int jCount = 0;
            for (char c : hand.toCharArray()) {
                if (c == 'J') jCount++;
            }
            int handType = determineHandType(frequencyMap, jCount);
            int highestRank = getHighestRank(frequencyMap, hand);

            return new int[]{handType, highestRank};
        }


        private int determineHandType(Map<Character, Integer> freqMap, int jCount) {
            int val = 0;
            if (isFiveOfAKind(freqMap)) val = 9;
            else if (isFourOfAKind(freqMap)) val = 8;
            else if (isFullHouse(freqMap)) val = 7;
            else if (isThreeOfAKind(freqMap)) val = 6;
            else if (isTwoPair(freqMap)) val = 5;
            else if (isOnePair(freqMap)) val = 4;
            else val = 3;
            if (jCount == 5) return 9; // 5 oak
            if (jCount == 4 && val == 3) return 9; // 1 + 4j = 5oak
            if (jCount == 3 && val == 4) return 9; // 3j + 1 pair = 5 oak
            if (jCount == 3 && val == 3) return 8; // 3j + high = 4oak
            if (jCount == 2 && val == 6) return 9; // 2j + 3oak = 5 oak
            if (jCount == 2 && val == 4) return 8; // 2j + 1pair = 4 oak
            if (jCount == 2 && val == 3) return 6; // 2j + high = 3 oak
            if (jCount == 1 && val == 8) return 9; // 1j + 4 oak ..
            if (jCount == 1 && val == 7) return 8; // full house + 1 joker = 4 oak
            if (jCount == 1 && val == 6) return 8; //
            if (jCount == 1 && val == 5) return 7; // fh
            if (jCount == 1 && val == 4) return 6;
            if (jCount == 1 && val == 3) return 4;
            return val;
        }

        private int getHighestRank(Map<Character, Integer> freqMap, String hand) {
            int highestRank = -1;
            for (char card : hand.toCharArray()) {
                if (card == 'J') continue;
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