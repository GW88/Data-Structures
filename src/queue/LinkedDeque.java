package queue;

import datastructures.adt_s.MyDeque;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 *Double ended Queue, Node based, Elements can be aded from both side of queue,
 * it can be used as queue or as stack.
 */
public class LinkedDeque<E> implements MyDeque<E> {

    private static class DequeNode<E>{
        private E value;
        private DequeNode<E> nextValue;
        private DequeNode<E> previousValue;

        public DequeNode(){

        }
        public DequeNode(E value){
            this.value = value;
        }
        public DequeNode(DequeNode<E> previousValue, E valie, DequeNode<E> nextValue){
            this.previousValue = previousValue;
            this.value = valie;
            this.nextValue = nextValue;
        }

        public E getValue() {
            return value;
        }

        public void setValue(E value) {
            this.value = value;
        }

        public DequeNode<E> getNextValue() {
            return nextValue;
        }

        public void setNextValue(DequeNode<E> nextValue) {
            this.nextValue = nextValue;
        }

        public DequeNode<E> getPreviousValue() {
            return previousValue;
        }

        public void setPreviousValue(DequeNode<E> previousValue) {
            this.previousValue = previousValue;
        }
    }

    protected DequeNode<E> root;
    protected DequeNode<E> tail;
    private int nodes;

    public LinkedDeque(){

    }
    public LinkedDeque(MyDeque<? extends E> values){
        this();
        this.addAll(values);
    }

    /**
     * adds element at first position, nulls are now allowed,
     * @param value
     */
    @Override
    public void addFirst(E value) {
        DequeNode<E> queue = new DequeNode<>(value);
        if (value == null){
            throw new IllegalArgumentException("NO NULLS");
        }else{
            queue.nextValue = root;
            root.previousValue = queue;
            root = queue;
        }
        nodes++;

    }

    /**
     * adds element at last position, nulls are not allowed
     * @param value
     */

    @Override
    public void addLast(E value) {
        DequeNode<E> queue = new DequeNode<>(value);
        if (value == null){
            throw new IllegalArgumentException("No nulls allowed");
        }else if( tail == null || root == null){
            tail = queue;
            root = queue;
        }else{
            queue.setPreviousValue(tail);
            tail.nextValue = queue;
            tail = queue;
        }
        nodes++;

    }

    /**
     * adds element at first position in structure, attempting to add null will return false
     * @param value
     * @return
     */
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

    /**
     * adds element at last position in structure, attempting to add null will return false
     * @param value
     * @return
     */
    @Override
    public boolean offerLast(E value) {
        boolean v;
        if (value == null){
            v = false;
        }else{
            addLast(value);
            v = true;
        }
        return v;
    }

    /**
     * removes first element, if structure is empty throw exception
     * @return
     * @throws NoSuchElementException
     */
    @Override
    public E removeFirst() throws NoSuchElementException {
        if (root==null){
            throw new NoSuchElementException("no element to remove from queue");
        }
        E temp = root.getValue();
        root = root.getNextValue();
        root.previousValue = null;
        nodes--;
        return temp;
    }

    /**
     * removes last element, if structure is empty throw exception
     * @return
     * @throws NoSuchElementException
     */
    @Override
    public E removeLast() throws NoSuchElementException {
        if (tail == null){
            throw new NoSuchElementException("No element to remove in queue");
        }
        E temp = tail.getValue();
        tail = tail.getPreviousValue();
        tail.nextValue = null;
        nodes--;
        return temp;
    }

    /**
     *
     * @return
     */
    @Override
    public E pollFirst() {
        if (root == null){
            return null;
        }
        E temp = root.getValue();
        root = root.getNextValue();
        root.previousValue = null;
        nodes--;
        return temp;
    }

    /**
     *
     * @return
     */
    @Override
    public E pollLast() {
        if (tail == null){
            return null;
        }
        E temp = tail.getValue();
        tail = tail.getPreviousValue();
        tail.nextValue = null;
        nodes--;
        return temp;
    }

    /**
     * returns first element withotu removing
     * @return
     * @throws NoSuchElementException
     */
    @Override
    public E getFirst() throws NoSuchElementException {
        if (root == null){
            throw new NoSuchElementException("No elements in queue");
        }
        return root.getValue();
    }

    /**
     * returns last element without removing
     * @return
     * @throws NoSuchElementException
     */
    @Override
    public E getLAst() throws NoSuchElementException {
        if (tail == null){
            throw new NoSuchElementException("No elements in queue");
        }
        return tail.getValue();
    }

    /**
     * returns first element without removing
     * @return
     */
    @Override
    public E peekFirst() {
        if (root == null){
            return null;
        }
        return root.getValue();
    }

    /**
     * returns last element without removing
     * @return
     */
    @Override
    public E peekLast() {
        if(tail == null){
            return null;
        }
        return tail.getValue();
    }

    /**
     * deletes first occurrence of pathed element
     * @param value
     * @return
     */
    @Override
    public boolean removeFirstOccurrence(E value) {
        boolean bol = false;
        if (root == null){
            bol= false;
        }else{
            DequeNode<E> temp = root;
            DequeNode<E> prev = null;
            while (temp!=null && !temp.getValue().equals(value)){
                prev = temp;
                temp = temp.getNextValue();
                if (temp !=null &&temp.getValue().equals(value)){
                    bol = true;
                    continue;
                }else{
                    bol = false;
                }
            }
            if (root.getValue().equals(value)){
                root = root.getNextValue();
                root.previousValue = null;
                bol = true;
            }else if((temp!=null ? temp.getNextValue(): null) == null) {
                tail = tail.getPreviousValue();
                tail.nextValue = null;
            }else if(temp!=null) {
                DequeNode<E> current = temp.getNextValue();
                prev.setNextValue(current);
                current.previousValue = prev;
            }

            nodes--;
        }
        return bol;
    }

