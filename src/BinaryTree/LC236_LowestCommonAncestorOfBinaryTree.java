package BinaryTree;

public class LC236_LowestCommonAncestorOfBinaryTree {

    /**
     * 此方法的语义：
     * <li>如果p和q在两边的子树下，则返回LCA</li>
     * <li>如果只有一个节点在两边的子树下，则返回该节点</li>
     * <li>如果两个节点都不在两边的子树下，则返回空</li>
      */

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null) {
            return null;
        }
        if(root == p || root == q) {
            // 如果当前节点为其中一个节点，则返回
            return root;
        }
        TreeNode leftAncestor = lowestCommonAncestor(root.left, p ,q);
        TreeNode rightAncestor = lowestCommonAncestor(root.right, p ,q);
        if(leftAncestor != null && rightAncestor != null) {
            return root;
        } else if(leftAncestor != null) {
            return leftAncestor;
        } else {
            // leftLCA不为空，但是rightLCA空不空都可能；两个都空返回的是null
            return rightAncestor;
        }
    }
}
