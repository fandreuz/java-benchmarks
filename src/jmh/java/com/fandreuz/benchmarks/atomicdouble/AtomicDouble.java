package com.fandreuz.benchmarks.atomicdouble;

public interface AtomicDouble {

    void set(double value);
    Double getAndReset();

}
