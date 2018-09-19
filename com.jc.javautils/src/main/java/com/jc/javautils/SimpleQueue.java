package com.jc.javautils;


public class SimpleQueue<T> {

    private final Object[] buf;
    private final int max;
    private int size;
    private int readPos;
    private int writePos;
    private int readBlockedCount;
    private int writeBlockedCount;

    public SimpleQueue(final int capacity) {
        buf = new Object[capacity];
        max = capacity;
        size = 0;
        readPos = 0;
        writePos = 0;
        readBlockedCount = 0;
        writeBlockedCount = 0;
    }

    public synchronized void enqueue(final T obj) {
        // block if queue is already full
        if (size >= max) {
            writeBlockedCount++;
            do {
                try {
                    wait();
                } catch (InterruptedException e) {
                    System.out.println("Interrupt exception");
                    Thread.currentThread().interrupt();
                    return;
                }
            } while (size >= max);
            writeBlockedCount--;
        }

        // put the obj into queue
        buf[writePos] = obj;
        writePos = (writePos + 1) % max;
        size++;

        // notify consumer if any are waiting
        if (readBlockedCount > 0) {
            notify();
        }
    }

    public synchronized T dequeue() {
        // block if queue is empty
        if (size == 0) {
            readBlockedCount++;
            do {
                try {
                    wait();
                } catch (InterruptedException e) {
                    System.out.println("Interrupt exception");
                    Thread.currentThread().interrupt();
                    return null;
                }
            } while (size == 0);
            readBlockedCount--;
        }

        // get the obj
        final T data = (T) buf[readPos];
        readPos = (readPos + 1) % max;
        size--;

        // notify producer if any are waiting
        if (writeBlockedCount > 0) {
            notify();
        }
        return data;
    }
}
