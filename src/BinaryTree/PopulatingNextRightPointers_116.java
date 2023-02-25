package BinaryTree;

import java.util.LinkedList;
import java.util.Queue;

public class PopulatingNextRightPointers_116 {
    public static Node connect(Node root) {
        if(root == null) {
            return root;
        }
        Queue<Node> nodeQueue = new LinkedList<>();
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
