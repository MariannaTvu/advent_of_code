package advent_2023._14;


import advent_2023.Reader;

public class Solution {

    public static void main(String[] args) {
        String input = Reader.getTestData();
        long res = getRes(input);
        System.out.println(res);
    }

    private static long getRes(String input) {
        long res = 0;
        char[][] mc = parseMatrix(input);
        moveOsUp(mc);
        int rows = mc.length;
        int cols = mc[0].length;
        int count = mc.length;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                // System.out.print(mc[row][col] + " ");
                if (mc[row][col] == '1' || mc[row][col] == 'O') {
                    res += (count);
                }
            }
            count--;
        }
        return res;
    }

    private static void moveOsUp(char[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        for (int col = 0; col < cols; col++) {
            for (int row = 1; row < rows; row++) {
                if (matrix[row][col] == 'O') {
                    int currentRow = row;
                    while (currentRow > 0 && matrix[currentRow - 1][col] == '.') {
                        matrix[currentRow - 1][col] = 'O';
                        matrix[currentRow][col] = '.';
                        currentRow--;
                    }
                    if (currentRow > 0 && (matrix[currentRow - 1][col] == '#' || matrix[currentRow - 1][col] == '1')) {
                        matrix[currentRow][col] = '1';
                    }
                }
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

    private static void printMatrix(char[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}