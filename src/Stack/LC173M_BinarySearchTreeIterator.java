package Stack;

import BinaryTree.TreeNode;

import java.util.ArrayDeque;

// 设计一个迭代器，对二叉树进行中序遍历
public class LC173M_BinarySearchTreeIterator {

    private final ArrayDeque<TreeNode> stack;
    public LC173M_BinarySearchTreeIterator(TreeNode root) {
        stack = new ArrayDeque<>();
        pushIntoStack(root);
    }

    private void pushIntoStack(TreeNode root) {
        while (root != null) {
            stack.push(root);
            root = root.left;
        }
    }

    public int next() {
        TreeNode minNode = stack.pop();
        // 中序遍历的核心思想
        pushIntoStack(minNode.right);
        return minNode.val;
    }

    public boolean hasNext() {
        return !stack.isEmpty();
    }
}
