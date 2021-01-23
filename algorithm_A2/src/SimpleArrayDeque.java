import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleArrayDeque<T> implements SimpleDeque<T> {

    int left;
    int right;
    int capacity;
    T[] array;
    boolean meet;
    int size;

    /**
     * Constructs a new array based deque with limited capacity.
     * 
     * @param capacity the capacity
     * @throws IllegalArgumentException if capacity <= 0
     */
    public SimpleArrayDeque(int capacity) throws IllegalArgumentException {
        if (capacity <= 0) throw new IllegalArgumentException();

        this.capacity = capacity;
        left = -1;
        right = 0;
        size = 0;
        array = (T[]) new Object[capacity];
    }

    /**
     * Constructs a new array based deque with limited capacity, and initially populates the deque
     * with the elements of another SimpleDeque.
     *
     * @param otherDeque the other deque to copy elements from. otherDeque should be left intact.
     * @param capacity the capacity
     * @throws IllegalArgumentException if capacity <= 0 or size of otherDeque is > capacity
     */
    public SimpleArrayDeque(int capacity, SimpleDeque<? extends T> otherDeque) 
            throws IllegalArgumentException {
        if (capacity <= 0 || capacity < otherDeque.size())
            throw new IllegalArgumentException();

        this.capacity = capacity;
        left = -1;
        right = 0;
        size = 0;
        array = (T[]) new Object[capacity];

        Iterator<T> iterator = (Iterator<T>) otherDeque.iterator();
        while (iterator.hasNext()) {
            this.pushRight((T) iterator.next());
        }
    }

    @Override
    public boolean isEmpty() {
        return (left == -1);
    }

    @Override
    public boolean isFull() {
        return ((left == 0 && right == capacity-1) || left == right+1);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void pushLeft(T e) throws RuntimeException {
        if(isFull()) throw new RuntimeException();

        if (left == -1) {
            left = 0;
            right = 0;
        }
        else if (left == 0)
            left =  capacity - 1;
        else
            left -= 1;

        size ++;
        array[left] = e;
    }

    @Override
    public void pushRight(T e) throws RuntimeException {
        if(isFull()) throw new RuntimeException();

        if (left == -1) {
            left = 0;
            right = 0;
        }
        else if (right == capacity-1)  right = 0;
        else right += 1;

        size ++;
        array[right] = e;
    }

    @Override
    public T peekLeft() throws NoSuchElementException {
        if(isEmpty()) throw new NoSuchElementException();
        return array[left];
    }

    @Override
    public T peekRight() throws NoSuchElementException {
        if(isEmpty()) throw new NoSuchElementException();
        return array[right];
    }

    @Override
    public T popLeft() throws NoSuchElementException {
        if(isEmpty()) throw new NoSuchElementException();

        size --;
        if (left == right) {
            T tmp = array[left];
            array[left] = null;
            left = -1;
            right = -1;
            return tmp;
        }
        else if (left == capacity-1) {
            T tmp = array[capacity-1];
            array[capacity-1] = null;
            left = 0;
            return tmp;
        }
        else {
            T tmp = array[left];
            array[left] = null;
            left += 1;

            return tmp;
        }


    }


    @Override
    public T popRight() throws NoSuchElementException {
        if(isEmpty()) throw new NoSuchElementException();

        size --;
        if (left == right) {
            T tmp = array[left];
            array[left] = null;
            left = -1;
            right = -1;
            return tmp;
        }
        else if (right == 0) {
            right = capacity - 1;
            T tmp = array[0];
            array[0] = null;
            return tmp;
        }
        else {
            T tmp = array[right];
            array[right] = null;
            right -= 1;
            return tmp;
        }
    }


    @Override
    public Iterator<T> iterator() {
        return new DequeIterator<T>();
    }

    private class DequeIterator<T> implements Iterator<T> {
        private int head = left;
        private int tail = right;
        int size = 0;

        public boolean hasNext() {
            if (size >= SimpleArrayDeque.this.size()) {
                head = left;
                tail = right;
                size = 0;
                return false;
            }

            return true;
        }

        public T next() {
            T result = (T) array[head];
            if (head == capacity - 1)
                head = 0;
            else
                head += 1;
            size ++;
            return result;
        }
    }


    @Override
    public Iterator<T> reverseIterator() {
        return new DequeReverseIterator<T>();
    }

    private class DequeReverseIterator<T> implements Iterator<T> {
        private int head = left;
        private int tail = right;
        int size = 0;

        public boolean hasNext(){
            if (size >= SimpleArrayDeque.this.size()) {
                head = left;
                tail = right;
                size = 0;
                return false;
            }
            return true;
        }

    public T next() {
            T result = (T) array[tail];
            if (tail == 0)
                tail = capacity - 1;
            else
                tail  -= 1;
            size ++;
            return result;
        }
    }

    public static void main(String[] args) {
        SimpleArrayDeque<Integer> deque = new SimpleArrayDeque<>(5);
        deque.pushLeft(5);

        deque.pushLeft(4);

        deque.pushLeft(3);

        deque.pushRight(1);

        deque.pushRight(2);
//
//        System.out.println(Arrays.toString(deque.array));
//
//
//        for (int i = 0; i < 6; ++i) {
//            System.out.println(deque.popLeft());
//            System.out.println(deque.peekLeft());
//        }

        for (int i = 0; i < 6; ++i) {
            System.out.println(deque.popRight());
        }

        /**test iterator*/
        System.out.println("--------------");
        System.out.println(Arrays.toString(deque.array));
        Iterator<Integer> it = deque.iterator();
//
////        System.out.println(deque.left);
////        System.out.println(deque.right);
//
////        System.out.println(it.next());
//        System.out.println(it.hasNext());
//        System.out.println(it.next());  System.out.println(it.hasNext());
//        System.out.println(it.next());  System.out.println(it.hasNext());
//        System.out.println(it.next());  System.out.println(it.hasNext());
//        System.out.println(it.next());  System.out.println(it.hasNext());
//        System.out.println(it.next());  System.out.println(it.hasNext());
//        System.out.println(it.next());


        /**test reverse iterator*/
//        System.out.println("--------------");
//        Iterator<Integer> itr = deque.reverseIterator();
//        System.out.println(itr.hasNext());
//        System.out.println(itr.next());System.out.println(itr.hasNext());
//        System.out.println(itr.next());System.out.println(itr.hasNext());
//        System.out.println(itr.next());System.out.println(itr.hasNext());
//        System.out.println(itr.next());System.out.println(itr.hasNext());
//        System.out.println(itr.next());System.out.println(itr.hasNext());
//        System.out.println(itr.next());

        //pass otherDeque test
//        SimpleArrayDeque<Integer> deque2 = new SimpleArrayDeque<>(10, deque);
//                for (int i = 0; i < 6; ++i) {
//            System.out.println(deque.popRight());
//        }
    }
}
