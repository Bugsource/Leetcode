package BinaryTree;

public class ConstructTreeFromInAndPreOrder {

    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        return buildTreeHelper(preorder, inorder, 0, 0, preorder.length);
    }

    private static TreeNode buildTreeHelper(int[] preorder, int[] inorder, int preStartIndex, int inStartIndex, int length) {
        if (preStartIndex >= preorder.length || length <= 0) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[preStartIndex]);

        int inRootIndex = inStartIndex;
        for (int i = inStartIndex; i < inStartIndex + length; ++i) {
            if (inorder[i] == preorder[preStartIndex]) {
                inRootIndex = i;
                break;
            }
        }
        int leftLength = inRootIndex - inStartIndex;
        int rightLength = inStartIndex + length - 1 - inRootIndex;
        TreeNode left = buildTreeHelper(preorder, inorder, preStartIndex + 1, inStartIndex, leftLength);
        TreeNode right = buildTreeHelper(preorder, inorder, preStartIndex + leftLength + 1, inRootIndex + 1, rightLength);
        root.left = left;
        root.right = right;
        return root;
    }

    public static void main(String[] args) {
        int[] pre = new int[]{3,9,20,15,7};
        int[] in = new int[]{9,3,15,20,7};
        buildTree(pre, in);
    }
}
