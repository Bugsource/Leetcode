package LinkedList;

/**
 * Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.
 * <p>
 * Implement the LRUCache class:
 * <p>
 * LRUCache(int capacity) Initialize the LRU cache with positive size capacity.
 * <p>
 * int get(int key) Return the value of the key if the key exists, otherwise return -1.
 * <p>
 * void put(int key, int value) Update the value of the key if the key exists. Otherwise, add the key-value pair to the cache. If the number of keys exceeds the capacity from this operation, evict the least recently used key.
 * <p>
 * The functions get and put must each run in O(1) average time complexity.
 */

import java.util.*;

/**
 * Example 1:
 * <p>
 * Input
 * ["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
 * [[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
 * Output
 * [null, null, null, 1, null, -1, null, -1, 3, 4]
 * <p>
 * Explanation
 * LRUCache lRUCache = new LRUCache(2);
 * lRUCache.put(1, 1); // cache is {1=1}
 * lRUCache.put(2, 2); // cache is {1=1, 2=2}
 * lRUCache.get(1);    // return 1
 * lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
 * lRUCache.get(2);    // returns -1 (not found)
 * lRUCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
 * lRUCache.get(1);    // return -1 (not found)
 * lRUCache.get(3);    // return 3
 * lRUCache.get(4);    // return 4
 */
public class LC146_LRU {

    private static class Node {
        int key;
        int val;
        Node pre;
        Node next;
        Node(int key, int val) {
            this.key = key;
            this.val = val;
            this.pre = null;
            this.next = null;
        }
    }

    Node head;
    Node tail;
    Map<Integer, Node> keyToNodeMap;
    int capacity;
    public LC146_LRU(int capacity) {
        head = new Node(0, 0);
        tail = new Node(0, 0);
        head.next = tail;
        tail.pre = head;
        this.capacity = capacity;
        keyToNodeMap = new HashMap<>();
    }

    public int get(int key) {
        Node node = keyToNodeMap.get(key);
        if(node == null) {
            return -1;
        }
        moveToHead(node);
        return node.val;
    }

    public void put(int key, int value) {
        Node node = keyToNodeMap.get(key);
        if(node == null) {
            // 原本不存在这个key，需要新增，那么就可能导致超过容量，需要淘汰
            Node newNode = new Node(key, value);
            keyToNodeMap.put(key, newNode);
            addToHead(newNode);
            if(keyToNodeMap.size() > this.capacity) {
                Node leastUsedRecently = popTail();
                keyToNodeMap.remove(leastUsedRecently.key);
            }
        }
        else {
            // 原来存在这个key，更新节点值，将其移动到队头，代表最近刚访问过
            node.val = value;
            moveToHead(node);
        }
    }

    private Node popTail() {
        Node leastUsedRecently = tail.pre;
        removeNode(leastUsedRecently);
        return leastUsedRecently;
    }

    private void removeNode(Node node) {
        Node pre = node.pre;
        Node next = node.next;

        pre.next = next;
        next.pre = pre;
    }

    private void addToHead(Node node) {
        Node next = head.next;
        node.pre = head;
        node.next = next;
        head.next = node;
        next.pre = node;
    }
    private void moveToHead(Node node) {
        removeNode(node);
        addToHead(node);
    }
}
