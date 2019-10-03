package com.omg.mytest.arithmetic;

/**
 * @Auther: cui
 * @Date: 2019-08-24 11:04
 * @Description:
 */
public class Node {

    public static class Node1{
        private int value;

        private Node1 next;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public Node1 getNext() {
            return next;
        }

        public void setNext(Node1 next) {
            this.next = next;
        }
    }

    private Node1 reverse(Node1 node){
        if(node ==null || node.next==null){
            return node;
        }
        Node1 next = node.next;
        Node1 newHead = reverse(next);
        next.next = node;
        node.next = null;
        return newHead;
    }

    public static void main(String[] args) {
        Node1 node1 = new Node1();
        node1.setValue(1);
        Node1 node2 = new Node1();
        node2.setValue(3);
        Node1 node3 = new Node1();
        node3.setValue(5);
        node1.setNext(node2);
        node2.setNext(node3);
        Node node = new Node();
        Node1 reverse = node.reverse(node1);
        System.out.println(reverse.getValue());
    }
}
