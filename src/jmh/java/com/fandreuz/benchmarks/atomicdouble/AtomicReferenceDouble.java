package com.fandreuz.benchmarks.atomicdouble;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceDouble implements AtomicDouble {
    private final AtomicReference<Double> atomicReference = new AtomicReference<>();

    @Override
    public void set(double value) {
        atomicReference.set(value);
    }

    @Override
    public Double getAndReset() {
        return atomicReference.getAndSet(null);
    }
}
