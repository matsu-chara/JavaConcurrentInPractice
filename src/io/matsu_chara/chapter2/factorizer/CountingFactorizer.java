package io.matsu_chara.chapter2.factorizer;

import io.matsu_chara.annotation.ThreadSafe;
import io.matsu_chara.chapter2.dummyServlet.Servlet;
import io.matsu_chara.chapter2.dummyServlet.ServletRequest;
import io.matsu_chara.chapter2.dummyServlet.ServletResponse;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@ThreadSafe
public class CountingFactorizer implements Servlet, Runnable {
    private final AtomicLong count = new AtomicLong(0);
    private BigInteger m;

    public CountingFactorizer(BigInteger m) {
        this.m = m;
    }

    public long getCount() {
        return count.get();
    }

    private BigInteger[] factor(BigInteger m) {
        List<BigInteger> facts = new ArrayList<>();
        for (BigInteger i = BigInteger.valueOf(2); i.compareTo(m.divide(BigInteger.valueOf(2))) < 0; i = i.add(BigInteger.ONE)) {
            if (m.mod(i).equals(BigInteger.ZERO)) facts.add(i);
        }
        count.incrementAndGet();
        BigInteger[] bis = new BigInteger[facts.size()];
        return facts.toArray(bis);
    }

    @Override
    public void run() {
        for (BigInteger bi : factor(m)) {
            System.out.println("fact: " + bi);
        }
        System.out.println("current count: " + count);
    }

    // 実際には使っていない
    @Override
    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger   i       = extractFromRequest(req);
        BigInteger[] factors = factor(i);
        count.incrementAndGet();
        encodeIntoResponse(resp, factors);
    }

    // 実際には使っていない
    private BigInteger extractFromRequest(ServletRequest req) {
        return BigInteger.ZERO;
    }

    // 実際には使っていない
    private void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
    }

}
