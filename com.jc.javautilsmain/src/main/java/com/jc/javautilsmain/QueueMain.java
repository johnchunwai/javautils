package com.jc.javautilsmain;

import com.jc.javautils.SimpleQueue;

public class QueueMain {
    public static void main(String[] args) {
        final var queue = new SimpleQueue<String>(10);

        final var producer = new QueueProducer(queue);
        final var consumer = new QueueConsumer(queue);

        consumer.start();
        producer.start();
    }
}
