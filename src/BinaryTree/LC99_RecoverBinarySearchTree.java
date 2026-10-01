package BinaryTree;

/**
 * 一、先搞懂题目到底要做什么
 * 给你一棵二叉搜索树（BST），但‌恰好有两个节点的值被不小心交换了‌，导致它不再满足BST的性质（左子树所有节点 < 根 < 右子树所有节点）。
 * 要求你不改变树的结构，只把这两个节点的值换回来，恢复成正确的BST。
 * <p>
 * 二、核心思路：用BST最经典的性质解题
 * 你只要记住一个关键结论：‌对正确的BST做中序遍历（左→根→右），得到的序列一定是严格递增的‌。
 * 两个节点被交换，本质上就是把这个递增序列里的两个数换了位置，我们只要在中序遍历的过程里找到这两个“站错位置”的数，最后交换它们的值就完事了。
 * <p>
 * 三、怎么找到这两个错误节点？
 * 交换两个数，只会让递增序列出现‌1处或2处“前一个数 > 后一个数”的逆序对‌，分两种情况：
 * <p>
 * ‌情况1：交换的是中序序列里相邻的两个数‌
 * 比如正确序列是[1,2,3,4,5]，交换2和3，得到[1,3,2,4,5]。
 * 这里只有1处逆序：3>2，这两个数就是要交换的错误节点。
 * <p>
 * ‌情况2：交换的是中序序列里不相邻的两个数‌
 * 比如正确序列是[1,2,3,4,5,6]，交换2和5，得到[1,5,3,4,2,6]。
 * 这里会出现2处逆序：第一处5>3，第二处4>2。要交换的是‌第一处逆序的前一个数（5），和第二处逆序的后一个数（2）‌。
 * <p>
 * ✅ 找节点的统一规则（不用特意区分两种情况）：
 * <p>
 * 遍历过程中记录上一个访问的节点pre
 * 第一次遇到pre.val > 当前节点.val时：把pre记为第一个错误节点first，把当前节点记为第二个错误节点second
 * 之后如果再遇到pre.val > 当前节点.val：只需要把second更新为当前节点即可
 * 遍历结束后，交换first和second的值，树就恢复了。
 */
public class LC99_RecoverBinarySearchTree {
    // 关键点：中序遍历是递增的；
    // 有序数组里交换两个数，产生逆序对，分为两种情况（相邻or不相邻），如上分析；但是逻辑上是可以统一处理的。
    public void recoverTree(TreeNode root) {
        if(root == null) {
            return;
        }
        inorderTraversal(root);
        int temp = first.val;
        first.val = second.val;
        second.val = temp;
    }

    TreeNode first = null;
    TreeNode second = null;
    TreeNode prev = null;
    public void inorderTraversal(TreeNode root) {
        if(root == null) {
            return;
        }
        inorderTraversal(root.left);
        // 找到逆序的两个节点
        if(prev != null && prev.val > root.val) {
            if(first == null) {
                // first只更新一次
                first = prev;
            }
            // second可能会更新一次
            second = root;
        }
        // 逻辑处理完，则更新前一个节点
        prev = root;
        inorderTraversal(root.right);
    }
}
