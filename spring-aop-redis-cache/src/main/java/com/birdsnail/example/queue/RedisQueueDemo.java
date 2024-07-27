package com.birdsnail.example.queue;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBoundedBlockingQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class RedisQueueDemo implements CommandLineRunner {

    @Autowired
    private RedissonClient redissonClient;

    private RBoundedBlockingQueue<String> queue;

    private Map<Thread, Boolean> consumerMap = new ConcurrentHashMap<>();

    @Override
    public void run(String... args) throws Exception {
        queue = redissonClient.getBoundedBlockingQueue("redis-queue-test");
        queue.trySetCapacity(5);

        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(this::startConsumer);
            consumerMap.put(thread, Boolean.TRUE);
            thread.start();
        }
    }

    private void startConsumer() {
        Thread thread = Thread.currentThread();
        String name = thread.getName();
        System.out.println(name + " -- 开始准备消费");
        while (consumerMap.get(thread)) {
            try {
                String data = queue.take();
                Thread.sleep(RandomUtil.randomLong(3000, 5000));
                System.out.printf("线程:%s，消费数据：%s%n", name, data);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        log.info("线程停止消费，thread：{}", name);
    }

    public void push(String data) {
        try {
            queue.put(data);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public int size() {
        return queue.size();
    }

    public void delete() {
        consumerMap.replaceAll((t, v) -> Boolean.FALSE);
        queue.delete();
    }
}
