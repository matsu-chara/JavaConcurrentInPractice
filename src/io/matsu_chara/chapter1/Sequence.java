package io.matsu_chara.chapter1;

import io.matsu_chara.annotation.GuardedBy;
import io.matsu_chara.annotation.ThreadSafe;

@ThreadSafe
public class Sequence implements Runnable {
    @GuardedBy("this")
    private int nextValue;

    public synchronized int getNext() {
        return nextValue++;
    }

    @Override
    public void run() {
        System.out.println("safe: " + getNext());
    }
}
