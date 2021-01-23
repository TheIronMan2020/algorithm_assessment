import java.util.Iterator;
import java.util.NoSuchElementException;

public class ReversibleDeque<T> implements SimpleDeque<T> {
    T[] array;
    private SimpleDeque<T> data;
    /**
     * Constructs a new reversible deque, using the given data deque to store
     * elements.
     * The data deque must not be used externally once this ReversibleDeque
     * is created.
     * @param data a deque to store elements in.
     */
    public ReversibleDeque(SimpleDeque<T> data) {
        this.data = data;
    }

    public void reverse() {
        array = (T[]) new Object[this.data.size()];
        int size = array.length;

        for (int i = 0; i < size; ++i) {
            array[i] = this.data.popRight();
        }

        for (int i = 0; i < size; ++i) {
            this.data.pushRight(array[i]);
        }
    }

    @Override
    public int size() {
        return this.data.size();
    }

    @Override
    public boolean isEmpty() {
        return this.data.isEmpty();
    }

    @Override
    public boolean isFull() {
        return this.data.isFull();
    }

    @Override
    public void pushLeft(T e) throws RuntimeException {
        this.data.pushLeft(e);
    }

    @Override
    public void pushRight(T e) throws RuntimeException {
        this.data.pushRight(e);
    }

    @Override
    public T peekLeft() throws NoSuchElementException {
        return this.data.peekLeft();
    }

    @Override
    public T peekRight() throws NoSuchElementException {
       return this.data.peekRight();
    }

    @Override
    public T popLeft() throws NoSuchElementException {
        return this.data.popLeft();
    }

    @Override
    public T popRight() throws NoSuchElementException {
       return this.data.popRight();
    }

    @Override
    public Iterator<T> iterator() {
        return this.data.iterator();
    }



    @Override
    public Iterator<T> reverseIterator() {
        return this.data.reverseIterator();
    }


    public static void main(String[] args) {
        SimpleArrayDeque<Integer> deque = new SimpleArrayDeque<>(5);
        deque.pushLeft(5);
        deque.pushLeft(4);
        deque.pushLeft(3);
        deque.pushRight(1);
        deque.pushRight(2);
//
//        ReversibleDeque<Integer> reverse = new ReversibleDeque<>(deque);

//        System.out.println(reverse.popLeft());
//        System.out.println(reverse.popLeft());
//        System.out.println(reverse.popLeft());
//        System.out.println(reverse.popLeft());
//        System.out.println(reverse.popLeft());

        /** linkedDeque test */
//        SimpleLinkedDeque<Integer> deque = new SimpleLinkedDeque<>();
//        deque.pushLeft(1);
//        deque.pushLeft(2);
//        deque.pushLeft(3);
//        deque.pushLeft(4);
//
        ReversibleDeque<Integer> reverse = new ReversibleDeque<>(deque);
        reverse.reverse();
        System.out.println(reverse.popLeft());
        System.out.println(reverse.popLeft());
        System.out.println(reverse.popLeft());
        System.out.println(reverse.popLeft());
        System.out.println(reverse.popLeft());
        System.out.println(reverse.popLeft());
        /** iterator test */
//        ReversibleDeque<Integer> reverse = new ReversibleDeque<>(deque);
//        Iterator<Integer> it = reverse.iterator();
//        while (it.hasNext()) {
//            System.out.println(it.next());
//        }
    }
}
//    public boolean hasNext(){
//        if(head == tail + 1) flag++;
//        if (head == tail + 1) {
//            if (flag != 1) return true;
//            else return false;
//        }
//        return true;
//
//    }
//
//    public T next() {
////            if(head == tail + 1) flag++;
////            if(flag == 1) return null;
//        if (hasNext()) {
//            T result = (T) array[tail];
//            if (tail == 0)
//                tail = capacity - 1;
//            else
//                tail  -= 1;
//
//            return result;
//        }
//        return null;
//
//    }





//
//
//    public boolean hasNext(){
//        return head != tail;
//    }
//
//    public T next() {
//        if(head == tail + 1) flag++;
//        if(flag == 1) return null;
//
//        T result = (T) array[tail];
//        if (tail == 0)
//            tail = capacity - 1;
//        else
//            tail  -= 1;
//
//        return result;
//    }