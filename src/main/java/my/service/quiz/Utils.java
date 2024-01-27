package my.service.quiz;

import java.util.Random;

public class Utils {

    public static int[][] generateMatrix(int i, int j){
        Random r = new Random();
        // generate 2x2 matrix
        int[][] result = new int[i][j];
        for (int k = 0; k < i; k++) {
            for (int l = 0; l < j; l++) {
                result[k][l] = r.nextInt(11) - 5;   //get int from -5 to +5
            }
        }
        return result;
    }

    public static String matrixToString(int[][] matrix){

        // row example - '1 & 2 & 3\\ '
        StringBuilder result = new StringBuilder("\\begin{bmatrix}");
        for (int[] ints : matrix) {
            StringBuilder temp = new StringBuilder();
            for (int anInt : ints) {
                temp.append(anInt).append(" & ");
            }
            temp = new StringBuilder(temp.substring(0, temp.length() - 3) + "\\\\ ");
            result.append(temp);
        }
        result = new StringBuilder(result.substring(0, result.length() - 2) + "end{bmatrix}");
        return result.toString();
    }

}
