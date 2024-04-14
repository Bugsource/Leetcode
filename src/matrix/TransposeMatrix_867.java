package matrix;

public class TransposeMatrix_867 {
    /**
     * 注意，矩阵转置可能行列不相等，不能像旋转图像(n*n)那题那样，原位对称交换。
     * @param matrix 输入
     */
    public int[][] transpose(int[][] matrix) {
        int rows = matrix.length, cols = matrix[0].length;
        int[][] res = new int [cols][rows];
        for(int i = 0; i < rows; ++ i) {
            for(int j = 0; j < cols; ++ j) {
                res[j][i] = matrix[i][j];
            }
        }
        return res;
    }
}
