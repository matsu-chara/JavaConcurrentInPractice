package io.matsu_chara.chapter5.Memoizer;

public interface Computable<A, V> {
    V compute(A arg) throws InterruptedException;
}
