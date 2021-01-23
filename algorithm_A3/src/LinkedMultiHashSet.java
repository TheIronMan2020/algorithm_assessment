
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * LinkedMultiHashSet is an implementation of a (@see MultiSet), using a hashtable as the internal
 * data structure, and with predictable iteration order based on the insertion order
 * of elements.
 * 
 * Its iterator orders elements according to when the first occurrence of the element 
 * was added. When the multiset contains multiple instances of an element, those instances 
 * are consecutive in the iteration order. If all occurrences of an element are removed, 
 * after which that element is added to the multiset, the element will appear at the end of the 
 * iteration.
 * 
 * The internal hashtable array should be doubled in size after an add that would cause it to be
 * at full capacity. The internal capacity should never decrease.
 * 
 * Collision handling for elements with the same hashcode (i.e. with hashCode()) should be done
 * using linear probing, as described in lectures.
 * 
 * @param <T> type of elements in the set
 */
public class LinkedMultiHashSet<T> implements MultiSet<T>, Iterable<T> {
    /** array that stores element according to hashcode and compression */
    private T[] array;
    /** array that record the order of elements */
    private T[] queue;
    /** maximum number of distinct elements */
    private int capacity;
    /** total count of elements in the collection */
    private int size = 0;
    /** count of distinct elements in the set */
    private int distinct = 0;
    /** keep tracks of the occurrences of elements*/
    private int[] count;
    /** next empty position of queue */
    private int current;
    /** deletion marker */
    private Object object = new Object();
    
    public LinkedMultiHashSet(int initialCapacity) {
        this.capacity = initialCapacity;
        array = (T[]) new Object[this.capacity];
        queue = (T[]) new Object[this.capacity];
        count = new int[this.capacity];
    }

    /**
     * Add newly added to queue to retain element order.
     *
     * @param e be added to queue.
     * @TimeComplexitiy O(1)
     * @MemoryComplexitiy O(1)
     */
    private void enQueue(T e) {
        queue[current++] = e;
        }

    // dequeue the element that's been removed entirely and
    // fill the gap

    /**
     * Remove element from queue to retain element order.
     *
     * @param e to be removed from queue.
     * @TimeComplexitiy O(n)
     * @MemoryComplexitiy O(1)
     */
    private void deQueue(T e) {

        int mark = 0;

        for(int i = 0; i < queue.length; ++i) {
            if (e.equals(queue[i])) {
                queue[i] = null;
                mark = i;
                break;
            }
        }

        for (int i = mark; i < current - 1; i++) {
            T temp = queue[i+1];
            queue[i] = temp;
            queue[i+1] = null;
        }

        current -= 1;
    }

    /**
     * Double the size of internal array storing elements when it is full.
     * @TimeComplexitiy O(n)
     * @MemoryComplexitiy O(n)
     */
    public void resize() {
        int length = array.length;
        Iterator<T> iterator = iterator();

        T[] q = (T[]) new Object[size()];

        for (int i = 0; i < q.length; i ++) {
            q[i] = iterator.next();
        }

        array = (T[]) new Object[length * 2];
        queue = (T[]) new Object[length * 2];
        count = new int[length * 2];
        this.capacity *= 2;
        size = 0;
        current = 0;
        distinct = 0;

        for (int i = 0; i < q.length; i++) {
            add(q[i]);
        }
    }

    /**
     * Adds the element to the set. If an equal element is already in the set,
     * increases its occurrence count by 1.
     *
     * @param element to add
     * @require element != null
     * @TimeComplexitiy O(1)
     * @MemoryComplexitiy O(1)
     */
    @Override
    public void add(T element) {
        int hash = element.hashCode() % capacity;
        int n = 0;

        if (!contains(element)) {
            while (array[hash] != null && !array[hash].equals(element) && n <= capacity && !array[hash].equals(object)) {
                hash = (hash + 1) % capacity;
                n ++;
            }

            if (n <= capacity) {
                if (array[hash] == null || array[hash].equals(object))
                    array[hash] = element;
                // Count numbers of occurrence
                count[hash] ++;
                distinct ++;
                enQueue(element);
            }

        } else {
            while (array[hash] != null && !array[hash].equals(element) && n <= capacity) {
                hash = (hash + 1) % capacity;
                n ++;
            }

            if (n <= capacity)
                if (array[hash].equals(element))
                    // Count numbers of occurrence
                    count[hash] ++;
        }

        size ++;

        // resize if the collection is full
        if (distinct == capacity) {
            resize();
        }
    }

    /**
     * Adds count to the number of occurrences of the element in set.
     *
     * @param element to add
     * @require element != null && count >= 0
     * @TimeComplexitiy O(1)
     * @MemoryComplexitiy O(1)
     */
    @Override
    public void add(T element, int count) {
        while (count > 0) {
            add(element);
            count --;
        }
    }

