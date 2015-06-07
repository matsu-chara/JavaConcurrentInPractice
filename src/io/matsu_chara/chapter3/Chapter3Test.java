package io.matsu_chara.chapter3;

import java.util.ArrayList;
import java.util.List;

public class Chapter3Test {

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
        List<Thread> ts1 = new ArrayList<>();
        List<Thread> ts2 = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            ts1.add(new Thread());
            ts2.add(new Thread());
        }

        for (Thread t : ts1) t.start();
        for (Thread t : ts2) t.start();
    }
}
