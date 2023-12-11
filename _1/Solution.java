package advent_2023._1;

import advent_2023.Reader;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Character.isDigit;

public class Solution {
    private static void getRes() {
        String input = Reader.getTestData();
        int res = 0;
        String[] in = input.split("\n");
        for (String s : in) {
            char[] cArr = s.toCharArray();
            int l = 0;
            int r = cArr.length - 1;
            boolean found = false;
            while (l <= r && !found) {
                if (isDigit(cArr[l])) {
                    if (isDigit(cArr[r])) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(cArr[l]);
                        sb.append(cArr[r]);
                        res += Integer.parseInt(sb.toString());
                        found = true;
                    } else {
                        r--;
                    }
                } else {
                    l++;
                }
            }
        }
        System.out.println(res);
    }

    private static void getRes2() {
        String input = Reader.getTestData();
        int res = 0;
        Map<String, Integer> wordToDigitMap = new HashMap<>();
        wordToDigitMap.put("one", 1);
        wordToDigitMap.put("two", 2);
        wordToDigitMap.put("three", 3);
        wordToDigitMap.put("four", 4);
        wordToDigitMap.put("five", 5);
        wordToDigitMap.put("six", 6);
        wordToDigitMap.put("seven", 7);
        wordToDigitMap.put("eight", 8);
        wordToDigitMap.put("nine", 9);
        String[] lines = input.split("\n");
        for (String line : lines) {
            char[] chars = line.toCharArray();
            int first = getFirst(chars, wordToDigitMap) * 10;
            int last = getLast(chars, wordToDigitMap);
            res += first + last;
        }
        System.out.println(res);
    }

    private static int getFirst(char[] chars, Map<String, Integer> wordToDigitMap) {
        int i = 0;
        int res = 0;
        StringBuilder sb = new StringBuilder();
        while (i < chars.length) {
            String prefix = sb.toString();
            for (Map.Entry<String, Integer> e : wordToDigitMap.entrySet()) {
                if (prefix.endsWith(e.getKey())) {
                    return e.getValue();
                }
            }
            if (Character.isDigit(chars[i])) {
                return Integer.parseInt(String.valueOf(chars[i]));
            }
            sb.append(chars[i]);
            i++;
        }
        return res;
    }

    private static int getLast(char[] chars, Map<String, Integer> wordToDigitMap) {
        int i = chars.length - 1;
        int res = 0;
        StringBuilder sb = new StringBuilder();
        while (i >= 0) {
            String prefix = sb.toString();
            for (Map.Entry<String, Integer> e : wordToDigitMap.entrySet()) {
                if (prefix.startsWith(e.getKey())) {
                    return e.getValue();
                }
            }
            if (Character.isDigit(chars[i])) {
                return Integer.parseInt(String.valueOf(chars[i]));
            }
            sb.insert(0, chars[i]);
            i--;
        }
        return res;
    }

    public static void main(String[] args) {
        getRes();
        getRes2();
    }
}
