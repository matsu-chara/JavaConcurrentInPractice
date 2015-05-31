package io.matsu_chara.chapter1;

import io.matsu_chara.annotation.NotThreadSafe;

@NotThreadSafe
public class UnsafeSequence implements Runnable {
    private int nextValue = 0;

    private int getNext() {
        return nextValue++;
    }

    @Override
    public void run() {
        System.out.println("unsafe: " + getNext());
    }
}
