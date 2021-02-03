package com.github.mzz.common.task;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

/**
 * @author mengzz
 **/
public class FluxBatchRunnerTest {

    @Test
    public void doFinal() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        FluxBatchRunner<Integer> batchRunner = new FluxBatchRunner<>(20, data -> {
            System.out.printf("begin:: thread=%s; data=%s%n", Thread.currentThread().getName(), data.toString());
            try {
                Thread.sleep(200);
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("end:: thread=%s; size=%s%n", Thread.currentThread().getName(), data.size());
        });
        IntStream.range(0, 100).forEach(batchRunner::add);
        batchRunner.doFinal();
        countDownLatch.await();
    }
}
