package com.github.mzz.common.task;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * 基于Flux的动态批量运行任务（阻塞版）
 *
 * @param <T> the type parameter
 * @author mengzz
 */
public class FluxBatchBlockRunner<T> {
    /**
     * 调度器
     */
    private Scheduler scheduler;
    /**
     * 分批处理大小
     */
    private int batchSize;
    private Consumer<Collection<T>> consumer;
    private Collection<T> records;
    /**
     * 标记每个任务的完成情况
     */
    private List<CountDownLatch> countDownLatches;

    public FluxBatchBlockRunner(int batchSize, Consumer<Collection<T>> consumer) {
        if (batchSize <= 0) {
            throw new IllegalArgumentException();
        }
        Objects.requireNonNull(consumer);

        this.batchSize = batchSize;
        this.consumer = consumer;
        scheduler = Schedulers.parallel();
        records = new ArrayList<>(batchSize);
        countDownLatches = new ArrayList<>();
    }

    public FluxBatchBlockRunner<T> setBatchSize(int batchSize) {
        this.batchSize = batchSize;
        return this;
    }

    public FluxBatchBlockRunner<T> setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
        return this;
    }

    /**
     * 添加数据
     *
     * @param t the t
     */
    public void add(T t) {
        records.add(t);
        if (records.size() == batchSize) {
            subscribeToMono(records);
            // reset
            records = new ArrayList<>(batchSize);
        }
    }

    /**
     * 等待所有任务执行完毕
     *
     * @throws InterruptedException the interrupted exception
     */
    public void awaitAll() throws InterruptedException {
        subscribeLast();
        for (CountDownLatch countDownLatch : countDownLatches) {
            countDownLatch.await();
        }
    }

    /**
     * 等待所有任务执行完毕
     *
     * @param timeout the timeout
     * @param unit    the unit
     * @throws InterruptedException the interrupted exception
     */
    public void awaitAll(long timeout, TimeUnit unit) throws InterruptedException {
        subscribeLast();
        for (CountDownLatch countDownLatch : countDownLatches) {
            countDownLatch.await(timeout, unit);
        }
    }

    private void subscribeLast() {
        if (!records.isEmpty()) {
            subscribeToMono(this.records);
        }
    }

    private void subscribeToMono(Collection<T> records) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatches.add(countDownLatch);
        Mono.just(records).subscribeOn(scheduler).subscribe(data -> {
            try {
                consumer.accept(data);
            } finally {
                //执行完毕
                countDownLatch.countDown();
            }
        });
    }
}
