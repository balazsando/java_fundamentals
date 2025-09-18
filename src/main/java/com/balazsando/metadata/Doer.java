package com.balazsando.metadata;

public class Doer {
    @Deprecated
    public void doItThisWay() {
    }

    public void doItThisNewWay() {
    }
}


class MyWorker {
    @SuppressWarnings("deprecation")
    void doSomeWork() {
        Doer d = new Doer();
        d.doItThisWay();
    }

    @SuppressWarnings("deprecation")
    void doDoubleWork() {
        Doer d = new Doer();
        Doer d2 = new Doer();
        d.doItThisWay();
        d2.doItThisWay();
    }
}