    /**
     * Checks if the element is in the set (at least once).
     *
     * @param element to check
     * @return true if the element is in the set, else false.
     * @TimeComplexitiy O(1)
     * @MemoryComplexitiy O(1)
     */
    @Override
    public boolean contains(T element) {
        int hash = element.hashCode() % capacity;
        int n = 0;

        while (array[hash] != null && !array[hash].equals(element) && n <= capacity) {
            hash = (hash + 1) % capacity;
            n ++;
        }

        if (n <= capacity) {
            // element not exists
            if (array[hash] == null)
                return false;
        }

        // element found
        return array[hash].equals(element);
    }

    /**
     * Returns the count of how many occurrences of the given elements there
     * are currently in the set.
     *
     * @param element to check
     * @return the count of occurrences of element
     * @TimeComplexitiy O(1)
     * @MemoryComplexitiy O(1)
     */
    @Override
    public int count(T element) {
        int hash = element.hashCode() % capacity;
        int n = 0;

        while (array[hash] != null && !array[hash].equals(element) && n <= capacity) {
            hash = (hash + 1) % capacity;
            n ++;
        }

        return this.count[hash];
    }

    /**
     * Removes a single occurrence of element from the set.
     *
     * @param element to remove
     * @throws NoSuchElementException if the set doesn't currently
     *         contain the given element
     * @require element != null
     * @TimeComplexitiy O(1) if still contains the element after removal, otherwise O(n)
     * @MemoryComplexitiy O(1)
     */
    @Override
    public void remove(T element) throws NoSuchElementException {
        if (!contains(element)) throw new NoSuchElementException();

        int hash = element.hashCode() % capacity;
        int n = 0;

        while (array[hash] != null && !array[hash].equals(element) && n <= capacity) {
            hash = (hash + 1) % capacity;
            n ++;
        }

        if (-- count[hash] == 0) {
            // remove element from array
            array[hash] = (T) object;
            distinct --;
            // remove element from queue
            deQueue(element);
        }

        size --;
    }

    /**
     * Removes several occurrences of the element from the set.
     *
     * @param element to remove
     * @param count the number of occurrences of element to remove
     * @throws NoSuchElementException if the set contains less than
     *         count occurrences of the given element
     * @require element != null && count >= 0
     * @TimeComplexitiy O(1)
     * @MemoryComplexitiy O(1)
     */
    @Override
    public void remove(T element, int count) throws NoSuchElementException {
        if (count(element) < count) throw new NoSuchElementException();

        while (count > 0) {
            remove(element);
            count --;
        }
    }

    /**
     * Returns the total count of all elements in the multiset.
     *
     * Note that duplicates of an element all contribute to the count here.
     *
     * @return total count of elements in the collection
     * @TimeComplexitiy O(1)
     * @MemoryComplexitiy O(1)
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Returns the maximum number of *distinct* elements the internal data
     * structure can contain before resizing.
     *
     * @return capacity of internal array
     * @TimeComplexitiy O(1)
     * @MemoryComplexitiy O(1)
     */
    @Override
    public int internalCapacity() {
        return capacity;
    }

    /**
     * Returns the number of distinct elements currently stored in the set.
     *
     * @return count of distinct elements in the set
     * @TimeComplexitiy O(1)
     * @MemoryComplexitiy O(1)
     */
    @Override
    public int distinctCount() {
        return distinct;
    }

    /**
     * Returns an iterator for the multiset that return elements grouped by equivalence,
     * Additionally, these groups of elements should be ordered by when the earliest
     * occurrence of that element was added.
     *
     * @returns an iterator over the elements grouped by equivalence.
     * @timecomplexity O(1)
     * @memorycomplexity O(1)
     */
    @Override
    public Iterator<T> iterator() {
        return new MyIterator<>();
    }

    /**
     * A class which implements generic Iterator interface.
     */
    private class MyIterator<T> implements Iterator<T> {
        private int size;
        int[] countCopy;
        int cur = 0;

        public MyIterator() {
            size = LinkedMultiHashSet.this.size();
            countCopy = count.clone();
        }

        /**
         * To tell whether the HashSet has next element or not.
         *
         * @returns true if it has next element, false otherwise.
         */
        public boolean hasNext() {
            return size != 0;
        }

        /**
         * To tell whether the HashSet has next element or not.
         *
         * @returns element if the HashSet has next element.
         */
        public T next() {
            if (hasNext()) {
                // next element to be popped
                T element = (T) queue[cur];
                int hash = element.hashCode() % capacity;

                while (array[hash] != null && !array[hash].equals(element)) {
                    hash = (hash + 1) % capacity;
                }
                // if it is the last time it occurs, move to next element
                if (--countCopy[hash] == 0) {
                    cur ++;
                }

                size --;
                return element;
            }

            return null;
        }
    }
}