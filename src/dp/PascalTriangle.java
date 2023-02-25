package dp;

import java.util.ArrayList;
import java.util.List;

public class PascalTriangle {
    public static List<List<Integer>> generate0(int numRows) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> prevRow = null;
        for (int i = 0; i < numRows; ++i) {
            List<Integer> currRow = new ArrayList<>();
            for (int j = 0; j <= i; ++j) {
                if (j == 0 || j == i) {
                    currRow.add(1);
                } else {
                    currRow.add(prevRow.get(j - 1) + prevRow.get(j));
                }
            }
            prevRow = currRow;
            res.add(new ArrayList<>(currRow));
        }
        return res;
    }

    public static List<List<Integer>> generate(int numRows) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> currRow = new ArrayList<>();

        for (int i = 0; i < numRows; ++i) {
            // 每一行都比上一行的元素多一个1，加在队头
            currRow.add(0, 1);
            for (int j = 1; j < currRow.size() - 1; ++j) {
                // 从第二个元素开始，利用上一次的结果进行dp，直到倒数第二个元素
                currRow.set(j, currRow.get(j) + currRow.get(j+1));
            }
            res.add(new ArrayList<>(currRow));
        }
        return res;
    }

    public static void main(String[] args) {
        List<List<Integer>> res = generate(5);
        System.out.println(res);
    }

}
