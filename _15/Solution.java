package advent_2023._15;

import advent_2023.Reader;

public class Solution {

    public static void main(String[] args) {
        String input = Reader.getTestData();
        long res = getRes(input);
        System.out.println(res);
    }

    private static long getRes(String input) {
        long res = 0;
        String[] in = input.split(",");
        for (String s : in) {
            long r = 0;
            char[] ch = s.toCharArray();
            for (int i = 0; i < ch.length; i++) {
                int code = ch[i];
                r += code;
                r *= 17;
                r %= 256;
            }
            res += r;
        }
        return res;
    }
}