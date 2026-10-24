package BinaryTree;

import static util.Util.assertEquals;

// 求所有节点之间，最小的插值
public class LC530E_MinimumAbsoluteDifferenceInBST {

    public int getMinimumDifference(TreeNode root) {
        inorderTraverse(root);
        return minDiff;
    }
    TreeNode prev = null;
    Integer minDiff = Integer.MAX_VALUE;
    private void inorderTraverse(TreeNode root) {
        if(root == null) {
            return;
        }
        inorderTraverse(root.left);
        if(prev != null) {
            Integer currDiff = root.val - prev.val;
            if(currDiff < minDiff) {
                minDiff = currDiff;
            }
        }
        // 更新前一个节点
        prev = root;
        inorderTraverse(root.right);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(4);

        TreeNode left = new TreeNode(2);
        left.left = new TreeNode(1);
        left.right = new TreeNode(3);
        TreeNode right = new TreeNode(6);

        root.left = left;
        root.right = right;

        assertEquals(1, new LC530E_MinimumAbsoluteDifferenceInBST().getMinimumDifference(root));
    }
}
