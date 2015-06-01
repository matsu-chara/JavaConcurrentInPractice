package io.matsu_chara.chapter2;

import io.matsu_chara.annotation.NotThreadSafe;

@NotThreadSafe
public class LazyInitRace {
    private ExpensiveObject instance = null;
    public ExpensiveObject getInstance() {
        if(instance == null) {
            instance = new ExpensiveObject();
        }
        return instance;
    }

    private class ExpensiveObject {
    }
}
