package BinaryTree;


public class SymmetricTree {

    public boolean isSymmetric(TreeNode root) {
        if(root == null) {
            return true;
        }
        return isSubtreeSymmetric(root.left, root.right);
    }

    private boolean isSubtreeSymmetric(TreeNode left, TreeNode right) {
        if(left == null || right == null) {
            return left == right;
        }

        if(left.val != right.val) {
            return false;
        }

        return isSubtreeSymmetric(left.left, right.right) && isSubtreeSymmetric(left.right, right.left);
    }
}
