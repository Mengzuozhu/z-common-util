package com.github.mzz.common.task;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 基于Flux的动态批量运行任务（异步版）
 *
 * @param <T> the type parameter
 * @author mengzz
 */
public class FluxBatchRunner<T> {
    private Scheduler scheduler;
    private int batchSize;
    private Consumer<Collection<T>> consumer;
    private Collection<T> records;

    public FluxBatchRunner(int batchSize, Consumer<Collection<T>> consumer) {
        if (batchSize <= 0) {
            throw new IllegalArgumentException();
        }
        Objects.requireNonNull(consumer);
        this.batchSize = batchSize;
        this.consumer = consumer;
        scheduler = Schedulers.parallel();
        records = new ArrayList<>(batchSize);
    }

    public FluxBatchRunner<T> setBatchSize(int batchSize) {
        this.batchSize = batchSize;
        return this;
    }

    public FluxBatchRunner<T> setScheduler(Scheduler scheduler) {
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
     * Do final.
     */
    public void doFinal() {
        if (!records.isEmpty()) {
            subscribeToMono(this.records);
        }
    }

    private void subscribeToMono(Collection<T> records) {
        Mono.just(records).subscribeOn(scheduler).subscribe(data -> consumer.accept(data));
    }
}