    /**
     * removes last occurrence of pathed element
     * @param value
     * @return
     */
    @Override
    public boolean removeLastOccurrence(E value) {
        boolean bool = false;
        if (tail == null){
            bool = false;
        }else{
            DequeNode<E> temp = tail;
            DequeNode<E> prev = null;
            while (temp!=null && !temp.getValue().equals(value)){
                prev = temp;
                temp = temp.getPreviousValue();
                if (temp!=null && temp.getValue().equals(value)){
                    bool = true;
                    continue;
                }else{
                    bool = false;
                }

            }
            if (tail.getValue().equals(value)){
                tail = tail.previousValue;
                tail.nextValue =null;
                bool = true;
            }else if((temp!=null ? temp.getPreviousValue() : null) == null){
                root = root.getNextValue();
                root.previousValue = null;
            }else if (temp!=null){
                DequeNode<E> current = temp.getPreviousValue();
                prev.setPreviousValue(current);
                current.nextValue = prev;
            }
            nodes--;
        }
        return bool;
    }

    /**
     * adds new element at last position of structure
     * @param value
     * @return
     */
    @Override
    public boolean add(E value) {
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
    public boolean offer(E value) {
        boolean bul;
        if (value == null){
            bul = false;
        }else{
            addLast(value);
            bul = true;
        }
        return bul;
    }

    /**
     * returns first element
     * @return
     */
    @Override
    public E peek() {
        final DequeNode<E> temp = root;
        return (temp==null)? null: temp.getValue();
    }

    /**
     * removes fist element
     * @return
     */
    @Override
    public E poll() {
        final DequeNode<E> queue = root;
        return (queue==null)? null : removeFirst();
    }

    /**
     * deletes first element
     * @return
     * @throws NoSuchElementException
     */
    @Override
    public E remove() throws NoSuchElementException {
        if (root == null){
            throw new NoSuchElementException("queue empty");
        }
        return removeFirst();
    }

    /**
     * returns first element
     * @return
     * @throws NoSuchElementException
     */
    @Override
    public E element() throws NoSuchElementException {
        if (root == null){
            throw new NoSuchElementException("Queue is empty");
        }
        final DequeNode<E> temp = root;
        return temp.getValue();
    }

    /**
     *
     * @param value
     */
    @Override
    public void push(E value) {
        DequeNode<E> stack = new DequeNode<>(value);
      if (value == null){
          throw new IllegalArgumentException("no nulls");
      }else if (root !=null){
          addFirst(value);
      }else{
          stack.setNextValue(root);
          root = stack;
          nodes++;
      }
    }

    /**
     * stack method pop
     * @return
     */
    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public int size() {
        return nodes;
    }

    @Override
    public boolean contains(E value) {
         DequeNode<E> temp = root;
        DequeNode<E> prev = null;
        while (temp.getNextValue()!=null){
            prev = temp;
            temp = temp.getNextValue();
            if (prev.getValue().equals(value)) {
                return true;
            }else{
                prev = temp;
                if (prev.getValue().equals(value))
                    return true;
            }
        }

        return false;
    }
    public Object[] toArray(){
        Object[] elem = new Object[nodes];
        int i =0;
        for (DequeNode<E> k = root; k!=null; k=k.getNextValue()){
            elem[i++] = k.getValue();
        }
        return elem;
    }

    /**
     * adds all element to another structure, if root is empty if statement works if not else side will
     * do her job. MyDeque can be replaced with Collection or with any atehr interface, custom or standart from
     * packages.
     * @param values
     */
    public void addAll(MyDeque<? extends E> values){
        Object[] elements = ((LinkedDeque<E>) values).toArray();
        int newSize = elements.length;
        if (root == null){
            for (Object x: elements){
                @SuppressWarnings("unchecked")
                E valuesToAdd = (E)x;
                this.offer(valuesToAdd);
            }
        }else {
            DequeNode<E> temp = root;
            DequeNode<E> prev = null;
            while (temp.getNextValue() != null) {
                prev = temp;
                temp = temp.getNextValue();
            }
            prev = temp;
            for (Object elem : elements) {
                @SuppressWarnings("unchecked")
                E newValues = (E) elem;
                DequeNode<E> list = new DequeNode<>(newValues);
                if (prev != null) {
                    prev.nextValue = list;
                    list.previousValue = prev;
                    prev = list;
                    tail = prev;
                }
            }
            nodes+=newSize;
        }


    }
    public void clear(){
        DequeNode<E> rootTemp = null;
        DequeNode<E>  tailTemp = null;
        while (root.getNextValue()!=null && tail.getPreviousValue()!=null){
            rootTemp = root;
            tailTemp = tail;
            root = root.getNextValue();
            tail = tail.getPreviousValue();
            rootTemp = null;
            tailTemp = null;
            nodes--;
       }
        if (root!=null && tail !=null){
            root = null;
            tail = null;
            nodes--;
        }

    }
    public boolean isEmpty(){
        return nodes == 0;
    }
    public String toString(){
        Object[] elements = new Object[nodes];
        int i = 0;
        for (DequeNode<E> x = root; x!=null; x= x.getNextValue()){
            elements[i++] = x.getValue();
        }
        return Arrays.toString(elements);
    }
}
