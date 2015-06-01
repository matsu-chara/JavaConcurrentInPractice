package io.matsu_chara.chapter2;

import io.matsu_chara.annotation.NotThreadSafe;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@NotThreadSafe
public class UnsafeCountingFactorizer implements Servlet, Runnable {
    private long count = 0;
    private BigInteger m;

    public UnsafeCountingFactorizer(BigInteger m) {
        this.m = m;
    }

    public long getCount() { return count; }

    private BigInteger[] factor(BigInteger m) {
        List<BigInteger> facts = new ArrayList<>();
        for(BigInteger i = BigInteger.valueOf(2); i.compareTo(m.divide(BigInteger.valueOf(2))) < 0; i = i.add(BigInteger.ONE)) {
            if(m.mod(i).equals(BigInteger.ZERO)) facts.add(i);
        }
        ++count;

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
        ++count;
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
