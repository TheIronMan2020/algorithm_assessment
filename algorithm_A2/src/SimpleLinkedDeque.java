import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleLinkedDeque<T> implements SimpleDeque<T> {
    private int capacity;
    private Node head;
    private Node tail;
    private int size;
    /**
     * Constructs a new linked list based deque with unlimited capacity.
     */
    public SimpleLinkedDeque() {
        capacity = -1;
    }

    /**
     * Constructs a new linked list based deque with limited capacity.
     *
     * @param capacity the capacity
     * @throws IllegalArgumentException if capacity <= 0
     */
    public SimpleLinkedDeque(int capacity) throws IllegalArgumentException {
        this.capacity = capacity;
    }

    /**
     * Constructs a new linked list based deque with unlimited capacity, and initially 
     * populates the deque with the elements of another SimpleDeque.
     *
     * @param otherDeque the other deque to copy elements from. otherDeque should be left intact.
     * @requires otherDeque != null
     */
    public SimpleLinkedDeque(SimpleDeque<? extends T> otherDeque) {
        capacity = -1;
        Iterator<T> iterator = (Iterator<T>) otherDeque.iterator();

        while (iterator.hasNext()) {
            pushLeft((T) iterator.next());
        }
    }
    
    /**
     * Constructs a new linked list based deque with limited capacity, and initially 
     * populates the deque with the elements of another SimpleDeque.
     *
     * @param otherDeque the other deque to copy elements from. otherDeque should be left intact.
     * @param capacity the capacity
     * @throws IllegalArgumentException if capacity <= 0 or size of otherDeque is > capacity
     */
    public SimpleLinkedDeque(int capacity, SimpleDeque<? extends T> otherDeque) 
            throws IllegalArgumentException {

    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public boolean isFull() {
        return size == capacity;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void pushLeft(T e) throws RuntimeException {
        if (isFull()) throw new RuntimeException();

        Node newNode = new Node(e);

        if(isEmpty()){
            tail = newNode;
        }else{
            head.prev= newNode;
        }
        newNode.next = head;
        head = newNode;

        size++;
    }

    @Override
    public void pushRight(T e) throws RuntimeException {
        if (isFull()) throw new RuntimeException();

        Node newNode = new Node(e);

        if(isEmpty()){
            head = newNode;
        }else{
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;

        size++;
    }

    @Override
    public T peekLeft() throws NoSuchElementException {
        if (isEmpty()) throw new NoSuchElementException();
        return head.getItem();
    }

    @Override
    public T peekRight() throws NoSuchElementException {
        if (isEmpty()) throw new NoSuchElementException();
        return tail.getItem();
    }

    @Override
    public T popLeft() throws NoSuchElementException {
        if (isEmpty()) throw new NoSuchElementException();

        Node node = head;

        if (head.next == null) {
            tail = null;
        } else {
            head.next.prev = null;
        }
        head = head.next;
        size--;

        return (T) node.getItem();
    }

    @Override
    public T popRight() throws NoSuchElementException {
        if (isEmpty()) throw new NoSuchElementException();

        Node node = tail;

        if(tail.prev == null){
            head = null;
        } else {
            tail.prev.next = null;
        }
        tail = tail.prev;
        size--;

        return (T) node.getItem();
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedDequeIterator<>();
    }

    private class LinkedDequeIterator<T> implements Iterator<T> {
        private Node cursor =  head;
        private Node fence =  tail;
        Node node;

        public boolean hasNext() { return (cursor != null); }

        public T next() {
            if (hasNext()) {
                node = cursor;
                cursor = cursor.next;
                return (T) node.getItem();
            }
            throw new NoSuchElementException();
        }
    }

    @Override
    public Iterator<T> reverseIterator() {
        return new LinkedDequeReverseIterator<>();
    }

    private class LinkedDequeReverseIterator<T> implements Iterator<T> {
        private Node cursor =  head;
        private Node fence =  tail;
        Node node;

        public boolean hasNext() {
            return (fence != null);
        }

        public T next() {
            if (hasNext()) {
                node = fence;
                fence = fence.prev;
                return (T) node.getItem();
            }
           throw new NoSuchElementException();
        }
    }


    private class Node {
        T e;
        Node next;
        Node prev;

        Node(T e) {
            this.e = e;
        }

        public T getItem() {
            return e;
        }
    }

    public static void main(String[] args) {
        SimpleLinkedDeque<Integer> deque = new SimpleLinkedDeque<>();
        deque.pushLeft(1);
        deque.pushLeft(2);
        deque.pushLeft(3);
        deque.pushLeft(4);




//        System.out.println(deque.peekLeft().toString());
//        System.out.println(deque.popLeft().toString());
//        System.out.println(deque.peekLeft().toString());
//        System.out.println(deque.popLeft().toString());
//        System.out.println(deque.peekLeft().toString());
//        System.out.println(deque.popLeft().toString());
//        System.out.println(deque.peekLeft().toString());
//        System.out.println(deque.popLeft().toString());

        System.out.println(deque.popRight().toString());
        System.out.println(deque.popRight().toString());
        System.out.println(deque.popRight().toString());
        System.out.println(deque.popRight().toString());
        System.out.println(deque.popRight().toString());
        /** iterator test */
//        Iterator<Integer> it = deque.iterator();
//        System.out.println(it.hasNext());
//        System.out.println(it.next());
//        System.out.println(it.hasNext());
//        System.out.println(it.next());
//        System.out.println(it.hasNext());
//        System.out.println(it.next());
//        System.out.println(it.hasNext());
//        System.out.println(it.next());
//        System.out.println(it.hasNext());
//        System.out.println(it.next());

        /** reverse iterator test */
        Iterator<Integer> it = deque.reverseIterator();
        System.out.println(it.hasNext());
        System.out.println(it.next());
        System.out.println(it.hasNext());
        System.out.println(it.next());
        System.out.println(it.hasNext());
        System.out.println(it.next());
        System.out.println(it.hasNext());
        System.out.println(it.next());
        System.out.println(it.hasNext());
        System.out.println(it.next());

        /** Constructor test */
//        SimpleLinkedDeque<Integer> deque2 = new SimpleLinkedDeque<>(deque);
//        System.out.println(deque2.popRight().toString());
//        System.out.println(deque2.popRight().toString());
//        System.out.println(deque2.popRight().toString());
//        System.out.println(deque2.popRight().toString());
//        System.out.println(deque2.popRight().toString());
//

    }
}
