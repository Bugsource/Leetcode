package BinaryTree;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

// 完美二叉树，除叶子节点外，‌每个节点都有两个子节点‌，且‌所有叶子节点都在同一层‌；
// 要求把所有兄弟节点连接起来。
public class LC116M_PopulatingNextRightPointers {
    public static Node connect(Node root) {
        if(root == null) {
            return root;
        }
        Queue<Node> nodeQueue = new ArrayDeque<>();
        nodeQueue.offer(root);
        while (!nodeQueue.isEmpty()) {
            int levelNodeCount = nodeQueue.size();
            Node pre = null;
            Node curr = null;
            for(int i = 0; i < levelNodeCount; ++ i) {
                curr = nodeQueue.poll();
                curr.next = pre;
                pre = curr;
                // 注意，必须先压入右子节点
                if(curr.right != null) {
                    nodeQueue.offer(curr.right);
                }
                if(curr.left != null) {
                    nodeQueue.offer(curr.left);
                }
            }
        }
        return root;
    }

    // O(1)空间解法，不需要借助队列
    public Node connectOpt(Node root) {
        Node currLevelStart = root;
        while (currLevelStart != null) {

            Node node = currLevelStart;
            while (node != null){
                if(node.left == null) {
                    // 完美二叉树，左孩子为空，意味着到了最后一层
                    break;
                }

                node.left.next = node.right;
                if(node.next != null) {
                    node.right.next = node.next.left;
                }

                node = node.next;
            }
            // 移动到下一层，利用完美二叉树的性质，如果下一层是叶子节点，那下一次迭代退出循环
            currLevelStart = currLevelStart.left;
        }
        return root;
    }

    public static void main(String[] args) {
        Node root = new Node(1);

        Node left = new Node(2);
        left.left = new Node(4);
        left.right = new Node(5);
        Node right = new Node(3);
        right.left = new Node(6);
        right.right = new Node(7);

        root.left = left;
        root.right = right;

        connect(root);
    }


}
