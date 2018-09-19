package com.jc.javautilsmain;

import com.jc.javautils.SimpleQueue;

public class QueueConsumer extends Thread {

    private final SimpleQueue<String> queue;

    public QueueConsumer(final SimpleQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        String s;
        do {
            s = queue.dequeue();
            System.out.println(s);
        } while (!s.equals("EOF"));
        System.out.println("All done");
    }
}
