package com.github.mzz.common.task;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Flux批量处理，并返回每批次的处理结果
 *
 * @param <T> the type parameter
 * @author mengzz
 */
public class FluxBatchFuture<T, R> {
    private List<Mono<R>> monoList;
    private Scheduler scheduler;
    private int batchSize;
    private Function<Collection<T>, R> function;
    private List<T> records;

    public FluxBatchFuture(int batchSize, Function<Collection<T>, R> function) {
        this.batchSize = batchSize;
        this.function = function;
        scheduler = Schedulers.boundedElastic();
        this.monoList = new ArrayList<>();
        records = new ArrayList<>();
    }

    public FluxBatchFuture setBatchSize(int batchSize) {
        this.batchSize = batchSize;
        return this;
    }

    public FluxBatchFuture setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
        return this;
    }

    /**
     * Add.
     *
     * @param t the t
     */
    public void add(T t) {
        records.add(t);
        if (records.size() == batchSize) {
            subscribeToMono(new ArrayList<>(records));
            records.clear();
        }
    }

    /**
     * Do final.
     *
     * @return the list
     */
    public List<R> doFinal() {
        if (!records.isEmpty()) {
            subscribeToMono(this.records);
        }
        //等待所有操作执行完毕
        return Flux.fromIterable(monoList).filter(Objects::nonNull).flatMap(t -> t).collectList().block();
    }

    /**
     * Size.
     *
     * @return the int
     */
    public int size() {
        return monoList.size();
    }

    private void subscribeToMono(Collection<T> records) {
        Mono<R> mono = Mono.fromCallable(() -> {
            R r = function.apply(records);
            records.clear();
            return r;
        }).subscribeOn(scheduler);
        monoList.add(mono);
    }
}
