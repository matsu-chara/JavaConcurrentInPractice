package io.matsu_chara.chapter2;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Chapter2Test {

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
        BigInteger bi = BigInteger.valueOf(150000);
        CountingFactorizer cf = new CountingFactorizer(bi);
        UnsafeCountingFactorizer ucf = new UnsafeCountingFactorizer(bi);
        SynchronizedFactorizer sf = new SynchronizedFactorizer(bi);

        List<Thread> ts1 = new ArrayList<>();
        List<Thread> ts2 = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            ts1.add(new Thread(cf));
            ts2.add(new Thread(sf));
        }

        for (Thread t : ts1) t.start();
        for (Thread t : ts2) t.start();
    }

}
