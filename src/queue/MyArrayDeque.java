package queue;

import datastructures.adt_s.MyDeque;

import java.util.*;


/**
 * Array based Deque, it can be used as stack as well.
 *
 * @param <E>
 */
public class MyArrayDeque<E> implements MyDeque<E> {

    private Object[] elements;
    private int root;
    private int tail;
    private static final int DEFAULT_SIZE = 8;
    private int elementsSize = 0;


    public MyArrayDeque(){
        elements = new Object[16];
    }
    public MyArrayDeque(int initialCapacity){
        allocateElements(initialCapacity);

    }
    private void allocateElements(int capacity){
        int capacityToKept = DEFAULT_SIZE;
        if (capacity>= capacityToKept){
            capacityToKept = capacity;
            capacityToKept |= (capacityToKept>>>1);
            capacityToKept |= (capacityToKept>>>2);
            capacityToKept |= (capacityToKept>>>4);
            capacityToKept |= (capacityToKept>>>8);
            capacityToKept |= (capacityToKept>>>16);
            capacityToKept++;
        }
        if (capacityToKept<0)
            capacityToKept >>>= 1;

        elements = new Object[capacityToKept];
    }
    private void shrinkInSize(){
        assert root == tail;
        int startingPoint = root;
        int totalLength = elements.length;
        int numberRight = totalLength - startingPoint;
        int newSize = totalLength<<1; // double the size
        if (newSize<0){
            throw new IllegalStateException("Deque is big");
        }
        Object[] data = new Object[newSize];
        System.arraycopy(elements,startingPoint, data, 0, numberRight);
        System.arraycopy(elements,0, data, numberRight, startingPoint);
        elements = data;
        root = 0;
        tail = totalLength;
    }

    @Override
    public void addFirst(E value) {
        if (value == null)
            throw new NullPointerException();
        elements[root = (root - 1) & (elements.length - 1)] = value;
        elementsSize++;
        if (root == tail)
            shrinkInSize();


    }

    @Override
    public void addLast(E value) {
        if (value == null)
            throw new NullPointerException();
        elements[tail] = value;
        elementsSize++;
        if ( (tail = (tail + 1) & (elements.length - 1)) == root)
            shrinkInSize();

    }

    @Override
    public boolean offerFirst(E value) {
        boolean bo;
        if (value == null){
            bo = false;
        }else{
            addFirst(value);
            bo = true;
        }

        return bo;
    }

    @Override
    public boolean offerLast(E value) {
        boolean bo;
        if (value == null){
            bo = false;
        }else{
            addLast(value);
            bo = true;
        }
        return bo;
    }

    @Override
    public E removeFirst() throws NoSuchElementException {
       E value = this.pollFirst();
        if (value == null){
            throw new NoSuchElementException();
        }
        return value;
    }

    @Override
    public E removeLast() throws NoSuchElementException {
        E value = this.pollLast();
        if (value == null){
            throw new NoSuchElementException();
        }
        return value;
    }

    @Override
    public E pollFirst() {
        int num = root;
        @SuppressWarnings("unchecked")
        E value = (E) elements[num];
        if (value == null){
            return null;
        }
        elements[num] = null;
        root = (num+1)&(elements.length-1);
        return value;
    }

    @Override
    public E pollLast() {
       int num = (tail-1)&(elements.length-1);
        @SuppressWarnings("unchecked")
        E value = (E)elements[num];
        if (value == null){
            return null;
        }
        elements[num] = null;
        tail = num;
        return value;
    }

    @Override
    public E getFirst() throws NoSuchElementException {
        @SuppressWarnings("unchecked")
        E value = (E) elements[root];
        if (value == null){
            throw new NoSuchElementException();
        }
        return value;
    }

    @Override
    public E getLAst() throws NoSuchElementException {
        @SuppressWarnings("unchecked")
        E value = (E)elements[(tail-1)&(elements.length-1)];
        if (value == null){
            throw new NoSuchElementException();
        }
        return value;
    }

    @Override
    public E peekFirst() {
        @SuppressWarnings("unchecked")
        E value = (E)elements[root];
        if (value == null){
            return null;
        }
        return value;
    }

    @Override
    public E peekLast() {
        @SuppressWarnings("unchecked")
        E value = (E)elements[(tail-1)&(elements.length-1)];
        if (value == null){
            return null;
        }
        return value;
    }

    @Override
    public boolean removeFirstOccurrence(E value) {
        if (value == null)
            return false;
        int total = elements.length - 1;
        int head = root;
        Object object;
        while ( (object = elements[head]) != null) {
            if (value.equals(object)) {
                remove(head);
                return true;
            }
            head = (head + 1) & total;
            elementsSize--;
        }
        return false;
    }


