package io.matsu_chara.chapter2.factorizer;

import io.matsu_chara.annotation.GuardedBy;
import io.matsu_chara.annotation.ThreadSafe;
import io.matsu_chara.chapter2.dummyServlet.Servlet;
import io.matsu_chara.chapter2.dummyServlet.ServletRequest;
import io.matsu_chara.chapter2.dummyServlet.ServletResponse;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * 同期範囲が広すぎて遅いバージョン
  */

@ThreadSafe
public class SynchronizedFactorizer implements Servlet, Runnable {
    @GuardedBy("this") private BigInteger lastNumber;
    @GuardedBy("this") private BigInteger[] lastFactors;

    private BigInteger m;

    public SynchronizedFactorizer(BigInteger m) {
        this.m = m;
    }

    private BigInteger[] factor(BigInteger m) {
        List<BigInteger> facts = new ArrayList<>();
        for (BigInteger i = BigInteger.valueOf(2); i.compareTo(m.divide(BigInteger.valueOf(2))) < 0; i = i.add(BigInteger.ONE)) {
            if (m.mod(i).equals(BigInteger.ZERO)) facts.add(i);
        }
        BigInteger[] bis = new BigInteger[facts.size()];
        return facts.toArray(bis);
    }

    @Override
    public void run() {
        synchronized (this) {
            if (m.equals(lastNumber)) {
                System.out.println("from cache");
            } else {
                BigInteger[] factors = factor(m);
                lastNumber = m;
                lastFactors = factors;
            }

            for (BigInteger bi : lastFactors) {
                System.out.println("fact: " + bi);
            }
        }
    }

    // 実際には使っていない
    @Override
    public synchronized void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        if (i.equals(lastNumber)) encodeIntoResponse(resp, lastFactors);
        else {
            BigInteger[] factors = factor(i);
            // 一つ一つはatomicでも、２つの操作はatomicではない。
            lastNumber = i;
            lastFactors = factors;
            encodeIntoResponse(resp, factors);
        }
    }

    // 実際には使っていない
    private BigInteger extractFromRequest(ServletRequest req) {
        return BigInteger.ZERO;
    }

    // 実際には使っていない
    private void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
    }
}

