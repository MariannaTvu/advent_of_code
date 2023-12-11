package advent_2023._10;

import advent_2023.Reader;

import java.util.*;

public class Solution {
    //  | is a vertical pipe
    //  - is a horizontal pipe
    //  L  ==   └
    //  J  ==   ┘
    //  7  ==   ┐
    //  F  ==   ┌
    //  . is ground; there is no pipe in this tile.
    private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // R, D, L, U
    private static final Map<Character, Map<String, Set<Character>>> pipeConnections = new HashMap<>();
    static {
        pipeConnections.put('-', new HashMap<>() {{
            put("RIGHT", Set.of('-', '7', 'J'));
            put("LEFT", Set.of('-', 'L', 'F'));
        }});
        pipeConnections.put('|', new HashMap<>() {{
            put("UP", Set.of('|', 'F', '7'));
            put("DOWN", Set.of('|', 'J', 'L'));
        }});
        pipeConnections.put('L', new HashMap<>() {{
            put("UP", Set.of('|', '7', 'F'));
            put("RIGHT", Set.of('-', '7', 'J'));
        }});
        pipeConnections.put('J', new HashMap<>() {{
            put("UP", Set.of('|', 'F', '7'));
            put("LEFT", Set.of('-', 'L', 'F'));
        }});
        pipeConnections.put('7', new HashMap<>() {{
            put("DOWN", Set.of('|', 'L', 'J'));
            put("LEFT", Set.of('-', 'L', 'F'));
        }});
        pipeConnections.put('F', new HashMap<>() {{
            put("DOWN", Set.of('|', 'J', 'L'));
            put("RIGHT", Set.of('-', 'J', '7'));
        }});
        pipeConnections.put('S', new HashMap<>() {{
            put("DOWN", Set.of('|', 'J', 'L'));
            put("RIGHT", Set.of('-', 'J', '7'));
            put("LEFT", Set.of('-', 'L', 'F'));
            put("UP", Set.of('|', 'F', '7'));
        }});
    }

    private static boolean canMove(int x, int y, char pipe, int newX, int newY, char[][] matrix) {
        if (newX < 0 || newX >= matrix.length || newY < 0 || newY >= matrix[0].length) return false;
        char nextPipe = matrix[newX][newY];

        String direction = getDirection(x, y, newX, newY);
        Map<String, Set<Character>> validConnections = pipeConnections.get(pipe);
        return validConnections != null && validConnections.getOrDefault(direction, Set.of()).contains(nextPipe);
    }

    private static String getDirection(int x, int y, int newX, int newY) {
        if (newX > x) return "DOWN";
        if (newX < x) return "UP";
        if (newY > y) return "RIGHT";
        return "LEFT";
    }

    public static int[][] traverseAndCalculateDistance(char[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] distances = new int[rows][cols];
        boolean[][] visited = new boolean[rows][cols];

        Queue<int[]> queue = new LinkedList<>();
        int[] start = findStart(matrix);
        queue.add(start);
        visited[start[0]][start[1]] = true;
        distances[start[0]][start[1]] = 0;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];

            for (int[] direction : DIRECTIONS) {
                int newX = x + direction[0];
                int newY = y + direction[1];

                if (isValid(newX, newY, rows, cols) && !visited[newX][newY] && canMove(x, y, matrix[x][y], newX, newY, matrix)) {
                    visited[newX][newY] = true;
                    distances[newX][newY] = distances[x][y] + 1;
                    queue.add(new int[]{newX, newY});
                }
            }
        }
        for (int[] row : distances) {
            for (int c : row) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
        return distances;
    }

    private static boolean isValid(int x, int y, int rows, int cols) {
        return x >= 0 && y >= 0 && x < rows && y < cols;
    }

    private static int[] findStart(char[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 'S') {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    public static char[][] parseStringToMatrix(String input) {
        String[] lines = input.split("\n");
        int rows = lines.length;
        int cols = lines[0].length();

        char[][] matrix = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            matrix[i] = lines[i].toCharArray();
        }

        return matrix;
    }

    public static void main(String[] args) {
        String input = Reader.getTestData();
        char[][] matrix = parseStringToMatrix(input);
        int[][] res = traverseAndCalculateDistance(matrix);
        int max = 0;
        for (int[] row : res) {
            for (int c : row) {
                max = Math.max(max, c);
            }
        }
        System.out.println(max);
    }
}
