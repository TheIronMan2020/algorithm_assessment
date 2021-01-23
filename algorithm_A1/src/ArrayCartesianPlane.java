/**
 * A 2D cartesian plane implemented as with an array. Each (x,y) coordinate can
 * hold a single item of type <T>.
 *
 * @param <T> The type of element held in the data structure
 */
public class ArrayCartesianPlane<T> implements CartesianPlane<T> {
    /** A new minimum bound for the x values of Y */
    private int minimumY;
    /** A new maximum bound for the x values of X */
    private int maxmumX;
    /** A new maximum bound for the x values of X */
    private int maxmumY;
    /** A new minimum bound for the x values of Y */
    private int minimumX;
    /** The total length of the x-axis */
    private int xLength;
    /** The total length of the y-axis */
    private int yLength;
    /**  2D array that holds item of type <T> */
    T[][] plane;
    /** The rightmost x-coordinate of elements */
    private int maxElementX;
    /** The topmost x-coordinate of elements */
    private int maxElementY;
    /** The leftmost y-coordinate of elements */
    private int minElementX;
    /** The bottommost y-coordinate of elements */
    private int minElementY;

    /**
     * Constructs a new ArrayCartesianPlane object with given minimum and
     * maximum bounds.
     *
     * Note that these bounds are allowed to be negative.
     *
     * @param minimumX A new minimum bound for the x values of
     *         elements.
     * @param maximumX A new maximum bound for the x values of
     *         elements.
     * @param minimumY A new minimum bound for the y values of
     *         elements.
     * @param maximumY A new maximum bound for the y values of
     *         elements.
     * @throws IllegalArgumentException if the x minimum is greater
     *         than the x maximum (and resp. with y min/max)
     */
    public ArrayCartesianPlane(int minimumX, int maximumX, int minimumY,
            int maximumY) throws IllegalArgumentException {
        if (minimumX >= maximumX || minimumY >= maximumY) {
            throw new IllegalArgumentException();
        }

        this.minimumX = minimumX;
        this.maxmumX = maximumX;
        this.minimumY = minimumY;
        this.maxmumY = maximumY;
        this.xLength = this.maxmumX - this.minimumX + 1;
        this.yLength = this.maxmumY - this.minimumY + 1;
        this.plane = (T[][]) new Object[this.xLength][this.yLength];
        minElementX = this.maxmumX;
        maxElementX = this.minimumX;
        minElementY = this.maxmumY;
        maxElementY = this.minimumY;
    }

    /**
     * Update the edge postion of the elements in the grid.
     *
     * @param x The x-coordinate of the element's position.
     * @param y The y-coordinate of the element's position.
     */
    public void updatePosition(int x, int y) {
        if (x >= this.maxElementX) this.maxElementX = x;
        if (x <= this.minElementX) this.minElementX = x;
        if (y >= this.maxElementY) this.maxElementY = y;
        if (y <= this.minElementY) this.minElementY = y;
    }

    /**
     * Add an element at a fixed position, overriding any existing element
     * there.
     *
     * @param x The x-coordinate of the element's position
     * @param y The y-coordinate of the element's position
     * @param element The element to be added at the indicated
     *         position
     * @throws IllegalArgumentException If the x or y value is out of
     *         the grid's minimum/maximum bounds
     */
    public void add(int x, int y, T element) throws IllegalArgumentException {
        if (x > this.maxmumX || y > this.maxmumY || x < this.minimumX || y < this.minimumY) {
            throw new IllegalArgumentException();
        }
        this.plane[x - this.minimumX][y - minimumY] = element;
        updatePosition(x, y);
    }

    /**
     * Returns the element at the indicated position.
     *
     * @param x The x-coordinate of the element to retrieve
     * @param y The y-coordinate of the element to retrieve
     * @return The element at this position, or null is no elements exist
     * @throws IndexOutOfBoundsException If the x or y value is out of
     *         the grid's minimum/maximum bounds
     */
    public T get(int x, int y) throws IndexOutOfBoundsException {
        if (x > this.maxmumX || y > this.maxmumY || x < this.minimumX || y < this.minimumY) {
            throw new IndexOutOfBoundsException();
        }
        T element = this.plane[x - this.minimumX][y - this.minimumY];
        return element;
    }

    /**
     * Removes the element at the indicated position.
     *
     * @param x The x-coordinate of the element to remove
     * @param y The y-coordinate of the element to remove
     * @return true if an element was successfully removed, false if no element
     *         exists at (x, y)
     * @throws IndexOutOfBoundsException If the x or y value is out of
     *         the grid's minimum/maximum bounds
     */
    public boolean remove(int x, int y) throws IndexOutOfBoundsException {
        if (x > this.maxmumX || y > this.maxmumY || x < this.minimumX || y < this.minimumY) {
            throw new IndexOutOfBoundsException();
        }
        T element= this.plane[x - this.minimumX][y - this.minimumY];
        if (element == null) {
            return false;
        } else {
            this.plane[x - this.minimumX][y - this.minimumY] = null;
            return true;
        }
    }

    /**
     * Removes all elements stored in the grid.
     */
    public void clear() {
        this.plane = (T[][]) new Object[this.xLength][this.yLength];
    }

    /**
     * Changes the size of the grid. Existing elements should remain at the
     *      * same (x, y) coordinate If a resizing operation has invalid dimensions or
     *      * causes an element to be lost, the grid should remain unmodified and an
     *      * IllegalArgumentException thrown
     *
     * @param newMinimumX A new minimum bound for the x values of
     *         elements.
     * @param newMaximumX A new maximum bound for the x values of
     *         elements.
     * @param newMinimumY A new minimum bound for the y values of
     *         elements.
     * @param newMaximumY A new maximum bound for the y values of
     *         elements.
     * @throws IllegalArgumentException if the x minimum is greater
     *         than the x maximum (and resp. with y min/max) or if an element
     *         would be lost after this resizing operation
     */
    public void resize(int newMinimumX, int newMaximumX, int newMinimumY,
                       int newMaximumY) throws IllegalArgumentException {
        if (this.maxElementX > newMaximumX ||
                this.minElementX < newMinimumX ||
                this.maxElementY > newMaximumY ||
                this.minElementY < newMinimumY ||
                newMaximumX < newMinimumX ||
                newMaximumY < newMinimumY) {
            throw new IllegalArgumentException();
        } else {
            int oldminx = this.minimumX;
            int oldminy = this.minimumY;
            this.minimumX = newMinimumX;
            this.maxmumX = newMaximumX;
            this.minimumY = newMinimumY;
            this.maxmumY = newMaximumY;
            this.xLength = this.maxmumX - this.minimumX + 1;
            this.yLength = this.maxmumY - this.minimumY + 1;
            T[][] planeCopy = this.plane;
            this.plane = (T[][]) new Object[this.xLength][this.yLength];

            for (int i = (this.minElementX - oldminx); i < (this.maxElementX - oldminx + 1); i++) {
                for (int j = (this.minElementY - oldminy); j < (this.maxElementY - oldminy + 1); j++) {
                    this.plane[i][j] = planeCopy[i][j];
                }
            }
        }

    }
}

