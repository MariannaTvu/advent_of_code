package advent_2023._13;


import advent_2023.Reader;

import java.util.ArrayList;
import java.util.List;

public class Solution {

    public static void main(String[] args) {
        String input = Reader.getTestData();
        long res = getRes(input);
        System.out.println(res);
    }

    private static long getRes(String input) {
        long res = 0;
        String[] matrixStrings = input.split("\n\n");
        List<char[][]> mirrors = new ArrayList<>();
        for (int i = 0; i < matrixStrings.length; i++) {
            mirrors.add(parseMatrix(matrixStrings[i]));
        }
        for (int i = 0; i < mirrors.size(); i++) {
            char[][] m = mirrors.get(i);
            int vLineIdx = findVerticalMirror(m) + 1;
            int hLineIdx = findHorizontalMirror(m) + 1;
            System.out.println(vLineIdx + " " + hLineIdx);
            res += vLineIdx + hLineIdx * 100;
        }
        System.out.println(res);
        return 0;
    }

    private static int findHorizontalMirror(char[][] matrix) {
        int rows = matrix.length;

        for (int mid = 0; mid < rows - 1; mid++) {
            boolean isMirror = true;
            int temp = mid;

            for (int row = mid + 1; row < rows && temp >= 0; row++, temp--) {
                if (!areRowsMirrored(matrix, temp, row)) {
                    isMirror = false;
                    break;
                }
            }
            if (isMirror) {
                return mid;
            }
        }
        return -1;
    }

    private static boolean areColumnsMirrored(char[][] matrix, int col1, int col2) {
        for (char[] chars : matrix) {
            if (chars[col1] != chars[col2]) {
                return false;
            }
        }
        return true;
    }

    private static boolean areRowsMirrored(char[][] matrix, int row1, int row2) {
        int cols = matrix[0].length;
        for (int col = 0; col < cols; col++) {
            if (matrix[row1][col] != matrix[row2][col]) {
                return false;
            }
        }
        return true;
    }


    private static int findVerticalMirror(char[][] matrix) {
        int cols = matrix[0].length;
        for (int mid = 0; mid < cols - 1; mid++) {
            boolean isMirror = true;
            int temp = mid;
            for (int col = mid + 1; col < cols && temp >= 0; col++, temp--) {
                if (!areColumnsMirrored(matrix, temp, col)) {
                    isMirror = false;
                    break;
                }
            }
            if (isMirror) {
                return mid;
            }
        }
        return -1;
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
        for (char[] chars : matrix) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(chars[j] + " ");
            }
            System.out.println();
        }
    }
}