package wenqi.base;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wenqi on 2021/1/8.
 * LRU算法的双链表实现
 * https://leetcode.com/problems/lru-cache/
 *
 * 基础算法描述：双向链表+Hash表，
 * 由于需要删除指定节点，所以要用双向链表，并且维护冗余的头节点（方便头插入），
 * 冗余尾结点（方便从尾部删除,不用每次费力更新它的值，相对不维护冗余尾部节点）
 */
public class LRUCache {
    public static void main(String[] args){
        LRUCache lRUCache = new LRUCache(2);
        lRUCache.put(1, 1); // cache is {1=1}
        lRUCache.put(2, 2); // cache is {1=1, 2=2}
        System.out.println(lRUCache.get(1));    // return 1
        lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
        System.out.println(lRUCache.get(2));    // returns -1 (not found)
        lRUCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
        System.out.println(lRUCache.get(1));     // return -1 (not found)
        System.out.println(lRUCache.get(3));     // return 3
        System.out.println(lRUCache.get(4));     // return 4
    }

    class ListNode{
        public int key;
        public int val;
        public ListNode next;
        public ListNode parent;
        public ListNode(){}
        public ListNode(int key,int val){
            this.key=key;
            this.val=val;
        }
    }
    private int capacity;
    private int size;
    private ListNode head;
    private ListNode tail;
    private Map<Integer,ListNode> hashMap;
    public LRUCache(int capacity){
        this.capacity=capacity;
        this.size=0;
        hashMap=new HashMap<>();

        head=new ListNode();
        tail=new ListNode();
        head.next=tail;
        tail.parent=head;
    }

    /**
     * 从hashMap中获取key的值，并且将节点提升到头部
     * @param key
     * @return
     */
    public int get(int key){
        if(hashMap.containsKey(key)){
            ListNode curNode=hashMap.get(key);
            //摘除node,更改链表关系
            pickNode(curNode);
            //将摘除的插入头部
            insertToHead(curNode);
            return curNode.val;
        }else{
            return -1;
        }
    }

    /**
     * LRU中插入值
     * 如果存在，则更新值，并将其提升到头部
     * 如果不存在，则判断是否到达容量，
     * 如果未到达容量，则直接插入头部，否则，删除最后一个节点，并插入头部
     * @param key
     * @param value
     */
    public void put(int key, int value){
        if(hashMap.containsKey(key)){
            ListNode curNode=hashMap.get(key);
            curNode.val=value;
            pickNode(curNode);
            insertToHead(curNode);
        }else{
            if(size==capacity){
                //已达到最大容量，需要删除最后的节点
                ListNode lastNode=pickNode(tail.parent);
                hashMap.remove(lastNode.key);
                //利用删除的节点
                lastNode.key=key;
                lastNode.val=value;
                insertToHead(lastNode);
                hashMap.put(key,lastNode);
            }else{
                //未达到最大容量
                ListNode newNode=new ListNode(key,value);
                insertToHead(newNode);
                hashMap.put(key,newNode);
                size++;
            }
        }
    }

    public ListNode pickNode(ListNode curNode){
        ListNode nextNode=curNode.next;
        ListNode parentNode=curNode.parent;
        parentNode.next=nextNode;
        nextNode.parent=parentNode;
        return curNode;
    }

    public void insertToHead(ListNode curNode){
        ListNode nextNode=head.next;

        head.next=curNode;
        curNode.parent=head;

        nextNode.parent=curNode;
        curNode.next=nextNode;
    }

}
