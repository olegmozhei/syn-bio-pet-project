package my.service.quiz;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

public class MatricesTests {

    @Test
    public void checkMatrixToStringConversionTest(){
        int[][] matrix = new int[2][2];
        matrix[0][0] = 1;
        matrix[0][1] = 2;
        matrix[1][0] = 3;
        matrix[1][1] = 4;

        String expectedResult = "\\begin{bmatrix}1 & 2\\" +
                                "\\ 3 & 4\\end{bmatrix}";

        Assert.isTrue(Utils.matrixToString(matrix).equals(expectedResult), "");
    }
}
