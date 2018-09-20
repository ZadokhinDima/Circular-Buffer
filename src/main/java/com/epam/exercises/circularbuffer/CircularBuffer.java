package com.epam.exercises.circularbuffer;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class CircularBuffer<T> {

    private static final String ILLEGAL_SIZE_MESSAGE = "Size of the buffer should be positive.";
    private static final String FULL_BUFFER_MESSAGE = "Cannot add element: the buffer is full.";
    private static final String NOT_ENOUGH_BUFFER_SPACE_MESSAGE = "Cannot add elements: the buffer has not enough space.";
    private static final String EMPTY_BUFFER_MESSAGE = "Cannot get element: the buffer is empty.";

    private Object[] storage;
    private final int size;
    private int head = 0;
    private int tail = 0;
    private boolean isFull = false;

    public CircularBuffer (int bufferSize) {
        if (bufferSize <= 0) {
            throw new IllegalArgumentException(ILLEGAL_SIZE_MESSAGE);
        }
        storage = new Object[bufferSize];
        size = bufferSize;
    }

    public void put(T newElement) {
        if (isFull) {
            throw new IndexOutOfBoundsException(FULL_BUFFER_MESSAGE);
        }
        storage[head] = newElement;
        incrementHead(1);
    }

    public T get() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException(EMPTY_BUFFER_MESSAGE);
        }
        T result = (T) storage[tail];
        incrementTail(1);
        return result;
    }

    public Object[] toObjectArray() {
        Object[] result = new Object[currentSize()];
        if (hasBreakInTheBuffer()) {
            System.arraycopy(storage, tail, result, 0, size - tail);
            System.arraycopy(storage, 0, result, size - tail, head);
        }  else {
            System.arraycopy(storage, tail, result, 0, currentSize());
        }
        return result;
    }

    public List<T> asList() {
        return (List<T>) Arrays.asList(toObjectArray());
    }

    public void addAll(List<? extends T> toAdd) {
        if (toAdd.size() > size - currentSize()) {
            throw  new IndexOutOfBoundsException(NOT_ENOUGH_BUFFER_SPACE_MESSAGE);
        }
        final Object[] arrayToAdd = toAdd.toArray();
        if (hasBreakInTheBuffer()) {
            System.arraycopy(arrayToAdd, 0, storage, head, toAdd.size());
        } else {
            System.arraycopy(arrayToAdd, 0, storage, head, size - head);
            System.arraycopy(arrayToAdd, size - head, storage, 0, toAdd.size() + head - size);
        }
        incrementHead(toAdd.size());
    }

    public void sort(Comparator<? super T> comparator) {
        final List<T> asList = asList();
        asList.sort(comparator);
        incrementTail(currentSize());
        addAll(asList);
    }

    public boolean isEmpty() {
        return head == tail && !isFull;
    }

    private void incrementHead(int size) {
        head = nextIndex(head, size);
        if (head == tail) {
            isFull = true;
        }
    }

    private void incrementTail(int size) {
        if (head == tail && isFull) {
            isFull = false;
        }
        tail = nextIndex(tail, size);
    }

    private int nextIndex(int index, int increment) {
        return (index + increment) % size;
    }

    private boolean hasBreakInTheBuffer() {
        return !isEmpty() && tail >= head;
    }

    private int currentSize() {
        if (isEmpty()) {
            return 0;
        }
        if (hasBreakInTheBuffer()) {
            return size - tail + head;
        } else {
            return head - tail;
        }
    }

}
