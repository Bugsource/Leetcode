package BinaryTree;

import static util.Util.assertEquals;

// 求所有节点之间，最小的差值；因为是二叉搜索树，核心就是中序遍历。
// 一般是用类的成员变量缓存上一个非空节点，这里在栈上引用一个堆上的变量来避免
public class LC530E_MinimumAbsoluteDifferenceInBST {

    // 定义一个极简的状态容器，替代成员变量
    // 为什么不用 Integer/TreeNode 传参？
    // 1. Integer 是不可变对象，赋值操作只会改变局部变量的指向，无法影响调用者。
    // 2. TreeNode 引用传递的是地址副本，修改副本的指向（prev = node）无法同步回上层栈帧。
    // 3. 此容器对象在堆上，所有递归层持有同一对象的引用，修改其字段对所有层可见。
    static class State {
        TreeNode prev = null;
        int minDiff = Integer.MAX_VALUE;
    }

    public int getMinimumDifference(TreeNode root) {
        State state = new State(); // 状态封装在局部变量中，线程安全，无残留
        inorder(root, state);
        return state.minDiff;
    }

    private void inorder(TreeNode node, State state) {
        if (node == null) return;

        inorder(node.left, state);

        // 核心逻辑：通过共享的 state 对象访问和更新状态
        if (state.prev != null) {
            state.minDiff = Math.min(state.minDiff, node.val - state.prev.val);
        }
        state.prev = node; // 直接修改堆上对象的内容，上层递归立即可见

        inorder(node.right, state);
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
