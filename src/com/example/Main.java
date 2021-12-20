package com.example;

public class Main {

    static ThreadLocal<String> local = new ThreadLocal<>();

    public static void main(String[] args) {

        ListNode<String> head = new ListNode<>("a");
        head.next = new ListNode<>("b");
        head.next.next = new ListNode<>("c");
        head.next.next.next = new ListNode<>("d");

        System.out.println("head = " + head);
        head = reverse(head);
        System.out.println("head = " + head);
    }

    public static ListNode<String> reverse(ListNode<String> head) {
        if (head == null) {
            return null;
        }
        ListNode<String> pre = null;
        ListNode<String> curr = head;
        ListNode<String> next = null;
        while (curr != null) {
            next = curr.next;
            curr.next = pre;
            pre = curr;
            curr = next;
        }
        return pre;
    }
}

class ListNode<T> {
    T val;
    ListNode<T> next;

    public ListNode(T val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "ListNode{" +
                "val=" + val +
                ", next=" + next +
                '}';
    }
}