    @Override
    public boolean removeLastOccurrence(E value) {
        if (value == null){
            return false;
        }
        int total = elements.length-1;
        int rear = (tail-1)&total;
        Object object;
        while ((object=elements[rear]) !=null){
            if (value.equals(object)) {
                remove(rear);
                return true;
            }
            rear = (rear-1)&total;
            elementsSize--;
        }
        return false;
    }

    @Override
    public boolean add(E value) {
        addLast(value);
        return true;
    }

    @Override
    public boolean offer(E value) {
        offerLast(value);
        return true;
    }

    @Override
    public E peek() {
        return this.peekFirst();
    }

    @Override
    public E poll() {
        return this.pollFirst();
    }

    @Override
    public E remove() throws NoSuchElementException {
        E value = this.removeFirst();
        if (value == null) {

            throw new NoSuchElementException();
        }
        return value;
    }

    @Override
    public E element() throws NoSuchElementException {
        E value = this.getFirst();
        if (value == null){
            throw new NoSuchElementException();
        }
        return value;
    }

    @Override
    public void push(E value) {
        this.addFirst(value);

    }

    @Override
    public E pop() {
        return this.removeFirst();
    }

    @Override
    public int size() {

        return (tail-root)&(elements.length-1);
    }

    /**
     * method checks if element is in array, operation is pretty straightforward,
     * we start iteratin from root and increment root , when root value hits more value
     * then elements lenght it is going to circle and starting counting from first index,
     * operations iterates until it hits null, null will be hit after last tail element.
     * @param value
     * @return
     */
    @Override
    public boolean contains(E value) {
        if (value == null){
            return false;
        }
        int temp = root;
        int inc = elements.length-1;
        Object obj;
        while ((obj = elements[temp]) !=null){
            if (value.equals(obj))
                return true;
            temp = (temp+1)&inc;

        }
        return false;
    }

    /**
     * Array of elements, helper method moveElements, check it below
     * @return
     */
    public Object[] toArray(){
        return moveElements(new Object[this.size()]);
    }

    /**
     * not the best way to iterate over elements, i would say, it is a worst way to do such thing,
     * but let's keep things simple and readable. main this using so much iterations is,
     * root will be posiitoned at the end of array and tail will be position at the begening,
     * beatween them is banch of nulls. not to display them i'm using stack from root side and link from tial side,
     * so they whould be displayed as normal line of elements.
     * suggestion: to shorten method you can always use iterator and stringbuilder.
     * i'm not using them because class is designed for learning purposes. or you can use circular iteration,
     * until you hit the null. check the method "contains"
     * @return
     */
    public String  toString(){
        List el = new Stack();
        List m = new LinkedList();
        if (root != 0) {
            for (int i = root; i < elements.length; i++) {
                if (elements[i] == null) {
                    break;
                } else {
                    el.add(elements[i]);
                }
            }
        }
        if (tail != 0){
            for (int i= 0; i<tail; i++){
                if (elements[i] == null){
                    break;
                }else{
                    m.add(elements[i]);
                }
            }

        }
        el.addAll(m);

       return el.toString();

    }

    /**
     * hellper method , do not use it public
     * @param index
     * @return
     */
    private boolean remove(int index){
        final Object[] elements = this.elements;
        final int total = elements.length - 1;
        final int head = root;
        final int rear = tail;
        final int front = (index - head) & total;
        final int back  = (rear - index) & total;


        if (front < back) {
            if (head <= index) {
                System.arraycopy(elements, head, elements, head + 1, front);
            } else {
                System.arraycopy(elements, 0, elements, 1, index);
                elements[0] = elements[total];
                System.arraycopy(elements, head, elements, head + 1, total - head);
            }
            elements[head] = null;
            root = (head + 1) & total;
            return false;
        } else {
            if (index < rear) {
                System.arraycopy(elements, index + 1, elements, index, back);
                tail = rear - 1;
            } else {
                System.arraycopy(elements, index + 1, elements, index, total - index);
                elements[total] = elements[0];
                System.arraycopy(elements, 1, elements, 0, rear);
                tail = (rear - 1) & total;
            }
            return true;
        }
    }

    private <E> E [] moveElements(E[]elem){
        if (root<tail){
            System.arraycopy(elements, root, elem, 0, this.size() );
        }else if (root>tail){
            int num = elements.length - root;
            System.arraycopy(elements, root, elem, 0, num);
            System.arraycopy(elements, 0, elem, num, tail);

        }
        return elem;
    }
}
