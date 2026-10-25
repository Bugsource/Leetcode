package BinaryTree;

import java.util.ArrayDeque;
import java.util.Deque;

// 116题的泛化版本
public class LC117M_PopulatingNextRightPointers2 {

    public Node connect(Node root) {
        if(root == null) {
            return null;
        }
        Deque<Node> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelNodeCount = queue.size();

            Node curr = null;
            Node pre = null;
            while(levelNodeCount > 0) {
                -- levelNodeCount;

                curr = queue.poll();
                curr.next = pre;
                pre = curr;
                // 先入队右子节点，因为出队要从右边，从右向左建立连接
                if (curr.right != null) {
                    queue.offer(curr.right);
                }
                if (curr.left != null) {
                    queue.offer(curr.left);
                }
            }
        }
        return root;
    }

    public Node connectOpt(Node root) {
        // dummyHead: 哑节点，始终作为“下一层”链表的虚拟头结点
        // 作用：简化链表头部的插入逻辑，避免处理“第一个节点”的特殊情况
        Node nextLevelDummyHead = new Node();
        Node currLevelStart = root;
        // 外层循环，控制层之间的遍历
        while (currLevelStart != null) {
            // tail: 指向“下一层”链表的当前尾节点，初始指向哑节点
            Node tail = nextLevelDummyHead;
            Node node = currLevelStart;
            // 内层循环，控制当前层的遍历，目的是连接下一层
            while (node != null) {
                if(node.left != null) {
                    // 连接左孩子到下一层链表；
                    // 同时这里也让nextLevelDummyHead指向了链表头部
                    tail.next = node.left;
                    //尾指针后移；注意，这里改的是tail，而不是tail的next指针，所以nextLevelDummyHead指向的是头部
                    tail = tail.next;
                }
                if(node.right != null) {
                    // 连接右孩子到下一层链表
                    tail.next = node.right;
                    tail = tail.next; //尾指针后移
                }
                // 当前层指针右移
                node = node.next;
            }
            // 准备进入下一层：
            // 1. dummyHead.next 即为“下一层”的第一个真实节点
            currLevelStart = nextLevelDummyHead.next;
            // 2. 断开哑节点与下一层的连接，防止下一轮迭代时出现脏数据
            nextLevelDummyHead.next = null;
        }
        return root;
    }
}
