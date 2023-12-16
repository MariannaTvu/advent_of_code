package advent_2023._15;


import advent_2023.Pair;
import advent_2023.Reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution2 {
    private static Map<Long, List<Pair<String, Long>>> lenses = new HashMap<>();

    public static void main(String[] args) {
        String input = Reader.getTestData();
        getRes(input);
        long res = calcFocusPower();
        System.out.println(res);
    }

    private static void getRes(String input) {
        String[] in = input.split(",");
        for (String s : in) {
            String[] ch = s.split("=");
            if (ch.length == 1) {
                ch = s.split("-");
                getRemove(ch);
            } else {
                getAdd(ch);
            }
        }
    }

    private static long calcFocusPower() {
        long res = 0;
        long a;
        long b;
        long c;
        for (Map.Entry<Long, List<Pair<String, Long>>> entry : lenses.entrySet()) {
            if (entry.getValue().size() == 0) continue;
            a = 1 + entry.getKey();
            List<Pair<String, Long>> lenses = entry.getValue();
            for (int i = 0; i < lenses.size(); i++) {
                b = i + 1;
                c = lenses.get(i).getValue();
                res += (a * b * c);
            }
        }
        return res;
    }

    private static void getAdd(String[] ch) {
        long key = getHash(ch[0]);
        if (lenses.containsKey(key)) {
            List<Pair<String, Long>> list = lenses.get(key);
            boolean updated = false;
            for (Pair<String, Long> lense : list) {
                if (lense.getKey().equals(ch[0])) {
                    lense.setValue(Long.parseLong(ch[1]));
                    updated = true;
                    break;
                }
            }
            if (!updated) {
                list.add(new Pair<>(ch[0], Long.parseLong(ch[1])));
            }
        } else {
            List<Pair<String, Long>> list = new ArrayList<>();
            list.add(0, new Pair<>(ch[0], Long.parseLong(ch[1])));
            lenses.put(key, list);
        }
    }

    private static void getRemove(String[] ch) {
        long key = getHash(ch[0]);
        Pair<String, Long> lensToRemove = null;
        if (lenses.containsKey(key)) {
            List<Pair<String, Long>> list = lenses.get(key);
            for (Pair<String, Long> lense : list) {
                if (lense.getKey().equals(ch[0])) {
                    lensToRemove = lense;
                }
            }
        }
        if (lensToRemove != null) {
            lenses.get(key).remove(lensToRemove);
        }
    }

    private static long getHash(String s) {
        long res = 0;
        char[] ch = s.toCharArray();
        for (char c : ch) {
            long r = 0;
            int code = c;
            res += code;
            res *= 17;
            res %= 256;
            res += r;
        }
        return res;
    }
}