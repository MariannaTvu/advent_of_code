package advent_2023._16;


import advent_2023.Reader;

import java.util.HashSet;
import java.util.Set;

public class Solution {
    private static long result = 0;

    public static void main(String[] args) {
        String input = Reader.getTestData();
        getRes(input);
        System.out.println(result);
    }

    private static void getRes(String input) {
        char[][] mirrors = parseMatrix(input);
        simulate(mirrors, 0, 0, 4, new HashSet<>(), new HashSet<>());
    }

    private static void simulate(char[][] matrix, int startI, int startJ, int direction, Set<String> visited, Set<String> marked) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        int currentI = startI;
        int currentJ = startJ;

        while (currentI >= 0 && currentI < rows && currentJ >= 0 && currentJ < cols) {
            boolean split = false;
            char cell = matrix[currentI][currentJ];
            String posKey = currentI + "," + currentJ + "," + direction;
            if (visited.contains(posKey)) {
                return;
            }
            visited.add(posKey);
            switch (cell) {
                case '.':
                    break;
                case '\\':
                    if (direction == 1) direction = 3;
                    else if (direction == 2) direction = 4;
                    else if (direction == 3) direction = 1;
                    else if (direction == 4) direction = 2;
                    break;
                case '/':
                    if (direction == 1) direction = 4;
                    else if (direction == 2) direction = 3;
                    else if (direction == 3) direction = 2;
                    else if (direction == 4) direction = 1;
                    break;
                case '-':
                    if (direction == 1 || direction == 2) {
                        split = true;
                        simulate(matrix, currentI, currentJ - 1, 3, visited, marked); // Left
                        simulate(matrix, currentI, currentJ + 1, 4, visited, marked); // Right
                    }
                    break;
                case '|':
                    if (direction == 3 || direction == 4) {
                        split = true;
                        simulate(matrix, currentI - 1, currentJ, 1, visited, marked); // Up
                        simulate(matrix, currentI + 1, currentJ, 2, visited, marked); // Down
                    }
                    break;
            }
            String k = currentI + " " + currentJ;
            if (!marked.contains(k)) {
                result++;
                marked.add(k);
            }
            if (!split) {
                if (direction == 1) currentI--;
                else if (direction == 2) currentI++;
                else if (direction == 3) currentJ--;
                else if (direction == 4) currentJ++;
            }
        }
    }

    private static char[][] parseMatrix(String matrixString) {
        String[] lines = matrixString.split("\n");
        int rows = lines.length;
        int cols = lines[0].length();
        char[][] matrix = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = lines[i].charAt(j);
            }
        }
        return matrix;
    }
}