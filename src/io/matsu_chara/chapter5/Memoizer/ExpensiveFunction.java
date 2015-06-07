package io.matsu_chara.chapter5.Memoizer;

import java.math.BigInteger;

public class ExpensiveFunction implements Computable<String, BigInteger> {
    public BigInteger compute(String arg) {
        // very long time
        return new BigInteger(arg);
    }
}