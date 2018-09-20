package com.epam.exercises.circularbuffer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CircularBufferTest {

    @Test
    void putAndGetShouldWorkAsExpected() {
        CircularBuffer<Integer> testInstance = new CircularBuffer<>(3);
        testInstance.put(1);
        testInstance.put(2);
        testInstance.put(3);
        assertEquals(1, (int) testInstance.get());
        assertEquals(2, (int) testInstance.get());
        assertEquals(3, (int) testInstance.get());
        testInstance.put(4);
        testInstance.put(5);
        assertEquals(4, (int) testInstance.get());
        testInstance.put(6);
        assertEquals(5, (int) testInstance.get());
    }

    @Test
    void constructorThrowsExceptionWhenSizeIsZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CircularBuffer<>(0);
        });
    }

    @Test
    void putThrowsExceptionWhenBufferIsFull() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            CircularBuffer<Integer> testInstance = new CircularBuffer<>(3);
            testInstance.put(1);
            testInstance.put(2);
            testInstance.put(3);
            testInstance.put(4);
        });
    }

    @Test
    void getThrowsExceptionWhenBufferIsEmpty() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            CircularBuffer<Integer> testInstance = new CircularBuffer<>(3);
            testInstance.put(1);
            testInstance.put(2);
            testInstance.get();
            testInstance.get();
            testInstance.get();
        });
    }

    @Test
    void toObjectArraySimpleCheck() {
        CircularBuffer<Integer> testInstance = new CircularBuffer<>(4);
        testInstance.put(1);
        testInstance.put(2);
        testInstance.put(3);
        final Object[] actual = testInstance.toObjectArray();
        final Object[] expected = new Object[] {1, 2, 3};
        assertArrayEquals(expected, actual);
    }

    @Test
    void toObjectArrayWithBoundsCheck() {
        CircularBuffer<Integer> testInstance = new CircularBuffer<>(4);

        testInstance.put(1);
        testInstance.get();
        testInstance.put(1);
        testInstance.get();
        testInstance.put(1);
        testInstance.get();

        testInstance.put(1);
        testInstance.put(2);
        testInstance.put(3);
        final Object[] actual = testInstance.toObjectArray();
        final Object[] expected = new Object[] {1, 2, 3};
        assertArrayEquals(expected, actual);
    }

    @Test
    void asListWorksAsExpected() {
        CircularBuffer<Integer> testInstance = new CircularBuffer<>(4);
        testInstance.put(1);
        testInstance.put(2);
        testInstance.put(3);

        final List<Integer> actual = testInstance.asList();
        final List<Integer> expected = Arrays.asList(1, 2, 3);
        assertEquals(expected, actual);
    }

    @Test
    void addAllWorksAsExpected() {
        CircularBuffer<Integer> testInstance = new CircularBuffer<>(4);
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        testInstance.addAll(list);
        assertEquals(3, (int) testInstance.get());
        assertEquals(4, (int) testInstance.get());
        assertEquals(5, (int) testInstance.get());
        assertEquals(6, (int) testInstance.get());
    }

    @Test
    void addAllThrowsAnExceptionWhenListIsTooLong() {
        CircularBuffer<Integer> testInstance = new CircularBuffer<>(3);
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        assertThrows(IndexOutOfBoundsException.class, () -> testInstance.addAll(list));
    }

    @Test
    void shouldSortElementsInBuffer() {
        CircularBuffer<Integer> testInstance = new CircularBuffer<>(10);
        testInstance.put(5);
        testInstance.put(2);
        testInstance.put(7);
        testInstance.put(10);
        testInstance.put(1);
        testInstance.put(6);
        testInstance.put(3);
        testInstance.put(8);
        testInstance.get();
        testInstance.get();
        testInstance.put(5);
        testInstance.put(2);
        testInstance.put(4);
        testInstance.put(9);
        testInstance.sort(Integer::compareTo);

        assertEquals(1, (int) testInstance.get());
        assertEquals(2, (int) testInstance.get());
        assertEquals(3, (int) testInstance.get());
        assertEquals(4, (int) testInstance.get());
        assertEquals(5, (int) testInstance.get());
        assertEquals(6, (int) testInstance.get());
        assertEquals(7, (int) testInstance.get());
        assertEquals(8, (int) testInstance.get());
        assertEquals(9, (int) testInstance.get());
        assertEquals(10, (int) testInstance.get());
    }

    @Test
    void isEmptyAfterCreation() {
        CircularBuffer<Integer> testInstance = new CircularBuffer<>(4);
        assertTrue(testInstance.isEmpty());
    }

    @Test
    void notEmptyAfterElementAdding() {
        CircularBuffer<Integer> testInstance = new CircularBuffer<>(4);
        testInstance.put(1);
        testInstance.put(2);
        assertFalse(testInstance.isEmpty());
    }

    @Test
    void isEmptyAfterPutAndGet() {
        CircularBuffer<Integer> testInstance = new CircularBuffer<>(4);
        testInstance.put(1);
        testInstance.get();
        assertTrue(testInstance.isEmpty());
    }
}