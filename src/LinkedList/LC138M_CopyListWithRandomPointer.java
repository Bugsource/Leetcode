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
