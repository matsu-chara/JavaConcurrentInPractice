package io.matsu_chara.chapter2;

import io.matsu_chara.annotation.GuardedBy;
import io.matsu_chara.annotation.ThreadSafe;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@ThreadSafe
public class CachedFactorizer implements Servlet, Runnable {
    @GuardedBy("this") private BigInteger   lastNumber;
    @GuardedBy("this") private BigInteger[] lastFactors;
    @GuardedBy("this") private long         hits;
    @GuardedBy("this") private long         cacheHits;

    private BigInteger m;

    public CachedFactorizer(BigInteger m) {
        this.m = m;
    }

    public synchronized long getHits() {
        return hits;
    }

    public synchronized double getCacheHitRatio() {
        return (double) cacheHits / (double) hits;
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
        BigInteger[] factors = null;

        synchronized (this) {
            if (m.equals(lastNumber)) {
                System.out.println("from cache");
                factors = lastFactors.clone();
            }
        }

        if (factors == null) {
            factors = factor(m);
            synchronized (this) {
                lastNumber = m;
                lastFactors = factors;
            }
        }

        for (BigInteger bi : lastFactors) {
            System.out.println("fact: " + bi);
        }
    }


    // 実際には使っていない
    @Override
    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger   i       = extractFromRequest(req);
        BigInteger[] factors = null;

        synchronized (this) {
            ++hits;
            if (i.equals(lastNumber)) {
                ++cacheHits;
                factors = lastFactors.clone();
            }
        }
        if (factors == null) {
            factors = factor(i);
            synchronized (this) {
                lastNumber = i;
                lastFactors = factors.clone();
            }
        }
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
