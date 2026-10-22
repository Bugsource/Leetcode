package BinaryTree;

public class LC222M_CountCompleteTreeNodes {


    /**
     * 计算完全二叉树的节点个数
     * <p>
     * 【核心结论与通俗解释】：
     * 1. 我们只比较“左子树的最左深度”和“右子树的最左深度”。
     * <p>
     * 2. 如果 左深度 == 右深度：
     *    -> 说明最后一层的节点已经延伸到了右边。
     *    -> 此时【左子树】一定是一个完美的满二叉树。
     *    -> 策略：直接用公式算出左子树节点数，然后递归去算【右子树】。
     * <p>
     * 3. 如果 左深度 > 右深度：
     *    -> 说明最后一层的节点只在左边，右边没填满。
     *    -> 此时【右子树】一定是一个完美的满二叉树（虽然比左子树矮一层）。
     *    -> 策略：直接用公式算出右子树节点数，然后递归去算【左子树】。
     * <p>
     * 4. 满二叉树节点数公式：2^h - 1。加上根节点后，这部分总数即为 2^h (即 1 << h)。
     * <p>
     * 每次算完两边子树的深度，就会剪枝掉一半的节点。算深度的复杂度是 O(logn)，递归计算的复杂度也是O(logn)，所以总复杂度是O(logn)平方。
     */
    public int countNodes(TreeNode root) {
        if(root == null) {
            return 0;
        }
        int heightOfLeftSubTree = height(root.left);
        int heightOfRightSubTree = height(root.right);
        if(heightOfLeftSubTree == heightOfRightSubTree) {
            // 左子树一定填满了，否则右子树不会有最后一层；填满了则可以套用公式直接算；
            // 但是右子树不一定填满，需要递归继续算
            // 注意，左移运算符优先级低，需要加括号
            return (1 << heightOfLeftSubTree) + countNodes(root.right);
        }
        else {
            // 左子树的高度 = 右子树高度 + 1；这代表右子树一定填满，直接套用公式；递归算左子树
            return (1 << heightOfRightSubTree) + countNodes(root.left);
        }
    }

    // 注意，都是算的左子树的高度
    private static int height(TreeNode root) {
        int height = 0;
        while(root != null) {
            ++ height;
            root = root.left;
        }
        return height;
    }

}
