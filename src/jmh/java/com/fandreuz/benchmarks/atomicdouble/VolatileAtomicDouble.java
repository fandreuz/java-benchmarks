package com.fandreuz.benchmarks.atomicdouble;

public class VolatileAtomicDouble implements AtomicDouble {
    private volatile boolean set = false;
    private volatile double current = 0;

    @Override
    public void set(double value) {
        current = value;
        set = true;
    }

    @Override
    public Double getAndReset() {
        double local = current;
        if (!set) {
            return null;
        }
        set = false;
        return local;
    }
}
