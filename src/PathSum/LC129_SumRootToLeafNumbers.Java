package PathSum;

/**
 * You are given the root of a binary tree containing digits from 0 to 9 only.
 * <p>
 * Each root-to-leaf path in the tree represents a number.
 * <p>
 * For example, the root-to-leaf path 1 -> 2 -> 3 represents the number 123.
 * Return the total sum of all root-to-leaf numbers. Test cases are generated so that the answer will fit in a 32-bit integer.
 * <p>
 * A leaf node is a node with no children.
 */

// 要返回从根节点到每个叶节点的所有路径之和，有一条路径就要算一次
class Solution {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x;}
    }
    public int sumNumbersV0(TreeNode root) {
        int[] total = new int[]{0};
        helper(root, 0, total);
        return total[0];        
    }
    private void helper(TreeNode root, int currSum, int[] total){
        if(root == null){
            return;
        }
        currSum = currSum * 10 + root.val;
        if(root.left == null && root.right == null){
            total[0] += currSum;
            return;
        }        
        helper(root.left, currSum, total);
        helper(root.right, currSum, total);
    }

    public int sumNumbersV1(TreeNode root) {
        return helper(root, 0);
    }
    //helper的含义是计算从该节点出发的所有路径和的总和，因此无需再用一个引用变量算所有路径的总和
    private int helper(TreeNode root, int currSum){
        if(root == null){
            return 0;
        }
        currSum = currSum * 10 + root.val;
        if(root.left == null && root.right == null){
            return currSum;
        }
        return helper(root.left, currSum) + helper(root.right, currSum);
    }
}

