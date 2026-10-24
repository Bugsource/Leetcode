package LinkedList;

import java.util.HashMap;
import java.util.Map;
// 需要深拷贝链表，特殊之处在于，每个节点还有个随机指针，指向随机节点
public class LC138M_CopyListWithRandomPointer {
    // 思路是，深拷贝新链表的时候，维护老节点和新节点的关系；再遍历一次，再连接新链表
    // 链表节点的随机读取，一般都是用map的思路
    public Node copyRandomList(Node head) {
        // 原始节点和深拷贝节点的映射
        Map<Node, Node> originNodeToNewNode = new HashMap<>();

        Node temp = head;
        // 先创建新链表，完成每个节点的深拷贝
        while (temp != null) {
            Node newNode = new Node(temp.val);
            originNodeToNewNode.put(temp, newNode);
            temp = temp.next;
        }
        Node newHead = originNodeToNewNode.get(head);
        Node tempNewNode = newHead;
        temp = head;
        // 再遍历一次，根据原有链表的映射关系，把新链表的节点关联起来
        while (temp != null && tempNewNode != null) {
            Node originNext = temp.next;
            Node originRandom = temp.random;

            Node newNext = originNodeToNewNode.get(originNext);
            Node newRandom = originNodeToNewNode.get(originRandom);
            tempNewNode.next = newNext;
            tempNewNode.random = newRandom;

            temp = originNext;
            tempNewNode = newNext;
        }

        return newHead;
    }

    // todo:O(1)空间复杂度的做法，不能用map；
    // 思路是把深拷贝节点先连接到每个原始节点后面，利用物理位置临近，将新链表的随机指针关联起来；然后再拆分链表

    // 基础：深拷贝链表，不需要map
    public Node copyPlainList(Node head) {
        if (head == null) return null;

        // 创建哨兵节点，简化头节点处理
        Node dummy = new Node(0);
        Node prevNew = dummy;      // 指向新链表的最后一个节点
        Node curOld = head;        // 指向原链表的当前节点

        while (curOld != null) {
            // 1. 创建新节点
            Node newNode = new Node(curOld.val);

            // 2. 将新节点接到新链表末尾
            prevNew.next = newNode;

            // 3. 指针后移
            prevNew = newNode;     // 新链表指针移到最新节点
            curOld = curOld.next;  // 原链表指针移到下一个
        }

        return dummy.next;
    }

}


// Definition for a Node.
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
