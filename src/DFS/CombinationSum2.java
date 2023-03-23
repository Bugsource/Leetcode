package DFS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CombinationSum2 {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(candidates);
        DFS(candidates, target, 0, new ArrayList<>(), res);
        return res;
    }

    private void DFS(int[] candidates, int target, int index, List<Integer> currCombinations, List<List<Integer>> res) {

        for(int i = index; i < candidates.length; ++ i) {
            if(target < 0 || (i > index && candidates[i] == candidates[i-1])) {
                // 当前target已经小于0，没必要再重复计算了
                // 重复值判断，必须i > index而不是0
                continue;
            }
            currCombinations.add(candidates[i]);
            int nextTarget = target - candidates[i];
            if(nextTarget == 0) {
                res.add(new ArrayList<>(currCombinations));
            } else {
                // 取当前值
                DFS(candidates, nextTarget, i + 1, currCombinations, res);
            }

            currCombinations.remove(currCombinations.size()-1);
            //  DFS(candidates, target, index + 1, currCombinations, res);
        }
    }

    public static void main(String[] args) {
        CombinationSum2 combinationSum2 = new CombinationSum2();

        int[] input1 = new int[]{10,1,2,7,6,1,5};

        List<List<Integer>> res1 = combinationSum2.combinationSum2(input1, 8);
        System.out.println(res1.toString());
    }
}
