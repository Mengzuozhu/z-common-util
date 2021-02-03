package com.github.mzz.common.task;

import com.github.mzz.common.util.CollectionUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Flux Util
 *
 * @author mengzz
 */
public class FluxUtil {

    /**
     * For each skip null by elastic.
     *
     * @param <E>        the type parameter
     * @param collection the collection
     * @param action     the action
     */
    public static <E> void forEachSkipNullByElastic(Collection<E> collection, Consumer<? super E> action) {
        forEachSkipNull(collection, Schedulers.boundedElastic(), action);
    }

    /**
     * For each skip null.
     *
     * @param <E>        the type parameter
     * @param collection the collection
     * @param scheduler  the scheduler
     * @param action     the action
     */
    public static <E> void forEachSkipNull(Collection<E> collection, Scheduler scheduler, Consumer<? super E> action) {
        if (CollectionUtils.isEmpty(collection) || action == null) {
            return;
        }
        //等待所有操作执行完毕
        Flux.fromIterable(collection)
                .filter(Objects::nonNull)
                .flatMap(t -> Mono.fromRunnable(() -> action.accept(t)).subscribeOn(scheduler))
                .collectList()
                .block();
    }

}
