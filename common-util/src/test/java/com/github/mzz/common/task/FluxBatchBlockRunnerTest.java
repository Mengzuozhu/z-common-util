package com.github.mzz.common.task;

import org.junit.jupiter.api.Test;
import reactor.core.scheduler.Schedulers;

import java.util.stream.IntStream;

/**
 * @author mengzz
 **/
public class FluxBatchBlockRunnerTest {

    @Test
    public void add() throws InterruptedException {
        int batchSize = 20;
        FluxBatchBlockRunner<Integer> batchRunner = new FluxBatchBlockRunner<>(batchSize, data -> {
            System.out.printf("begin:: thread=%s; data=%s%n", Thread.currentThread().getName(), data.toString());
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("end:: thread=%s; size=%s%n", Thread.currentThread().getName(), data.size());
        });
        IntStream.range(0, 100).forEach(batchRunner::add);
        // 动态修改调度器和批量处理大小
        batchRunner.setScheduler(Schedulers.boundedElastic());
        batchRunner.setBatchSize(50);
        IntStream.range(0, 100).forEach(batchRunner::add);
        // 等待所有任务执行完毕
        batchRunner.awaitAll();
    }
}
