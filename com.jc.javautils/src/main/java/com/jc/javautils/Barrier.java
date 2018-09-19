package com.jc.javautils;

public class Barrier {
    private final Marker marker;
    private int count;

    private static class Marker {
        private boolean locked = true;
        public synchronized void setDone() {
            locked = false;
            notify();
        }

        public synchronized void waitDone() throws InterruptedException {
            if (locked) {
                wait();
            }
        }
    }

    public Barrier(final int count) {
        marker = new Marker();
        this.count = count;
    }

    public synchronized void enter() throws InterruptedException {
        count--;
        if (count == 0) {
            marker.setDone();
        }
        wait();
    }

    public void release() throws InterruptedException {
        marker.waitDone();
        synchronized (this) {
            notifyAll();
        }
    }
}
