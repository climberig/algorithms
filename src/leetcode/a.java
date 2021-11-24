package leetcode;

import java.util.LinkedList;
public class a{
    public static void main(String[] args){
        ListNode node = new ListNode(0, new ListNode(5, new ListNode(-5)));
        ListNode x = new a().sortLinkedList(node);
        System.out.println(x);
    }
    public ListNode sortLinkedList(ListNode head){
        ListNode node = head.next, newHead = head, prev = head;
        while(node != null)
            if(node.val < 0){
                ListNode next = node.next;
                prev.next = next;
                newHead = new ListNode(node.val, newHead);
                node = next;
            }else{
                prev = node;
                node = node.next;
            }
        return newHead;
    }
}
