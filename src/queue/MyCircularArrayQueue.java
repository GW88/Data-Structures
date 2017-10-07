package queue;

import datastructures.adt_s.MyQueue;

import java.util.NoSuchElementException;

/**
 * Circular Fixed Size Queue, if queue is full elemenet wount be added, until we free the space for
 * new elemenet
 */
public class MyCircularArrayQueue<E> implements MyQueue<E> {

    private Object[] data;
    private int size;
    private int root, tail;


    public MyCircularArrayQueue(int capacity){
        data = new Object[capacity];
        size = 0;
        root = 0;
        tail = 0;

    }

    /**
     * method adds element to queue until there is a free space for new one
     * if not method returns false
     * @param value
     * @return
     */
    @Override
    public boolean add(E value) {
        if (value == null){
            throw new NullPointerException();
        }
        boolean bo;
        if (size == data.length){
            bo = false;
            // if queue is full throw some exception, extend runtime exception class and
            // rewrite some methods there, i'm to lazy for that right now
        }else{
            data[tail] = value;
            tail = (tail+1)%data.length;
            size++;
            bo = true;
        }

        return bo;
    }

    /**
     * method returns false if queue is full, else it adds element to queue
     * @param value
     * @return
     */
    @Override
    public boolean offer(E value) {
        if (value == null){
            return false;
        }
        boolean bo;
        if (size == data.length){
            bo = false;
        }else{
            add(value);
            bo = true;

        }
        return bo;
    }

    /**
     * returns first added element , if queue is empty it returns null
     * @return
     */
    @Override
    public E peek() {
        if (data[root] == null){
            return null;
        }
        return elementAtData(root);
    }

    /**
     * returns deleted element,
     * @return
     */
    @Override
    public E poll() {
        E valueToReturn = elementAtData(root);
        if (this.size>0){
            data[root] = null;
            root = (root+1)%data.length;
            size--;

        }else{
            return null;
        }
        return valueToReturn;
    }

    /**
     * returns deleted element, if queue is empty throws exception
     * @return
     * @throws NoSuchElementException
     */
    @Override
    public E remove() throws NoSuchElementException {
        E e = elementAtData(root);
        if (size>0){
            data[root] = null;
            root = (root+1)%data.length;
            size--;
        }else{
            throw new NoSuchElementException("Queue is Empty");
        }

        return e;
    }

    /**
     * returns first element
     * @return
     * @throws NoSuchElementException
     */
    @Override
    public E element() throws NoSuchElementException {
        if (data[root] == null){
            throw new NoSuchElementException("Queue Is Empty");
        }
        return elementAtData(root);
    }
    public int size(){
        return this.size;
    }
    public boolean isEmpty(){
        return this.size == 0;
    }


//    public String toString(){
//        return Arrays.toString(data);
//    }
    @SuppressWarnings("unchecked")
    private E elementAtData(int index){
        if (index<0){
            throw new ArrayIndexOutOfBoundsException(""+index);
        }
        return (E) data[index];
    }

}
