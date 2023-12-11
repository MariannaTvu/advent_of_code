package advent_2023._10;

import advent_2023.Reader;

import java.util.*;

public class Solution2 {
    private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // R, D, L, U
    private static final Map<Character, Map<String, Set<Character>>> pipeConnections = new HashMap<>();
    public static final String OUTER = "O";
    private static int[] START = null;
    private static int[] PART_2_START = new int[]{37, 0}; // set start manually

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

    public static void traverseAndMark(char[][] matrix, int[][] dist) {
        int rows = dist.length;
        int cols = dist[0].length;
        String[][] directionS = new String[rows][cols];
        boolean[][] visited = new boolean[rows][cols];

        Queue<int[]> queue = new LinkedList<>();
        int[] start = findStartOne(dist);
        queue.add(start);
        visited[start[0]][start[1]] = true;
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];

            for (int[] direction : DIRECTIONS) {
                int newX = x + direction[0];
                int newY = y + direction[1];
                boolean isValid = isValid(newX, newY, rows, cols);
                if (!isValid) continue;
                boolean canMove = (dist[newX][newY] - dist[x][y]) == 1;
                if (canMove && !visited[newX][newY]) {
                    directionS[newX][newY] = getDirection(newX, newY, x, y);
                    if (x == start[0] && y == start[1]) {
                        directionS[x][y] = directionS[newX][newY];
                    }
                    queue.add(new int[]{newX, newY});
                    visited[newX][newY] = true;
                }
            }
        }
        boolean[][] visitedFinal = new boolean[rows][cols];
        applyPlusSign(matrix, directionS, PART_2_START, rows, cols, visitedFinal);
        clear(directionS);
        int res = 0;
        for (String[] row : directionS) {
            for (String c : row) {
                char ch = toCharDirection(c);
                if (ch == '-') {
                    res++;
                }
            }
        }
        System.out.println(res);
    }

    private static void clear(String[][] tubeMap) {
        int rows = tubeMap.length;
        int cols = tubeMap[0].length;
        Deque<int[]> toVisit = new LinkedList<>();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == 0 || j == 0 || i == rows - 1 || j == cols - 1 || OUTER.equals(tubeMap[i][j])) {
                    toVisit.add(new int[]{i, j});
                }
            }
        }
        while (!toVisit.isEmpty()) {
            int[] current = toVisit.poll();
            int currentRow = current[0];
            int currentCol = current[1];

            for (int[] dir : DIRECTIONS) {
                int newRow = currentRow + dir[0];
                int newCol = currentCol + dir[1];
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && tubeMap[newRow][newCol] == null) {
                    tubeMap[newRow][newCol] = ".";
                    toVisit.add(new int[]{newRow, newCol});
                }
            }
        }
    }

    private static void applyPlusSign(char[][] matrix, String[][] directionS, int[] part2Start, int rows, int cols, boolean[][] visitedFinal) {
        int[] point = part2Start;
        do {
            int i = point[0];
            int k = point[1];
            visitedFinal[i][k] = true;
            if (directionS[i][k] == null || directionS[i][k].equals("0")) return;

            switch (matrix[i][k]) {
                case '|':
                    if (directionS[i][k].equals("UP") && k > 0 && (directionS[i][k - 1] == null || directionS[i][k - 1].equals("0"))) {
                        directionS[i][k - 1] = OUTER; // | and UP -> + on the left
                    }
                    if (directionS[i][k].equals("DOWN") && k < cols - 1 && (directionS[i][k + 1] == null || directionS[i][k + 1].equals("0"))) {
                        directionS[i][k + 1] = OUTER; // | and DOWN -> + on the right
                    }
                    break;

                case '-':
                    if (directionS[i][k].equals("RIGHT") && i > 0 && (directionS[i - 1][k] == null || directionS[i - 1][k].equals("0"))) {
                        directionS[i - 1][k] = OUTER; // - and RIGHT -> + on top
                    }
                    if (directionS[i][k].equals("LEFT") && i < rows - 1 && (directionS[i + 1][k] == null || directionS[i + 1][k].equals("0"))) {
                        directionS[i + 1][k] = OUTER; // - and LEFT -> + on bottom
                    }
                    break;

                case 'F':
                    if (directionS[i][k].equals("RIGHT")) {
                        if (k > 0 && (directionS[i][k - 1] == null || directionS[i][k - 1].equals("0"))) {
                            directionS[i][k - 1] = OUTER; // F and UP -> + on the left
                        }
                        if (i > 0 && (directionS[i - 1][k] == null || directionS[i - 1][k].equals("0"))) {
                            directionS[i - 1][k] = OUTER; // F and UP -> + on top
                        }
                    }
                    break;
                case '7':
                    if (directionS[i][k].equals("DOWN")) {
                        if (k < cols - 1 && (directionS[i][k + 1] == null || directionS[i][k + 1].equals("0"))) {
                            directionS[i][k + 1] = OUTER; // 7 and UP -> + on the right
                        }
                        if (i > 0 && (directionS[i - 1][k] == null || directionS[i - 1][k].equals("0"))) {
                            directionS[i - 1][k] = OUTER; // 7 and UP -> + on top
                        }
                    }
                    break;
                case 'L':
                    if (directionS[i][k].equals("UP")) {
                        if (k > 0 && (directionS[i][k - 1] == null || directionS[i][k - 1].equals("0"))) {
                            directionS[i][k - 1] = OUTER; // L and DOWN -> + on the left
                        }
                        if (i < rows - 1 && (directionS[i + 1][k] == null || directionS[i + 1][k].equals("0"))) {
                            directionS[i + 1][k] = OUTER; // L and DOWN -> + on bottom
                        }
                    }
                    break;
                case 'J':
                    if (directionS[i][k].equals("RIGHT")) {
                        if (k < cols - 1 && (directionS[i][k + 1] == null || directionS[i][k + 1].equals("0"))) {
                            directionS[i][k + 1] = OUTER; // J and DOWN -> + on the right
                        }
                        if (i < rows - 1 && (directionS[i + 1][k] == null || directionS[i + 1][k].equals("0"))) {
                            directionS[i + 1][k] = OUTER; // J and DOWN -> + on bottom
                        }
                    }
                    break;
            }
            int[] inc = directionToInc(directionS[i][k]);
            point = new int[]{point[0] + inc[0], point[1] + inc[1]};

        } while (!Arrays.equals(point, part2Start));
    }

    private static int[] directionToInc(String direction) {
        switch (direction) {
            case "UP":
                return new int[]{-1, 0};
            case "DOWN":
                return new int[]{1, 0};
            case "LEFT":
                return new int[]{0, -1};
            case "RIGHT":
                return new int[]{0, 1};
            default:
                throw new RuntimeException("Unsupported direction: " + direction);
        }
    }

    private static char toCharDirection(String direction) {
        if (direction == null) {
            return '-';
        }
        switch (direction) {
            case "DOWN":
                return '↓';
            case "UP":
                return '↑';
            case "LEFT":
                return '←';
            case "RIGHT":
                return '→';
            case OUTER:
                return 'O';
            default:
                return '.';
        }
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
        replaceNonLoopTiles(matrix, distances);
        clean(matrix);
        fixDist(distances);
        traverseAndMark(matrix, distances);
        return distances;
    }

    private static void fixDist(int[][] dist) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(START);
        int rows = dist.length;
        int cols = dist[0].length;
        int prev = dist[START[0]][START[1]];
        boolean[][] visited = new boolean[rows][cols];
        visited[START[0]][START[1]] = true;
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];
            boolean foundBigger = false;
            for (int[] direction : DIRECTIONS) {
                int newX = x + direction[0];
                int newY = y + direction[1];
                if (isValid(newX, newY, rows, cols) && !visited[newX][newY]) {
                    if (dist[newX][newY] == prev + 1) {
                        foundBigger = true;
                        prev = dist[newX][newY];
                        queue.add(new int[]{newX, newY});
                    }
                }
            }
            if (!foundBigger) {
                markFromSmaller(dist, x, y);
                return;
            }
        }
    }

    private static void markFromSmaller(int[][] dist, int xStart, int yStart) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{xStart, yStart});
        int rows = dist.length;
        int cols = dist[0].length;
        int prev = dist[xStart][yStart];
        int currentVal = dist[xStart][yStart] + 1;
        boolean[][] visited = new boolean[rows][cols];
        visited[START[0]][START[1]] = true;
        visited[xStart][yStart] = true;
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];
            for (int[] direction : DIRECTIONS) {
                int newX = x + direction[0];
                int newY = y + direction[1];
                if (isValid(newX, newY, rows, cols) && !visited[newX][newY] && dist[newX][newY] != 0) {
                    if (dist[newX][newY] == (prev - 1)) {
                        prev = dist[newX][newY];
                        dist[newX][newY] = currentVal;
                        currentVal++;
                        queue.add(new int[]{newX, newY});
                    }
                }
                if (isValid(newX, newY, rows, cols) && visited[newX][newY] && dist[newX][newY] == 0) {
                    dist[newX][newY] = currentVal;
                    return;
                }
            }
        }
    }

    private static void clean(char[][] tubeMap) {
        for (int i = 0; i < tubeMap.length; i++) {
            for (int j = 0; j < tubeMap[0].length; j++) {
                if (tubeMap[i][j] != '.') break;
                tubeMap[i][j] = '0';
            }
        }
        for (int j = 0; j < tubeMap[0].length; j++) {
            for (int i = 0; i < tubeMap.length; i++) {
                if (tubeMap[i][j] != '.') break;
                tubeMap[i][j] = '0';
            }
        }
        for (int i = tubeMap.length - 1; i >= 0; i--) {
            for (int j = tubeMap[0].length - 1; j >= 0; j--) {
                if (tubeMap[i][j] != '.') break;
                tubeMap[i][j] = '0';
            }
        }
        for (int j = tubeMap[0].length - 1; j >= 0; j--) {
            for (int i = tubeMap.length - 1; i >= 0; i--) {
                if (tubeMap[i][j] != '.') break;
                tubeMap[i][j] = '0';
            }
        }
    }

    public static void replaceNonLoopTiles(char[][] matrix, int[][] distances) {
        int startX = -1, startY = -1;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == 'S') {
                    startX = i;
                    startY = j;
                    break;
                }
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if ((i != startX || j != startY) && distances[i][j] == 0) {
                    matrix[i][j] = '.';
                }
            }
        }
    }

    private static boolean isValid(int x, int y, int rows, int cols) {
        return x >= 0 && y >= 0 && x < rows && y < cols;
    }

    private static int[] findStart(char[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 'S') {
                    START = new int[]{i, j};
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }


    private static int[] findStartOne(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 1) {
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
        traverseAndCalculateDistance(matrix);
    }
}
