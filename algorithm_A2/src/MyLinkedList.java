public class MyLinkedList {

    private Node head;
    private Node tail;
    int size;

    public MyLinkedList() {
        this.head = null;
        this.tail = null;
        size = 0;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void insertFirst(int data) {
        Node newNode = new Node(data);

        if(isEmpty()){
            tail = newNode;
        }else{
            head.pre = newNode;
        }
        newNode.next = head;
        head = newNode;
        size++;

    }

    public void insertLast(int data){
        Node newNode = new Node(data);
        // if first insertion head should
        // also point to this node
        if(isEmpty()){
            head = newNode;
        }else{
            tail.next = newNode;
            newNode.pre = tail;
        }
        tail = newNode;
        size++;
    }

    public void deleteFirst() {
        if (head.next == null) {
            tail = null;
        } else {
            head.next.pre = null;
        }
        head = head.next;
        size--;
    }

    public void deleteLast() {
        if(head.next == null){
            head = null;
        } else {
            tail.pre.next = null;
        }
        tail = tail.pre;
        size--;
    }

    void displayForward() {
        Node cur = head;
        while(cur != null) {
            cur.printNode();
            cur = cur.next;
        }
    }

    void displayBackward() {
        Node cur = tail;
        while(cur != null) {
            cur.printNode();
            cur = cur.pre;
        }
    }

    private class Node {
        int data;
        Node next;
        Node pre;
        public Node(int data) {
            this.data = data;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public void setPre(Node pre) {
            this.pre = pre;
        }

        public int printNode() {
            return this.data;
        }
    }
}
