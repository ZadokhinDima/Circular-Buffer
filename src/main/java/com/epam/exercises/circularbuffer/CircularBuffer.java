package com.epam.exercises.circularbuffer;

import java.util.Comparator;
import java.util.List;

public class CircularBuffer<T> {

    private Object[] storage;
    private final int size;
    private int head = 0;
    private int tail = 0;

    public CircularBuffer (int bufferSize) {
        storage = new Object[bufferSize];
        size = bufferSize;
    }

    public void put(T newElement) {

    }

    public T get() {
        return null;
    }

    public Object[] toObjectArray() {
        return null;
    }

    public List<T> asList() {
        return null;
    }

    public void addAll(List<? extends T> toAdd) {

    }

    public void sort(Comparator<? super T> comparator) {

    }

    public boolean isEmpty() {
        return head == tail;
    }

    private void incrementHead() {
        head = (head + 1) % size;
    }

    private void incrementTail() {
        tail = (tail + 1) % size;
    }

}
