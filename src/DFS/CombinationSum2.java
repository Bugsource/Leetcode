package DFS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given a collection of candidate numbers (candidates) and a target number (target),
 * find all unique combinations in candidates where the candidate numbers sum to target.
 * Each number in candidates may only be used once in the combination.
 * Note: The solution set must not contain duplicate combinations.
 * <p>
 * Example 1:
 * Input: candidates = [10,1,2,7,6,1,5], target = 8
 * Output:
 * [
 * [1,1,6],
 * [1,2,5],
 * [1,7],
 * [2,6]
 * ]
 * <p>
 * Example 2:
 * Input: candidates = [2,5,2,1,2], target = 5
 * Output:
 * [
 * [1,2,2],
 * [5]
 * ]
 * <p>
 * Constraints:
 * <p>
 * 1 <= candidates.length <= 100
 * 1 <= candidates[i] <= 50
 * 1 <= target <= 30
 */
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
                // 注意这里的深浅拷贝问题，必须new一个新对象出来
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
