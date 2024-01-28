package my.service.quiz.autogeneratedQuestions;

import my.service.quiz.entities.AutogeneratedQuestion;
import my.service.quiz.enums.QuestionType;

import java.util.Random;

import static my.service.quiz.Utils.generateMatrix;
import static my.service.quiz.Utils.matrixToString;

public class Question5 implements IAutogeneratedQuestion{
    @Override
    public AutogeneratedQuestion generateQuestion(String topic) {
        Random r = new Random();

        int firstMatrixRows = r.nextInt(4) + 1;    // random number from 1 to 4
        int firstMatrixColumns = r.nextInt(4) + 1;
        int secondMatrixColumns = r.nextInt(4) + 1;

        int[][] a = generateMatrix(firstMatrixRows, firstMatrixColumns);
        int[][] b = generateMatrix(firstMatrixColumns, secondMatrixColumns);




        String task = "Find the product of matrices A and B:\n" +
                "<math-field>A=" + matrixToString(a) + "</math-field>\n" +
                "<math-field>B=" + matrixToString(b) + "</math-field>";

        String answer = matrixToString(multiplyMatrix(a, b));

        return new AutogeneratedQuestion(QuestionType.FORMULA, task, answer, null, null, null, topic, null);
    }

    public int[][] multiplyMatrix(int[][] a, int[][] b){
        int[][] result = new int[a.length][b[0].length];

        // multiply row by column:
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = multiplyRowByColumn(a[i], b, j);
            }
        }
        return result;
    }

    public int multiplyRowByColumn(int[] row, int[][] b, int columnIndex){
        int result = 0;
        for (int i = 0; i < row.length; i++) {
            result+=row[i] * b[i][columnIndex];
        }
        return result;
    }
}
