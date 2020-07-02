package io.micronaut.quartz;

import com.google.common.collect.Queues;

import javax.inject.Singleton;
import java.util.Queue;

@Singleton
public class PayloadContainer {
    public Queue<String> arrQueue = Queues.newArrayDeque();

    public void add(String value) {
        arrQueue.add(value);
    }
}
