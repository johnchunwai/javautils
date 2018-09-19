package com.jc.javautilsmain;

import com.jc.javautils.SimpleQueue;

public class QueueProducer extends Thread {

    private final SimpleQueue<String> queue;

    public QueueProducer(final SimpleQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            queue.enqueue("hi-" + i);
        }
        queue.enqueue("EOF");
    }
}
