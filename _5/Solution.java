package advent_2023._5;

import advent_2023.Reader;

import java.util.*;

public class Solution {
    private static void getRes2() { // for 1 interval
        String input = Reader.getTestData();
        Scanner scanner = new Scanner(input);
        Map<Long, Long> currentMap = null;
        Map<String, Map<Long, Long>> maps = new HashMap<>();
        List<Long> seeds = new ArrayList<>();
        if (scanner.hasNextLine()) {
            String seedsLine = scanner.nextLine().trim();
            String[] seedParts = seedsLine.replace("seeds: ", "").split(" ");
            for (String seed : seedParts) {
                seeds.add(Long.parseLong(seed));
            }
        }

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            if (line.endsWith("map:")) {
                currentMap = new HashMap<>();
                maps.put(line.replace(" map:", ""), currentMap);
            } else if (currentMap != null) {
                String[] parts = line.split(" ");
                long end = Integer.parseInt(parts[0]);
                long start = Integer.parseInt(parts[1]);
                long count = Integer.parseInt(parts[2]);

                for (int i = 0; i < count; i++) {
                    currentMap.put(start + i, end + i);
                }
            }
        }
        List<Long> resList = new ArrayList<>();
        for (long seed : seeds) {
            Map<Long, Long> next = maps.get("seed-to-soil");
            long soil = seed;
            if (next.containsKey(seed)) {
                soil = next.get(seed);
            }
            Map<Long, Long> next2 = maps.get("soil-to-fertilizer");
            long fertilizer = soil;
            if (next2.containsKey(soil)) {
                fertilizer = next2.get(soil);
            }
            Map<Long, Long> next3 = maps.get("fertilizer-to-water");
            long water = fertilizer;
            if (next3.containsKey(fertilizer)) {
                water = next3.get(fertilizer);
            }
            Map<Long, Long> next4 = maps.get("water-to-light");
            long light = water;
            if (next4.containsKey(water)) {
                light = next4.get(water);
            }
            Map<Long, Long> next5 = maps.get("light-to-temperature");
            long temperature = light;
            if (next5.containsKey(light)) {
                temperature = next5.get(light);
            }
            Map<Long, Long> next6 = maps.get("temperature-to-humidity");
            long humidity = temperature;
            if (next6.containsKey(temperature)) {
                humidity = next6.get(temperature);
            }
            Map<Long, Long> next7 = maps.get("humidity-to-location");
            long location = humidity;
            if (next7.containsKey(humidity)) {
                location = next7.get(humidity);
            }
            resList.add(location);
        }

        Collections.sort(resList);
        System.out.println(resList.get(0));

    }

    private static void getRes() {
        String input = Reader.getTestData();

        Scanner scanner = new Scanner(input);
        Map<Long, Long> currentMap = null;
        Map<String, Map<Long, Long>> maps = new HashMap<>();
        List<Long> seeds = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            if (line.startsWith("seeds:")) {
                String[] seedParts = line.replace("seeds: ", "").split(" ");
                for (String seed : seedParts) {
                    seeds.add(Long.parseLong(seed));
                }
            } else if (line.endsWith("map:")) {
                currentMap = new HashMap<>();
                maps.put(line.replace(" map:", ""), currentMap);
            } else if (currentMap != null) {
                String[] parts = line.split(" ");
                long start = Long.parseLong(parts[1]);
                long end = Long.parseLong(parts[0]);
                long count = Long.parseLong(parts[2]);

                for (long i = 0; i < count; i++) {
                    currentMap.put(start + i, end + i);
                }
            }
        }

        TreeSet<Long> locations = new TreeSet<>();
        for (long seed : seeds) {
            long location = seed;
            for (String mapName : maps.keySet()) {
                Map<Long, Long> map = maps.get(mapName);
                location = map.getOrDefault(location, location);
            }
            locations.add(location);
        }

        if (!locations.isEmpty()) {
            System.out.println(locations.first());
        } else {
            System.out.println("No locations found.");
        }
    }

    public static void main(String[] args) {
        getRes();
        getRes2();
    }
}
