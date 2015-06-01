package io.matsu_chara.chapter1;

import io.matsu_chara.chapter1.seq.Sequence;
import io.matsu_chara.chapter1.seq.UnsafeSequence;

import java.util.ArrayList;
import java.util.List;

public class Chapter1Test {

    public static void main(String[] args) {
        long start, end;

        start = System.currentTimeMillis();
        testChapter();
        end = System.currentTimeMillis();
        System.out.println("実行にかかった時間は " + (end - start) + " ミリ秒です。");
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            System.exit(-1);
        }
    }

    public static void testChapter() {
        UnsafeSequence us = new UnsafeSequence();
        Sequence       s  = new Sequence();

        List<Thread> ts1 = new ArrayList<>();
        List<Thread> ts2 = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            ts1.add(new Thread(us));
            ts2.add(new Thread(s));
        }

        for (Thread t : ts1) t.start();
        for (Thread t : ts2) t.start();
    }
}
