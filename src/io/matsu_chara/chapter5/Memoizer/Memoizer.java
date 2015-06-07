package io.matsu_chara.chapter5.Memoizer;

import io.matsu_chara.annotation.GuardedBy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class Memoizer<A, V> implements Computable<A, V> {
    @GuardedBy("this") private final ConcurrentMap<A, Future<V>> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> c;

    public Memoizer(Computable<A, V> c) {
        this.c = c;
    }

    public V compute(final A arg) throws InterruptedException {
        Future<V> f = cache.get(arg);
        if (f == null){
            Callable<V> eval = new Callable<V>() {
                public V call() throws InterruptedException {
                    return c.compute(arg);
                }
            };
            FutureTask<V> ft = new FutureTask<V>(eval);
            f = ft;
            cache.putIfAbsent(arg, ft);
            ft.run();
        }

        try {
            return f.get();
        } catch (CancellationException e) {
            cache.remove(arg, f);
        } catch (ExecutionException e) {
            throw new IllegalStateException("not unchecked", e);
        }
        return null;
    }
}
