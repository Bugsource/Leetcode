package matrix;

public class RotateImage_48 {
    public void rotate(int[][] matrix) {
        int rowEnd = matrix.length, colEnd = matrix[0].length;
        // 先对矩阵进行上下翻转
        // 如果是逆时针旋转，则是对矩阵进行左右翻转
        for(int i = 0, j = rowEnd - 1; i < j; ++ i, -- j) {
            int[] temp = matrix[i];
            matrix[i] = matrix[j];
            matrix[j] = temp;
        }
        // 转置矩阵（对称性）
        for(int i = 0; i < rowEnd; ++ i) {
            for (int j = i + 1; j < colEnd; ++ j) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
    }
}
