package io.matsu_chara.chapter2.factorizer;

import io.matsu_chara.annotation.NotThreadSafe;
import io.matsu_chara.chapter2.dummyServlet.Servlet;
import io.matsu_chara.chapter2.dummyServlet.ServletRequest;
import io.matsu_chara.chapter2.dummyServlet.ServletResponse;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@NotThreadSafe
public class UnsafeCachingFactorizer implements Servlet, Runnable {
    private final AtomicReference<BigInteger>   lastNumber  = new AtomicReference<>();
    private final AtomicReference<BigInteger[]> lastFactors = new AtomicReference<>();

    private BigInteger m;

    public UnsafeCachingFactorizer(BigInteger m) {
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
        if (m.equals(lastNumber.get())) {
            System.out.println("from cache");
        } else {
            BigInteger[] factors = factor(m);
            lastNumber.set(m);
            lastFactors.set(factors);
        }

        for (BigInteger bi : lastFactors.get()) {
            System.out.println("fact: " + bi);
        }
    }

    // 実際には使っていない
    @Override
    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        if (i.equals(lastNumber.get())) encodeIntoResponse(resp, lastFactors.get());
        else {
            BigInteger[] factors = factor(i);
            // 一つ一つはatomicでも、２つの操作はatomicではない。
            lastNumber.set(i);
            lastFactors.set(factors);
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
