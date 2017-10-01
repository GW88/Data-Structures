package queue;

import datastructures.adt_s.MyQueue;

import java.util.NoSuchElementException;

/**
 *Queue implementation based on node. class has 3 basic queue method.
 */
public class MyLinkedQueue<E> implements MyQueue<E> {

    private final static class QueueNode<E>{
        private E value;
        private QueueNode<E> nextValue;
        private QueueNode<E> previousValue;

        public QueueNode(){

        }
        public QueueNode(E value){
            this.value = value;
        }
        public QueueNode(E value, QueueNode<E> nextValue){
            this.value = value;
            this.nextValue = nextValue;
        }

        public E getValue() {
            return value;
        }

        public void setValue(E value) {
            this.value = value;
        }

        public QueueNode<E> getNextValue() {
            return nextValue;
        }

        public void setNextValue(QueueNode<E> nextValue) {
            this.nextValue = nextValue;
        }

        public QueueNode<E> getPreviousValue() {
            return previousValue;
        }

        public void setPreviousValue(QueueNode<E> previousValue) {
            this.previousValue = previousValue;
        }
    }
    private QueueNode<E> root;
    private QueueNode<E> tail;
    private int size;

    @Override
    public boolean add(E value) {
        QueueNode<E> queueNode = new QueueNode<>(value, null);
        if (root == null || tail == null){
            root = queueNode;
            tail = queueNode;
        }else{
            queueNode.setNextValue(tail);
            tail.previousValue = queueNode;
            tail = queueNode;

        }
        size++;
        return true;
    }

    @Override
    public boolean offer(E value) {
        add(value);
        return true;
    }

    @Override
    public E peek() {
        if (root == null){
            return null;
        }else {
            return root.getValue();
        }
    }
    @Override
    public E element() throws NoSuchElementException {
        if (root == null){
            throw new NoSuchElementException("Queue Is empty");
        }else {
            return root.getValue();
        }
    }

    @Override
    public E poll() {
        E element = root.getValue();
        if (root == null){
            return null;
        }else {
            QueueNode<E> temp = tail;
            QueueNode<E> previous = null;
            while (temp.getNextValue()!=null){
                previous = temp;
                temp = temp.getNextValue();
            }
            previous.setNextValue(null);
            root = previous;
        }
        size--;
        return element;
    }

    @Override
    public E remove() throws NoSuchElementException {
        if (root == null){
            throw new NoSuchElementException("Queue is empty");
        }else {
            return this.poll();
        }
    }


    public int size(){
        return this.size;
    }
    public boolean isEmpty(){
        return size == 0;
    }

    public Object[] toArray(){
        Object[] elem = new Object[size];
        int g = 0;
        for (QueueNode<E> x = root; x!=null; x = x.previousValue) {
            elem[g++] = x.getValue();
        }
        return elem;
    }

//    uncomment if you need 2 side compilation
//    public void compileForward(){
//        QueueNode<E> temp = tail;
//        while (temp.getNextValue()!=null){
//            System.out.println(temp.getValue());
//            temp = temp.getNextValue();
//        }
//        System.out.println(temp.getValue());
//    }
//    public void compile(){
//        QueueNode<E> temp = root;
//        while (temp.getPreviousValue()!=null){
//            System.out.println(temp.getValue());
//            temp = temp.getPreviousValue();
//        }
//        System.out.println(temp.getValue());
//    }
    public String toString(){
        String values = "";
        QueueNode<E> temp = root;
        while (temp.getPreviousValue()!=null){
            values += (String)temp.getValue() + ',';
            temp = temp.getPreviousValue();
        }
        values += (String)temp.getValue();
        temp = temp.getPreviousValue();
        return "[" + values  + "]";
     }
}